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
package com.gr72s.glsp.server.utils;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import com.gr72s.glsp.graph.GModelElement;
import com.gr72s.glsp.graph.GNode;
import com.gr72s.glsp.graph.GPort;
import com.gr72s.glsp.server.model.GModelState;

public final class GModelUtil {
   private GModelUtil() {}

   public static Function<Integer, String> idAndIndex(final String id) {
      return i -> id + i;
   }

   public static int generateId(final EClass eClass, final String id, final GModelState modelState) {
      return modelState.getIndex().getCounter(eClass, idAndIndex(id));
   }

   public static int generateId(final GModelElement element, final String id, final GModelState modelState) {
      int index = generateId(element.eClass(), id, modelState);
      element.setId(idAndIndex(id).apply(index));
      return index;
   }

   public static <T> Stream<T> filterByType(final List<GModelElement> elements, final Class<T> clazz) {
      return elements.stream().filter(clazz::isInstance).map(clazz::cast);
   }

   @SuppressWarnings("checkstyle:VisibilityModifier")
   public static Predicate<GModelElement> IS_CONNECTABLE = modelElement -> modelElement instanceof GPort
      || modelElement instanceof GNode;
}
