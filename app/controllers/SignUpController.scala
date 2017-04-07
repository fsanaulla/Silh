package controllers

import play.api.mvc.Action

/**
  * Created by faiaz on 06.04.17.
  */
class SignUpController extends BaseController {

  def startSignUp = Action {
    Ok("startSignUp")
  }

  def handleSignUp = Action {
    Ok("handleSignUp")
  }

  def completeSignUp(token: String) = Action {
    Ok("completeSignUp")
  }
}
