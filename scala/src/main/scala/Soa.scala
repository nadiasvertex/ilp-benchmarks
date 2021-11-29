package dev.csn

class Soa(aor: Aor) {
  val ids = aor.items.map(_.id).toVector
  val selectedItems = aor.items.map(_.selected).toVector

  def selected(id: Int) : Boolean =
    selectedItems(ids.indexOf(id))

}
