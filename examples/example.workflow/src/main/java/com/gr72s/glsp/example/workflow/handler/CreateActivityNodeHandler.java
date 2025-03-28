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
package com.gr72s.glsp.example.workflow.handler;

import java.util.Map;
import java.util.Optional;

import com.gr72s.glsp.example.workflow.utils.ModelTypes;
import com.gr72s.glsp.example.workflow.utils.WorkflowBuilder.ActivityNodeBuilder;
import com.gr72s.glsp.graph.GNode;
import com.gr72s.glsp.graph.GPoint;
import com.gr72s.glsp.server.model.GModelState;

public abstract class CreateActivityNodeHandler extends CreateWorkflowNodeOperationHandler {

   private final String label;
   private final String elementTypeId;

   public CreateActivityNodeHandler(final String elementTypeId, final String label) {
      super(elementTypeId);
      this.elementTypeId = elementTypeId;
      this.label = label;
   }

   protected String getElementTypeId() { return elementTypeId; }

   protected ActivityNodeBuilder builder(final Optional<GPoint> point, final GModelState modelState) {
      String nodeType = ModelTypes.toNodeType(getElementTypeId());
      return new ActivityNodeBuilder(getElementTypeId(), nodeType) //
         .position(point.orElse(null));
   }

   @Override
   protected GNode createNode(final Optional<GPoint> point, final Map<String, String> args) {
      return builder(point, modelState).build();
   }

   @Override
   public String getLabel() { return label; }

}
