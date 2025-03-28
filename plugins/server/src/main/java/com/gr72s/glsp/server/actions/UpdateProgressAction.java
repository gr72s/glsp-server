/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
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
package com.gr72s.glsp.server.actions;

import java.util.Optional;

/**
 * Sent by the server to the client to presenting an update of the progress of a long running process in the UI.
 */
public class UpdateProgressAction extends Action {
   public static final String KIND = "updateProgress";
   private static final int NO_PERCENTAGE = -1;

   private String progressId;

   private String message;
   private int percentage;

   public UpdateProgressAction() {
      super(KIND);
   }

   public UpdateProgressAction(final String progressId) {
      this();
      this.progressId = progressId;
      this.percentage = NO_PERCENTAGE;
   }

   public UpdateProgressAction(final String progressId, final String message) {
      this(progressId);
      this.message = message;
      this.percentage = NO_PERCENTAGE;
   }

   public UpdateProgressAction(final String progressId, final String message, final int percentage) {
      this(progressId, message);
      this.percentage = percentage;
   }

   public void setProgressId(final String progressId) { this.progressId = progressId; }

   public String getProgressId() { return progressId; }

   public void setMessage(final String message) { this.message = message; }

   public Optional<String> getMessage() { return Optional.ofNullable(message); }

   public void setPercentage(final int percentage) { this.percentage = percentage; }

   public Optional<Integer> getPercentage() {
      if (percentage == NO_PERCENTAGE) {
         return Optional.empty();
      }
      return Optional.of(percentage);
   }

}
