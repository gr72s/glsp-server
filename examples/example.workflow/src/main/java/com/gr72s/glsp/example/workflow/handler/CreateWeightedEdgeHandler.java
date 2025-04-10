/********************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
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

import com.gr72s.glsp.example.workflow.utils.ModelTypes;
import com.gr72s.glsp.example.workflow.utils.WorkflowBuilder.WeightedEdgeBuilder;
import com.gr72s.glsp.graph.GEdge;
import com.gr72s.glsp.graph.GModelElement;
import com.gr72s.glsp.server.gmodel.GModelCreateEdgeOperationHandler;
import com.gr72s.glsp.server.model.GModelState;

public class CreateWeightedEdgeHandler extends GModelCreateEdgeOperationHandler {

   public CreateWeightedEdgeHandler() {
      super(ModelTypes.WEIGHTED_EDGE, "Weighted edge");
   }

   @Override
   protected Optional<GEdge> createEdge(final GModelElement source, final GModelElement target,
      final GModelState modelState) {
      return Optional.of(new WeightedEdgeBuilder() //
         .source(source) //
         .target(target) //
         .probability("medium") //
         .addCssClass("medium") //
         .build());
   }

}
