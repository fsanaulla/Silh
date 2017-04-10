package services.dao

import javax.inject.Inject

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.impl.providers.OAuth1Info
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import services.UserService

import scala.concurrent.Future

/**
  * Created by faiaz on 10.04.17.
  */
class OAuth1InfoDao @Inject()(userService: UserService) extends DelegableAuthInfoDAO[OAuth1Info] {

  override def find(loginInfo: LoginInfo): Future[Option[OAuth1Info]] = {
   userService.find(loginInfo).map(_.flatMap(_.oauth1Info))
  }

  override def add(loginInfo: LoginInfo, authInfo: OAuth1Info): Future[OAuth1Info] = {
    userService.updateOAuthInfo(loginInfo, authInfo).map(_ => authInfo)
  }

  override def update(loginInfo: LoginInfo, authInfo: OAuth1Info): Future[OAuth1Info] = {
    userService.updateOAuthInfo(loginInfo, authInfo).map(_ => authInfo)
  }

  override def save(loginInfo: LoginInfo, authInfo: OAuth1Info): Future[OAuth1Info] = {
    userService.updateOAuthInfo(loginInfo, authInfo).map(_ => authInfo)
  }

  override def remove(loginInfo: LoginInfo): Future[Unit] = {
    userService.remove(loginInfo)
  }
}
