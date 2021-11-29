class Soa(aor: Aor) {
    val ids: Array<Int>
    val selectedItems: Array<Boolean>

    init {
        ids = Array(aor.size) { i ->
            aor.items[i].id
        }
        selectedItems = Array(aor.size) { i ->
            aor.items[i].selected
        }
    }

    fun selected(id: Int): Boolean {
        return selectedItems[ids.indexOf(id)]
    }
}