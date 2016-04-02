package com.linkedin.gradle.pi.api;

import org.gradle.api.Project;

/**
 * Provides and instance of a project that we need to apply the plugin, and track changes to the model
 * and build the documentation.
 *
 * Example use case: in my team, instances of Gradle projects require some extra files present and properties present.
 * Hence, we need to use custom implementation that creates project instances
 * that can correctly apply our custom plugins.
 */
public interface ProjectProvider {

    /**
     * Creates an instance of Gradle project
     */
    Project buildProject();
}
