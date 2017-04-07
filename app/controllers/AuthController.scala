package controllers

import com.mohiva.play.silhouette.api.actions.{SecuredRequest, UserAwareRequest}
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import models.User
import play.api.i18n.I18nSupport
import utils.MyEnv

/**
  * Created by faiaz on 07.04.17.
  */
trait AuthController extends BaseController with I18nSupport {
  def silhouette: Silhouette[MyEnv]
  def env: Environment[MyEnv] = silhouette.env

  def SecuredAction = silhouette.SecuredAction
  def UnsecuredAction = silhouette.UnsecuredAction
  def UserAwareAction = silhouette.UserAwareAction

  implicit def securedRequest2User[A](implicit request: SecuredRequest[MyEnv, A]): User = request.identity
  implicit def userAwareRequest2UserOpt[A](implicit request: UserAwareRequest[MyEnv, A]): Option[User] = request.identity
}
