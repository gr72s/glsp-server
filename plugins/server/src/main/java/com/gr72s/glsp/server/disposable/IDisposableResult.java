/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
package com.gr72s.glsp.server.disposable;

/**
 * An IDisposable that also holds a result. Useful to wrap
 * elements that should be disposed, but don't directly
 * implement IDisposable.
 *
 * @param <T> The type of the wrapped element/result
 */
public interface IDisposableResult<T> extends IDisposable {
   /**
    * @return the value wrapped by this {@link IDisposableResult}
    */
   T getResult();
}
