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

  def randomIds(): Array[Int] = {
    val r = scala.util.Random
    val lids = items.map(_.id).toArray
    val indexes = for _ <- 0 to size yield r.nextInt(size)
    return indexes.map(lids(_)).toArray
  }

  def selected(id: Int): Boolean =
    items.find(_.id == id).get.selected

}
