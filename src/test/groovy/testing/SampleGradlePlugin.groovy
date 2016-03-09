package testing

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by sfaber on 3/8/16.
 */
class SampleGradlePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.configurations.create("sampleConfiguration").description = "This is sample configuration"
        project.plugins.apply("java")
        project.tasks.create("sampleTask").description = "This is sample task"
    }
}
