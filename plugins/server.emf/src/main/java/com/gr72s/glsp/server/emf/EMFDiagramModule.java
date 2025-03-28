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

import com.gr72s.glsp.server.di.DiagramModule;

import com.google.inject.Singleton;

/**
 * Base module for diagram implementations that operate on EMF resources.
 */
public abstract class EMFDiagramModule extends DiagramModule {

   @Override
   protected void configureBase() {
      super.configureBase();
      configureEMFEditingDomainFactory(bindEMFEditingDomainFactory());
      configureEMFIdGenerator(bindEMFIdGenerator());
   }

   protected Class<? extends EMFEditingDomainFactory> bindEMFEditingDomainFactory() {
      return DefaultEditingDomainFactory.class;
   }

   protected void configureEMFEditingDomainFactory(
      final Class<? extends EMFEditingDomainFactory> editingDomainFactoryClass) {
      bind(editingDomainFactoryClass).in(Singleton.class);
      bind(EMFEditingDomainFactory.class).to(editingDomainFactoryClass);
   }

   protected abstract Class<? extends EMFIdGenerator> bindEMFIdGenerator();

   protected void configureEMFIdGenerator(final Class<? extends EMFIdGenerator> generatorClass) {
      bind(generatorClass).in(Singleton.class);
      bind(EMFIdGenerator.class).to(generatorClass);
   }

   @Override
   protected Class<? extends EMFModelState> bindGModelState() {
      return EMFModelStateImpl.class;
   }

   @Override
   protected Class<? extends EMFSourceModelStorage> bindSourceModelStorage() {
      return EMFSourceModelStorage.class;
   }

   @Override
   protected void configure() {
      super.configure();
      configureEMFModelState(bindGModelState());
   }

   protected void configureEMFModelState(final Class<? extends EMFModelState> emfStateClass) {
      bind(EMFModelState.class).to(emfStateClass).in(Singleton.class);
   }

}
