/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
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
package com.gr72s.glsp.server.gmodel;

import java.util.ArrayList;
import java.util.List;

import com.gr72s.glsp.server.actions.ActionDispatcher;
import com.gr72s.glsp.server.operations.CreateEdgeOperation;
import com.gr72s.glsp.server.operations.CreateOperation;
import com.gr72s.glsp.server.operations.CreateOperationHandler;
import com.gr72s.glsp.server.operations.GModelOperationHandler;

import com.google.inject.Inject;

/**
 * Abstract base class for applying an {@link CreateEdgeOperation} directly to the GModel.
 */
public abstract class GModelCreateOperationHandler<T extends CreateOperation>
   extends GModelOperationHandler<T> implements CreateOperationHandler<T> {

   @Inject
   protected ActionDispatcher actionDispatcher;

   protected List<String> handledElementTypeIds;

   public GModelCreateOperationHandler(final String... elementTypeIds) {
      this(new ArrayList<>(List.of(elementTypeIds)));
   }

   public GModelCreateOperationHandler(final List<String> handledElementTypeIds) {
      this.handledElementTypeIds = handledElementTypeIds;
   }

   @Override
   public List<String> getHandledElementTypeIds() { return handledElementTypeIds; }

   public void setHandledElementTypeIds(final List<String> handledElementTypeIds) {
      this.handledElementTypeIds = handledElementTypeIds;
   }

}
