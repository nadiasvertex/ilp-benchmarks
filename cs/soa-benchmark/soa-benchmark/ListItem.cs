namespace soa_benchmark
{
    public record ListItem
    {
        public int Id;
        public bool Selected;
        public string Title;
        public string Url;

        public ListItem(int id, bool selected, string title, string url)
        {
            Id = id;
            Selected = selected;
            Title = title;
            Url = url;
        }
    }
}