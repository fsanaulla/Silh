package services.dao

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import services.UserService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
/**
  * Created by faiaz on 07.04.17.
  */
class PasswordInfoDao @Inject()(userService: UserService) extends DelegableAuthInfoDAO[PasswordInfo] {

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = {
    userService.retrieve(loginInfo).map(_.flatMap(_.passwordInfo))
  }

  override def add(loginInfo: LoginInfo, passInfo: PasswordInfo): Future[PasswordInfo] = {
    userService.updatePassHash(loginInfo, passInfo).map(_ => passInfo)
  }

  override def update(loginInfo: LoginInfo, passInfo: PasswordInfo): Future[PasswordInfo] = {
    userService.updatePassHash(loginInfo, passInfo).map(_ => passInfo)
  }

  override def save(loginInfo: LoginInfo, passInfo: PasswordInfo): Future[PasswordInfo] = {
    userService.updatePassHash(loginInfo, passInfo).map(_ => passInfo)
  }

  override def remove(loginInfo: LoginInfo): Future[Unit] = {
    userService.remove(loginInfo)
  }
}
