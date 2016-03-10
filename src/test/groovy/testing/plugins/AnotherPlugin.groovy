package testing.plugins;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Created by sfaber on 3/9/16.
 */
public class AnotherPlugin implements Plugin<Project> {

    public void apply(Project project) {
        project.configurations.create("anotherConfiguration").description = "This is another configuration"
        project.tasks.create("anotherTask").description = "This is another task"
    }
}
