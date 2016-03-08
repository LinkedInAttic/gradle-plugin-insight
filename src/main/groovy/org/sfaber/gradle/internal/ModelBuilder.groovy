package org.sfaber.gradle.internal

/**
 * Created by sfaber on 3/5/16.
 */
class ModelBuilder {

  LinkedList<ModelSnapshot> snapshots = new LinkedList<ModelSnapshot>()

  void add(ModelSnapshot sn) {
    if (snapshots.isEmpty()) {
      snapshots.add(sn)
    } else {
      snapshots.add(snapshots.getLast().delta(sn))
    }
  }

  public String toString() {
    def out = new StringBuilder()
    snapshots.each {
      out.append(it).append("\n\n")
    }
    out
  }
}
