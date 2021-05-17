package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._


class TestApiSimulation extends Simulation {

  //http cong
  val httpConf = http.baseUrl("https://reqres.in")
    .header("Accept", "Application/json")
    .header("content-type", "Application/json")

  //Scenario
  val scn = scenario("get user")
    .exec(http("get user request")
      .get("/api/users/2")
      .check(status is 200)
    )

  //setup
  setUp(scn.inject(atOnceUsers(100))).protocols(httpConf)


}
