package com.getcraftdone.gradle.api

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.process.JavaExecSpec
import com.getcraftdone.gradle.internal.PluginDocGenerator

/**
 * Created by sfaber on 3/6/16.
 */
class PluginInsightTask extends DefaultTask {

  private final static Logger LOG = Logging.getLogger(PluginInsightTask)

  @Input String pluginId
  @OutputFile File outputFile
  @InputFiles FileCollection classpath

  @TaskAction generate() {
    def cp = classpath
    LOG.lifecycle("Generating documentation for plugin '{}' into file://{}", pluginId, outputFile)
    project.javaexec { JavaExecSpec spec ->
      spec.classpath = cp
      spec.main = PluginDocGenerator.class.name
      spec.args pluginId, outputFile.absolutePath
    }
  }
}
