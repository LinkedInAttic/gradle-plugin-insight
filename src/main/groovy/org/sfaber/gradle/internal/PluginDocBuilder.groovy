package org.sfaber.gradle.internal

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by sfaber on 3/5/16.
 */
class PluginDocBuilder {

  ModelBuilder builder = new ModelBuilder()

  void init(Project project) {
    project.plugins.whenPluginAdded { Plugin it ->
      def sn = new SnapshotTaker().snapshot(project, it)
      builder.add(sn)
    }
  }

  public String toString() {
    builder.toString()
  }
}
