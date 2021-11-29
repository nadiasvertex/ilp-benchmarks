using System.Collections;
using System.Collections.Generic;

namespace soa_benchmark
{
    public class Mor
    {
        public Dictionary<int, ListItem> items;

        public Mor(Aor aor)
        {
            items = new Dictionary<int, ListItem>();
            foreach (var item in aor.Items)
            {
                    items.Add(item.Id, item);
            }
        }

        public bool Selected(int id)
        {
            return items[id].Selected;
        }
    }
}