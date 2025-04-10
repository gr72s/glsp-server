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
package com.gr72s.glsp.server.session;

import com.gr72s.glsp.server.actions.ActionDispatcher;
import com.gr72s.glsp.server.protocol.InitializeClientSessionParameters;

/**
 * Handles the construction of new {@link ClientSession}. A client session factory has to know
 * how to derive the client session specific injector and its entrypoint (i.e. the {@link ActionDispatcher}
 * from a given client session id and a given diagram type.
 */
public interface ClientSessionFactory {

   /**
    * Create a new {@link ClientSession} based on the given initalize parameters.
    *
    * @param params The client session initialize parameters.
    * @return A new instance of {@link ClientSession} that correlates to the given input parameters.
    */
   ClientSession create(InitializeClientSessionParameters params);
}
