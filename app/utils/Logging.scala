package utils

import play.api.Logger

/**
  * Created by faiaz on 13.03.17.
  */
trait Logging {
  val log = Logger("application")
}
