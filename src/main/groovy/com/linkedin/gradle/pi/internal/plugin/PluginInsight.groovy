package com.linkedin.gradle.pi.internal.plugin

import com.linkedin.gradle.pi.api.PluginInsightTask
import com.linkedin.gradle.pi.internal.GenerateIndexHtml
import com.linkedin.gradle.pi.internal.project.DefaultProjectProvider
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

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

      project.dependencies.add("plugin-insight-aop", "org.aspectj:aspectjweaver:1.8.5")

      def outputDir = project.file("$project.buildDir/docs/plugins")

      project.tasks.create("pluginInsight", PluginInsightTask) { PluginInsightTask task ->
        task.group = "Documentation"
        task.description = "Generates markdown documentation for all custom Gradle plugins developed in this project."

        //TODO avoid compiling the classes
        task.classpath = project.files()
        task.classpath += project.sourceSets.main.output //so that we have user's plugins
        task.classpath += project.configurations.getByName("runtime") //so that we have user's plugins' classpath/dependencies
        task.classpath += project.buildscript.configurations.getByName("classpath") //so that we have PluginDoc classes
        task.classpath += project.rootProject.buildscript.configurations.getByName("classpath") //

        task.pluginIdDir = project.file("src/main/resources/META-INF/gradle-plugins")
        task.outputDir = outputDir
        task.projectProviderImpl = DefaultProjectProvider.name
        task.aspectjAgent = aop

        //TODO does not belong here
        task.doLast(new GenerateIndexHtml(new File(outputDir, "html"), project.name))
      }
    }
  }
}
