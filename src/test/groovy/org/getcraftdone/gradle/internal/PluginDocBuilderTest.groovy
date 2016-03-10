package org.getcraftdone.gradle.internal

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
import testing.plugins.AnotherPlugin
import testing.plugins.SampleGradlePlugin

/**
 * Created by sfaber on 3/9/16.
 */
class PluginDocBuilderTest extends Specification {

    def project = new ProjectBuilder().build()

    def "builds doc"() {
        def p = new PluginDocBuilder()
        p.init(project)

        when:
        //simulating applying sample plugin
        def p1 = new SampleGradlePlugin()
        p.beforeApplied(p1)
        project.configurations.create("sampleConfiguration").description = "This is sample configuration"

        //simulating applying another plugin inside sample plugin
        def p2 = new AnotherPlugin()
        p.beforeApplied(p2)
        project.configurations.create("anotherConfiguration").description = "This is another configuration"
        project.tasks.create("anotherTask").description = "This is another task"
        p.afterApplied(p2)

        //finish applying sample plugin
        project.tasks.create("sampleTask").description = "This is sample task"
        project.tasks.create("sampleTask2").description = "This is sample task 2"
        p.afterApplied(p1)

        then:
        p.toString() == """* Plugin: AnotherPlugin
   * Tasks:
      - anotherTask - This is another task
   * Configurations:
      - anotherConfiguration - This is another configuration

* Plugin: SampleGradlePlugin
   * Tasks:
      - sampleTask - This is sample task
      - sampleTask2 - This is sample task 2
   * Configurations:
      - sampleConfiguration - This is sample configuration

"""
    }
}
