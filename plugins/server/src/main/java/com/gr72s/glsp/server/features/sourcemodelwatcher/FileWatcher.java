/********************************************************************************
 * Copyright (c) 2020-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package com.gr72s.glsp.server.features.sourcemodelwatcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.gr72s.glsp.server.actions.ActionDispatcher;
import com.gr72s.glsp.server.di.ClientId;
import com.gr72s.glsp.server.disposable.IDisposable;
import com.gr72s.glsp.server.model.GModelState;
import com.gr72s.glsp.server.session.ClientSession;
import com.gr72s.glsp.server.session.ClientSessionListener;
import com.gr72s.glsp.server.session.ClientSessionManager;
import com.gr72s.glsp.server.utils.ClientOptionsUtil;
import com.gr72s.glsp.server.utils.Debouncer;

import com.google.inject.Inject;

public class FileWatcher implements ClientSessionListener, SourceModelWatcher {

   protected Debouncer<ClientNotification> clientNotificationDebouncer;

   @Inject
   protected ActionDispatcher actionDispatcher;

   @Inject
   protected GModelState modelState;

   protected int debounceDelay = 500;

   protected final List<FileWatchWorker> workers = new ArrayList<>();

   @Inject
   public FileWatcher(final ClientSessionManager sessionManager, @ClientId final String clientId) {
      sessionManager.addListener(this, clientId);
   }

   public FileWatcher(final ClientSessionManager sessionManager, final ActionDispatcher actionDispatcher) {
      this(sessionManager, "");
      this.actionDispatcher = actionDispatcher;
   }

   public FileWatcher(final ClientSessionManager sessionManager, final ActionDispatcher actionDispatcher,
      final GModelState modelState) {
      this(sessionManager, actionDispatcher);
      this.modelState = modelState;
   }

   public int getDebounceDelay() { return debounceDelay; }

   public void setDebounceDelay(final int debounceDelay) { this.debounceDelay = debounceDelay; }

   @Override
   public void sessionDisposed(final ClientSession session) {
      disposeAllWorkers();
   }

   @Override
   public void startWatching() {
      start();
   }

   @Override
   public void stopWatching() {
      disposeAllWorkers();
   }

   @Override
   public void pauseWatching() {
      workers.forEach(FileWatchWorker::pauseNotifications);
   }

   @Override
   public void continueWatching() {
      workers.forEach(FileWatchWorker::continueNotifications);
   }

   protected void start() {
      IDisposable.disposeIfExists(clientNotificationDebouncer);
      clientNotificationDebouncer = new Debouncer<>(this::notifyClient, getDebounceDelay(), TimeUnit.MILLISECONDS);
      createWorkers(modelState).forEach(FileWatchWorker::start);
   }

   protected void stop() {
      disposeAllWorkers();
      IDisposable.disposeIfExists(clientNotificationDebouncer);
   }

   protected List<Path> getPaths() {
      return ClientOptionsUtil.getSourceUriAsFile(modelState.getClientOptions()).stream()
         .map(file -> file.toPath()).collect(Collectors.toList());
   }

   protected List<FileWatchWorker> createWorkers(final GModelState modelState) {
      disposeAllWorkers();
      workers.addAll(createFileWatchWorkers(modelState));
      return workers;
   }

   protected void disposeAllWorkers() {
      workers.forEach(FileWatchWorker::stopWorking);
      workers.clear();
   }

   private List<FileWatchWorker> createFileWatchWorkers(final GModelState modelState) {
      return getPaths().stream()
         .map(path -> new FileWatchWorker(modelState.getClientId(), path))
         .collect(Collectors.toList());
   }

   protected void scheduleClientNotification(final String clientId, final Path filePath) {
      clientNotificationDebouncer.accept(new ClientNotification(clientId, filePath.getFileName().toString()));
   }

   protected void notifyClient(final ClientNotification clientNotification) {
      actionDispatcher.dispatch(new SourceModelChangedAction(clientNotification.modelSourceName));
   }

   protected class FileWatchWorker extends Thread {

      protected boolean stopped;
      protected boolean paused;

      protected final String clientId;
      protected final Path filePath;
      protected WatchKey key;

      FileWatchWorker(final String clientId, final Path filePath) {
         super();
         this.clientId = clientId;
         this.filePath = filePath;
         this.setName("File watcher: file " + filePath + " [" + clientId + "]");
      }

      @Override
      public void run() {
         try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            final Path directory = filePath.getParent();
            key = directory.register(watchService, ENTRY_MODIFY, ENTRY_DELETE);
            while (!stopped) {
               final WatchKey newKey = watchService.take();
               if (this.key != newKey) {
                  continue;
               }
               pollEventsAndNotifyClient(directory);
               if (!key.reset()) {
                  break;
               }
            }
         } catch (IOException | ClosedWatchServiceException | InterruptedException e) {
            Thread.currentThread().interrupt();
         }
      }

      protected void pollEventsAndNotifyClient(final Path directory)
         throws IOException {
         for (final WatchEvent<?> event : key.pollEvents()) {
            if (!paused && !stopped && Files.isSameFile(directory.resolve((Path) event.context()), filePath)) {
               scheduleClientNotification(clientId, filePath);
            }
         }
      }

      public void stopWorking() {
         stopped = true;
         if (key != null) {
            key.reset();
         }
         interrupt();
      }

      public void pauseNotifications() {
         paused = true;
      }

      public void continueNotifications() {
         paused = false;
      }

   }

   protected class ClientNotification {

      protected final String clientId;
      protected final String modelSourceName;

      ClientNotification(final String clientId, final String modelSourceName) {
         super();
         this.clientId = clientId;
         this.modelSourceName = modelSourceName;
      }

      @Override
      public int hashCode() {
         final int prime = 31;
         int result = 1;
         result = prime * result + getEnclosingInstance().hashCode();
         result = prime * result + Objects.hash(clientId, modelSourceName);
         return result;
      }

      @Override
      public boolean equals(final Object obj) {
         if (this == obj) {
            return true;
         }
         if (!(obj instanceof ClientNotification)) {
            return false;
         }
         ClientNotification other = (ClientNotification) obj;
         if (!getEnclosingInstance().equals(other.getEnclosingInstance())) {
            return false;
         }
         return Objects.equals(clientId, other.clientId) && Objects.equals(modelSourceName, other.modelSourceName);
      }

      protected FileWatcher getEnclosingInstance() { return FileWatcher.this; }

   }

}
