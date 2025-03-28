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
package com.gr72s.glsp.server.di;

import java.util.Optional;

import com.gr72s.glsp.graph.GraphExtension;
import com.gr72s.glsp.server.actions.Action;
import com.gr72s.glsp.server.actions.ActionDispatcher;
import com.gr72s.glsp.server.actions.ActionHandler;
import com.gr72s.glsp.server.actions.ActionHandlerRegistry;
import com.gr72s.glsp.server.actions.SaveModelActionHandler;
import com.gr72s.glsp.server.actions.SetEditModeActionHandler;
import com.gr72s.glsp.server.di.scope.DiagramGlobalScope;
import com.gr72s.glsp.server.di.scope.DiagramGlobalSingleton;
import com.gr72s.glsp.server.diagram.DiagramConfiguration;
import com.gr72s.glsp.server.diagram.ServerConfigurationContribution;
import com.gr72s.glsp.server.features.commandpalette.CommandPaletteActionProvider;
import com.gr72s.glsp.server.features.contextactions.ContextActionsProvider;
import com.gr72s.glsp.server.features.contextactions.ContextActionsProviderRegistry;
import com.gr72s.glsp.server.features.contextactions.RequestContextActionsHandler;
import com.gr72s.glsp.server.features.contextmenu.ContextMenuItemProvider;
import com.gr72s.glsp.server.features.core.model.ComputedBoundsActionHandler;
import com.gr72s.glsp.server.features.core.model.GModelFactory;
import com.gr72s.glsp.server.features.core.model.RequestModelActionHandler;
import com.gr72s.glsp.server.features.core.model.SourceModelStorage;
import com.gr72s.glsp.server.features.directediting.ContextEditValidator;
import com.gr72s.glsp.server.features.directediting.ContextEditValidatorRegistry;
import com.gr72s.glsp.server.features.directediting.LabelEditValidator;
import com.gr72s.glsp.server.features.directediting.RequestEditValidationHandler;
import com.gr72s.glsp.server.features.navigation.NavigationTargetProvider;
import com.gr72s.glsp.server.features.navigation.NavigationTargetProviderRegistry;
import com.gr72s.glsp.server.features.navigation.NavigationTargetResolver;
import com.gr72s.glsp.server.features.navigation.RequestNavigationTargetsActionHandler;
import com.gr72s.glsp.server.features.navigation.ResolveNavigationTargetActionHandler;
import com.gr72s.glsp.server.features.popup.PopupModelFactory;
import com.gr72s.glsp.server.features.popup.RequestPopupModelActionHandler;
import com.gr72s.glsp.server.features.progress.DefaultProgressService;
import com.gr72s.glsp.server.features.progress.ProgressService;
import com.gr72s.glsp.server.features.sourcemodelwatcher.SourceModelWatcher;
import com.gr72s.glsp.server.features.toolpalette.ToolPaletteItemProvider;
import com.gr72s.glsp.server.features.typehints.EdgeCreationChecker;
import com.gr72s.glsp.server.features.typehints.RequestCheckEdgeActionHandler;
import com.gr72s.glsp.server.features.typehints.RequestTypeHintsActionHandler;
import com.gr72s.glsp.server.features.undoredo.UndoRedoActionHandler;
import com.gr72s.glsp.server.features.validation.ModelValidator;
import com.gr72s.glsp.server.features.validation.RequestMarkersHandler;
import com.gr72s.glsp.server.gmodel.GModelCutOperationHandler;
import com.gr72s.glsp.server.gson.GraphGsonConfigurationFactory;
import com.gr72s.glsp.server.internal.actions.DefaultActionDispatcher;
import com.gr72s.glsp.server.internal.actions.DefaultActionHandlerRegistry;
import com.gr72s.glsp.server.internal.diagram.DefaultServerConfigurationContribution;
import com.gr72s.glsp.server.internal.featues.directediting.DefaultContextEditValidatorRegistry;
import com.gr72s.glsp.server.internal.featues.navigation.DefaultNavigationTargetProviderRegistry;
import com.gr72s.glsp.server.internal.features.contextactions.DefaultContextActionsProviderRegistry;
import com.gr72s.glsp.server.internal.gson.DefaultGraphGsonConfigurationFactory;
import com.gr72s.glsp.server.internal.operations.DefaultOperationHandlerRegistry;
import com.gr72s.glsp.server.internal.toolpalette.DefaultToolPaletteItemProvider;
import com.gr72s.glsp.server.layout.LayoutEngine;
import com.gr72s.glsp.server.model.DefaultGModelState;
import com.gr72s.glsp.server.model.GModelState;
import com.gr72s.glsp.server.operations.CompoundOperationHandler;
import com.gr72s.glsp.server.operations.LayoutOperationHandler;
import com.gr72s.glsp.server.operations.OperationActionHandler;
import com.gr72s.glsp.server.operations.OperationHandler;
import com.gr72s.glsp.server.operations.OperationHandlerRegistry;
import com.gr72s.glsp.server.protocol.GLSPServer;

import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

/**
 * The diagram module is the central configuration artifact for configuring a client session specific injector. For each
 * session that is initialized by a {@link GLSPServer} a new client session injector is created. The diagram module
 * provides the base bindings necessary to provide diagram implementation (i.e. diagram language). In addition to the
 * diagram specific bindings, session specific bindings like the {@link GModelState} are configured
 *
 * Client session injectors are child injectors of a server injector and therefore inherit the bindings from
 * {@link ServerModule}.
 * <p>
 * The following bindings are provided:
 *
 * <ul>
 * <li>{@link String} annotated with @{@link DiagramType}
 * <li>{@link String} annotated with @{@link ClientId}
 * <li>{@link DiagramGlobalSingleton} bound as Scope
 * <li>{@link DiagramConfiguration}
 * <li>{@link ServerConfigurationContribution}
 * <li>{@link GModelState}
 * <li>{@link SourceModelStorage}
 * <li>{@link GModelFactory}
 * <li>{@link SourceModelWatcher} as {@link Optional}
 * <li>{@link GraphGsonConfigurationFactory}
 * <li>{@link ModelValidator} as {@link Optional}
 * <li>{@link LabelEditValidator} as {@link Optional}
 * <li>{@link ToolPaletteItemProvider} as {@link Optional}
 * <li>{@link CommandPaletteActionProvider} as {@link Optional}
 * <li>{@link ContextMenuItemProvider} as {@link Optional}
 * <li>{@link ContextActionsProvider} as {@link Multibinder}
 * <li>{@link ContextActionsProviderRegistry}
 * <li>{@link LabelEditValidator} as {@link Optional}
 * <li>{@link ToolPaletteItemProvider} as {@link Optional}
 * <li>{@link ActionDispatcher}
 * <li>{@link Action} as {@link Multibinder}
 * <li>{@link ActionHandler} as {@link Multibinder}
 * <li>{@link ActionHandlerRegistry}
 * <li>{@link OperationHandler} as {@link Multibinder}
 * <li>{@link OperationHandlerRegistry}
 * <li>{@link NavigationTargetResolver}
 * <li>{@link NavigationTargetProvider} as {@link Multibinder}
 * <li>{@link NavigationTargetProviderRegistry}
 * <li>{@link ContextEditValidator} as {@link Multibinder}
 * <li>{@link ContextEditValidatorRegistry}
 * <li>{@link ProgressService}
 * <li>{@link PopupModelFactory} as {@link Optional}
 * <li>{@link LayoutEngine} as {@link Optional}
 * <li>{@link GraphExtension} as {@link Optional}
 * <li>{@link EdgeCreationChecker} as {@link Optional}
 * </ul>
 *
 *
 */
public abstract class DiagramModule extends GLSPModule {
   public static final String FALLBACK_CLIENT_ID = "FallbackClientId";

   protected DiagramGlobalScope scope;

   @Override
   protected void configureBase() {
      bindDiagramType();
      bindClientId();
      bindDiagramGobalScope();

      // Configurations
      bind(DiagramConfiguration.class).to(bindDiagramConfiguration()).in(DiagramGlobalSingleton.class);
      bind(ServerConfigurationContribution.class).to(bindServerConfigurationContribution()).in(Singleton.class);
      // Model-related bindings
      configureGModelState(bindGModelState());
      bind(SourceModelStorage.class).to(bindSourceModelStorage());
      bind(GModelFactory.class).to(bindGModelFactory());
      bindOptionally(SourceModelWatcher.class, bindSourceModelWatcher())
         .ifPresent(binder -> binder.in(Singleton.class));
      bind(GraphGsonConfigurationFactory.class).to(bindGraphGsonConfiguratorFactory()).in(Singleton.class);

      // Model Validation
      bindOptionally(ModelValidator.class, bindModelValidator());
      bindOptionally(LabelEditValidator.class, bindLabelEditValidator());

      // ContextActionProviders
      bindOptionally(ToolPaletteItemProvider.class, bindToolPaletteItemProvider());
      bindOptionally(CommandPaletteActionProvider.class, bindCommandPaletteActionProvider());
      bindOptionally(ContextMenuItemProvider.class, bindContextMenuItemProvider());
      configure(MultiBinding.create(ContextActionsProvider.class), this::configureContextActionsProviders);
      bind(ContextActionsProviderRegistry.class).to(bindContextActionsProviderRegistry()).in(Singleton.class);

      // Action & Operation related bindings
      bind(ActionDispatcher.class).to(bindActionDispatcher()).in(Singleton.class);
      configure(MultiBinding.create(ActionHandler.class), this::configureActionHandlers);
      bind(ActionHandlerRegistry.class).to(bindActionHandlerRegistry());
      configure(MultiBinding.create(new TypeLiteral<OperationHandler<?>>() {}), this::configureOperationHandlers);
      bind(OperationHandlerRegistry.class).to(bindOperationHandlerRegistry());

      // Navigation
      bindOptionally(NavigationTargetResolver.class, bindNavigationTargetResolver());
      configure(MultiBinding.create(NavigationTargetProvider.class), this::configureNavigationTargetProviders);
      bind(NavigationTargetProviderRegistry.class).to(bindNavigationTargetProviderRegistry()).in(Singleton.class);

      // ContextEdit
      configure(MultiBinding.create(ContextEditValidator.class), this::configureContextEditValidators);
      bind(ContextEditValidatorRegistry.class).to(bindContextEditValidatorRegistry()).in(Singleton.class);

      // Misc
      bind(ProgressService.class).to(bindProgressService()).in(Singleton.class);
      bindOptionally(PopupModelFactory.class, bindPopupModelFactory());
      bindOptionally(LayoutEngine.class, bindLayoutEngine());
      bindOptionally(GraphExtension.class, bindGraphExtension());
      bindOptionally(EdgeCreationChecker.class, bindEdgeCreationChecker());
   }

   protected void bindDiagramType() {
      bind(String.class).annotatedWith(DiagramType.class).toInstance(getDiagramType());
   }

   protected void bindClientId() {
      bind(String.class).annotatedWith(ClientId.class).toInstance(FALLBACK_CLIENT_ID);
   }

   protected void bindDiagramGobalScope() {
      bindScope(DiagramGlobalSingleton.class, new DiagramGlobalScope.NullImpl());
   }

   protected abstract Class<? extends DiagramConfiguration> bindDiagramConfiguration();

   protected Class<? extends ServerConfigurationContribution> bindServerConfigurationContribution() {
      return DefaultServerConfigurationContribution.class;
   }

   protected void configureGModelState(final Class<? extends GModelState> gmodelStateClass) {
      bind(gmodelStateClass).in(Singleton.class);
      bind(GModelState.class).to(gmodelStateClass);
   }

   protected Class<? extends GModelState> bindGModelState() {
      return DefaultGModelState.class;
   }

   protected abstract Class<? extends SourceModelStorage> bindSourceModelStorage();

   protected abstract Class<? extends GModelFactory> bindGModelFactory();

   protected Class<? extends SourceModelWatcher> bindSourceModelWatcher() {
      return null;
   }

   protected Class<? extends GraphGsonConfigurationFactory> bindGraphGsonConfiguratorFactory() {
      return DefaultGraphGsonConfigurationFactory.class;
   }

   protected Class<? extends ModelValidator> bindModelValidator() {
      return null;
   }

   protected Class<? extends LabelEditValidator> bindLabelEditValidator() {
      return null;
   }

   protected Class<? extends ToolPaletteItemProvider> bindToolPaletteItemProvider() {
      return DefaultToolPaletteItemProvider.class;
   }

   protected Class<? extends CommandPaletteActionProvider> bindCommandPaletteActionProvider() {
      return null;
   }

   protected Class<? extends ContextMenuItemProvider> bindContextMenuItemProvider() {
      return null;
   }

   protected void configureContextActionsProviders(final MultiBinding<ContextActionsProvider> binding) {}

   protected Class<? extends ContextActionsProviderRegistry> bindContextActionsProviderRegistry() {
      return DefaultContextActionsProviderRegistry.class;
   }

   protected Class<? extends ActionDispatcher> bindActionDispatcher() {
      return DefaultActionDispatcher.class;
   }

   protected void configureActionHandlers(final MultiBinding<ActionHandler> binding) {
      binding.add(DefaultActionDispatcher.JoinActionHandler.class);
      binding.add(OperationActionHandler.class);
      binding.add(RequestModelActionHandler.class);
      binding.add(RequestPopupModelActionHandler.class);
      binding.add(ResolveNavigationTargetActionHandler.class);
      binding.add(RequestNavigationTargetsActionHandler.class);
      binding.add(RequestTypeHintsActionHandler.class);
      binding.add(RequestContextActionsHandler.class);
      binding.add(RequestEditValidationHandler.class);
      binding.add(RequestMarkersHandler.class);
      binding.add(SetEditModeActionHandler.class);
      binding.add(ComputedBoundsActionHandler.class);
      binding.add(SaveModelActionHandler.class);
      binding.add(UndoRedoActionHandler.class);
      binding.add(RequestCheckEdgeActionHandler.class);
   }

   protected Class<? extends ActionHandlerRegistry> bindActionHandlerRegistry() {
      return DefaultActionHandlerRegistry.class;
   }

   protected void configureOperationHandlers(final MultiBinding<OperationHandler<?>> binding) {
      binding.add(CompoundOperationHandler.class);
      binding.add(LayoutOperationHandler.class);
      binding.add(GModelCutOperationHandler.class);
   }

   protected Class<? extends OperationHandlerRegistry> bindOperationHandlerRegistry() {
      return DefaultOperationHandlerRegistry.class;
   }

   protected Class<? extends NavigationTargetResolver> bindNavigationTargetResolver() {
      return null;
   }

   protected void configureNavigationTargetProviders(final MultiBinding<NavigationTargetProvider> binding) {}

   protected Class<? extends NavigationTargetProviderRegistry> bindNavigationTargetProviderRegistry() {
      return DefaultNavigationTargetProviderRegistry.class;
   }

   protected void configureContextEditValidators(final MultiBinding<ContextEditValidator> binding) {}

   protected Class<? extends ContextEditValidatorRegistry> bindContextEditValidatorRegistry() {
      return DefaultContextEditValidatorRegistry.class;
   }

   protected Class<? extends ProgressService> bindProgressService() {
      return DefaultProgressService.class;
   }

   protected Class<? extends PopupModelFactory> bindPopupModelFactory() {
      return null;
   }

   protected Class<? extends LayoutEngine> bindLayoutEngine() {
      return null;
   }

   protected Class<? extends GraphExtension> bindGraphExtension() {
      return null;
   }

   protected Class<? extends EdgeCreationChecker> bindEdgeCreationChecker() {
      return null;
   }

   public abstract String getDiagramType();
}
