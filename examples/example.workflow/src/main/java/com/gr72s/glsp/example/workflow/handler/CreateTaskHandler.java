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
import java.util.function.Function;

import com.gr72s.glsp.example.workflow.utils.ModelTypes;
import com.gr72s.glsp.example.workflow.utils.WorkflowBuilder.TaskNodeBuilder;
import com.gr72s.glsp.example.workflow.wfgraph.WfgraphPackage;
import com.gr72s.glsp.graph.GNode;
import com.gr72s.glsp.graph.GPoint;
import com.gr72s.glsp.graph.builder.impl.GArguments;
import com.gr72s.glsp.server.model.GModelState;
import com.gr72s.glsp.server.utils.GModelUtil;

public abstract class CreateTaskHandler extends CreateWorkflowNodeOperationHandler {

   private final Function<Integer, String> labelProvider;
   private final String elementTypeId;

   public CreateTaskHandler(final String elementTypeId, final Function<Integer, String> labelProvider) {
      super(elementTypeId);
      this.elementTypeId = elementTypeId;
      this.labelProvider = labelProvider;
   }

   protected String getElementTypeId() { return elementTypeId; }

   protected TaskNodeBuilder builder(final Optional<GPoint> point, final GModelState modelState) {
      int nodeCounter = GModelUtil.generateId(WfgraphPackage.Literals.TASK_NODE, "task", modelState);
      String name = labelProvider.apply(nodeCounter);
      String taskType = ModelTypes.toNodeType(getElementTypeId());
      return new TaskNodeBuilder(getElementTypeId(), name, taskType, 0) //
         .position(point.orElse(null))
         .addArguments(GArguments.cornerRadius(5))
         .addCssClass("task");
   }

   @Override
   protected GNode createNode(final Optional<GPoint> point, final Map<String, String> args) {
      return builder(point, modelState).build();
   }

}
