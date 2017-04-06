package services
import java.util.UUID

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import com.mohiva.play.silhouette.impl.providers.CommonSocialProfile
import models.{Profile, User}
import play.api.libs.json.Json
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.play.json.collection.JSONCollection
import utils.Logging

import scala.concurrent.Future

/**
  * Created by faiaz on 06.04.17.
  */
class UserService @Inject()(reactiveMongoApi: ReactiveMongoApi) extends IdentityService[User] with Logging {

  private def db = reactiveMongoApi.database.map(_.collection[JSONCollection](User.COLLECTION_NAME))

  def save(socialProfile: CommonSocialProfile): Future[User] = {
    log.debug(s"Save user by SocialProvider $socialProfile")

    val profile = Profile.toProfile(socialProfile)
    val selector = Json.obj("profiles.loginInfo" -> profile.loginInfo)

    db.flatMap(_.find(selector).one[User])
    .flatMap {
      case None => save(User(UUID.randomUUID(), List(profile)))
      case _ => update(profile)
    }
  }

  def save(user: User): Future[User] = {
    db.flatMap(_.insert(user))
    .map(_ => {
      log.debug(s"Successfully saved User: $user")
      user
    })
  }

  def find(userId: UUID): Future[Option[User]] = {
    log.debug(s"Searching user by UUID: $userId")

    db.flatMap(_.find(Json.obj("id" -> userId)).one[User])
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

  def link(user: User, profile: Profile): Future[User] = {
    log.debug(s"Linking Profile: $profile  ---> to User: $User")

    val selector = Json.obj("id" -> user.id)
    val pushProfile = Json.obj("$push" -> Json.obj("profiles" -> Json.toJson(profile)))

    for {
      _ <- db.flatMap(_.update(selector, pushProfile))
      user <- find(user.id)
    } yield {
      log.debug("User profile linked")
      user.get
    }
  }

  def update(profile: Profile): Future[User] = {
    log.debug(s"Updating profile $profile")

    val selector = Json.obj("profiles.loginInfo" -> profile.loginInfo)
    val update = Json.obj("$set" -> Json.obj("profiles.$" -> Json.toJson(profile)))
    for {
      _ <- db.flatMap(_.update(selector, update))
      user <- retrieve(profile.loginInfo)
    } yield {
      log.debug("User profile updated")
      user.get
    }
  }

  override def retrieve(loginInfo: LoginInfo): Future[Option[User]] = {
    log.debug(s"Retrieve User by LoginInfo: $loginInfo")

    db.flatMap(_.find(Json.obj("profiles.loginInfo" -> loginInfo)).one[User])
  }
}
