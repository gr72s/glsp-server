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
import com.gr72s.glsp.graph.GPreRenderedElement;
import com.gr72s.glsp.graph.GraphFactory;
import com.gr72s.glsp.graph.builder.AbstractGPreRenderedElementBuilder;

public class GPreRenderedElementBuilder
   extends AbstractGPreRenderedElementBuilder<GPreRenderedElement, GPreRenderedElementBuilder> {

   public GPreRenderedElementBuilder() {
      this(DefaultTypes.PRE_RENDERED);
   }

   public GPreRenderedElementBuilder(String type) {
      super(type);
   }

   @Override
   protected GPreRenderedElement instantiate() {
      return GraphFactory.eINSTANCE.createGPreRenderedElement();
   }

   @Override
   protected GPreRenderedElementBuilder self() {
      return this;
   }

}
