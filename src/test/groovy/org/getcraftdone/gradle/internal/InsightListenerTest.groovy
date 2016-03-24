package org.getcraftdone.gradle.internal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by sfaber on 3/9/16.
 */
class InsightListenerTest extends Specification {

    def project = new ProjectBuilder().build()

    class DummySamplePlugin implements Plugin<Project> { void apply(Project project) {} }
    class DummyOtherPlugin implements Plugin<Project> { void apply(Project project) {} }

    def "builds doc"() {
        def p = new InsightListener()
        p.init(project)

        when:
        //simulating applying sample plugin
        def p1 = new DummySamplePlugin()
        p.beforeApplied(p1)
        project.configurations.create("sampleConfiguration").description = "This is sample configuration"

        //simulating applying another plugin inside sample plugin
        def p2 = new DummyOtherPlugin()
        p.beforeApplied(p2)
        project.configurations.create("anotherConfiguration").description = "This is another configuration"
        project.tasks.create("anotherTask").description = "This is another task"
        p.afterApplied(p2)

        //finish applying sample plugin
        project.tasks.create("sampleTask").description = "This is sample task"
        project.tasks.create("sampleTask2").description = "This is sample task 2"
        p.afterApplied(p1)

        then:
        p.getMarkdownDoc() == """* Plugin: DummySamplePlugin
    * Tasks:
        - sampleTask - This is sample task
        - sampleTask2 - This is sample task 2
    * Configurations:
        - sampleConfiguration - This is sample configuration

* Plugin: DummyOtherPlugin
    * Tasks:
        - anotherTask - This is another task
    * Configurations:
        - anotherConfiguration - This is another configuration
"""
        p.getHtmlDoc() == """<li>Plugin: DummySamplePlugin
<ul>
    <li><p>Tasks:</p><ul>
        <li>sampleTask - This is sample task</li>
        <li>sampleTask2 - This is sample task 2</li>
    </ul></li>
    <li><p>Configurations:</p><ul>
        <li>sampleConfiguration - This is sample configuration</li>
    </ul></li>
</ul>
</li>
<li>Plugin: DummyOtherPlugin
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
