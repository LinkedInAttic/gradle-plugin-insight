package org.getcraftdone.gradle.internal

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

/**
 * Created by sfaber on 3/20/16.
 */
class GenerateIndexHtmlTest extends Specification {

    @Rule TemporaryFolder tmp = new TemporaryFolder()

    def "no plugins"() {
        def dir = tmp.newFolder()
        assert dir.isDirectory() && dir.listFiles().length == 0

        when:
        new GenerateIndexHtml(dir, "foo").execute(null)

        then:
        new File(dir, "index.html").text == "<h1>Project 'foo' does not expose public Gradle plugins.</h1>"
    }

    def "plugins"() {
        def dir = tmp.newFolder()
        assert dir.isDirectory() && dir.listFiles().length == 0
        new File(dir, "a-plugin.html") << ""
        new File(dir, "b-plugin.html") << ""

        when:
        new GenerateIndexHtml(dir, "foo").execute(null)

        then:
        new File(dir, "index.html").text == """<h1>Project 'foo' exposes following public Gradle plugins:</h1>
<ul>
    <li><a href='a-plugin.html'>a-plugin</a></li>
    <li><a href='b-plugin.html'>b-plugin</a></li>
</ul>"""

    }
}
