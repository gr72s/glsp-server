/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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
package com.gr72s.glsp.graph.builder.impl;

import com.gr72s.glsp.graph.DefaultTypes;
import com.gr72s.glsp.graph.GIssueMarker;
import com.gr72s.glsp.graph.GraphFactory;
import com.gr72s.glsp.graph.builder.AbstractGIssueMarkerBuilder;

public class GIssueMarkerBuilder extends AbstractGIssueMarkerBuilder<GIssueMarker, GIssueMarkerBuilder> {

   public GIssueMarkerBuilder() {
      this(DefaultTypes.ISSUE_MARKER);
   }

   public GIssueMarkerBuilder(String type) {
      super(type);
   }

   @Override
   protected GIssueMarker instantiate() {
      return GraphFactory.eINSTANCE.createGIssueMarker();
   }

   @Override
   protected GIssueMarkerBuilder self() {
      return this;
   }

}
