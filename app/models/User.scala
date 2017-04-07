package models

import java.util.{Date, UUID}

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}
import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * Created by faiaz on 06.04.17.
  */
case class User(
                id: UUID = UUID.randomUUID(),
                email: Option[String],
                phone: Option[String],
                passHash: String,
                firstName: Option[String] = None,
                lastName: Option[String] = None,
                created: Date = new Date()) extends Identity {

  def fullName(loginInfo:LoginInfo): Option[String] = for {
    f <- firstName
    l <- lastName
  } yield f + " " + l

  def uuidStr: String = id.toString
}

object User {
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

