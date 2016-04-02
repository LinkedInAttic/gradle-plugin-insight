package com.linkedin.gradle.pi.internal.project

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import com.linkedin.gradle.pi.api.ProjectProvider

/**
 * Created by sfaber on 3/8/16.
 */
class DefaultProjectProvider implements ProjectProvider {
    @Override
    Project buildProject() {
        new ProjectBuilder().build()
    }
}
