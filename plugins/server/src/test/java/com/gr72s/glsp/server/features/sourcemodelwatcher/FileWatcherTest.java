/********************************************************************************
 * Copyright (c) 2020-2023 EclipseSource and others.
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

import com.gr72s.glsp.server.actions.Action;
import com.gr72s.glsp.server.actions.ActionDispatcher;
import com.gr72s.glsp.server.disposable.Disposable;
import com.gr72s.glsp.server.model.DefaultGModelState;
import com.gr72s.glsp.server.model.GModelState;
import com.gr72s.glsp.server.protocol.InitializeClientSessionParameters;
import com.gr72s.glsp.server.session.ClientSession;
import com.gr72s.glsp.server.session.ClientSessionListener;
import com.gr72s.glsp.server.session.ClientSessionManager;
import com.gr72s.glsp.server.utils.ClientOptionsUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class FileWatcherTest {

    private static final String DIR = "fileWatcherTests";

    private RecordingActionDispatcher actionDispatcher;
    private ClientSessionManager sessionManager;

    @BeforeEach
    public void before()
            throws IOException {
        actionDispatcher = new RecordingActionDispatcher();
        sessionManager = new MockClientSessionManager();
        Files.createDirectory(Path.of(DIR));
    }

    @AfterEach
    public void removeDirectory()
            throws IOException {
        Files.walk(Path.of(DIR))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    void changingWatchedFileNotifiesClient()
            throws IOException, InterruptedException {
        final File file = createFile("test.txt");
        final GModelState modelState = modelState("1", fileUri(file));

        final FileWatcher fileWatcher = new FileWatcher(sessionManager, actionDispatcher, modelState);
        fileWatcher.setDebounceDelay(0);
        fileWatcher.startWatching();
        sleep();
        changeFile(file);
        sleep();
        fileWatcher.stopWatching();

        assertNotifications(1);
    }

    @Test
    void deletingWatchedFileNotifiesClient()
            throws IOException, InterruptedException {
        final File file = createFile("test.txt");
        final GModelState modelState = modelState("1", fileUri(file));

        final FileWatcher fileWatcher = new FileWatcher(sessionManager, actionDispatcher, modelState);
        fileWatcher.setDebounceDelay(0);
        fileWatcher.startWatching();
        sleep();
        deleteFile(file);
        sleep();
        fileWatcher.stopWatching();

        assertNotifications(1);
    }

    @Test
    void changingWatchedFileWhilePausedDoesntNotifyClient()
            throws IOException, InterruptedException {
        final File file = createFile("test.txt");
        final GModelState modelState = modelState("1", fileUri(file));

        final FileWatcher fileWatcher = new FileWatcher(sessionManager, actionDispatcher, modelState);
        fileWatcher.setDebounceDelay(0);
        fileWatcher.startWatching();
        sleep();
        fileWatcher.pauseWatching();
        sleep();
        changeFile(file);
        sleep();
        fileWatcher.stopWatching();

        assertNoNotification();
    }

    @Test
    void changingWatchedFileAfterPauseAndContinueNotifiesClient()
            throws IOException, InterruptedException {
        final File file = createFile("test.txt");
        final GModelState modelState = modelState("1", fileUri(file));

        final FileWatcher fileWatcher = new FileWatcher(sessionManager, actionDispatcher, modelState);
        fileWatcher.setDebounceDelay(0);
        fileWatcher.startWatching();
        sleep();
        fileWatcher.pauseWatching();
        sleep();
        fileWatcher.continueWatching();
        sleep();
        changeFile(file);
        sleep();
        fileWatcher.stopWatching();

        assertNotifications(1);
    }

    private void assertNoNotification() {
        assertEquals(0, actionDispatcher.dispatchedActions.size());
    }

    private void assertNotifications(final int expectedNumberOfNotifications) throws InterruptedException {
        assertEquals(expectedNumberOfNotifications, actionDispatcher.dispatchedActions.size());
        for (int i = 0; i < expectedNumberOfNotifications; i++) {
            assertInstanceOf(SourceModelChangedAction.class, actionDispatcher.dispatchedActions.get(i));
        }
    }

    private File createFile(final String fileName)
            throws IOException {
        final File file = Path.of(DIR).resolve(fileName).toFile();
        deleteFile(file);
        file.createNewFile();
        return file;
    }

    private void deleteFile(final File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    private String fileUri(final File file) {
        return "file://" + file.getAbsolutePath();
    }

    private void changeFile(final File file)
            throws IOException {
        Files.write(file.toPath(), "some Content".getBytes(), StandardOpenOption.APPEND);
    }

    private GModelState modelState(final String clientId, final String sourceUri) {
        final DefaultGModelState modelState = new DefaultGModelState();
        modelState.setClientId(clientId);
        Map<String, String> options = new HashMap<>();
        options.put(ClientOptionsUtil.SOURCE_URI, sourceUri);
        modelState.setClientOptions(options);
        return modelState;
    }

    private void sleep()
            throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(300);
    }

    @SuppressWarnings("checkstyle:VisibilityModifier")
    class RecordingActionDispatcher extends Disposable implements ActionDispatcher {
        List<Action> dispatchedActions = new ArrayList<>();

        @Override
        public CompletableFuture<Void> dispatch(final Action action) {
            dispatchedActions.add(action);
            return CompletableFuture.completedFuture(null);
        }

        @Override
        public void dispatchAfterNextUpdate(final Action... actions) {
        }
    }

    class MockClientSessionManager implements ClientSessionManager {

        @Override
        public void dispose() {
        }

        @Override
        public boolean isDisposed() {
            return false;
        }

        @Override
        public ClientSession getOrCreateClientSession(final InitializeClientSessionParameters params) {
            return null;
        }

        @Override
        public Optional<ClientSession> getSession(final String clientSessionId) {
            return Optional.empty();
        }

        @Override
        public List<ClientSession> getSessionsByType(final String diagramType) {
            return Collections.emptyList();
        }

        @Override
        public boolean disposeClientSession(final String clientSessionId) {
            return false;
        }

        @Override
        public boolean addListener(final ClientSessionListener listener, final String... clientSessionIds) {
            return false;
        }

        @Override
        public boolean removeListener(final ClientSessionListener listener) {
            return false;
        }

        @Override
        public void removeListeners(final String... clientSessionIds) {
        }

    }

}
