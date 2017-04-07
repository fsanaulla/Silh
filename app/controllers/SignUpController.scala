package controllers

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.Silhouette
import play.api.i18n.MessagesApi
import play.api.mvc.Action
import utils.MyEnv

/**
  * Created by faiaz on 06.04.17.
  */
class SignUpController @Inject()(
                                  val silhouette: Silhouette[MyEnv],
                                  val messagesApi: MessagesApi
                                ) extends AuthController {

  def startSignUp = UserAwareAction { implicit request =>
    Ok("startSignUp")
  }

  def handleSignUp = Action {
    Ok("handleSignUp")
  }

  def completeSignUp(token: String) = Action {
    Ok("completeSignUp")
  }
}
