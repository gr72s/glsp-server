/********************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
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
package com.gr72s.glsp.server.features.navigation;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.gr72s.glsp.server.actions.AbstractActionHandler;
import com.gr72s.glsp.server.actions.Action;

import com.google.inject.Inject;

public class ResolveNavigationTargetActionHandler extends AbstractActionHandler<ResolveNavigationTargetAction> {

   protected static final Logger LOGGER = LogManager.getLogger(ResolveNavigationTargetActionHandler.class);

   @Inject
   protected Optional<NavigationTargetResolver> navigationTargetResolver;

   @Override
   public List<Action> executeAction(final ResolveNavigationTargetAction action) {
      if (navigationTargetResolver.isEmpty()) {
         LOGGER.warn("Could not resolve navigation target. No implementation for: "
            + NavigationTargetResolver.class.getName() + " has been bound.");
         return none();
      }
      NavigationTarget target = action.getNavigationTarget();
      NavigationTargetResolution resolution = this.navigationTargetResolver.get().resolve(target);
      return listOf(new SetResolvedNavigationTargetAction(resolution.getElementIds(), resolution.getArgs()));
   }
}
