package utils

import models.PostgresqlModel.{Client, LogNote}
import play.api.libs.json.Json

/**
  * Created by faiaz on 12.03.17.
  */
object Implicits {
  implicit val fmtCl = Json.format[Client]
  implicit val fmtLog = Json.format[LogNote]
}