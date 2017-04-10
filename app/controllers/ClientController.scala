package controllers

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.Silhouette
import forms.ValidationForms._
import play.api.i18n.MessagesApi
import play.api.libs.json.Json
import services.ClientService
import utils.Implicits._
import utils.MyEnv

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by faiaz on 12.03.17.
  */
class ClientController @Inject()(
                                  clientService: ClientService,
                                  val silhouette: Silhouette[MyEnv],
                                  val messagesApi: MessagesApi
                                ) extends AuthController {

  def addClient = SecuredAction.async { implicit req =>
    log.info("Save new client")

    val client = addClientForm.bindFromRequest.get
    clientService.insertClient(client.first_name, client.last_name)
      .map { c =>
        log.info(s"Successfully created client $client")
        Ok(Json.toJson(c))
      }
  }

  def getClient(userId: Long) = SecuredAction.async { implicit req =>
    log.info(s"Get client by id: $userId")

    clientService.getClient(userId).map {
      case Some(u) =>
        log.info(s"Successfully retrieved client $u for id:$userId")
        Ok(Json.toJson(u))
      case None =>
        log.info(s"No client retrieved for id:$userId")
        BadRequest(s"UserID: $userId not exist")
    }
  }

  def updateClient(userId: Long) = SecuredAction.async { implicit req =>
    log.info(s"Update client with id: $userId")

    val update = updateClientForm.bindFromRequest.get
    clientService.updateClient(userId, update.first_name, update.last_name)
      .map { res =>
        log.info(s"Successfully update for id:$userId")
        Ok(Json.obj("status" -> res))
      }
  }

  def deleteClient(userId: Long) = SecuredAction.async { implicit req =>
    log.info(s"Delete client by id: $userId")

    clientService.deleteClient(userId)
      .map { _ =>
        log.info(s"Successfully deleting for id:$userId")
        NoContent
      }
  }

  def getAllClient = SecuredAction.async { implicit req =>
    log.info("Get all clients")
    clientService.allClients
      .map { seq =>
        log.info("Returning all clients")
        Ok(Json.obj("clients" -> seq))
      }
  }
}
