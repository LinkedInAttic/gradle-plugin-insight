package org.sfaber.gradle.api

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.process.JavaExecSpec
import org.sfaber.gradle.internal.PluginDocGenerator

/**
 * Created by sfaber on 3/6/16.
 */
class AllPluginsDocTask extends DefaultTask {

  private final static Logger LOG = Logging.getLogger(AllPluginsDocTask)

  @InputDirectory File pluginIdDir
  @OutputDirectory File outputDir
  @InputFiles FileCollection classpath

  @TaskAction generate() {
    def cp = classpath

    LOG.lifecycle("Generating documentation for all plugins plugin '{}' into file://{}", pluginId, outputFile)
    project.javaexec { JavaExecSpec spec ->
      spec.classpath = cp
      spec.main = PluginDocGenerator.class.name
      spec.args pluginIdDir.absolutePath, outputDir.absolutePath
    }
  }
}
