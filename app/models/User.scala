package models

import java.util.{Date, UUID}

import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import com.mohiva.play.silhouette.impl.providers.OAuth1Info
import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * Created by faiaz on 06.04.17.
  */
case class User(
                id: UUID = UUID.randomUUID(),
                loginInfo: LoginInfo,
                email: Option[String],
                phone: Option[String],
                firstName: Option[String] = None,
                lastName: Option[String] = None,
                passwordInfo: Option[PasswordInfo],
                oauth1Info: Option[OAuth1Info],
                created: Date = new Date()) extends Identity {

  def fullName(loginInfo: LoginInfo): Option[String] = for {
    f <- firstName
    l <- lastName
  } yield f + " " + l

  def uuidStr: String = id.toString
}

object User {
  implicit val passwordInfoJsonFormat = Json.format[PasswordInfo]
  implicit val oauth1InfoJsonFormat = Json.format[OAuth1Info]
  implicit val userJsonFormat = Json.format[User]

  val COLLECTION_NAME = "users"
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

