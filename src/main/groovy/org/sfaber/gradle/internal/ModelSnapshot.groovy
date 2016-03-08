package org.sfaber.gradle.internal

import org.gradle.api.Plugin
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration

/**
 * Created by sfaber on 3/5/16.
 */
class ModelSnapshot {

  Plugin plugin
  Collection<Configuration> allConfigurations = []
  Collection<Task> allTasks = []
  Collection<Configuration> configurations = []
  Collection<Task> tasks = []

  ModelSnapshot delta(ModelSnapshot sn) {
    def out = new ModelSnapshot()
    out.allConfigurations = allConfigurations + sn.configurations //accumulate configurations
    out.configurations = sn.configurations - allConfigurations //only newly created configurations
    out.allTasks = allTasks + sn.tasks
    out.tasks = sn.tasks - allTasks
    out.plugin = sn.plugin
    out
  }

  public String toString() {
    def sb = new StringBuilder()
    sb.append("  * Plugin: " + plugin.getClass().getSimpleName() + "\n")
    if (tasks.isEmpty()) {
      sb.append("    Tasks: none\n")
    } else {
      sb.append("    Tasks:\n")
      tasks.each {
        sb.append("     - $it.name")
        if (it.description) {
          sb.append(" - $it.description\n")
        } else {
          sb.append("\n")
        }
      }
    }
    if (configurations.isEmpty()) {
      sb.append("    Configurations: none\n")
    } else {
      sb.append("    Configurations:\n")
      configurations.each {
        sb.append("     - $it.name\n")
        if (it.description) {
          sb.append("       $it.description\n")
        }
      }
    }

    sb.toString()
  }
}
