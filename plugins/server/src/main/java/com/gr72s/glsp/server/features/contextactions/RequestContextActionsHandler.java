/********************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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
package com.gr72s.glsp.server.features.contextactions;

import java.util.ArrayList;
import java.util.List;

import com.gr72s.glsp.server.actions.AbstractActionHandler;
import com.gr72s.glsp.server.actions.Action;
import com.gr72s.glsp.server.features.directediting.LabeledAction;
import com.gr72s.glsp.server.types.EditorContext;

import com.google.inject.Inject;

public class RequestContextActionsHandler extends AbstractActionHandler<RequestContextActions> {

   @Inject
   protected ContextActionsProviderRegistry contextActionsProviderRegistry;

   @Override
   public List<Action> executeAction(final RequestContextActions action) {
      EditorContext editorContext = action.getEditorContext();
      List<LabeledAction> actions = new ArrayList<>();
      contextActionsProviderRegistry.get(action.getContextId())
         .map(provider -> provider.getActions(editorContext))
         .ifPresent(labeledActions -> actions.addAll(labeledActions));

      return listOf(new SetContextActions(actions, action.getEditorContext().getArgs()));
   }
}
