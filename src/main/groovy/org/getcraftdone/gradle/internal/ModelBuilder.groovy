package org.getcraftdone.gradle.internal

/**
 * Created by sfaber on 3/5/16.
 */
class ModelBuilder {

  LinkedList<ModelSnapshot> snapshots = new LinkedList<ModelSnapshot>()

  void add(ModelSnapshot sn) {
    for (ModelSnapshot previous : snapshots) {
      sn = sn.minus(previous)
    }
    snapshots.add(sn)
  }

  public String toString() {
    def out = new StringBuilder()
    snapshots.each {
      out.append(it).append("\n")
    }
    out
  }
}
