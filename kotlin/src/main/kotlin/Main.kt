import kotlin.system.measureNanoTime

fun main() {
    val aor = Aor(300)
    val soa = Soa(aor)
    val mor = Mor(aor)

    val benchmarkIterations = 100000
    val sourceIds = aor.randomIds()

    println("Layout\tDuration (ns)\tCount")

    var aosCount = 0
    var soaCount = 0
    var morCount = 0
    var bestAosTime: Long = 1.shl(30)
    var bestSoaTime: Long = 1.shl(30)
    var bestMorTime: Long = 1.shl(30)

    for (j in 0..benchmarkIterations) {
        val duration = measureNanoTime {
            for (id in sourceIds) {
                if (aor.selected(id)) {
                    aosCount++
                }
            }
        }
        if (duration < bestAosTime) {
            bestAosTime = duration
        }
    }

    println("aor\t${bestAosTime}\t${aosCount}")

    for (j in 0..benchmarkIterations) {
        val duration = measureNanoTime {
            for (id in sourceIds) {
                if (soa.selected(id)) {
                    soaCount++
                }
            }
        }
        if (duration < bestSoaTime) {
            bestSoaTime = duration
        }
    }

    println("soa\t${bestSoaTime}\t${soaCount}")

    for (j in 0..benchmarkIterations) {
        val duration = measureNanoTime {
            for (id in sourceIds) {
                if (mor.selected(id)) {
                    morCount++
                }
            }
        }
        if (duration < bestMorTime) {
            bestMorTime = duration
        }
    }

    println("mor\t${bestMorTime}\t${morCount}")
}