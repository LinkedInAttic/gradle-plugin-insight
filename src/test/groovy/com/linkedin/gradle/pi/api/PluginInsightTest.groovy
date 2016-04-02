package com.linkedin.gradle.pi.api

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by sfaber on 3/6/16.
 */
class PluginInsightTest extends Specification {

  def project = new ProjectBuilder().build()

  private void prepare() {
    project.plugins.apply("com.linkedin.plugin-insight")
    project.plugins.apply("java")
    project.repositories.mavenCentral()
    def t = project.tasks['pluginInsight'] as PluginInsightTask

    //so that the java process that has access to code under test
    def cp = System.getProperty("java.class.path")
    t.classpath += project.files(cp.split(":"))

    //so that there are some plugins we can generate documentation for
    def f = project.file("src/main/resources/META-INF/gradle-plugins/sample-plugin.properties")
    f.parentFile.mkdirs()
    f.text = "implementation-class=testing.plugins.SampleGradlePlugin"
  }

  def "generates plugin insight documentation"() {
    given:
    prepare()
    def t = project.tasks['pluginInsight'] as PluginInsightTask

    when:
    t.generate()

    then:
    new File(t.outputDir, "markdown/sample-plugin.md").text == """## Plugin 'sample-plugin' applies: ##

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

    new File(t.outputDir, "html/sample-plugin.html").text == """<h2>Plugin 'sample-plugin' applies: </h2>

<li>Plugin: SampleGradlePlugin
<ul>
    <li><p>Tasks:</p><ul>
        <li>sampleTask - This is sample task</li>
    </ul></li>
    <li><p>Configurations:</p><ul>
        <li>sampleConfiguration - This is sample configuration</li>
    </ul></li>
</ul>
</li>
<li>Plugin: AnotherPlugin
<ul>
    <li><p>Tasks:</p><ul>
        <li>anotherTask - This is another task</li>
    </ul></li>
    <li><p>Configurations:</p><ul>
        <li>anotherConfiguration - This is another configuration</li>
    </ul></li>
</ul>
</li>"""
  }
}
