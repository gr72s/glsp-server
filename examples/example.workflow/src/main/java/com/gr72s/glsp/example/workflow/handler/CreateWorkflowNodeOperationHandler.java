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
package com.gr72s.glsp.example.workflow.handler;

import java.util.HashMap;
import java.util.Optional;

import com.gr72s.glsp.example.workflow.utils.ModelTypes;
import com.gr72s.glsp.example.workflow.wfgraph.Category;
import com.gr72s.glsp.graph.GCompartment;
import com.gr72s.glsp.graph.GModelElement;
import com.gr72s.glsp.graph.GPoint;
import com.gr72s.glsp.server.actions.GhostElement;
import com.gr72s.glsp.server.gmodel.GModelCreateNodeOperationHandler;
import com.gr72s.glsp.server.operations.CreateNodeOperation;

public abstract class CreateWorkflowNodeOperationHandler extends GModelCreateNodeOperationHandler {

   public CreateWorkflowNodeOperationHandler(final String elementTypeId) {
      super(elementTypeId);
   }

   @Override
   public Optional<GPoint> getLocation(final CreateNodeOperation operation) {
      return GridSnapper.snap(operation.getLocation());
   }

   @Override
   protected Optional<GModelElement> getContainer(final CreateNodeOperation operation) {
      Optional<GModelElement> container = super.getContainer(operation);
      // If the container is a Category node, find its structure compartment
      Optional<GModelElement> structCompt = container.filter(Category.class::isInstance).map(Category.class::cast)
         .flatMap(this::getCategoryCompartment);
      return structCompt.isPresent() ? structCompt : container;
   }

   protected Optional<GCompartment> getCategoryCompartment(final Category category) {
      return category.getChildren().stream().filter(GCompartment.class::isInstance).map(GCompartment.class::cast)
         .filter(comp -> ModelTypes.STRUCTURE.equals(comp.getType())).findFirst();
   }

   @Override
   protected GhostElement createTriggerGhostElement(final String elementTypeId) {
      return new GhostElement(createNode(Optional.empty(), new HashMap<>()), true);
   }
}
