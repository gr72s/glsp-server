/********************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
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
package com.gr72s.glsp.server.features.validation;

import java.util.ArrayList;
import java.util.List;

import com.gr72s.glsp.server.actions.ResponseAction;

public class SetMarkersAction extends ResponseAction {

   public static final String KIND = "setMarkers";

   private List<Marker> markers;
   private String reason;

   public SetMarkersAction() {
      this(new ArrayList<>());
   }

   public SetMarkersAction(final List<Marker> markers) {
      this(markers, MarkersReason.BATCH);
   }

   public SetMarkersAction(final List<Marker> markers, final String reason) {
      super(KIND);
      this.markers = markers;
      this.reason = reason;
   }

   public List<Marker> getMarkers() { return markers; }

   public void setMarkers(final List<Marker> markers) { this.markers = markers; }

   public String getReason() { return reason; }

   public void setReason(final String reason) { this.reason = reason; }

}
