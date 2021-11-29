package dev.csn

import scala.collection.mutable.ArrayBuffer

class Aor(size: Int) {
  val items = ArrayBuffer[ListItem]()

  fill()

  def fill() = {
    val r = scala.util.Random
    for (_ <- 0 to size) do {
      val id = r.nextInt()
      items += ListItem(id, r.nextBoolean, s"Item ${id}", s"http://item.dev/${id}")
    }
  }

  def randomIds(): Vector[Int] = {
    val r = scala.util.Random
    val lids = items.map(_.id).toVector
    val rids = ArrayBuffer[Int]()

    for (_ <- 0 to size) do
      rids += lids(r.nextInt(size))

    return Vector.from(rids)
  }

  def selected(id: Int): Boolean =
    items.find(_.id == id).get.selected

}
