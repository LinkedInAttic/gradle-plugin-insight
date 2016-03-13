package org.getcraftdone.gradle.internal.plugin

import org.getcraftdone.gradle.api.PluginInsightTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.getcraftdone.gradle.internal.project.DefaultProjectProvider

/**
 * Created by sfaber on 3/6/16.
 */
class PluginInsight implements Plugin<Project> {

  @Override
  void apply(Project project) {
    project.plugins.withType(JavaPlugin) {
      project.repositories.mavenCentral()
      def aop = project.configurations.create("aop")
      project.dependencies.add("aop", "org.aspectj:aspectjweaver:1.8.8")

      project.tasks.create("pluginsInsight", PluginInsightTask) { PluginInsightTask task ->
        task.classpath = project.files()
        task.classpath += project.sourceSets.main.output //so that we have user's plugins
        task.classpath += project.configurations.getByName("runtime") //so that we have user's plugins' classpath/dependencies
        task.classpath += project.buildscript.configurations.getByName("classpath") //so that we have PluginDoc classes

        task.pluginIdDir = project.file("src/main/resources/META-INF/gradle-plugins")
        task.outputDir = project.file("$project.buildDir/doc/plugins")
        task.projectProviderImpl = DefaultProjectProvider.name
        task.aspectjAgent = aop.singleFile
      }
    }
  }
}
