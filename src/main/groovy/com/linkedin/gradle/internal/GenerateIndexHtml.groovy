package com.linkedin.gradle.internal

import groovy.transform.CompileStatic
import org.gradle.api.Action
import org.gradle.api.Task

/**
 * Created by sfaber on 3/20/16.
 */
@CompileStatic
class GenerateIndexHtml implements Action<Task> {

    private final File htmlDir
    private final String projectName

    GenerateIndexHtml(File htmlDir, String projectName) {
        this.projectName = projectName
        this.htmlDir = htmlDir
    }

    @Override
    void execute(Task task) {
        def output = new File(htmlDir, "index.html")
        Set<String> plugins = htmlDir.listFiles()*.name as TreeSet

        if (plugins.size() == 0) {
            output << "<h1>Project '$projectName' does not expose public Gradle plugins.</h1>"
            return
        }

        def index = new StringBuilder("""<h1>Project '$projectName' exposes following public Gradle plugins:</h1>
<ul>
""")
        for (String it: plugins) {
            index.append("    <li><a href='$it'>${it.replaceAll('.html$', '')}</a></li>\n")
        }
        output << index.append("</ul>")
    }
}
