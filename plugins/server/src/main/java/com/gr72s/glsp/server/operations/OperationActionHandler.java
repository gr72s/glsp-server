/********************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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
package com.gr72s.glsp.server.operations;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import com.gr72s.glsp.server.actions.AbstractActionHandler;
import com.gr72s.glsp.server.actions.Action;
import com.gr72s.glsp.server.actions.SetDirtyStateAction;
import com.gr72s.glsp.server.features.core.model.ModelSubmissionHandler;
import com.gr72s.glsp.server.model.GModelState;
import com.gr72s.glsp.server.utils.MessageActionUtil;

import com.google.inject.Inject;

public class OperationActionHandler extends AbstractActionHandler<Operation> {

   @Inject
   protected OperationHandlerRegistry operationHandlerRegistry;

   @Inject
   protected ModelSubmissionHandler modelSubmissionHandler;

   @Inject
   protected GModelState modelState;

   @Override
   public boolean handles(final Action action) {
      return action instanceof Operation;
   }

   @Override
   public List<Action> executeAction(final Operation operation) {
      if (modelState.isReadonly()) {
         return listOf(MessageActionUtil
            .warn("Server is in readonly-mode! Could not execute operation: " + operation.getKind()));
      }
      return executeOperation(operation);
   }

   protected List<Action> executeOperation(final Operation operation) {
      return operationHandlerRegistry.getOperationHandler(operation)
         .map(handler -> executeHandler(operation, handler))
         .orElseGet(this::none);
   }

   protected List<Action> executeHandler(final Operation operation, final OperationHandler<?> handler) {
      Optional<Command> command = handler.execute(operation);
      if (command.isPresent()) {
         exexcuteCommand(command.get());
         return submitModel();
      }
      return none();
   }

   protected void exexcuteCommand(final Command command) {
      modelState.execute(command);
   }

   protected List<Action> submitModel() {
      return modelSubmissionHandler.submitModel(SetDirtyStateAction.Reason.OPERATION);
   }

   /**
    * Use {@link OperationHandlerRegistry#getOperationHandler(Operation) instead}.
    */
   @Deprecated
   public static Optional<? extends OperationHandler<?>> getOperationHandler(final Operation operation,
      final OperationHandlerRegistry registry) {
      return registry.getOperationHandler(operation);
   }
}
