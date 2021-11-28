class Soa(aos: Aos) {
    val ids: Array<Int>
    val selectedItems: Array<Boolean>

    init {
        ids = Array(aos.size) { i ->
            aos.items[i].id
        }
        selectedItems = Array(aos.size) { i ->
            aos.items[i].selected
        }
    }

    fun selected(id: Int): Boolean {
        return selectedItems[ids.indexOf(id)]
    }
}