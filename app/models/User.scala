package models

import java.util.UUID

import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import com.mohiva.play.silhouette.impl.providers.{CommonSocialProfile, OAuth1Info}
import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * Created by faiaz on 06.04.17.
  */
case class User(id: UUID, profiles: List[Profile]) extends Identity {
  def profileFor(loginInfo:LoginInfo): Option[Profile] = profiles.find(_.loginInfo == loginInfo)
  def fullName(loginInfo:LoginInfo): Option[String] = profileFor(loginInfo).flatMap(_.fullName)
}

object User {
  implicit val userJsonFormat = Json.format[User]

  val COLLECTION_NAME = "users"
}

case class Profile(
                    loginInfo: LoginInfo,
                    confirmed: Boolean,
                    email: Option[String],
                    firstName: Option[String],
                    lastName: Option[String],
                    fullName: Option[String],
                    passwordInfo: Option[PasswordInfo],
                    oauth1Info: Option[OAuth1Info],
                    avatarUrl: Option[String]
                  )

object Profile {
  implicit val passwordInfoJsonFormat = Json.format[PasswordInfo]
  implicit val oauth1InfoJsonFormat = Json.format[OAuth1Info]
  implicit val profileJsonFormat = Json.format[Profile]

  def toProfile(p: CommonSocialProfile) = Profile(
    loginInfo = p.loginInfo,
    confirmed = true,
    email = p.email,
    firstName = p.firstName,
    lastName = p.lastName,
    fullName = p.fullName,
    passwordInfo = None,
    oauth1Info = None,
    avatarUrl = p.avatarURL
  )
}

case class UserToken(id: UUID, userId: UUID, email: String, expirationTime: DateTime, isSignUp: Boolean) {
  def isExpired: Boolean = expirationTime.isBeforeNow
}

object UserToken {
  implicit val toJson = Json.format[UserToken]

  val COLLECTION_NAME = "tokens"

  def create(userId: UUID, email: String, isSignUp: Boolean) =
    UserToken(UUID.randomUUID(), userId, email, new DateTime().plusHours(12), isSignUp)
}

