package dev.csn;

public class Main {

    public static void main(String[] args) {
        var aor = new Aor(300);
        var soa = new Soa(aor);
        var mor = new Mor(aor);

        int benchmarkIterations = 100000;

        var checkIds = aor.randomIds();
        long bestAorTime = 1 << 30;
        long aosCount = 0;
        long bestSoaTime = 1 << 30;
        long soaCount = 0;
        long bestMorTime = 1 << 30;
        long morCount = 0;

        System.out.println("Layout\tDuration (ns)\tCount");

        for (int j = 0; j < benchmarkIterations; ++j) {
            long startTime = System.nanoTime();
            for (int id : checkIds) {
                if (aor.selected(id)) {
                    aosCount++;
                }
            }
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            if (duration < bestAorTime) {
                bestAorTime = duration;
            }
        }

        System.out.println("aor\t" + bestAorTime + "\t" + aosCount);

        for (int j = 0; j < benchmarkIterations; ++j) {
            long startTime = System.nanoTime();
            for (int id : checkIds) {
                if (soa.selected(id)) {
                    soaCount++;
                }
            }
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            if (duration < bestSoaTime) {
                bestSoaTime = duration;
            }
        }

        System.out.println("soa\t" + bestSoaTime + "\t" + soaCount);

        for (int j = 0; j < benchmarkIterations; ++j) {
            long startTime = System.nanoTime();
            for (int id : checkIds) {
                if (mor.selected(id)) {
                    morCount++;
                }
            }
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            if (duration < bestMorTime) {
                bestMorTime = duration;
            }
        }

        System.out.println("mor\t" + bestMorTime + "\t" + morCount);
    }

}
