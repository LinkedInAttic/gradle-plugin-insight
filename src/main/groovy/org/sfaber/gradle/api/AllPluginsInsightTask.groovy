package org.sfaber.gradle.api

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.process.JavaExecSpec
import org.sfaber.gradle.internal.AllPluginsDocGenerator

/**
 * Created by sfaber on 3/6/16.
 */
class AllPluginsInsightTask extends DefaultTask {

  private final static Logger LOG = Logging.getLogger(AllPluginsInsightTask)

  @InputDirectory File pluginIdDir
  @OutputDirectory File outputDir
  @InputFiles FileCollection classpath
  @Input String projectProviderImpl

  @TaskAction void generate() {
    def cp = classpath

    LOG.lifecycle("Generating documentation for all plugins into dir: {}", outputDir.toString())

    assert pluginIdDir.isDirectory()

    project.javaexec { JavaExecSpec spec ->
      spec.classpath = cp
      spec.main = AllPluginsDocGenerator.class.name
      spec.args projectProviderImpl, pluginIdDir.absolutePath, outputDir.absolutePath
    }
  }
}
