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
package com.gr72s.glsp.example.workflow.provider;

import static com.gr72s.glsp.graph.DefaultTypes.EDGE;
import static com.gr72s.glsp.graph.util.GraphUtil.point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.gr72s.glsp.example.workflow.handler.GridSnapper;
import com.gr72s.glsp.example.workflow.utils.ModelTypes;
import com.gr72s.glsp.example.workflow.wfgraph.TaskNode;
import com.gr72s.glsp.graph.GModelElement;
import com.gr72s.glsp.graph.GModelIndex;
import com.gr72s.glsp.graph.GNode;
import com.gr72s.glsp.graph.GPoint;
import com.gr72s.glsp.server.features.commandpalette.CommandPaletteActionProvider;
import com.gr72s.glsp.server.features.directediting.LabeledAction;
import com.gr72s.glsp.server.model.GModelState;
import com.gr72s.glsp.server.operations.CreateEdgeOperation;
import com.gr72s.glsp.server.operations.CreateNodeOperation;
import com.gr72s.glsp.server.operations.DeleteOperation;
import com.gr72s.glsp.server.types.EditorContext;

import com.google.inject.Inject;

public class WorkflowCommandPaletteActionProvider implements CommandPaletteActionProvider {

   @Inject
   protected GModelState modelState;

   @Override
   @SuppressWarnings("checkstyle:CyclomaticComplexity")
   public List<LabeledAction> getActions(final EditorContext editorContext) {
      List<LabeledAction> actions = new ArrayList<>();
      if (modelState.isReadonly()) {
         return actions;
      }
      GModelIndex index = modelState.getIndex();
      List<String> selectedIds = editorContext.getSelectedElementIds();
      Optional<GPoint> lastMousePosition = GridSnapper.snap(editorContext.getLastMousePosition());
      Set<GModelElement> selectedElements = index.getAll(selectedIds);

      // Create node actions are always possible
      actions.addAll(new HashSet<>(Set.of(
         new LabeledAction("Create Automated Task", new ArrayList<>(List.of(
            new CreateNodeOperation(ModelTypes.AUTOMATED_TASK,
               lastMousePosition.orElse(point(0, 0)), "fa-plus-square")))),
         new LabeledAction("Create Manual Task",
            new ArrayList<>(
               List.of(new CreateNodeOperation(ModelTypes.MANUAL_TASK, lastMousePosition.orElse(point(0, 0)),
                  "fa-plus-square")))),
         new LabeledAction("Create Merge Node",
            new ArrayList<>(
               List.of(new CreateNodeOperation(ModelTypes.MERGE_NODE, lastMousePosition.orElse(point(0, 0)),
                  "fa-plus-square")))),
         new LabeledAction("Create Decision Node", new ArrayList<>(List.of(new CreateNodeOperation(
            ModelTypes.DECISION_NODE, lastMousePosition.orElse(point(0, 0)), "fa-plus-square")))),
         new LabeledAction("Create Category", new ArrayList<>(List.of(new CreateNodeOperation(
            ModelTypes.CATEGORY, lastMousePosition.orElse(point(0, 0)), "fa-plus-square")))))));

      // Create edge actions between two nodes
      if (selectedElements.size() == 1) {
         GModelElement element = selectedElements.iterator().next();
         if (element instanceof GNode) {
            actions.addAll(createEdgeActions((GNode) element, index.getAllByClass(TaskNode.class)));
         }
      } else if (selectedElements.size() == 2) {
         Iterator<GModelElement> iterator = selectedElements.iterator();
         GModelElement firstElement = iterator.next();
         GModelElement secondElement = iterator.next();
         if (firstElement instanceof TaskNode && secondElement instanceof TaskNode) {
            GNode firstNode = (GNode) firstElement;
            GNode secondNode = (GNode) secondElement;
            actions.add(createEdgeAction("Connect with Edge", firstNode, secondNode));
            actions.add(createWeightedEdgeAction("Connect with Weighted Edge", firstNode, secondNode));
         }
      }

      // Delete action
      if (selectedElements.size() == 1) {
         actions
            .add(new LabeledAction("Delete", new ArrayList<>(List.of(new DeleteOperation(selectedIds))),
               "fa-minus-square"));
      } else if (selectedElements.size() > 1) {
         actions.add(
            new LabeledAction("Delete All", new ArrayList<>(List.of(new DeleteOperation(selectedIds))),
               "fa-minus-square"));
      }

      return actions;
   }

   private Set<LabeledAction> createEdgeActions(final GNode source, final Set<? extends GNode> targets) {
      Set<LabeledAction> actions = new LinkedHashSet<>();
      // add first all edge, then all weighted edge actions to keep a nice order
      targets.forEach(node -> actions.add(createEdgeAction("Create Edge to " + getLabel(node), source, node)));
      targets.forEach(node -> actions
         .add(createWeightedEdgeAction("Create Weighted Edge to " + getLabel(node), source, node)));
      return actions;
   }

   private LabeledAction createWeightedEdgeAction(final String label, final GNode source, final GNode node) {
      return new LabeledAction(label, new ArrayList<>(List.of(
         new CreateEdgeOperation(ModelTypes.WEIGHTED_EDGE, source.getId(), node.getId()))), "fa-plus-square");
   }

   private LabeledAction createEdgeAction(final String label, final GNode source, final GNode node) {
      return new LabeledAction(label, new ArrayList<>(List.of(
         new CreateEdgeOperation(EDGE, source.getId(), node.getId()))), "fa-plus-square");
   }

   private String getLabel(final GNode node) {
      if (node instanceof TaskNode) {
         return ((TaskNode) node).getName();
      }
      return node.getId();
   }

}
