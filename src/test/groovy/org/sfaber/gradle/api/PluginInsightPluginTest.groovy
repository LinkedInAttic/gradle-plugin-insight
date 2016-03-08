package org.sfaber.gradle.api

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by sfaber on 3/6/16.
 */
class PluginInsightPluginTest extends Specification {

  def project = new ProjectBuilder().build()

  def "allows creation of tasks"() {
    given:
    project.plugins.apply("plugin-insight")
    project.plugins.apply("java")

    when:
    def t = project.tasks.create("t", PluginDocTask)

    then:
    t.classpath
  }
}
