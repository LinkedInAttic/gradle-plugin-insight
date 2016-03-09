package org.sfaber.gradle.api

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by sfaber on 3/6/16.
 */
class PluginInsightPluginTest extends Specification {

  def project = new ProjectBuilder().build()

  def "creates and configures the task"() {
    given:
    project.plugins.apply("plugin-insight")
    project.plugins.apply("java")

    when:
    def t = project.tasks.create("t", PluginInsightTask)

    then:
    t.classpath
  }

  def "generates plugin docs"() {
    project.plugins.apply("plugin-insight")
    project.plugins.apply("java")
    def t = project.tasks['allPluginsInsight'] as AllPluginsInsightTask

    //so that the java process that has access to code under test
    def cp = System.getProperty("java.class.path")
    t.classpath += project.files(cp.split(":"))

    //so that there are some plugins we can generate documentation for
    def f = project.file("src/main/resources/META-INF/gradle-plugins/sample-plugin.properties")
    f.parentFile.mkdirs()
    f.text = "implementation-class=testing.SampleGradlePlugin"

    when:
    t.generate()

    then:
    new File(t.outputDir, "sample-plugin.txt").text == """ --- Documentation for 'sample-plugin' plugin ---

  * Plugin: LifecycleBasePlugin
    Tasks: none
    Configurations: none


  * Plugin: BasePlugin
    Tasks:
     - assemble - Assembles the outputs of this project.
    Configurations:
     - archives - Configuration for archive artifacts.
     - default - Configuration for default artifacts.


  * Plugin: ReportingBasePlugin
    Tasks: none
    Configurations: none


  * Plugin: LanguageBasePlugin
    Tasks: none
    Configurations: none


  * Plugin: BinaryBasePlugin
    Tasks: none
    Configurations: none


  * Plugin: JavaBasePlugin
    Tasks:
     - buildDependents - Assembles and tests this project and all projects that depend on it.
     - buildNeeded - Assembles and tests this project and all projects it depends on.
    Configurations: none


  * Plugin: JavaPlugin
    Tasks:
     - check - Runs all checks.
     - classes - Assembles main classes.
     - compileJava - Compiles main Java source.
     - compileTestJava - Compiles test Java source.
     - jar - Assembles a jar archive containing the main classes.
     - javadoc - Generates Javadoc API documentation for the main source code.
     - processResources - Processes main resources.
     - processTestResources - Processes test resources.
     - test - Runs the unit tests.
     - testClasses - Assembles test classes.
    Configurations:
     - compile - Compile classpath for source set 'main'.
     - runtime - Runtime classpath for source set 'main'.
     - testCompile - Compile classpath for source set 'test'.
     - testRuntime - Runtime classpath for source set 'test'.


  * Plugin: SampleGradlePlugin
    Tasks:
     - sampleTask - This is sample task
    Configurations:
     - sampleConfiguration - This is sample configuration


"""
  }
}
