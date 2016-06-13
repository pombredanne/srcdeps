/**
 * Copyright 2015-2016 Maven Source Dependencies
 * Plugin contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.l2x6.srcdeps.core.shell;

import java.util.Arrays;

import org.l2x6.srcdeps.core.BuildException;

/**
 * Thrown when a shell command exits with a non-zero exit code.
 *
 * @author <a href="https://github.com/ppalaga">Peter Palaga</a>
 */
public class BadExitCodeException extends BuildException {
    private static final long serialVersionUID = 5437686562879042682L;
    private final int exitCode;

    public BadExitCodeException(String[] cmdArray, int exitCode) {
        super(String.format("Command returned exit code [%d]: %s", exitCode, Arrays.toString(cmdArray)));
        this.exitCode = exitCode;
    }

    /**
     * @return the exist code of a command that caused this {@link BadExitCodeException} to be thrown
     */
    public int getExitCode() {
        return exitCode;
    }

}
