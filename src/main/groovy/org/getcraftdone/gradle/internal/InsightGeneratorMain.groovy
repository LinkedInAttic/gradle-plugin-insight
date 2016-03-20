package org.getcraftdone.gradle.internal

import org.getcraftdone.gradle.api.ProjectProvider
import org.getcraftdone.gradle.internal.plugin.PluginsFinder

/**
 * Created by sfaber on 3/5/16.
 */
class InsightGeneratorMain {

  public static void main(String[] args) {
    assert args.length == 3 : "Expected 2 arguments: \n" +
            "  1. fully qualified class name of the ProjectProvider implementation\n" +
            "  2. dir path that contains the the plugin id files (e.g. <plugin-id>.properties files, typically it is src/main/resources)"
            "  3. dir path where the output documentation should be generated"

    def projectProviderImpl = args[0]
    ProjectProvider providerImpl = Class.forName(projectProviderImpl).newInstance() as ProjectProvider
    def pluginsIdDir = new File(args[1])
    def outputDir = new File(args[2])

    def plugins = new PluginsFinder().getAllPlugins(pluginsIdDir)
    plugins.each {
      def output = new File(outputDir, "${it}.md")
      println "Generating documentation for plugin '$it' to file://$output.absolutePath"
      def p = providerImpl.buildProject()
      new InsightGenerator().textDoc(p, it, output)
    }
  }
}
