/********************************************************************************
 * Copyright (c) 2020-2024 EclipseSource and others.
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
package com.gr72s.glsp.example.workflow.handler;

import java.util.ArrayList;
import java.util.List;

import com.gr72s.glsp.example.workflow.action.LogAction;
import com.gr72s.glsp.server.actions.Action;
import com.gr72s.glsp.server.features.contextactions.RequestContextActions;
import com.gr72s.glsp.server.features.contextactions.RequestContextActionsHandler;
import com.gr72s.glsp.server.features.contextactions.SetContextActions;
import com.gr72s.glsp.server.types.Severity;

public class WorkflowRequestContextActionsHandler extends RequestContextActionsHandler {
   @Override
   public List<Action> executeAction(final RequestContextActions action) {
      List<Action> actions = new ArrayList<>(super.executeAction(action));
      actions.stream()
         .filter(SetContextActions.class::isInstance)
         .map(SetContextActions.class::cast)
         .findAny()
         .ifPresent(response -> actions.add(createLogAction(action, response)));
      return actions;
   }

   protected LogAction createLogAction(final RequestContextActions request, final SetContextActions response) {
      return new LogAction(Severity.OK,
         "create " + response.getActions().size() + " server actions for " + request.getContextId() + ".");
   }
}
