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
package com.gr72s.glsp.server.internal.gson;

import static com.gr72s.glsp.server.di.ServerModule.DIAGRAM_MODULES;

import java.util.Collections;
import java.util.Map;

import com.gr72s.glsp.graph.gson.EnumTypeAdapter;
import com.gr72s.glsp.graph.gson.GraphGsonConfigurator;
import com.gr72s.glsp.server.actions.ActionRegistry;
import com.gr72s.glsp.server.diagram.ServerConfigurationContribution;
import com.gr72s.glsp.server.gson.ActionTypeAdapter;
import com.gr72s.glsp.server.gson.ServerGsonConfigurator;
import com.gr72s.glsp.server.protocol.InitializeClientSessionParameters;
import com.gr72s.glsp.server.session.ClientSession;
import com.gr72s.glsp.server.session.ClientSessionManager;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Named;

public class DefaultServerGsonConfigurator implements ServerGsonConfigurator {

   protected ActionRegistry actionRegistry;
   protected GraphGsonConfigurator graphGsonConfigurator;

   @Inject
   public DefaultServerGsonConfigurator(final Injector serverInjector,
      @Named(DIAGRAM_MODULES) final Map<String, Module> diagramModules, final ActionRegistry actionRegistry,
      final ClientSessionManager sessionManager) {
      this.actionRegistry = actionRegistry;
      this.graphGsonConfigurator = createGraphGsonConfigurator();
      configure(diagramModules, sessionManager);

   }

   protected void configure(final Map<String, Module> diagramModules, final ClientSessionManager sessionManager) {
      // Create a temporary client session for each diagramType to retrieve the diagram type specific
      // `ServerConfigurationContribution`. After the action registry and the gson configurator have been configured the
      // temporary session can be disposed
      diagramModules.keySet().forEach(diagramType -> {
         String sessionId = "TempServerConfigurationSession_" + diagramType;
         InitializeClientSessionParameters params = new InitializeClientSessionParameters(sessionId, diagramType,
            Collections.emptyList());
         ClientSession session = sessionManager.getOrCreateClientSession(params);
         ServerConfigurationContribution contribution = session.getInjector()
            .getInstance(ServerConfigurationContribution.class);
         contribution.configure(actionRegistry);
         contribution.configure(graphGsonConfigurator);

         // Configuration is done. We now can safely dispose the temporary client session.
         sessionManager.disposeClientSession(sessionId);
      });
   }

   protected TypeAdapterFactory getActionTypeAdapterFactory() {
      return new ActionTypeAdapter.Factory(actionRegistry.getAllAsMap());
   }

   protected GraphGsonConfigurator createGraphGsonConfigurator() {
      return new GraphGsonConfigurator();
   }

   @Override
   public GsonBuilder configureGsonBuilder(final GsonBuilder gsonBuilder) {
      gsonBuilder.registerTypeAdapterFactory(getActionTypeAdapterFactory());
      gsonBuilder.registerTypeAdapterFactory(new EnumTypeAdapter.Factory());
      return graphGsonConfigurator.configureGsonBuilder(gsonBuilder);
   }

}
