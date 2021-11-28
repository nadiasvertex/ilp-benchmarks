// See https://aka.ms/new-console-template for more information

using System;
using System.Diagnostics;
using soa_benchmark;

long nanoTime(long ticks)
{
    return (long) (1000000000.0 * (double) ticks / Stopwatch.Frequency);
}

var aor = new Aor(300);
const int benchmarkIterations = 1000;
var sourceIds = aor.RandomIds();

var aorCount = 0;
var soaCount = 0;
var morCount = 0;
var bestAorTime = 1L << 30;
var bestSoaTime = 1L << 30;
var bestMorTime = 1L << 30;

Console.WriteLine("Layout\tDuration (ns)\tCount");

for (var j = 0; j < benchmarkIterations; j++)
{
    var st = Stopwatch.StartNew();
    foreach (var id in sourceIds)
    {
        if (aor.Selected(id))
        {
            aorCount++;
        }
    }

    st.Stop();
    var duration = st.ElapsedTicks;
    if (duration < bestAorTime)
    {
        bestAorTime = duration;
    }
}

Console.WriteLine($"aor\t{nanoTime(bestAorTime)}\t{aorCount}");