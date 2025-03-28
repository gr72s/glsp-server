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
package com.gr72s.glsp.graph.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.gr72s.glsp.graph.GModelElement;

public abstract class GModelElementBuilder<T extends GModelElement, E extends GModelElementBuilder<T, E>>
   extends GArgumentableBuilder<T, E> {

   protected String type;
   protected String id;
   protected String trace;
   protected List<String> cssClasses = new ArrayList<>();
   protected List<GModelElement> children = new ArrayList<>();

   public GModelElementBuilder(final String type) {
      this.type = type;
   }

   public E id(final String id) {
      this.id = id;
      return self();
   }

   public E type(final String type) {
      this.type = type;
      return self();
   }

   public E trace(final String trace) {
      this.trace = trace;
      return self();
   }

   public E addCssClass(final String cssClass) {
      this.cssClasses.add(cssClass);
      return self();
   }

   public E addCssClasses(final List<String> cssClasses) {
      this.cssClasses.addAll(cssClasses);
      return self();
   }

   public E add(final GModelElement child) {
      this.children.add(child);
      return self();
   }

   public E addAll(final List<? extends GModelElement> children) {
      this.children.addAll(children);
      return self();
   }

   @Override
   protected abstract T instantiate();

   protected T generateId(final T element) {
      element.setId(UUID.randomUUID().toString());
      return element;
   }

   @Override
   protected void setProperties(final T element) {
      super.setProperties(element);
      if (id == null) {
         generateId(element);
      } else {
         element.setId(id);
      }

      element.setType(type);
      element.setTrace(trace);
      if (cssClasses != null) {
         element.getCssClasses().addAll(cssClasses);
      }
      if (children != null) {
         element.getChildren().addAll(children);
      }
   }

   @Override
   public T build() {
      final T element = instantiate();
      setProperties(element);
      return element;
   }
}
