package org.getcraftdone.gradle.internal

import groovy.transform.CompileStatic
import org.gradle.api.Project

/**
 * Created by sfaber on 3/5/16.
 */
@CompileStatic
class InsightGenerator {

  Insight generateInsight(Project project, String pluginId) {
    def listener = new InsightListener()
    listener.init(project)
    project.plugins.apply(pluginId)
    def out = new Insight()
    out.markdown = "## Plugin '$pluginId' applies: ##\n\n" + listener.getMarkdownDoc()
    out.html = "<h2>Plugin '$pluginId' applies: </h2>\n\n" + listener.getHtmlDoc()
    out
  }

  class Insight {
    String markdown
    String html
  }

  void generateInsight(Project project, String pluginId, File markdown, File html) {
    def out = generateInsight(project, pluginId)
    write(markdown, out.markdown)
    write(html, out.html)
  }

  private static void write(File file, String text) {
    file.parentFile.mkdirs()
    file.createNewFile()
    file.text = text
  }
}
