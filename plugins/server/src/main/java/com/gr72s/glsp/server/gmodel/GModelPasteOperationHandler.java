/********************************************************************************
 * Copyright (c) 2020-2023 EclipseSource and others.
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

import static com.gr72s.glsp.server.utils.GModelUtil.filterByType;
import static com.gr72s.glsp.server.utils.GeometryUtil.shift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import com.gr72s.glsp.graph.GBoundsAware;
import com.gr72s.glsp.graph.GEdge;
import com.gr72s.glsp.graph.GModelElement;
import com.gr72s.glsp.graph.GPoint;
import com.gr72s.glsp.graph.impl.GPointImpl;
import com.gr72s.glsp.server.gson.GraphGsonConfigurationFactory;
import com.gr72s.glsp.server.operations.GModelOperationHandler;
import com.gr72s.glsp.server.operations.PasteOperation;
import com.gr72s.glsp.server.types.EditorContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;

/**
 * Applies {@link PasteOperation} from the clipboard directly to the GModel.
 *
 * @see GModelRequestClipboardDataActionHandler
 */
public class GModelPasteOperationHandler extends GModelOperationHandler<PasteOperation> {

   protected static final int DEFAULT_OFFSET = 20;

   protected final Gson gson;

   @Inject
   public GModelPasteOperationHandler(final GraphGsonConfigurationFactory gsonConfigurator) {
      GsonBuilder builder = gsonConfigurator.configureGson();
      gson = builder.create();
   }

   @Override
   public Optional<Command> createCommand(final PasteOperation operation) {
      List<GModelElement> elements = getCopiedElements(operation.getClipboardData().get("application/json"));
      return elements.isEmpty()
         ? doNothing()
         : commandOf(() -> executePaste(elements, operation.getEditorContext()));
   }

   public void executePaste(final List<GModelElement> elements, final EditorContext context) {
      shift(elements, computeOffset(elements, context.getLastMousePosition()));

      Map<String, String> idMap = reassignIds(elements);
      filterElements(elements, idMap);
      rewireEdges(elements, idMap);

      modelState.getRoot().getChildren().addAll(elements);
   }

   protected List<GModelElement> getCopiedElements(final String jsonString) {
      GModelElement[] elements = gson.fromJson(jsonString, GModelElement[].class);
      return elements != null ? new ArrayList<>(Arrays.asList(elements)) : Collections.emptyList();
   }

   protected GPoint computeOffset(final List<GModelElement> elements, final Optional<GPoint> lastMousePosition) {
      GPoint offset = new GPointImpl();
      offset.setX(DEFAULT_OFFSET);
      offset.setY(DEFAULT_OFFSET);
      if (lastMousePosition.isPresent()) {
         Optional<GBoundsAware> referenceElement = getReferenceElementForPasteOffset(elements);
         if (referenceElement.isPresent()) {
            offset.setX(lastMousePosition.get().getX() - referenceElement.get().getPosition().getX());
            offset.setY(lastMousePosition.get().getY() - referenceElement.get().getPosition().getY());
         }
      }
      return offset;
   }

   protected Optional<GBoundsAware> getReferenceElementForPasteOffset(final List<GModelElement> elements) {
      double minY = Double.MAX_VALUE;
      // use top most element as a reference for the offset by default
      for (GModelElement element : elements) {
         if (element instanceof GBoundsAware) {
            GBoundsAware boundsAware = (GBoundsAware) element;
            if (minY > boundsAware.getPosition().getY()) {
               minY = boundsAware.getPosition().getY();
               return Optional.of(boundsAware);
            }
         }
      }
      return Optional.empty();
   }

   protected Map<String, String> reassignIds(final List<GModelElement> elements) {
      Map<String, String> idMap = new HashMap<>();
      for (GModelElement element : elements) {
         String newId = UUID.randomUUID().toString();
         idMap.put(element.getId(), newId);
         element.setId(newId);
         idMap.putAll(reassignIds(element.getChildren()));
      }
      return idMap;
   }

   protected void filterElements(final List<GModelElement> elements,
      final Map<String, String> idMap) {
      List<GModelElement> toFilter = elements.stream().filter(e -> this.shouldExcludeElement(e, idMap))
         .collect(Collectors.toList());
      elements.removeAll(toFilter);
   }

   protected boolean shouldExcludeElement(final GModelElement element, final Map<String, String> idMap) {
      return false;
   }

   protected void rewireEdges(final List<GModelElement> elements, final Map<String, String> idMap) {
      filterByType(elements, GEdge.class).forEach(edge -> {
         if (idMap.containsKey(edge.getSourceId())) {
            edge.setSourceId(idMap.get(edge.getSourceId()));
         }
         if (idMap.containsKey(edge.getTargetId())) {
            edge.setTargetId(idMap.get(edge.getTargetId()));
         }
      });
   }

}
