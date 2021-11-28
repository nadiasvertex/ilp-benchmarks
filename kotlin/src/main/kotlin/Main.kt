import kotlin.system.measureNanoTime

fun main() {
    val aos = Aos(300)
    val soa = Soa(aos)

    val benchmarkIterations = 100000
    val sourceIds = aos.randomIds()

    println("Layout\tDuration (ns)\tCount")

    var aosCount = 0
    var soaCount = 0
    var bestAosTime: Long = 1.shl(30)
    var bestSoaTime: Long = 1.shl(30)

    for (j in 0..benchmarkIterations) {
        val duration = measureNanoTime {
            for (id in sourceIds) {
                if (aos.selected(id)) {
                    aosCount++
                }
            }
        }
        if (duration < bestAosTime) {
            bestAosTime = duration
        }
    }

    println("aos\t${bestAosTime}\t${aosCount}")

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
}