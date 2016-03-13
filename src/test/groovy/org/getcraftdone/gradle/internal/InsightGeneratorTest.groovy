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

  def "generates doc for java plugin"() {
    def f = tmp.newFile()

    when:
    new InsightGenerator().textDoc(project, "java", f)

    then: //sanity test only
    f.text
  }
}
