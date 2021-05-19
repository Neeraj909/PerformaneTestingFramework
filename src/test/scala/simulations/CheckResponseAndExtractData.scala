package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt

class CheckResponseAndExtractData extends Simulation {
  val httpConf = http.baseUrl("https://gorest.co.in")
    .header("Authorization", "Bearer 253c90823c7ecc52a1dfb0857ec0212cea069a98757a0c47c0ecbfd8ba1d6f25")

  val scn = scenario("check correlation and extract data")
    //first call get all the user
    .exec(http("get all the user")
      .get("/public-api/users")
      .check(jsonPath("$.data[0].id").saveAs("userId"))
    )
    .exec(http("get specific user")
      .get("/public-api/users/${userId}")
      .check(jsonPath("$.data.id").is("20"))
      .check(jsonPath("$.data.name").is("The Hon. Tejas Kaul"))
      .check(jsonPath("$.data.email").is("tejas_hon_kaul_the@shanahan-mcglynn.biz"))
    )
  setUp(scn.inject(atOnceUsers(2))).protocols(httpConf)
}
