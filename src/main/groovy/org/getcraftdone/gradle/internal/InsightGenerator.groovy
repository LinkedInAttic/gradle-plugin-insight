package org.getcraftdone.gradle.internal

import org.gradle.api.Project

/**
 * Created by sfaber on 3/5/16.
 */
class InsightGenerator {

  String textDoc(Project project, String pluginId) {
    def builder = new InsightListener()

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
