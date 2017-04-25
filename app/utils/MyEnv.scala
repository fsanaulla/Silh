package utils

import com.mohiva.play.silhouette.api.Env
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import models.User

/**
  * Created by faiaz on 07.04.17.
  */
trait MyEnv extends Env {
  type I = User
  type A = CookieAuthenticator
}
