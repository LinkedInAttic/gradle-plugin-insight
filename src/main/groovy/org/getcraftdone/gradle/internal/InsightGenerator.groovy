package org.getcraftdone.gradle.internal

import org.gradle.api.Project

/**
 * Created by sfaber on 3/5/16.
 */
class InsightGenerator {

  String textDoc(Project project, String pluginId) {
    def listener = new InsightListener()
    listener.init(project)
    project.plugins.apply(pluginId)
    "## Plugin '$pluginId' applies: ##\n\n" + listener.toString()
  }

  void textDoc(Project project, String pluginId, File file) {
    def text = textDoc(project, pluginId)
    file.parentFile.mkdirs()
    file << text
  }
}
