package com.linkedin.gradle.pi.internal.plugin

/**
 * Created by sfaber on 3/6/16.
 */
class PluginsFinder {

  Set<String> getAllPlugins(File searchDir) {
    if (searchDir == null || !searchDir.isDirectory()) {
      throw new IllegalArgumentException("Provided search dir must be a valid directory: '" + searchDir + "'")
    }
    def out = new HashSet<String>()
    searchDir.listFiles().each { File f ->
      if (f.name.endsWith(".properties")) {
        out.add(f.name.replaceAll("\\.properties", ""))
      }
    }
    out
  }
}
