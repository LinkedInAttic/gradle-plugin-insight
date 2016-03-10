package com.getcraftdone.gradle.internal

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

/**
 * Created by sfaber on 3/5/16.
 */
class PluginDocGenerator {

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
    assert args.length == 2 : "Expected 2 arguments: plugin id and report file location"
    def p = new ProjectBuilder().build()
    new PluginDocGenerator().textDoc(p, args[0], new File(args[1]))
  }
}
