/********************************************************************************
 * Copyright (c) 2020-2022 EclipseSource and others.
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

import java.io.File;
import java.util.function.Predicate;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import com.gr72s.glsp.server.utils.LaunchUtil;
import com.gr72s.glsp.server.utils.LaunchUtil.DefaultOptions;

public class DefaultCLIParser extends CLIParser {
   public static final String OPTION_HELP = "help";
   public static final String OPTION_PORT = "port";
   public static final String OPTION_HOST_NAME = "host";
   public static final String OPTION_CONSOLE_LOG = "consoleLog";
   public static final String OPTION_FILE_LOG = "fileLog";
   public static final String OPTION_LOG_LEVEL = "logLevel";
   public static final String OPTION_LOG_DIR = "logDir";

   public DefaultCLIParser(final String[] args, final String processName) throws ParseException {
      this(args, getDefaultOptions(), processName);
   }

   public DefaultCLIParser(final String[] args, final Options options, final String processName) throws ParseException {
      super(args, options, processName);
   }

   public int parsePort() {
      Predicate<Integer> validator = (port) -> LaunchUtil.isValidPort(port);
      return parseIntOption(OPTION_PORT, DefaultOptions.SERVER_PORT, validator);
   }

   public String parseHostname() {
      return parseOption(OPTION_HOST_NAME, DefaultOptions.HOST_NAME);
   }

   public String parseLogDir() {
      Predicate<String> validator = (logDirArg) -> {
         File file = new File(logDirArg);
         return file.exists() && file.isDirectory();
      };
      String logDir = parseOption(OPTION_LOG_DIR, DefaultOptions.LOG_DIR, validator);
      return new File(logDir).getAbsolutePath();
   }

   public Level parseLogLevel() {
      String levelArg = parseOption(OPTION_LOG_LEVEL, DefaultOptions.LOG_LEVEL.toString());
      return Level.toLevel(levelArg, DefaultOptions.LOG_LEVEL);
   }

   public boolean isConsoleLog() { return parseBoolOption(OPTION_CONSOLE_LOG, DefaultOptions.CONSOLE_LOG_ENABLED); }

   public boolean isFileLog() { return parseBoolOption(OPTION_FILE_LOG, DefaultOptions.FILE_LOG_ENABLED); }

   public boolean isHelp() { return hasOption(OPTION_HELP); }

   public static Options getDefaultOptions() {
      Options options = new Options();
      options.addOption("h", OPTION_HELP, false, "Display usage information about GLSPServerLauncher");
      options.addOption("n", OPTION_HOST_NAME, true, String.format("Set server host name. [default='%s']",
         DefaultOptions.HOST_NAME));
      options.addOption("p", OPTION_PORT, true,
         String.format("Set server port. [default='%s']", DefaultOptions.SERVER_PORT));
      options.addOption("c", OPTION_CONSOLE_LOG, true,
         String.format("Enable/Disable console logging. [default='%s']", DefaultOptions.CONSOLE_LOG_ENABLED));
      options.addOption("f", OPTION_FILE_LOG, true,
         String.format("Enable/Disable file logging. [default='%s']", DefaultOptions.FILE_LOG_ENABLED));
      options.addOption("d", OPTION_LOG_DIR, true,
         String.format("Set the directory for log files (File logging has to be enabled)",
            DefaultOptions.LOG_DIR));
      options.addOption("l", OPTION_LOG_LEVEL, true,
         String.format("Set the log level. [default='%s']", DefaultOptions.LOG_LEVEL));
      return options;
   }

}
