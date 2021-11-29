namespace soa_benchmark
{
    public class Soa
    {
        public readonly int[] Ids;
        public readonly bool[] SelectedItems;


        public Soa(Aor aor)
        {
            Ids = new int[aor.Size];
            SelectedItems = new bool[aor.Size];
            for (var i = 0; i < aor.Size; ++i)
            {
                var item = aor.Items[i];
                Ids[i] = item.Id;
                SelectedItems[i] = item.Selected;
            }
        }

        public bool Selected(int id)
        {
            var i = 0;
            foreach (var currentId in Ids)
            {
                if (currentId == id)
                {
                    return SelectedItems[i];
                }

                i++;
            }

            return false;
        }
        
    }
}