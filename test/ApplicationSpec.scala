import org.scalatestplus.play._
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._
import play.api.{Application, Mode}

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by faiaz on 12.03.17.
  */
class ApplicationSpec extends PlaySpec {

  implicit lazy val app: Application = new GuiceApplicationBuilder()
    .in(Mode.Test)
    .build()

  "Application Controller" should {
//    "POST /cl" in {
//      val Some(result) = route(app, FakeRequest(POST, "/cl")
//        withJsonBody Json.obj(
//        "first_name" -> "Fayaz",
//        "last_name" -> "San")
//      )
//
//      status(result) mustBe OK
//
//      val json = contentAsJson(result)
//
//      (json \ "first_name").as[String] mustEqual "Fayaz"
//    }
//
    "GET /cl:1231" in {
      val Some(result) = route(app, FakeRequest(GET, "/cl/1231"))

      result.map(_ === NO_CONTENT)
    }

//    "POST /lg" in {
//      val Some(result) = route(app, FakeRequest(POST, "/lg")
//        .withJsonBody(Json.obj(
//          "userId" -> 4,
//          "content" -> "Some content"
//        )))
//
//      val json = contentAsJson(result)
//
//      (json \ "content").as[String] mustEqual "Some content"
//    }

//    "DELETE /cl:2" in {
//      val Some(result) = route(app, FakeRequest(DELETE, "/cl/2"))
//
//      result.map(res => res mustEqual NO_CONTENT)
//    }
//    "PUT /cl/1" in {
//      val Some(result) = route(app, FakeRequest(PUT, "/cl/1")
//        .withJsonBody(
//          Json.obj(
//            "first_name" -> "Martin"
//          )
//        ))
//
//      val str = contentAsString(result)
//
//      str mustEqual "Update status: 1"
//    }
  }
}
