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
package com.gr72s.glsp.server.internal.toolpalette;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gr72s.glsp.server.actions.TriggerElementCreationAction;
import com.gr72s.glsp.server.features.toolpalette.PaletteItem;
import com.gr72s.glsp.server.features.toolpalette.ToolPaletteItemProvider;
import com.gr72s.glsp.server.operations.CreateEdgeOperation;
import com.gr72s.glsp.server.operations.CreateNodeOperation;
import com.gr72s.glsp.server.operations.CreateOperation;
import com.gr72s.glsp.server.operations.CreateOperationHandler;
import com.gr72s.glsp.server.operations.OperationHandlerRegistry;

import com.google.inject.Inject;

public class DefaultToolPaletteItemProvider implements ToolPaletteItemProvider {

   @Inject
   protected OperationHandlerRegistry operationHandlerRegistry;
   private int counter;

   @SuppressWarnings("rawtypes")
   @Override
   public List<PaletteItem> getItems(final Map<String, String> args) {
      List<CreateOperationHandler> handlers = operationHandlerRegistry.getAll().stream()
         .filter(CreateOperationHandler.class::isInstance)
         .map(CreateOperationHandler.class::cast)
         .collect(Collectors.toList());

      counter = 0;
      List<PaletteItem> nodes = createPaletteItems(handlers, CreateNodeOperation.class);
      List<PaletteItem> edges = createPaletteItems(handlers, CreateEdgeOperation.class);
      return new ArrayList<>(List.of(
         PaletteItem.createPaletteGroup("node-group", "Nodes", nodes, "symbol-property", "A"),
         PaletteItem.createPaletteGroup("edge-group", "Edges", edges, "symbol-property", "B")));

   }

   @SuppressWarnings({ "rawtypes", "unchecked" })
   protected List<PaletteItem> createPaletteItems(final List<CreateOperationHandler> handlers,
      final Class<? extends CreateOperation> operationClass) {
      return handlers.stream()
         .filter(handler -> operationClass.isAssignableFrom(handler.getHandledOperationType()))
         .flatMap(handler -> ((List<TriggerElementCreationAction>) handler.getTriggerActions())
            .stream()
            .map(action -> create(action, handler.getLabel())))
         .sorted(Comparator.comparing(PaletteItem::getLabel))
         .collect(Collectors.toList());
   }

   protected PaletteItem create(final TriggerElementCreationAction action, final String label) {
      return new PaletteItem("palette-item" + counter++, label, action);
   }
}
