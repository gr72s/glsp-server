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
package com.gr72s.glsp.server.operations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.gr72s.glsp.graph.GPoint;

public class CreateNodeOperation extends CreateOperation {

   public static final String KIND = "createNode";

   private GPoint location;

   private String containerId;

   public CreateNodeOperation() {
      super(KIND);
   }

   public CreateNodeOperation(final String elementTypeId) {
      this(elementTypeId, null, null, new HashMap<>());
   }

   public CreateNodeOperation(final String elementTypeId, final GPoint location) {
      this(elementTypeId, location, null, new HashMap<>());
   }

   public CreateNodeOperation(final String elementTypeId, final String containerId) {
      this(elementTypeId, null, containerId, new HashMap<>());
   }

   public CreateNodeOperation(final String elementTypeId, final GPoint location, final String containerId) {
      this(elementTypeId, location, containerId, new HashMap<>());
   }

   public CreateNodeOperation(final String elementTypeId, final GPoint location, final String containerId,
      final Map<String, String> args) {
      super(KIND, elementTypeId, args);
      this.location = location;
      this.containerId = containerId;
   }

   public Optional<GPoint> getLocation() { return Optional.ofNullable(location); }

   public void setLocation(final GPoint location) { this.location = location; }

   public String getContainerId() { return containerId; }

   public void setContainerId(final String containerId) { this.containerId = containerId; }
}
