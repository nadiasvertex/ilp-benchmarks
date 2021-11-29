package dev.csn;

import java.util.HashMap;

public class Mor {
    public HashMap<Integer, ListItem> ids;
    Mor(Aor src) {
        ids = new HashMap<Integer, ListItem>();

        for (var item : src.items) {
            ids.put(item.itemId, item);
        }
    }

    public boolean selected(int id) {
        return ids.get(id).selected;
    }

}
