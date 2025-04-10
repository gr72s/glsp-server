/********************************************************************************
 * Copyright (c) 2022-2023 EclipseSource and others.
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
package com.gr72s.glsp.server.emf;

import java.util.List;

import com.gr72s.glsp.server.operations.CreateNodeOperation;

/**
 * A special {@link EMFOperationHandler} that is responsible for the handling of {@link CreateNodeOperation}s.
 * It provides an EMF command to create the specific node.
 *
 * @deprecated Use {@link EMFCreateOperationHandler}
 */
@Deprecated
public abstract class AbstractEMFCreateNodeOperationHandler
   extends AbstractEMFCreateOperationHandler<CreateNodeOperation> {

   public AbstractEMFCreateNodeOperationHandler(final String... elementTypeIds) {
      super(elementTypeIds);
   }

   public AbstractEMFCreateNodeOperationHandler(final List<String> handledElementTypeIds) {
      super(handledElementTypeIds);
   }

}
