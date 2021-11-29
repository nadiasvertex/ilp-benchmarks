package dev.csn;

public class Soa {
    public int[] ids;
    public boolean[] selected;

    Soa(Aor src) {
        ids = new int[src.items.size()];
        selected = new boolean[src.items.size()];

        for (var i = 0; i < ids.length; i++) {
            var item = src.items.get(i);
            ids[i] = item.itemId;
            selected[i] = item.selected;
        }
    }

    public boolean selected(int id) {
        for (int i = 0; i < ids.length; ++i) {
            if (ids[i] == id) {
                return selected[i];
            }
        }
        return false;
    }

}
