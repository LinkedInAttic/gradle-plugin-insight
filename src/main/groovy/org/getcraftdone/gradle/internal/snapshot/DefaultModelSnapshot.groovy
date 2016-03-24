package org.getcraftdone.gradle.internal.snapshot

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration

/**
 * Created by sfaber on 3/12/16.
 */
class DefaultModelSnapshot implements ModelSnapshot {

    Collection<Configuration> configurations = []
    Collection<Task> tasks = []
    Plugin plugin

    ModelSnapshot take(Plugin plugin, Project project) {
        this.configurations = new LinkedHashSet<>(project.configurations)
        this.tasks = new LinkedHashSet<>(project.tasks)
        this.plugin = plugin
        this
    }

    ModelSnapshot minus(ModelSnapshot other) {
        DefaultModelSnapshot o = other as DefaultModelSnapshot
        return new DefaultModelSnapshot(
                configurations: this.configurations - o.configurations,
                tasks: this.tasks - o.tasks,
                plugin: this.plugin)
    }

    String toMarkdown() {
        def sb = new StringBuilder()
        sb.append("* Plugin: " + plugin.getClass().getSimpleName() + "\n")
        if (tasks.isEmpty()) {
            sb.append("    * Tasks: none\n")
        } else {
            sb.append("    * Tasks:\n")
            tasks.each {
                sb.append("        - $it.name")
                if (it.description) {
                    sb.append(" - $it.description\n")
                } else {
                    sb.append("\n")
                }
            }
        }
        if (configurations.isEmpty()) {
            sb.append("    * Configurations: none\n")
        } else {
            sb.append("    * Configurations:\n")
            configurations.each {
                sb.append("        - $it.name")
                if (it.description) {
                    sb.append(" - $it.description\n")
                } else {
                    sb.append("\n")
                }
            }
        }

        sb.toString()
    }

    String toHtml() {
        def sb = new StringBuilder()
        sb.append("<li><p>Plugin: " + plugin.getClass().getSimpleName() + "</p>\n")
        sb.append("<ul>\n")
        if (tasks.isEmpty()) {
            sb.append("    <li>Tasks: none</li>\n")
        } else {
            sb.append("    <li><p>Tasks:</p><ul>\n")
            tasks.each {
                sb.append("        <li>$it.name")
                if (it.description) {
                    sb.append(" - $it.description</li>\n")
                } else {
                    sb.append("</li>\n")
                }
            }
            sb.append("    </ul></li>\n")
        }
        if (configurations.isEmpty()) {
            sb.append("    <li>Configurations: none</li>\n")
        } else {
            sb.append("    <li><p>Configurations:</p><ul>\n")
            configurations.each {
                sb.append("        <li>$it.name")
                if (it.description) {
                    sb.append(" - $it.description</li>\n")
                } else {
                    sb.append("</li>\n")
                }
            }
            sb.append("    </ul></li>\n")
        }
        sb.append("</ul>\n")
        sb.append("</li>")
        sb.toString()
    }
}
