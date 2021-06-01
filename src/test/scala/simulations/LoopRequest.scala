package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class LoopRequest extends Simulation {
  val httpConf = http.baseUrl("https://reqres.in")
    .header("Accept", "Application/json")
    .header("content-type", "Application/json")

  def getAllUserRequest() = {
    repeat(2) {
      exec(http("get all user request").
        get("/api/users?page=2")
        .check(status.is(200)))
    }
  }

  def getSingleUserRequest() = {
    repeat(2) {
      exec(http("get single user request")
        .get("/api/users/2")
        .check(status.is(200)))
    }
  }

  def getAddUserRequest() = {
    repeat(2) {
      exec(http("add user")
        .post("/api/users")
        .body(RawFileBody("./src/test/resources/jsonbody/adduser.json")).asJson
        .header("content-type", "Application/json")
        .check(status is 201))
    }
  }

  var scn = scenario("user request")
  .exec(getAllUserRequest())
    .pause(2)
  .exec(getSingleUserRequest())
    .pause(2)
  .exec(getAddUserRequest())

  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf)
}
