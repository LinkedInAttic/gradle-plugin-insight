package org.getcraftdone.gradle.internal

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
    def f = tmp.newFile()
    f << "Existing content needs to be replaced"

    when:
    new InsightGenerator().generateMarkdown(project, "java", f)

    then:
    //sanity test only, we don't have aop agent to trigger the events needed to track changes to the model
    f.text == "## Plugin 'java' applies: ##\n\n"
  }
}
