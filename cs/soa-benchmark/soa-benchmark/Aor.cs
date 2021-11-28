using System;
using System.Collections;
using System.Collections.Generic;
using System.Data;

namespace soa_benchmark
{
    public class Aor
    {
        public List<ListItem> Items;
        public int Size;

        public Aor(int size)
        {
            Size = size;
            Items = new List<ListItem>(size);
            
            Fill();
        }

        public void Fill()
        {
            var r = new Random();
            for (var i = 0; i < Size; i++)
            {
                Items.Add(new ListItem(r.Next(), r.Next() %10 > 5, $"Item {i}", $"http://item.dev/{i}"));
            }
        }

        public int[] RandomIds()
        {
            var r = new Random();
            var lids = new int[Size];
            var rids = new int[Size];
            for (var i = 0; i < Size; i++)
            {
                lids[i] = Items[i].Id;
            }
            for (var i = 0; i < Size; i++)
            {
                rids[i] = lids[r.Next(0, Size)];
            }

            return rids;
        }

        public bool Selected(int id)
        {
            foreach (var item in Items)
            {
                if (item.Id == id)
                {
                    return item.Selected;
                }
            }

            return false;
        }
    }
}