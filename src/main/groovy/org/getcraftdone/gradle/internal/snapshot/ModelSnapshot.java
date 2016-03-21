package org.getcraftdone.gradle.internal.snapshot;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Snapshot of the model information that we are interested in documenting.
 */
public interface ModelSnapshot {

    /**
     * Creates new snapshot
     */
    ModelSnapshot take(Plugin plugin, Project project);

    /**
     * Creates new snapshot that is a result of subtraction of 'this' and 'other' snapshot.
     * Required to correctly calculate the delta of changes a plugin applies to the project.
     */
    ModelSnapshot minus(ModelSnapshot other);

    /**
     * Describes the snapshot in human readable way
     */
    String describe();

}
