package com.example.hellospark

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.SparkSession

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd.RDD

/** In order to call this with sbt run you need to comment out % "provided" in
  * build.sbt, or submit to Spark with spark-submit.
  */
object HelloSpark {

  def makeSparkSession(
      name: String = "Test job",
      master: String = "local"
  ): SparkSession = {
    val spark = SparkSession
      .builder()
      .appName(name)
      .config("spark.master", master)
      .getOrCreate()
    spark.sparkContext.setLogLevel("ERROR")
    spark
  }

  def wordCount(words: RDD[String]): RDD[(String, Int)] = {
    words
      .flatMap(line => line.split(" "))
      .map(word => (word.toLowerCase(), 1))
      .reduceByKey { case (x, y) => x + y }
  }

  def main(args: Array[String]): Unit = {
    val inputFile = args(0)
    val outputFile = args(1)
    val spark = makeSparkSession()
    val input = spark.sparkContext.textFile(inputFile)
    wordCount(input)
      .saveAsTextFile(outputFile)
    spark.stop()
  }
}
