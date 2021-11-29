package dev.csn

import scala.collection.immutable.HashMap

class Mor(aor: Aor) {
  val ids = HashMap.from(for item <- aor.items yield (item.id, item))

  def selected(id: Int) : Boolean =
    ids.get(id).get.selected

}
