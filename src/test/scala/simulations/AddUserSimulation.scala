package simulations


import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class AddUserSimulation extends Simulation {

  val httpConf = http.baseUrl("https://reqres.in")
    .header("Accept", "Application/json")
    .header("content-type", "Application/json")

  val scn = scenario("Add User")
    .exec(http("Add user request")
      .post("/api/users")
      .body(RawFileBody("./src/test/resources/jsonbody/adduser.json")).asJson
      .header("content-type", "Application/json")
      .check(status is 201)
    )
    .pause(3)
    .exec(http("get user request")
      .get("/api/users/2")
      .check(status is 200)
    )
    .pause(2)

    .exec(http("get all user")
      .get("/api/users?page=2")
      .check(status is 200))
  setUp(scn.inject(atOnceUsers(100))).protocols(httpConf)

}
