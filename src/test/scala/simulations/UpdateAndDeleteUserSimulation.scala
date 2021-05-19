package simulations
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration.DurationInt

class UpdateAndDeleteUserSimulation extends Simulation {
  val httpConf = http.baseUrl("https://reqres.in")
    .header("Accept", "Application/json")
    .header("content-type", "Application/json")

  val scn = scenario("Update User")

    //first updating user
    .exec(http("update user request")
      .put("/api/users/2")
      .body(RawFileBody("./src/test/resources/jsonbody/updateuser.json")).asJson
      .header("content-type", "Application/json")
      .check(status.in(200 to 201))

    )
    .pause(5)

    // deleting user
    .exec(http("delete user request")
      .delete("/api/users/2")
      .check(status.in(200 to 204))

    )
  setUp(scn.inject(atOnceUsers(11))).protocols(httpConf)

}
