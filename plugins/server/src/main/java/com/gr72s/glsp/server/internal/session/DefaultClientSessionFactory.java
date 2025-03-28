/********************************************************************************
 * Copyright (c) 2021-2023 EclipseSource and others.
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
package com.gr72s.glsp.server.internal.session;

import static com.gr72s.glsp.server.types.GLSPServerException.getOrThrow;

import java.util.Map;
import java.util.Optional;

import com.gr72s.glsp.server.actions.ActionDispatcher;
import com.gr72s.glsp.server.di.ClientSessionModule;
import com.gr72s.glsp.server.di.ServerModule;
import com.gr72s.glsp.server.di.scope.DiagramGlobalScope;
import com.gr72s.glsp.server.di.scope.DiagramGlobalScopeModule;
import com.gr72s.glsp.server.protocol.InitializeClientSessionParameters;
import com.gr72s.glsp.server.session.ClientSession;
import com.gr72s.glsp.server.session.ClientSessionFactory;
import com.gr72s.glsp.server.utils.ModuleUtil;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Named;

public class DefaultClientSessionFactory implements ClientSessionFactory {

   @Inject
   protected Injector serverInjector;

   @Inject
   protected DiagramGlobalScope diagramGlobalScope;

   @Inject
   @Named(ServerModule.DIAGRAM_MODULES)
   protected Map<String, Module> diagramModules;

   @Override
   public ClientSession create(final InitializeClientSessionParameters params) {
      String diagramType = params.getDiagramType();
      String clientSessionId = params.getClientSessionId();
      Module diagramModule = getOrThrow(Optional.of(diagramModules.get(diagramType)),
         "Could not retrieve module configuration for diagram type: " + diagramType);
      Module clientIdModule = new ClientSessionModule(clientSessionId, params.getClientActionKinds());
      Module diagramScopeModule = new DiagramGlobalScopeModule(diagramGlobalScope);

      Module clientSessionModule = ModuleUtil.mixin(diagramModule, clientIdModule, diagramScopeModule);

      Injector sessionInjector = serverInjector.createChildInjector(clientSessionModule);
      return new DefaultClientSession(clientSessionId, diagramType,
         sessionInjector.getInstance(ActionDispatcher.class), sessionInjector);
   }

}
