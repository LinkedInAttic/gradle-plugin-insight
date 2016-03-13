package org.getcraftdone.gradle.api

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.process.JavaExecSpec
import org.getcraftdone.gradle.internal.InsightGeneratorMain

/**
 * Generates documentation about all plugins found in provided directory.
 */
class PluginInsightTask extends DefaultTask {

  private final static Logger LOG = Logging.getLogger(PluginInsightTask)

  /**
   * Location of directory that contains plugin identifiers e.g. the dir with "<plugin-id>.properties" files
   */
  @InputDirectory File pluginIdDir

  /**
   * Where the documentation is generated
   */
  @OutputDirectory File outputDir

  /**
   * To avoid classpath/classloader issues, we will generate documentation in a separate java process.
   * The classpath needs:
   *   a) plugin classes (main source set output), so that they can be applied to test project
   *   b) dependencies of plugin classes (main source set runtime classpath), so that plugins can be applied
   *   c) 'gradle-plugin-insight' classes along with their dependencies
   */
  @InputFiles FileCollection classpath

  /**
   * Fully qualified name of the project provider implementation class
   */
  @Input String projectProviderImpl

  /**
   * The aspectj agent jar, so that we can weave in beforeApplied/afterApplied listeners
   */
  @InputFile File aspectjAgent

  @TaskAction void generate() {
    def cp = classpath

    LOG.lifecycle("Generating documentation for all plugins into dir: {}", outputDir.toString())

    assert pluginIdDir.isDirectory()

    project.javaexec { JavaExecSpec spec ->
      spec.classpath = cp
      spec.main = InsightGeneratorMain.class.name
      spec.args projectProviderImpl, pluginIdDir.absolutePath, outputDir.absolutePath
      spec.jvmArgs "-javaagent:$aspectjAgent.absolutePath"
    }
  }
}
