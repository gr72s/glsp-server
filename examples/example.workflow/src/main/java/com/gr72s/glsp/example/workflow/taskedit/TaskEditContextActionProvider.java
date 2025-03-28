/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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
package com.gr72s.glsp.example.workflow.taskedit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.gr72s.glsp.example.workflow.wfgraph.TaskNode;
import com.gr72s.glsp.server.features.contextactions.ContextActionsProvider;
import com.gr72s.glsp.server.features.contextactions.SetAutoCompleteValueAction;
import com.gr72s.glsp.server.features.directediting.LabeledAction;
import com.gr72s.glsp.server.model.GModelState;
import com.gr72s.glsp.server.types.EditorContext;

import com.google.inject.Inject;

public class TaskEditContextActionProvider implements ContextActionsProvider {

   public static final String DURATION_PREFIX = "duration:";
   public static final String TYPE_PREFIX = "type:";
   public static final String TASK_PREFIX = "task:";

   @Override
   public String getContextId() { return "task-editor"; }

   @Inject
   protected GModelState modelState;

   @Override
   public List<? extends LabeledAction> getActions(final EditorContext editorContext) {
      String text = editorContext.getArgs().getOrDefault("text", "");
      Optional<TaskNode> taskNode = modelState.getIndex()
         .findElementByClass(editorContext.getSelectedElementIds().get(0), TaskNode.class);
      if (taskNode.isEmpty()) {
         return Collections.emptyList();
      }
      if (text.startsWith(TYPE_PREFIX)) {
         String id = taskNode.get().getId();
         return new ArrayList<>(List.of(
            new LabeledAction("type:automated",
               new ArrayList<>(List.of(new EditTaskOperation(id, "taskType", "automated")))),
            new LabeledAction("type:manual",
               new ArrayList<>(List.of(new EditTaskOperation(id, "taskType", "manual"))))));
      }
      if (text.startsWith(DURATION_PREFIX)) {
         return Collections.emptyList();
      }
      String taskType = taskNode.get().getType().substring(TASK_PREFIX.length());
      int duration = taskNode.get().getDuration();
      return new ArrayList<>(List.of(
         new SetAutoCompleteValueAction("type:", "", TYPE_PREFIX + taskType),
         new SetAutoCompleteValueAction("duration:", "", DURATION_PREFIX + duration)));
   }

}
