/********************************************************************************
 * Copyright (c) 2019-2025 EclipseSource and others.
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

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.command.Command;
import com.gr72s.glsp.graph.GBoundsAware;
import com.gr72s.glsp.graph.GDimension;
import com.gr72s.glsp.graph.GModelElement;
import com.gr72s.glsp.graph.GModelIndex;
import com.gr72s.glsp.graph.GModelRoot;
import com.gr72s.glsp.graph.GNode;
import com.gr72s.glsp.graph.GPoint;
import com.gr72s.glsp.graph.builder.impl.GLayoutOptions;
import com.gr72s.glsp.graph.util.GraphUtil;
import com.gr72s.glsp.server.operations.ChangeBoundsOperation;
import com.gr72s.glsp.server.operations.GModelOperationHandler;
import com.gr72s.glsp.server.types.ElementAndBounds;

/**
 * Applies {@link ChangeBoundsOperation} directly to the GModel.
 */
public class GModelChangeBoundsOperationHandler extends GModelOperationHandler<ChangeBoundsOperation> {

   protected static Logger LOGGER = LogManager.getLogger(GModelChangeBoundsOperationHandler.class);

   @Override
   public Optional<Command> createCommand(final ChangeBoundsOperation operation) {
      List<ElementAndBounds> changedElementAndBounds = operation.getNewBounds().stream().filter(this::hasChanged)
         .toList();
      return changedElementAndBounds.isEmpty()
         ? doNothing()
         : commandOf(() -> executeChangeBounds(new ChangeBoundsOperation(changedElementAndBounds)));
   }

   protected boolean hasChanged(final ElementAndBounds elementAndBounds) {
      Optional<GModelElement> element = this.modelState.getIndex().get(elementAndBounds.getElementId());
      if (element.isEmpty() || !(element.get() instanceof GBoundsAware boundsAware)) {
         return true;
      }
      return !GraphUtil.equals(boundsAware.getSize(), elementAndBounds.getNewSize()) ||
         !GraphUtil.equals(boundsAware.getPosition(), elementAndBounds.getNewPosition());
   }

   protected void executeChangeBounds(final ChangeBoundsOperation operation) {
      for (ElementAndBounds element : operation.getNewBounds()) {
         changeElementBounds(element.getElementId(), element.getNewPosition(), element.getNewSize());
      }
   }

   protected void changeElementBounds(final String elementId, final GPoint newPosition, final GDimension newSize) {
      if (elementId == null) {
         LOGGER.warn("Invalid ChangeBounds Action; missing mandatory arguments");
         return;
      }

      GModelIndex index = modelState.getIndex();
      GNode nodeToUpdate = getOrThrow(index.findElementByClass(elementId, GNode.class),
         "GNode with id " + elementId + " not found");

      GModelElement parent = nodeToUpdate.getParent();
      GPoint positionToSet = parent instanceof GModelRoot
         // For root nodes (Owned by the model root), allow negative coordinates
         ? newPosition
         // For child nodes (Owned by another node or compartment), restrict the movement
         // to positive coordinates, to avoid weird layout behavior
         : GraphUtil.point(Math.max(0, newPosition.getX()), Math.max(0, newPosition.getY()));
      if (nodeToUpdate.getLayout() != null) {
         nodeToUpdate.getLayoutOptions().put(GLayoutOptions.KEY_PREF_WIDTH, newSize.getWidth());
         nodeToUpdate.getLayoutOptions().put(GLayoutOptions.KEY_PREF_HEIGHT, newSize.getHeight());
      }

      nodeToUpdate.setSize(newSize);
      nodeToUpdate.setPosition(positionToSet);
   }
}
