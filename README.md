## Goal

Automatic generation of plugin documentation, based on what the plugin changes in the project (e.g. track added tasks, configurations, dependencies, etc)

## Status

Prototype, actively developed

## Usage

In order to generate documentation for all custom Gradle plugins developed in given project:

    apply plugin: 'plugin-insight'

Then execute:

    ./gradlew pluginInsight

Then inspect the contents of "build/doc/plugins" (configurable at task level)

## Limitations

 - Does not document the model changes applied during 'beforeEvaluate', 'afterEvaluate', etc.
This can be fixed but for now, we don't need it that much.
 - Assumes that plugin ids can be found in 'src/main/resources/META-INF/gradle-plugins' (configurable at the task level)
 - Generates markdown

## Future ides an plans

 - we have tons of ideas but let's see if what we have is useful first
 - html report?
 - generate at runtime for any task?
 - task for generation for given task?

----

# Example report

Example report generated for one of our custom plugins:

## Documentation for 'pemberly-app' plugin ##

* Plugin: LifecycleBasePlugin
   * Tasks: none
   * Configurations: none

* Plugin: BasePlugin
   * Tasks: none
   * Configurations:
      - archives - Configuration for archive artifacts.
      - default - Configuration for default artifacts.

* Plugin: LiReposPlugin
   * Tasks: none
   * Configurations: none

* Plugin: ReportingBasePlugin
   * Tasks: none
   * Configurations: none

* Plugin: ProjectReportsPlugin
   * Tasks:
      - dependencyReport - Generates a report about your library dependencies.
      - htmlDependencyReport - Generates an HTML report about your library dependencies.
      - projectReport - Generates a report about your project.
      - propertyReport - Generates a report about your properties.
      - taskReport - Generates a report about your tasks.
   * Configurations: none

* Plugin: LiSpecPlugin
   * Tasks: none
   * Configurations: none

* Plugin: LiBaseSettingsPlugin
   * Tasks: none
   * Configurations: none

* Plugin: LiVariantPluginImpl
   * Tasks: none
   * Configurations: none

* Plugin: LiArtifactBasePlugin
   * Tasks:
      - assemble - Assembles all artifacts (javadoc and sources require -PallArtifacts).
      - clean - Deletes the build directory.
      - generateModuleArtifactSpec - Generates module-artifact-spec.json file and refreshes artifact-spec.json file
      - ivyForArchives - Generates ivy xml file for given project.
   * Configurations: none
