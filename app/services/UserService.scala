package services
import com.google.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.impl.providers.OAuth1Info
import models.User
import models.User._
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.play.json.collection.JSONCollection
import utils.Logging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by faiaz on 06.04.17.
  */
class UserService @Inject()(reactiveMongoApi: ReactiveMongoApi) extends IdentityService[User] with Logging {

  private def db = reactiveMongoApi.database.map(_.collection[JSONCollection](User.COLLECTION_NAME))

  def save(user: User): Future[User] = {
    db.flatMap(_.insert(user))
    .map(_ => {
      log.debug(s"Successfully saved User: $user")
      user
    })
  }

  def find(userId: String): Future[Option[User]] = {
    log.debug(s"Searching user by UUID: $userId")

    db.flatMap(_.find(Json.obj("id" -> userId)).one[User])
  }

  def find(loginInfo: LoginInfo): Future[Option[User]] = {
    log.debug(s"Searching user by login info: $loginInfo")

    db.flatMap(_.find(Json.obj("loginInfo" -> loginInfo)).one[User])
  }

  def confirm(loginInfo: LoginInfo): Future[User] = {
    log.debug(s"Confirmation for: $loginInfo")

    val selector = Json.obj("profiles.loginInfo" -> loginInfo)
    val confirmation = Json.obj("$set" -> Json.obj("profiles.$.confirmed" -> true))

    for {
      _ <- db.flatMap(_.update(selector, confirmation))
      user <- db.flatMap(_.find(selector).one[User])
    } yield {
      log.debug("User profile confirmed")
      user.get
    }
  }

  def update(user: User): Future[User] = {
    log.debug(s"Updating User $user")

    val selector = Json.obj("id" -> user.id)
    val update = Json.obj("$set" -> user)
    for {
      _ <- db.flatMap(_.update(selector, update))
      user <- find(user.uuidStr)
    } yield {
      log.debug("User profile updated")
      user.get
    }
  }

  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = {
    log.debug(s"Retrieve User by LoginInfo: $loginInfo")

    db.flatMap(_.find(Json.obj("id" -> loginInfo.providerID)).one[User])
  }

  def updatePassHash(loginInfo: LoginInfo, passInfo: PasswordInfo): Future[Option[User]] = {
    log.debug(s"Updating passInfo for User with id: $loginInfo to passHash: $passInfo")

    val selector = Json.obj("loginInfo" -> loginInfo)
    val update = Json.obj("$set" -> Json.obj("passwordInfo" -> passInfo))

    for {
      _ <- db.flatMap(_.update(selector, update)).map(_ => {})
      user <- find(loginInfo)
    } yield user
  }

  def updateOAuthInfo(loginInfo: LoginInfo, oAuth1Info: OAuth1Info): Future[Option[User]] = {
    log.debug(s"Updating passInfo for User with id: $loginInfo to passHash: $oAuth1Info")

    val selector = Json.obj("loginInfo" -> loginInfo)
    val update = Json.obj("$set" -> Json.obj("passwordInfo" -> oAuth1Info))

    for {
      _ <- db.flatMap(_.update(selector, update)).map(_ => {})
      user <- find(loginInfo)
    } yield user
  }

  def remove(loginInfo: LoginInfo): Future[Unit] = {
    log.debug(s"Remove User with id: $loginInfo")

    val selector = Json.obj("id" -> loginInfo)

    db.flatMap(_.remove(selector)).map(_ => {})
  }
}
