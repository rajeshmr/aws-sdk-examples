package cloudfront

import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.cloudfront.AmazonCloudFrontClient
import com.amazonaws.services.cloudfront.model.ListDistributionsRequest

object CloudFrontExample {
  def main(args: Array[String]): Unit = {
    val clientBuilder = AmazonCloudFrontClient.builder()
      clientBuilder.setRegion(Region.getRegion(Regions.US_EAST_1).toString)

    val client = clientBuilder.build()

    val req = new ListDistributionsRequest()
    val distributions = client.listDistributions(req)

    distributions.getDistributionList.getItems.forEach(distribution => distribution)
  }
}
