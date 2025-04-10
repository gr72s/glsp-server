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

import static com.gr72s.glsp.example.workflow.utils.ModelTypes.AUTOMATED_TASK;
import static com.gr72s.glsp.example.workflow.utils.ModelTypes.MANUAL_TASK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.gr72s.glsp.example.workflow.handler.GridSnapper;
import com.gr72s.glsp.graph.GPoint;
import com.gr72s.glsp.server.features.contextmenu.ContextMenuItemProvider;
import com.gr72s.glsp.server.features.contextmenu.MenuItem;
import com.gr72s.glsp.server.model.GModelState;
import com.gr72s.glsp.server.operations.CreateNodeOperation;

import com.google.inject.Inject;

public class WorkflowContextMenuItemProvider implements ContextMenuItemProvider {

   @Inject
   protected GModelState modelState;

   @Override
   public List<MenuItem> getItems(final List<String> selectedElementIds, final GPoint position,
      final Map<String, String> args) {
      if (modelState.isReadonly()) {
         return Collections.emptyList();
      }
      GPoint snappedPosition = GridSnapper.snap(position);
      MenuItem newAutTask = new MenuItem("newAutoTask", "Automated Task",
         Arrays.asList(new CreateNodeOperation(AUTOMATED_TASK, snappedPosition)), true);
      MenuItem newManTask = new MenuItem("newManualTask", "Manual Task",
         Arrays.asList(new CreateNodeOperation(MANUAL_TASK, snappedPosition)), true);
      MenuItem newChildMenu = new MenuItem("new", "New", Arrays.asList(newAutTask, newManTask), "add", "0_new");
      return new ArrayList<>(List.of(newChildMenu));
   }

}
