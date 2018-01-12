import java.time.format.DateTimeFormatter

import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder
import com.amazonaws.services.cloudwatch.model._
import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}

import scala.collection.JavaConverters._

// Get CloudFront metrics from CloudWatch

object CloudWatchCloudFrontExample {

  def main(args: Array[String]): Unit = {

    val distribution = "E3JZXXXXXXXXXX"
    val startTime = new DateTime(2017, 12, 1, 0, 0, DateTimeZone.UTC).toDate
    val endTime = new DateTime(2017, 12, 31, 23, 59, DateTimeZone.UTC).toDate

    val dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
    val client = AmazonCloudWatchClientBuilder.standard().withRegion("us-east-1").build()

    val dimensions = List("DistributionId" -> distribution, "Region" -> "Global").map{case (dimName, dimValue) => {
      new Dimension().withName(dimName).withValue(dimValue)
    }}.asJava

    val metricRequest = new GetMetricStatisticsRequest()
      .withStartTime(startTime)
      .withEndTime(endTime)
      .withMetricName("BytesDownloaded")
      .withNamespace("AWS/CloudFront")
      .withDimensions(dimensions)
      .withStatistics(List("Sum").asJava)
      .withPeriod(3600)

    client.getMetricStatistics(metricRequest)
      .getDatapoints.asScala
      .sortBy(_.getTimestamp)
      .foreach(datapoint => {
        val utctime = new DateTime(datapoint.getTimestamp).toDateTime(DateTimeZone.UTC)
        val valueInGB = datapoint.getSum * 1e-9
        println(s"${dateTimeFormat.print(utctime)}\t${valueInGB}")
      })

  }

}
