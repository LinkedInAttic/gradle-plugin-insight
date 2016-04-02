package com.linkedin.gradle.pi.internal

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

/**
 * Created by sfaber on 3/6/16.
 */
class InsightGeneratorTest extends Specification {

  def project = new ProjectBuilder().build()
  @Rule TemporaryFolder tmp = new TemporaryFolder()

  def "doc generation sanity test"() {
    def markdown = tmp.newFile(); markdown << "Existing content needs to be replaced"
    def html = tmp.newFile(); html << "foo"

    when:
    new InsightGenerator().generateInsight(project, "java", markdown, html)

    then:
    //sanity test only, we don't have aop agent to trigger the events needed to track changes to the model
    markdown.text == "## Plugin 'java' applies: ##\n\n"
    html.text == "<h2>Plugin 'java' applies: </h2>\n\n"
  }
}
