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
package com.gr72s.glsp.server.actions;

/**
 * Instructs the client to export an SVG from the current diagram.
 */
public class ExportSVGAction extends Action {

   public static final String KIND = "exportSvg";

   private String svg;

   public ExportSVGAction() {
      super(KIND);
   }

   public ExportSVGAction(final String svg) {
      this();
      this.svg = svg;
   }

   public String getSvg() { return svg; }

}
