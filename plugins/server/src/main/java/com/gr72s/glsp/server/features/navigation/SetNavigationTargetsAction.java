/*******************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
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
 ******************************************************************************/
package com.gr72s.glsp.server.features.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gr72s.glsp.server.actions.ResponseAction;

public class SetNavigationTargetsAction extends ResponseAction {

   public static final String KIND = "setNavigationTargets";

   private List<NavigationTarget> targets;
   private Map<String, String> args;

   public SetNavigationTargetsAction() {
      this(new ArrayList<>(), new HashMap<>());
   }

   public SetNavigationTargetsAction(final List<NavigationTarget> targets, final Map<String, String> map) {
      super(KIND);
      this.targets = targets;
      this.args = map;
   }

   public List<NavigationTarget> getTargets() { return targets; }

   public void setTargets(final List<NavigationTarget> targets) { this.targets = targets; }

   public Map<String, String> getArgs() { return args; }

   public void setArgs(final Map<String, String> args) { this.args = args; }
}
