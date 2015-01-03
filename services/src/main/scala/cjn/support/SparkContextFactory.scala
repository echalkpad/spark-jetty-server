package cjn.support

import org.apache.spark.{SparkContext, SparkConf}

/**
 * A simple factory to create a SparkContext.
 */
object SparkContextFactory {


  /**
   * This method is responsible for shipping jars off to the executors and setting any base state on the
   * SparkConf. It's important that we ship off our services jar so that the executors can access any
   * classes we've created.
   */
  def newSparkContext: SparkContext = {

    /**
     * Just for simplicity, the spark home is coming in through a system property
     */
    val sparkHome = sys.props.get("spark.home").getOrElse("")

    val sparkConf = new SparkConf()
      .setJars(Array(SparkContext.jarOfClass(this.getClass).get))
      .setSparkHome(sparkHome)
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.scheduler.mode", "FAIR")

    new SparkContext("yarn-client", "TestSparkJettyServer", sparkConf)
  }
}
