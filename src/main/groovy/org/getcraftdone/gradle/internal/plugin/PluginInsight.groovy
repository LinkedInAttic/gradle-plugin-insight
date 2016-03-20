package org.getcraftdone.gradle.internal.plugin

import org.getcraftdone.gradle.api.PluginInsightTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.getcraftdone.gradle.internal.project.DefaultProjectProvider
import org.kordamp.gradle.markdown.MarkdownToHtmlTask

/**
 * Created by sfaber on 3/6/16.
 */
class PluginInsight implements Plugin<Project> {

  @Override
  void apply(Project project) {
    project.plugins.withType(JavaPlugin) {
      def aop = project.configurations.create("plugin-insight-aop")
      aop.description = "Contains exactly one dependency, the aspectj weaver jar" +
              "(needed for weaving in hooks to Gradle plugin lifecycle)"
      aop.visible = false

      project.dependencies.add("plugin-insight-aop", "org.aspectj:aspectjweaver:1.8.8")

      def markdownOutputDir = project.file("$project.buildDir/doc/plugins/markdown")
      def htmlOutputDir = project.file("$project.buildDir/doc/plugins/html")

      project.tasks.create("pluginsInsight", PluginInsightTask) { PluginInsightTask task ->
        task.group = "Documentation"
        task.description = "Generates documentation for all custom Gradle plugins developed in this project."

        task.classpath = project.files()
        task.classpath += project.sourceSets.main.output //so that we have user's plugins
        task.classpath += project.configurations.getByName("runtime") //so that we have user's plugins' classpath/dependencies
        task.classpath += project.buildscript.configurations.getByName("classpath") //so that we have PluginDoc classes

        task.pluginIdDir = project.file("src/main/resources/META-INF/gradle-plugins")
        task.outputDir = markdownOutputDir
        task.projectProviderImpl = DefaultProjectProvider.name
        task.aspectjAgent = aop
      }

      project.tasks.create("pluginInsightHtml", MarkdownToHtmlTask) { MarkdownToHtmlTask task ->
        task.group = "Documentation"

        task.sourceDir = markdownOutputDir
        task.outputDir = htmlOutputDir
      }
    }
  }
}
