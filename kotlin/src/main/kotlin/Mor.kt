class Mor(aor: Aor) {
    val ids: HashMap<Int, ListItem>

    init {
        ids = HashMap(aor.size)
        for(item in aor.items) {
            ids[item.id] = item
        }
    }

    fun selected(id: Int): Boolean {
        return ids[id]?.selected ?: false
    }
}