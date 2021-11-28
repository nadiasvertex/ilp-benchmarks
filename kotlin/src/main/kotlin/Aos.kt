import kotlin.random.Random

class Aos(size_: Int) {
    val size: Int
    var items: ArrayList<ListItem>

    init {
        size = size_
        val itemsInit = Array(size) { i ->
            ListItem(Random.nextInt(), Random.nextBoolean(), "Item $i", "http://items.dev/$i")
        }
        items = ArrayList(size)
        items.addAll(itemsInit)
    }

    fun randomIds(): Array<Int> {
        val lids = Array(size) { i ->
            items[i].id
        }
        val rids = Array(size) {
            lids[Random.nextInt(0, size)]
        }

        return rids
    }

    fun selected(id: Int): Boolean {
        for (item in items) {
            if (item.id == id) {
                return item.selected
            }
        }
        return false
    }
}