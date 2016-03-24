package org.getcraftdone.gradle.internal

import org.getcraftdone.gradle.internal.snapshot.ModelSnapshot
import org.getcraftdone.gradle.internal.snapshot.DefaultModelSnapshot
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.getcraftdone.gradle.internal.aop.PluginLifecycleAspect

/**
 * Created by sfaber on 3/5/16.
 */
class InsightListener implements PluginLifecycleAspect.Listener {

  Project project

  void init(Project project) {
    PluginLifecycleAspect.LISTENER = this
    this.project = project
  }

  public String getMarkdownDoc() {
    result*.toMarkdown().join("\n")
  }

  public String getHtmlDoc() {
    result*.toHtml().join("\n")
  }

  LinkedList<ModelSnapshot> queue = new LinkedList<>()
  LinkedList<ModelSnapshot> result = new LinkedList<>()

  void beforeApplied(Plugin<Project> plugin) {
    queue << new DefaultModelSnapshot().take(plugin, project)
  }

  void afterApplied(Plugin<Project> plugin) {
    def s = new DefaultModelSnapshot().take(plugin, project)
    def delta = s.minus(queue.removeLast())
    result.each {
      delta = delta.minus(it)
    }
    result.addFirst(delta)
  }
}
