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
package com.gr72s.glsp.graph.builder;

import java.util.ArrayList;
import java.util.List;

import com.gr72s.glsp.graph.GHtmlRoot;

public abstract class AbstractGHtmlRootBuilder<T extends GHtmlRoot, E extends AbstractGHtmlRootBuilder<T, E>>
   extends GModelRootBuilder<T, E> {

   protected List<String> classes = new ArrayList<>();

   public AbstractGHtmlRootBuilder(final String type) {
      super(type);
   }

   public E addClass(final String clazz) {
      this.classes.add(clazz);
      return self();
   }

   public E addClasses(final List<String> classes) {
      this.classes.addAll(classes);
      return self();
   }

   @Override
   protected void setProperties(final T element) {
      super.setProperties(element);
      element.getClasses().addAll(classes);
   }

}
