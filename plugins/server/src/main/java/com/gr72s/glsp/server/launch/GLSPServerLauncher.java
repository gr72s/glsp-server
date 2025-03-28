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
package com.gr72s.glsp.server.launch;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import com.gr72s.glsp.server.di.ServerModule;
import com.gr72s.glsp.server.utils.LaunchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class GLSPServerLauncher {

    protected static Logger LOGGER = LoggerFactory.getLogger(GLSPServerLauncher.class);
    protected final List<Module> modules;

    public GLSPServerLauncher(final ServerModule serverModule, final Module... additionalModules) {
        modules = new ArrayList<>();
        modules.add(serverModule);
        Stream.of(additionalModules).forEach(modules::add);
    }

    public Injector createInjector() {
        return Guice.createInjector(modules);
    }

    public abstract void start(String hostname, int port);

    public void start(final String hostname, final int port, final boolean consoleLogging, final String logDir,
                      final Level logLevel) {
        // configure logging
        LaunchUtil.configureLogger(consoleLogging, logDir, logLevel);

        start(hostname, port);
    }

    public void start(final String hostname, final int port, final DefaultCLIParser parser) {
        try {
            // configure logging
            LaunchUtil.configure(parser);
        } catch (ParseException e) {
            LOGGER.error("Error during log configuration!", e);
        }
        start(hostname, port);
    }

    public abstract void shutdown();

}
