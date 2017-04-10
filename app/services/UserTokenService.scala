package services

import java.util.UUID

import com.google.inject.Inject
import models.UserToken
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
class UserTokenService @Inject()(reactiveMongoApi: ReactiveMongoApi) extends Logging {

  private def db = reactiveMongoApi.database.map(_.collection[JSONCollection](UserToken.COLLECTION_NAME))

  def find(id: UUID): Future[Option[UserToken]] = {
    log.debug(s"Searching UserToken by UUID $id")

    val selector = Json.obj("id" -> id)

    db.flatMap(_.find(selector).one[UserToken])
  }

  def save(token: UserToken): Future[UserToken] = {
    log.debug(s"Saving token: $token")

    db.flatMap(_.insert(token)).map(_ => token)
  }

  def remove(id: UUID): Future[Unit] = {
    log.debug(s"Removing token with UUID: $id")

    val selector = Json.obj("id" -> id)

    db.flatMap(_.remove(selector)).map(_ => {})
  }
}
