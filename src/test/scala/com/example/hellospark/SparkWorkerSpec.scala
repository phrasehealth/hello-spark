package com.example.hellospark

import org.scalatest.BeforeAndAfter
import org.scalatest.funspec._

class HelloSparkSpec extends AnyFunSpec with BeforeAndAfter {

  describe("create a simple dataframe in a unit test") {
    it("should work and show the dataframe") {
      val spark = HelloSpark.makeSparkSession()

      import spark.implicits._

      val testData = List("The quick brown fox jumps over the lazy dog")
      val testInput = spark.sparkContext.parallelize(testData)

      val output = HelloSpark.wordCount(testInput).collect().toMap

      assertResult(output) {
        Map(
          "lazy" -> 1,
          "dog" -> 1,
          "over" -> 1,
          "brown" -> 1,
          "quick" -> 1,
          "jumps" -> 1,
          "fox" -> 1,
          "the" -> 2
        )
      }
    }
  }
}
