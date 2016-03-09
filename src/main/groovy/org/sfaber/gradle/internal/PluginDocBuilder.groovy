package org.sfaber.gradle.internal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.sfaber.gradle.internal.aop.PluginLifecycleAspect

/**
 * Created by sfaber on 3/5/16.
 */
class PluginDocBuilder implements PluginLifecycleAspect.Listener{

  ModelBuilder builder = new ModelBuilder()
  Project project

  void init(Project project) {
    PluginLifecycleAspect.LISTENER = this
    this.project = project
  }

  public String toString() {
    builder.toString()
  }

  LinkedList<ModelSnapshot> snapshots = new LinkedList<>()

  void beforeApplied(Plugin<Project> plugin) {
    println "Before $plugin"
    snapshots << new SnapshotTaker().snapshot(project, plugin)
  }

  void afterApplied(Plugin<Project> plugin) {
    println "After $plugin"
    def s = new SnapshotTaker().snapshot(project, plugin)
    def delta = s.minus(snapshots.removeLast())
    builder.add(delta)
  }
}
