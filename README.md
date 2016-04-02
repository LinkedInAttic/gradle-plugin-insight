## Goal

Automatic, effortless, accurate documentation for any Gradle plugin.
Seriously.

This plugin can be even better! 
Sign up for one of the features we plan.
There is nothing better than the smell of fresh pull request in the morning.

## Motivation

At LinkedIn we created and maintain many custom Gradle plugins to support our engineers across many technology stacks.
We needed an effortless way to accurately document our plugins, without the necessity to maintain the documentation manually.
In march'16 we came up with an idea to generate absolutely accurate documentation of any Gradle plugin.
To generate the documentation, we apply given plugin to a test Gradle project and observe changes in the model.
This way, we can detect and document things like:

 - tasks the plugin adds
 - other plugins the plugin applies
 - configurations the plugin adds
 - ...

## Usage

In order to generate documentation for all public custom Gradle plugins developed in given project:

    apply plugin: 'com.linkedin.plugin-insight'

Then execute:

    ./gradlew pluginInsight

Then inspect the contents of "build/docs/plugins" (configurable at task level)

## Limitations

 - "org.aspectj:aspectjweaver:" dependency must be resolvable in the project.
 For example, this can be achieved by adding 'mavenCentral' repository to current project.
 - Does not document the model changes applied during 'beforeEvaluate', 'afterEvaluate', etc. (planned)
 - Assumes that plugin ids can be found in 'src/main/resources/META-INF/gradle-plugins' (configurable at the task level)
 - Generates markdown and html

## Plan

Help us making this plugin even better!

 - support afterEvaluate
 - make the html report look professionally beautiful (there are times in life when I wished I was a web dev)
 - make the markdown report neat and tidy
 - generate at runtime, on-demand, to the console for any plugin, even built-in Gradle plugins
 - filter out irrelevant plugins (option?), show/hide the internal plugins
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
