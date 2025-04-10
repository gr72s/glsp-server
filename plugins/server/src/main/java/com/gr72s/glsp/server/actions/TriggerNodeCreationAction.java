/********************************************************************************
 * Copyright (c) 2020-2022 EclipseSource and others.
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
package com.gr72s.glsp.server.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * Instructs the client to trigger the node creation tool for a specified edge element type.
 */
public class TriggerNodeCreationAction extends TriggerElementCreationAction {

   public static final String KIND = "triggerNodeCreation";

   private GhostElement ghostElement;

   public TriggerNodeCreationAction() {
      this(null);
   }

   public TriggerNodeCreationAction(final String elementTypeId) {
      this(elementTypeId, new HashMap<>());
   }

   public TriggerNodeCreationAction(final String elementTypeId, final GhostElement ghostElement) {
      this(elementTypeId, new HashMap<>(), ghostElement);
   }

   public TriggerNodeCreationAction(final String elementTypeId,
      final Map<String, String> args) {
      super(KIND, elementTypeId, args);
   }

   public TriggerNodeCreationAction(final String elementTypeId,
      final Map<String, String> args, final GhostElement ghostElement) {
      super(KIND, elementTypeId, args);
      this.ghostElement = ghostElement;
   }

   public GhostElement getGhostElement() { return ghostElement; }

   public void setGhostElement(final GhostElement ghostElement) { this.ghostElement = ghostElement; }

}
