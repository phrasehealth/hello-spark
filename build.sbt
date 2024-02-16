version := "0.1.0"
organization := "com.example"
organizationName := "ACME Inc."

scalaVersion := "2.12.17"

val sparkVersion = "3.5.0"
val hadoopVer = "3.3.3"
val hadoopAwsVer = hadoopVer

// comment out provided to run Spark code with sbt run, but needed when building fat jar
libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
libraryDependencies += "org.scalatest" %% "scalatest" % "latest.integration" % "test"
libraryDependencies += "org.apache.hadoop" % "hadoop-client" % hadoopVer % "provided"
libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % hadoopAwsVer % "provided"

// Only needed for fat jar
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", "services", xs @ _*) =>
    MergeStrategy.filterDistinctLines
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case "application.conf"            => MergeStrategy.concat
  case _                             => MergeStrategy.first
}

assembly / assemblyExcludedJars := {
  val cp = (assembly / fullClasspath).value
  cp filter { f =>
    f.data.getName.contains("hadoop-hdfs-2")
    f.data.getName.contains("hadoop-client")
  }
}
