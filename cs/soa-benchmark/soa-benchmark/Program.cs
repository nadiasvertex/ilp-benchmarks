// See https://aka.ms/new-console-template for more information

using System;
using System.Diagnostics;
using soa_benchmark;

long NanoTime(long ticks)
{
    return (long) (1000000000.0 * (double) ticks / Stopwatch.Frequency);
}

var aor = new Aor(300);
var soa = new Soa(aor);
var mor = new Mor(aor);
const int benchmarkIterations = 100000;
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

Console.WriteLine($"aor\t{NanoTime(bestAorTime)}\t{aorCount}");

for (var j = 0; j < benchmarkIterations; j++)
{
    var st = Stopwatch.StartNew();
    foreach (var id in sourceIds)
    {
        if (soa.Selected(id))
        {
            soaCount++;
        }
    }

    st.Stop();
    var duration = st.ElapsedTicks;
    if (duration < bestSoaTime)
    {
        bestSoaTime = duration;
    }
}

Console.WriteLine($"soa\t{NanoTime(bestSoaTime)}\t{soaCount}");

for (var j = 0; j < benchmarkIterations; j++)
{
    var st = Stopwatch.StartNew();
    foreach (var id in sourceIds)
    {
        if (mor.Selected(id))
        {
            morCount++;
        }
    }

    st.Stop();
    var duration = st.ElapsedTicks;
    if (duration < bestMorTime)
    {
        bestMorTime = duration;
    }
}

Console.WriteLine($"mor\t{NanoTime(bestMorTime)}\t{morCount}");
