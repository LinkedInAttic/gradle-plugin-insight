package com.getcraftdone.gradle.internal

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

/**
 * Created by sfaber on 3/6/16.
 */
class PluginDocGeneratorTest extends Specification {

  def project = new ProjectBuilder().build()
  @Rule TemporaryFolder tmp = new TemporaryFolder()

  def "generates doc"() {
    def f = tmp.newFile()

    when:
    new PluginDocGenerator().textDoc(project, "java", f)

    then:
    f.text == """ --- Documentation for 'java' plugin ---

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


"""
  }
}
