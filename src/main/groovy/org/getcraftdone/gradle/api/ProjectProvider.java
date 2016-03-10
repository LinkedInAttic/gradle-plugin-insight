package org.getcraftdone.gradle.api;

import org.gradle.api.Project;

/**
 * Created by sfaber on 3/8/16.
 */
public interface ProjectProvider {
    Project buildProject();
}
