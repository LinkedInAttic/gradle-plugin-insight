package org.getcraftdone.gradle.api

import org.gradle.testfixtures.ProjectBuilder
import org.kordamp.gradle.markdown.MarkdownToHtmlTask
import spock.lang.Specification

/**
 * Created by sfaber on 3/6/16.
 */
class PluginInsightTest extends Specification {

  def project = new ProjectBuilder().build()

  private void prepare() {
    project.plugins.apply("org.getcraftdone.plugin-insight")
    project.plugins.apply("java")
    project.repositories.mavenCentral()
    def t = project.tasks['pluginsInsight'] as PluginInsightTask

    //so that the java process that has access to code under test
    def cp = System.getProperty("java.class.path")
    t.classpath += project.files(cp.split(":"))

    //so that there are some plugins we can generate documentation for
    def f = project.file("src/main/resources/META-INF/gradle-plugins/sample-plugin.properties")
    f.parentFile.mkdirs()
    f.text = "implementation-class=testing.plugins.SampleGradlePlugin"
  }

  def "generates plugin docs"() {
    given:
    prepare()
    def t = project.tasks['pluginsInsight'] as PluginInsightTask

    when:
    t.generate()

    then:
    new File(t.outputDir, "sample-plugin.md").text == """## Plugin 'sample-plugin' applies: ##

* Plugin: SampleGradlePlugin
    * Tasks:
        - sampleTask - This is sample task
    * Configurations:
        - sampleConfiguration - This is sample configuration

* Plugin: AnotherPlugin
    * Tasks:
        - anotherTask - This is another task
    * Configurations:
        - anotherConfiguration - This is another configuration
"""
  }

  def "generates plugin html docs"() {
    given:
    prepare()
    def t = project.tasks['pluginsInsight'] as PluginInsightTask
    def h = project.tasks['pluginInsightHtml'] as MarkdownToHtmlTask

    when:
    t.generate()
    h.execute()

    then:
    println h.outputDir
    new File(h.outputDir, "sample-plugin.html").text.contains "'sample-plugin'"
    new File(h.outputDir, "index.html").text.contains "sample-plugin"
  }
}
