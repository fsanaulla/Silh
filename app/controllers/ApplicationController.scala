package controllers

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.Silhouette
import play.api.i18n.MessagesApi
import utils.MyEnv

/**
  * Created by faiaz on 07.04.17.
  */
class ApplicationController @Inject()(
                                       val silhouette: Silhouette[MyEnv],
                                       val messagesApi: MessagesApi
                                     ) extends AuthController {

  //  def index = UserAwareAction { implicit request =>
  //    Ok(views.html.index())
  //  }

}
