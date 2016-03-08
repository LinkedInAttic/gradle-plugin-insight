package org.sfaber.gradle.internal

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

/**
 * Created by sfaber on 3/5/16.
 */
class AllPluginsDocGenerator {

  String textDoc(Project project, String pluginId) {
    def builder = new PluginDocBuilder()
    builder.init(project)
    project.plugins.apply(pluginId)
    " --- Documentation for '$pluginId' plugin ---\n\n" + builder.toString()
  }

  void textDoc(Project project, String pluginId, File file) {
    def text = textDoc(project, pluginId)
    file.parentFile.mkdirs()
    file << text
  }

  public static void main(String[] args) {
    assert args.length == 2 : "Expected 1 argument: dir path that contains the plugin id files (e.g. plugin-id.properties files)"

    def outputDir = new File(args[1])
    def plugins = new PluginsFinder().getAllPlugins(new File(args[0]))
    plugins.each {
      def p = new ProjectBuilder().build()
      new PluginDocGenerator().textDoc(p, it, new File(outputDir, "${it}.txt"))
    }
  }
}
