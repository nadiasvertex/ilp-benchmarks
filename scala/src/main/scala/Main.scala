package dev.csn

@main def main: Unit = {

  val aor = Aor(300)
  val soa = Soa(aor)
  val mor = Mor(aor)
  val benchmarkIterations = 100000
  val sourceIds = aor.randomIds()

  println(s"Layout\tDuration (ns)\tCount")

  var aorCount = 0;
  var aorBestTime = 1L << 30;
  var soaCount = 0;
  var soaBestTime = 1L << 30;
  var morCount = 0;
  var morBestTime = 1L << 30;

  for (_ <- 0 to benchmarkIterations) do {
    val startTime = System.nanoTime()
    sourceIds
      .foreach(id => {
        if (aor.selected(id)) {
          aorCount += 1
        }
      })

    val endTime = System.nanoTime()
    val duration = endTime - startTime
    if (duration < aorBestTime) {
      aorBestTime = duration
    }
  }

  println(s"aor\t${aorBestTime}\t${aorCount}")

  for (_ <- 0 to benchmarkIterations) do {
    val startTime = System.nanoTime()
    sourceIds
      .foreach(id => {
        if (soa.selected(id)) {
          soaCount += 1
        }
      })
    val endTime = System.nanoTime()
    val duration = endTime - startTime
    if (duration < soaBestTime) {
      soaBestTime = duration
    }
  }

  println(s"soa\t${soaBestTime}\t${soaCount}")

  for (_ <- 0 to benchmarkIterations) do {
    val startTime = System.nanoTime()
    sourceIds
      .foreach(id => {
        if (mor.selected(id)) {
          morCount += 1
        }
      })
    val endTime = System.nanoTime()
    val duration = endTime - startTime
    if (duration < morBestTime) {
      morBestTime = duration
    }
  }

  println(s"mor\t${morBestTime}\t${morCount}")
}