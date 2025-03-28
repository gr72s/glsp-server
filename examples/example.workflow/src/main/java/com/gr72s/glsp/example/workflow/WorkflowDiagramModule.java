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
package com.gr72s.glsp.example.workflow;

import com.gr72s.glsp.example.workflow.handler.CreateAutomatedTaskHandler;
import com.gr72s.glsp.example.workflow.handler.CreateCategoryHandler;
import com.gr72s.glsp.example.workflow.handler.CreateDecisionNodeHandler;
import com.gr72s.glsp.example.workflow.handler.CreateEdgeHandler;
import com.gr72s.glsp.example.workflow.handler.CreateForkNodeHandler;
import com.gr72s.glsp.example.workflow.handler.CreateJoinNodeHandler;
import com.gr72s.glsp.example.workflow.handler.CreateManualTaskHandler;
import com.gr72s.glsp.example.workflow.handler.CreateMergeNodeHandler;
import com.gr72s.glsp.example.workflow.handler.CreateWeightedEdgeHandler;
import com.gr72s.glsp.example.workflow.handler.LogActionHandler;
import com.gr72s.glsp.example.workflow.handler.WorkflowRequestContextActionsHandler;
import com.gr72s.glsp.example.workflow.labeledit.WorkflowLabelEditValidator;
import com.gr72s.glsp.example.workflow.layout.WorkflowLayoutEngine;
import com.gr72s.glsp.example.workflow.marker.WorkflowModelValidator;
import com.gr72s.glsp.example.workflow.model.WorkflowNavigationTargetResolver;
import com.gr72s.glsp.example.workflow.provider.NextNodeNavigationTargetProvider;
import com.gr72s.glsp.example.workflow.provider.NodeDocumentationNavigationTargetProvider;
import com.gr72s.glsp.example.workflow.provider.PreviousNodeNavigationTargetProvider;
import com.gr72s.glsp.example.workflow.provider.WorkflowCommandPaletteActionProvider;
import com.gr72s.glsp.example.workflow.provider.WorkflowContextMenuItemProvider;
import com.gr72s.glsp.example.workflow.taskedit.EditTaskOperationHandler;
import com.gr72s.glsp.example.workflow.taskedit.TaskEditContextActionProvider;
import com.gr72s.glsp.example.workflow.taskedit.TaskEditValidator;
import com.gr72s.glsp.example.workflow.typehints.WorkflowEdgeCreationChecker;
import com.gr72s.glsp.graph.GraphExtension;
import com.gr72s.glsp.server.actions.ActionHandler;
import com.gr72s.glsp.server.di.MultiBinding;
import com.gr72s.glsp.server.diagram.DiagramConfiguration;
import com.gr72s.glsp.server.features.commandpalette.CommandPaletteActionProvider;
import com.gr72s.glsp.server.features.contextactions.ContextActionsProvider;
import com.gr72s.glsp.server.features.contextactions.RequestContextActionsHandler;
import com.gr72s.glsp.server.features.contextmenu.ContextMenuItemProvider;
import com.gr72s.glsp.server.features.core.model.SourceModelStorage;
import com.gr72s.glsp.server.features.directediting.ContextEditValidator;
import com.gr72s.glsp.server.features.directediting.LabelEditValidator;
import com.gr72s.glsp.server.features.navigation.NavigationTargetProvider;
import com.gr72s.glsp.server.features.navigation.NavigationTargetResolver;
import com.gr72s.glsp.server.features.popup.PopupModelFactory;
import com.gr72s.glsp.server.features.sourcemodelwatcher.FileWatcher;
import com.gr72s.glsp.server.features.sourcemodelwatcher.SourceModelWatcher;
import com.gr72s.glsp.server.features.typehints.EdgeCreationChecker;
import com.gr72s.glsp.server.features.validation.ModelValidator;
import com.gr72s.glsp.server.gmodel.GModelDiagramModule;
import com.gr72s.glsp.server.gmodel.GModelStorage;
import com.gr72s.glsp.server.layout.LayoutEngine;
import com.gr72s.glsp.server.operations.OperationHandler;

public class WorkflowDiagramModule extends GModelDiagramModule {

   @Override
   protected Class<? extends DiagramConfiguration> bindDiagramConfiguration() {
      return WorkflowDiagramConfiguration.class;
   }

   @Override
   protected Class<? extends SourceModelStorage> bindSourceModelStorage() {
      return GModelStorage.class;
   }

   @Override
   protected Class<? extends SourceModelWatcher> bindSourceModelWatcher() {
      return FileWatcher.class;
   }

   @Override
   protected Class<? extends GraphExtension> bindGraphExtension() {
      return WFGraphExtension.class;
   }

   @Override
   protected void configureContextActionsProviders(final MultiBinding<ContextActionsProvider> binding) {
      super.configureContextActionsProviders(binding);
      binding.add(TaskEditContextActionProvider.class);
   }

   @Override
   protected void configureContextEditValidators(final MultiBinding<ContextEditValidator> binding) {
      super.configureContextEditValidators(binding);
      binding.add(TaskEditValidator.class);
   }

   @Override
   protected void configureNavigationTargetProviders(final MultiBinding<NavigationTargetProvider> binding) {
      super.configureNavigationTargetProviders(binding);
      binding.add(NextNodeNavigationTargetProvider.class);
      binding.add(PreviousNodeNavigationTargetProvider.class);
      binding.add(NodeDocumentationNavigationTargetProvider.class);
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler<?>> binding) {
      super.configureOperationHandlers(binding);
      binding.add(CreateAutomatedTaskHandler.class);
      binding.add(CreateManualTaskHandler.class);
      binding.add(CreateDecisionNodeHandler.class);
      binding.add(CreateMergeNodeHandler.class);
      binding.add(CreateForkNodeHandler.class);
      binding.add(CreateJoinNodeHandler.class);
      binding.add(CreateEdgeHandler.class);
      binding.add(CreateWeightedEdgeHandler.class);
      binding.add(CreateCategoryHandler.class);
      binding.add(EditTaskOperationHandler.class);
   }

   @Override
   protected void configureActionHandlers(final MultiBinding<ActionHandler> binding) {
      super.configureActionHandlers(binding);
      binding.rebind(RequestContextActionsHandler.class, WorkflowRequestContextActionsHandler.class);
      binding.add(LogActionHandler.class);
   }

   @Override
   public Class<? extends PopupModelFactory> bindPopupModelFactory() {
      return WorkflowPopupFactory.class;
   }

   @Override
   protected Class<? extends ModelValidator> bindModelValidator() {
      return WorkflowModelValidator.class;
   }

   @Override
   protected Class<? extends LabelEditValidator> bindLabelEditValidator() {
      return WorkflowLabelEditValidator.class;
   }

   @Override
   protected Class<? extends LayoutEngine> bindLayoutEngine() {
      return WorkflowLayoutEngine.class;
   }

   @Override
   protected Class<? extends ContextMenuItemProvider> bindContextMenuItemProvider() {
      return WorkflowContextMenuItemProvider.class;
   }

   @Override
   protected Class<? extends CommandPaletteActionProvider> bindCommandPaletteActionProvider() {
      return WorkflowCommandPaletteActionProvider.class;
   }

   @Override
   protected Class<? extends NavigationTargetResolver> bindNavigationTargetResolver() {
      return WorkflowNavigationTargetResolver.class;
   }

   @Override
   protected Class<? extends EdgeCreationChecker> bindEdgeCreationChecker() {
      return WorkflowEdgeCreationChecker.class;
   }

   @Override
   public String getDiagramType() { return "workflow-diagram"; }

}
