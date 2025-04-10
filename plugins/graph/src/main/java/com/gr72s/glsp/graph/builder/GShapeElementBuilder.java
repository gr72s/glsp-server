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

import java.util.List;

import com.gr72s.glsp.graph.GDimension;
import com.gr72s.glsp.graph.GPoint;
import com.gr72s.glsp.graph.GShapeElement;
import com.gr72s.glsp.graph.util.GraphUtil;

public abstract class GShapeElementBuilder<T extends GShapeElement, E extends GShapeElementBuilder<T, E>>
   extends GModelElementBuilder<T, E> {

   protected GDimension size;
   protected GPoint position;
   protected List<String> resizeLocations;

   public GShapeElementBuilder(String type) {
      super(type);
   }

   public E size(GDimension size) {
      this.size = size;
      return self();
   }

   public E size(double width, double height) {
      return size(GraphUtil.dimension(width, height));
   }

   public E position(GPoint position) {
      this.position = position;
      return self();
   }

   public E position(double x, double y) {
      return position(GraphUtil.point(x, y));
   }

   public E resizeLocations(String... locations) {
      this.resizeLocations = List.of(locations);
      return self();
   }

   @Override
   protected void setProperties(T element) {
      super.setProperties(element);
      element.setSize(size);
      element.setPosition(position);
      if (this.resizeLocations != null) {
         element.getResizeLocations().addAll(this.resizeLocations);
      }
   }

}
