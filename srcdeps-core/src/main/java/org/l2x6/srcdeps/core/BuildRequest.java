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
package org.l2x6.srcdeps.core;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.l2x6.srcdeps.core.shell.IoRedirects;
import org.l2x6.srcdeps.core.util.SrcdepsCoreUtils;

/**
 * A description of what and how should be built.
 *
 * @author <a href="https://github.com/ppalaga">Peter Palaga</a>
 */
public class BuildRequest {

    /**
     *
     * A builder for {@link BuildRequest}s.
     *
     */
    /**
     * @author <a href="https://github.com/ppalaga">Peter Palaga</a>
     *
     */
    public static class BuildRequestBuilder {

        private boolean addDefaultBuildArguments = true;
        private List<String> buildArguments = new ArrayList<>();
        private Map<String, String> buildEnvironment = new LinkedHashMap<>();
        private IoRedirects ioRedirects = IoRedirects.inheritAll();
        private Path projectRootDirectory;
        private List<String> scmUrls = new ArrayList<>();
        private SrcVersion srcVersion;
        private long timeoutMs = DEFAULT_TIMEOUT_MS;
        private Verbosity verbosity = Verbosity.default_;

        /**
         * @param addDefaultBuildArguments
         *            see {@link BuildRequest#isAddDefaultBuildArguments()}
         * @return this {@link BuildRequestBuilder}
         */
        public BuildRequestBuilder addDefaultBuildArguments(boolean addDefaultBuildArguments) {
            this.addDefaultBuildArguments = addDefaultBuildArguments;
            return this;
        }

        /**
         * @return a new {@link BuildRequest} based on the values stored in fields of this {@link BuildRequestBuilder}
         */
        public BuildRequest build() {
            return new BuildRequest(projectRootDirectory, srcVersion, Collections.unmodifiableList(scmUrls),
                    Collections.unmodifiableList(buildArguments), addDefaultBuildArguments,
                    Collections.unmodifiableMap(buildEnvironment), verbosity, ioRedirects, timeoutMs);
        }

        /**
         * @param argument
         *            the single build argument to add to {@link #buildArguments}; see
         *            {@link BuildRequest#getBuildArguments()}
         * @return this {@link BuildRequestBuilder}
         */
        public BuildRequestBuilder buildArgument(String argument) {
            this.buildArguments.add(argument);
            return this;
        }

        /**
         * @param arguments
         *            the arguments to add to {@link #buildArguments}; see {@link BuildRequest#getBuildArguments()}
         * @return this {@link BuildRequestBuilder}
         */
        public BuildRequestBuilder buildArguments(List<String> buildArguments) {
            this.buildArguments.addAll(buildArguments);
            return this;
        }

        /**
         * @param arguments
         *            the arguments to add to {@link #buildArguments}; see {@link BuildRequest#getBuildArguments()}
         * @return this {@link BuildRequestBuilder}
         */
        public BuildRequestBuilder buildArguments(String... arguments) {
            for (String argument : arguments) {
                this.buildArguments.add(argument);
            }
            return this;
        }

        /**
         * @param buildEnvironment
         *            see {@link BuildRequest#getBuildEnvironment()}
         * @return this {@link BuildRequestBuilder}
         */
        public BuildRequestBuilder buildEnvironment(Map<String, String> buildEnvironment) {
            this.buildEnvironment.putAll(buildEnvironment);
            return this;
        }

        /**
         * Add an environment variable of the given {@code name} and {@code value} to {@link #buildEnvironment}.
         *
         * @see BuildRequest#getBuildEnvironment()
         * @param name
         *            the name of the environment variable to add to {@link #buildEnvironment}
         * @param value
         *            the value of the environment variable to add to {@link #buildEnvironment}
         * @return this {@link BuildRequestBuilder}
         */
        public BuildRequestBuilder buildEnvironmentVariable(String name, String value) {
            this.buildEnvironment.put(name, value);
            return this;
        }

        /**
         * @param ioRedirects
         *            see {@link BuildRequest#getIoRedirects()}
         * @return this {@link BuildRequestBuilder}
         */
        public BuildRequestBuilder ioRedirects(IoRedirects ioRedirects) {
            this.ioRedirects = ioRedirects;
            return this;
        }

        /**
         * @param projectRootDirectory
         *            see {@link BuildRequest#getProjectRootDirectory()}
         * @return this {@link BuildRequestBuilder}
         */
        public BuildRequestBuilder projectRootDirectory(Path projectRootDirectory) {
            this.projectRootDirectory = projectRootDirectory;
            return this;
        }

        /**
         * @param scmUrl
         *            a SCM url to add to {@link #scmUrls}. See {@link BuildRequest#getScmUrls()}
         * @return this {@link BuildRequestBuilder}
         */
        public BuildRequestBuilder scmUrl(String scmUrl) {
            this.scmUrls.add(scmUrl);
            return this;
        }

        /**
         * @param scmUrls
         *            SCM urls to add to {@link #scmUrls}. See {@link BuildRequest#getScmUrls()}
         * @return this {@link BuildRequestBuilder}
         */
        public BuildRequestBuilder scmUrls(Collection<String> scmUrls) {
            this.scmUrls.addAll(scmUrls);
            return this;
        }

        /**
         * @param srcVersion
         *            see {@link BuildRequest#getSrcVersion()}
         * @return this {@link BuildRequestBuilder}
         */
        public BuildRequestBuilder srcVersion(SrcVersion srcVersion) {
            this.srcVersion = srcVersion;
            return this;
        }

        /**
         * @param timeoutMs
         *            see {@link BuildRequest#getTimeoutMs()}
         * @return this {@link BuildRequestBuilder}
         */
        public BuildRequestBuilder timeoutMs(long timeoutMs) {
            this.timeoutMs = timeoutMs;
            return this;
        }

        /**
         * @param verbosity
         *            see {@link BuildRequest#getVerbosity()}
         * @return this {@link BuildRequestBuilder}
         */
        public BuildRequestBuilder verbosity(Verbosity verbosity) {
            this.verbosity = verbosity;
            return this;
        }
    };

    /**
     * The verbosity level to use when configuring the {@link Builder}.
     */
    public enum Verbosity {
        debug, default_("default"), quiet;
        public static Verbosity ofId(String id) {
            switch (id) {
            case "debug":
                return debug;
            case "default":
            case "default_":
                return default_;
            case "quiet":
                return quiet;
            default:
                throw new IllegalStateException("No such " + Verbosity.class.getName() + " with id [" + id + "]");
            }
        }

        private final String id;

        private Verbosity() {
            this.id = name();
        }

        private Verbosity(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return id;
        }

    }

    /** 5 minutes */
    public static final long DEFAULT_TIMEOUT_MS = 5 * 60 * 1000;

    /**
     * @return a new {@link BuildRequestBuilder}
     */
    public static BuildRequestBuilder builder() {
        return new BuildRequestBuilder();
    }

    private final boolean addDefaultBuildArguments;
    private final List<String> buildArguments;
    private final Map<String, String> buildEnvironment;
    private final IoRedirects ioRedirects;
    private final Path projectRootDirectory;
    private final List<String> scmUrls;
    private final SrcVersion srcVersion;
    private final long timeoutMs;
    private final Verbosity verbosity;

    private BuildRequest(Path projectRootDirectory, SrcVersion srcVersion, List<String> scmUrls,
            List<String> buildArguments, boolean addDefaultBuildArguments, Map<String, String> buildEnvironment,
            Verbosity verbosity, IoRedirects ioRedirects, long timeoutMs) {
        super();

        SrcdepsCoreUtils.assertArgNotNull(projectRootDirectory, "projectRootDirectory");
        SrcdepsCoreUtils.assertArgNotNull(srcVersion, "srcVersion");
        SrcdepsCoreUtils.assertArgNotNull(scmUrls, "scmUrls");
        SrcdepsCoreUtils.assertCollectionNotEmpty(scmUrls, "scmUrls");
        SrcdepsCoreUtils.assertArgNotNull(buildArguments, "buildArguments");
        SrcdepsCoreUtils.assertArgNotNull(buildEnvironment, "buildEnvironment");
        SrcdepsCoreUtils.assertArgNotNull(ioRedirects, "ioRedirects");

        this.projectRootDirectory = projectRootDirectory;
        this.srcVersion = srcVersion;
        this.scmUrls = scmUrls;
        this.buildArguments = buildArguments;
        this.buildEnvironment = buildEnvironment;
        this.verbosity = verbosity;
        this.timeoutMs = timeoutMs;
        this.addDefaultBuildArguments = addDefaultBuildArguments;
        this.ioRedirects = ioRedirects;
    }

    /**
     * @return a {@link List} of arguments that should be used by {@link Builder#build(BuildRequest)}. Cannot be
     *         {@code null}. The {@link Builder} will combine this list with its default list of arguments if
     *         {@link #isAddDefaultBuildArguments()} returns {@code true}.
     */
    public List<String> getBuildArguments() {
        return buildArguments;
    }

    /**
     * @return a {@link Map} of environment variables that should be used by {@link Builder#build(BuildRequest)}. Cannot
     *         be {@code null}. Note that these are just overlay variables - if the {@link Builder} spawns a new
     *         process, the environment is copied from the present process and the variables provided by the present
     *         method are overwritten or added.
     */
    public Map<String, String> getBuildEnvironment() {
        return buildEnvironment;
    }

    /**
     * @return the {@link IoRedirects} to use when the {@link Builder} spawns new {@link Process}es
     */
    public IoRedirects getIoRedirects() {
        return ioRedirects;
    }

    /**
     * @return the root directory of the project's source tree that should be built
     *
     */
    public Path getProjectRootDirectory() {
        return projectRootDirectory;
    }

    /**
     * @return a {@link List} of URLs that should be tried one after another to checkout the version determined by
     *         {@link #getSrcVersion()}. The URLs will be tried by the {@link BuildService} in the given order until the
     *         checkout is successful. See {@link Scm} for the information about the format of the URLs.
     */
    public List<String> getScmUrls() {
        return scmUrls;
    }

    /**
     * @return the {@link SrcVersion} to checkout and build
     */
    public SrcVersion getSrcVersion() {
        return srcVersion;
    }

    /**
     * @return the timeout in milliseconds for the {@link Builder#setVersions(BuildRequest)} and
     *         {@link Builder#build(BuildRequest)} operations.
     */
    public long getTimeoutMs() {
        return timeoutMs;
    }

    /**
     * @return the verbosity level to use when configuring the {@link Builder}
     */
    public Verbosity getVerbosity() {
        return verbosity;
    }

    /**
     * @return {@code true} if the given {@link Builder}'s default arguments should be combined with the arguments
     *         returned by {@link #getBuildArguments()}; {@code false} otherwise
     */
    public boolean isAddDefaultBuildArguments() {
        return addDefaultBuildArguments;
    }

    @Override
    public String toString() {
        return "BuildRequest [projectRootDirectory=" + projectRootDirectory + ", srcVersion=" + srcVersion
                + ", scmUrls=" + scmUrls + ", buildArguments=" + buildArguments + ", addDefaultBuildArguments="
                + addDefaultBuildArguments + ", buildEnvironment=" + buildEnvironment + ", verbosity=" + verbosity
                + ", timeoutMs=" + timeoutMs + "]";
    }

}
