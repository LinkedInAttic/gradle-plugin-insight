package org.getcraftdone.gradle.internal

import org.gradle.api.Project

/**
 * Created by sfaber on 3/5/16.
 */
class InsightGenerator {

  String generateMarkdown(Project project, String pluginId) {
    def listener = new InsightListener()
    listener.init(project)
    project.plugins.apply(pluginId)
    "## Plugin '$pluginId' applies: ##\n\n" + listener.getMarkdownDoc()
  }

  void generateMarkdown(Project project, String pluginId, File file) {
    def text = generateMarkdown(project, pluginId)
    file.parentFile.mkdirs()
    file.createNewFile()
    file.text = text
  }
}
