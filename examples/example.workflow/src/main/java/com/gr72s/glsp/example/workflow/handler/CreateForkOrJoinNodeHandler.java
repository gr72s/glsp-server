/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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

import java.util.Optional;

import com.gr72s.glsp.example.workflow.utils.WorkflowBuilder.ActivityNodeBuilder;
import com.gr72s.glsp.graph.GPoint;
import com.gr72s.glsp.server.model.GModelState;

public abstract class CreateForkOrJoinNodeHandler extends CreateActivityNodeHandler {

   public CreateForkOrJoinNodeHandler(final String elementTypeId, final String label) {
      super(elementTypeId, label);
   }

   @Override
   protected ActivityNodeBuilder builder(final Optional<GPoint> point, final GModelState modelState) {
      return super.builder(point, modelState)
         .addCssClass("forkOrJoin")
         .size(10d, 50d);
   }
}
