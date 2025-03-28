/********************************************************************************
 * Copyright (c) 2019-2023 EclipseSource and others.
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

import static com.gr72s.glsp.server.types.GLSPServerException.getOrThrow;
import static com.gr72s.glsp.server.utils.GModelUtil.IS_CONNECTABLE;

import java.util.Optional;

import org.eclipse.emf.common.command.Command;
import com.gr72s.glsp.graph.GEdge;
import com.gr72s.glsp.graph.GModelElement;
import com.gr72s.glsp.graph.GModelIndex;
import com.gr72s.glsp.server.operations.GModelOperationHandler;
import com.gr72s.glsp.server.operations.ReconnectEdgeOperation;

/**
 * Applies {@link ReconnectEdgeOperation} directly to the GModel.
 */
public class GModelReconnectEdgeOperationHandler extends GModelOperationHandler<ReconnectEdgeOperation> {

   @Override
   public Optional<Command> createCommand(final ReconnectEdgeOperation operation) {
      return commandOf(() -> executeReconnect(operation));
   }

   @SuppressWarnings("checkstyle:CyclomaticComplexity")
   protected void executeReconnect(final ReconnectEdgeOperation operation) {

      if (operation.getEdgeElementId() == null || operation.getSourceElementId() == null
         || operation.getTargetElementId() == null) {
         throw new IllegalArgumentException("Incomplete reconnect connection action");
      }

      // check for existence of matching elements
      GModelIndex index = modelState.getIndex();

      GEdge edge = getOrThrow(index.findElementByClass(operation.getEdgeElementId(), GEdge.class),
         "Invalid edge: edge ID " + operation.getEdgeElementId());
      GModelElement source = getOrThrow(index.findElement(operation.getSourceElementId(), IS_CONNECTABLE),
         "Invalid source: source ID " + operation.getSourceElementId());
      GModelElement target = getOrThrow(index.findElement(operation.getTargetElementId(), IS_CONNECTABLE),
         "Invalid target: target ID: " + operation.getTargetElementId());

      // reconnect
      edge.setSourceId(source.getId());
      edge.setTargetId(target.getId());
      edge.getRoutingPoints().clear();
   }
}
