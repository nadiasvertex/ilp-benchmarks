package dev.csn

class Soa(aor: Aor) {
  val ids = Array.from(aor.items.map(_.id))
  val selectedItems = Array.from(aor.items.map(_.selected))

  def selected(id: Int) : Boolean =
    selectedItems(ids.indexOf(id))

}
