/********************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
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
package com.gr72s.glsp.server.features.undoredo;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.gr72s.glsp.server.actions.Action;
import com.gr72s.glsp.server.actions.ActionHandler;
import com.gr72s.glsp.server.actions.SetDirtyStateAction;
import com.gr72s.glsp.server.features.core.model.ModelSubmissionHandler;
import com.gr72s.glsp.server.model.GModelState;

import com.google.inject.Inject;

public class UndoRedoActionHandler implements ActionHandler {
   protected static final Logger LOGGER = LogManager.getLogger(UndoRedoActionHandler.class);

   @Inject
   protected ModelSubmissionHandler modelSubmissionHandler;

   @Inject
   protected GModelState modelState;

   @Override
   public List<Action> execute(final Action action) {
      if (action instanceof UndoAction && modelState.canUndo()) {
         modelState.undo();
         return modelSubmissionHandler.submitModel(SetDirtyStateAction.Reason.UNDO);
      } else if (action instanceof RedoAction && modelState.canRedo()) {
         modelState.redo();
         return modelSubmissionHandler.submitModel(SetDirtyStateAction.Reason.REDO);
      }
      LOGGER.warn("Cannot undo or redo");
      return none();
   }

   @Override
   public List<Class<? extends Action>> getHandledActionTypes() {
      return new ArrayList<>(List.of(UndoAction.class, RedoAction.class));
   }
}
