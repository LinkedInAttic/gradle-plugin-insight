## Goal

Automatic generation of plugin documentation, based on what the plugin changes in the project (e.g. track added tasks, configurations, dependencies, etc)

## Status

Used currently in one of our plugins. Contributors are more than welcome!!! Making the html report look better would be great!!!

## Usage

In order to generate documentation for all custom Gradle plugins developed in given project:

    apply plugin: 'plugin-insight'

Then execute:

    ./gradlew pluginInsight

Then inspect the contents of "build/docs/plugins" (configurable at task level)

## Limitations

 - "org.aspectj:aspectjweaver:" dependency must be resolvable in the project.
 For example, this can be achieved by adding 'mavenCentral' repository to current project.
 - Does not document the model changes applied during 'beforeEvaluate', 'afterEvaluate', etc. (TODO)
 - Assumes that plugin ids can be found in 'src/main/resources/META-INF/gradle-plugins' (configurable at the task level)
 - Generates markdown and html only

## TODOs, future ides an plans

Contributors we need help! :)

 - make the html report pretty!!!
 - generate at runtime for any plugin
 - support afterEvaluate
 - filter the plugins, show/hide the internal plugins
 - combine with javadoc
 - show plugin id
 - sort plugins / tasks

----

# Example markdown report

Example report generated for one of our custom plugins:

## Plugin 'pemberly-app' applies: ##

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
