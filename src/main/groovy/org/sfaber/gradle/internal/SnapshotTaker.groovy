package org.sfaber.gradle.internal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration

/**
 * Created by sfaber on 3/5/16.
 */
class SnapshotTaker {

  ModelSnapshot snapshot(Project project, Plugin plugin) {
    new ModelSnapshot(plugin: plugin,
        configurations: new LinkedHashSet<Configuration>(project.configurations),
        tasks: new LinkedHashSet<Task>(project.tasks))
  }
}
