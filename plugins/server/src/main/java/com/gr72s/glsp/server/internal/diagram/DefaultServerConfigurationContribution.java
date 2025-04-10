/********************************************************************************
 * Copyright (c) 2021-2023 EclipseSource and others.
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
package com.gr72s.glsp.server.internal.diagram;

import java.util.Set;
import java.util.stream.Stream;

import com.gr72s.glsp.graph.gson.GraphGsonConfigurator;
import com.gr72s.glsp.server.actions.Action;
import com.gr72s.glsp.server.actions.ActionHandler;
import com.gr72s.glsp.server.actions.ActionRegistry;
import com.gr72s.glsp.server.di.DiagramType;
import com.gr72s.glsp.server.diagram.ServerConfigurationContribution;
import com.gr72s.glsp.server.gson.GraphGsonConfigurationFactory;
import com.gr72s.glsp.server.internal.util.ReflectionUtil;
import com.gr72s.glsp.server.operations.Operation;
import com.gr72s.glsp.server.operations.OperationHandler;

import com.google.inject.Inject;

public class DefaultServerConfigurationContribution implements ServerConfigurationContribution {

   @Inject
   @DiagramType
   protected String diagramType;

   @Inject
   protected GraphGsonConfigurationFactory graphGsonConfigurationFactory;

   @Inject
   protected Set<ActionHandler> actionHandlers;

   @Inject
   protected Set<OperationHandler<?>> operationHandlers;

   @Override
   public void configure(final ActionRegistry registry) {
      Stream<? extends Action> handledActions = ReflectionUtil.construct(actionHandlers.stream()
         .flatMap(h -> h.getHandledActionTypes().stream()));

      Stream<? extends Operation> handledOperations = ReflectionUtil.construct(operationHandlers.stream()
         .map(OperationHandler::getHandledOperationType));

      handledActions.forEach(action -> registry.register(diagramType, action.getKind(), action.getClass()));
      handledOperations
         .forEach(operation -> registry.register(diagramType, operation.getKind(), operation.getClass()));
   }

   @Override
   public void configure(final GraphGsonConfigurator gsonConfigurator) {
      graphGsonConfigurationFactory.configure(gsonConfigurator);
   }

}
