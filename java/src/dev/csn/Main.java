package dev.csn;

public class Main {

    public static void main(String[] args) {
        var aos = new Aos(300);
        var soa = new Soa(aos);

        int benchmarkIterations = 100000;

        var checkIds = aos.randomIds();
        long bestAosTime = 1 << 30;
        long aosCount = 0;
        long bestSoaTime = 1 << 30;
        long soaCount = 0;

        for (int j = 0; j < benchmarkIterations; ++j) {
            long startTime = System.nanoTime();
            for (int id : checkIds) {
                if (aos.selected(id)) {
                    aosCount++;
                }
            }
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            if (duration < bestAosTime) {
                bestAosTime = duration;
            }
        }

        System.out.println("Layout\tDuration (ns)\tCount");
        System.out.println("aos\t" + bestAosTime + "\t" + aosCount);

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
    }

}
