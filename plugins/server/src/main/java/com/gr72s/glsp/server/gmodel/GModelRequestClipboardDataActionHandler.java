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
package com.gr72s.glsp.server.gmodel;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.gr72s.glsp.graph.GModelElement;
import com.gr72s.glsp.graph.GModelIndex;
import com.gr72s.glsp.server.actions.AbstractActionHandler;
import com.gr72s.glsp.server.actions.Action;
import com.gr72s.glsp.server.features.clipboard.RequestClipboardDataAction;
import com.gr72s.glsp.server.features.clipboard.SetClipboardDataAction;
import com.gr72s.glsp.server.gson.GraphGsonConfigurationFactory;
import com.gr72s.glsp.server.model.GModelState;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.inject.Inject;

/**
 * Returns the serialized JSON data of the selected GModel elements as a {@link SetClipboardDataAction}.
 */
public class GModelRequestClipboardDataActionHandler extends AbstractActionHandler<RequestClipboardDataAction> {

   protected final Gson gson;

   @Inject
   protected GModelState modelState;

   @Inject
   public GModelRequestClipboardDataActionHandler(final GraphGsonConfigurationFactory gsonConfigurator) {
      GsonBuilder builder = gsonConfigurator.configureGson();
      gson = builder.create();
   }

   @Override
   public List<Action> executeAction(final RequestClipboardDataAction action) {
      JsonArray jsonArray = new JsonArray();
      GModelIndex index = modelState.getIndex();
      Set<GModelElement> selectedElements = index.getAll(action.getEditorContext().getSelectedElementIds());
      selectedElements.stream().map(gson::toJsonTree).forEach(jsonArray::add);
      return listOf(new SetClipboardDataAction(Collections.singletonMap("application/json", jsonArray.toString())));
   }

}
