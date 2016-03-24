package org.getcraftdone.gradle.internal

import groovy.transform.CompileStatic
import org.getcraftdone.gradle.api.ProjectProvider
import org.getcraftdone.gradle.internal.plugin.PluginsFinder

/**
 * Created by sfaber on 3/5/16.
 */
@CompileStatic
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

    File htmlDir = new File(outputDir, "html")
    File markdownDir = new File(outputDir, "markdown")

    for (String it : plugins) {
      def markdown = new File(markdownDir, "${it}.md")
      def html = new File(htmlDir, "${it}.html")
      println "Generating documentation for plugin '$it' to dir: $outputDir"
      def p = providerImpl.buildProject()
      new InsightGenerator().generateInsight(p, it, markdown, html)
    }
  }
}
