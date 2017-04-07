package models.dao

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.password.BCryptPasswordHasher
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import services.UserService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
/**
  * Created by faiaz on 07.04.17.
  */
class PasswordInfoDao @Inject()(userService: UserService) extends DelegableAuthInfoDAO[PasswordInfo] {

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = {
    userService.retrieve(loginInfo).map(_.map(u => PasswordInfo(BCryptPasswordHasher.ID, u.passHash)))
  }

  override def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    userService.updatePassHash(loginInfo.providerID, authInfo.password).map(_ => authInfo)
  }

  override def update(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    userService.updatePassHash(loginInfo.providerID, authInfo.password).map(_ => authInfo)
  }

  override def save(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    userService.updatePassHash(loginInfo.providerID, authInfo.password).map(_ => authInfo)
  }

  override def remove(loginInfo: LoginInfo): Future[Unit] = {
    userService.remove(loginInfo.providerID)
  }
}
