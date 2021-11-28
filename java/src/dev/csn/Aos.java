package dev.csn;

import java.util.ArrayList;
import java.util.Random;

public class Aos {
    public ArrayList<ListItem> items;
    public int size;

    Aos(int size) {
        items = new ArrayList<>(size);
        this.size = size;
        fill(size);
    }

    public void fill(int size) {
        var r = new Random();
        for (var i = 0; i < size; i++) {
            var item = new ListItem();
            item.itemId = r.nextInt();
            item.selected = r.nextInt(10) > 5;
            items.add(item);
        }
    }

    public int[] randomIds() {
        var lids = new int[size];
        var rids = new int[size];
        for (var i = 0; i < size; i++) {
            lids[i] = items.get(i).itemId;
        }
        var r = new Random();
        for (var i = 0; i < size; i++) {
            rids[i] = lids[r.nextInt(size)];
        }
        return rids;
    }

    public boolean selected(int id) {
        for (ListItem item : items) {
            if (item.itemId == id) {
                return item.selected;
            }
        }
        return false;
    }
}
