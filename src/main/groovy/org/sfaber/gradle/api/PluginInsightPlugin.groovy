package org.sfaber.gradle.api

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.sfaber.gradle.internal.DefaultProjectProvider

/**
 * Created by sfaber on 3/6/16.
 */
class PluginInsightPlugin implements Plugin<Project> {

  @Override
  void apply(Project project) {
    project.plugins.withType(JavaPlugin) {
      project.tasks.withType(PluginInsightTask) { PluginInsightTask task ->
        task.classpath = project.files()
        task.classpath += project.sourceSets.main.output //so that we have user's plugins
        task.classpath += project.configurations.getByName("runtime") //so that we have user's plugins' classpath/dependencies
        task.classpath += project.buildscript.configurations.getByName("classpath") //so that we have PluginDoc classes
        println "Configured classpath:"
        println project.sourceSets.main.output.files
      }

      project.tasks.create("allPluginsInsight", AllPluginsInsightTask) { AllPluginsInsightTask task ->
        task.classpath = project.files()
        task.classpath += project.sourceSets.main.output //so that we have user's plugins
        task.classpath += project.configurations.getByName("runtime") //so that we have user's plugins' classpath/dependencies
        task.classpath += project.buildscript.configurations.getByName("classpath") //so that we have PluginDoc classes

        task.pluginIdDir = project.file("src/main/resources/META-INF/gradle-plugins")
        task.outputDir = project.file("$project.buildDir/doc/plugins")
        task.projectProviderImpl = DefaultProjectProvider.name
      }
    }
  }
}
