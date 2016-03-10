package com.getcraftdone.gradle.internal

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import com.getcraftdone.gradle.api.ProjectProvider

/**
 * Created by sfaber on 3/8/16.
 */
class DefaultProjectProvider implements ProjectProvider {
    @Override
    Project buildProject() {
        new ProjectBuilder().build()
    }
}
