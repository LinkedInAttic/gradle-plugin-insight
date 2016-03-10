package org.getcraftdone.gradle.api

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by sfaber on 3/6/16.
 */
class PluginInsightTest extends Specification {

  def project = new ProjectBuilder().build()

  def "generates plugin docs"() {
    project.plugins.apply("org.getcraftdone.plugin-insight")
    project.plugins.apply("java")
    def t = project.tasks['pluginsInsight'] as PluginInsightTask

    //so that the java process that has access to code under test
    def cp = System.getProperty("java.class.path")
    t.classpath += project.files(cp.split(":"))

    //so that there are some plugins we can generate documentation for
    def f = project.file("src/main/resources/META-INF/gradle-plugins/sample-plugin.properties")
    f.parentFile.mkdirs()
    f.text = "implementation-class=testing.plugins.SampleGradlePlugin"

    when:
    t.generate()

    then:
    new File(t.outputDir, "sample-plugin.txt").text == """## Documentation for 'sample-plugin' plugin ##

* Plugin: AnotherPlugin
   * Tasks:
      - anotherTask - This is another task
   * Configurations:
      - anotherConfiguration - This is another configuration

* Plugin: SampleGradlePlugin
   * Tasks:
      - sampleTask - This is sample task
   * Configurations:
      - sampleConfiguration - This is sample configuration

"""
  }
}
