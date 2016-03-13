package org.getcraftdone.gradle.internal

import org.gradle.api.Project

/**
 * Created by sfaber on 3/5/16.
 */
class PluginDocGenerator {

  String textDoc(Project project, String pluginId) {
    def builder = new PluginDocBuilder()

    builder.init(project)
    project.plugins.apply(pluginId)
    "## Documentation for '$pluginId' plugin ##\n\n" + builder.toString()
  }

  void textDoc(Project project, String pluginId, File file) {
    def text = textDoc(project, pluginId)
    file.parentFile.mkdirs()
    file << text
  }
}
