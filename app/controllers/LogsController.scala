package controllers

import com.google.inject.Inject
import com.mohiva.play.silhouette.api.Silhouette
import forms.ValidationForms.{addLogForm, singleLogsUpdate}
import play.api.i18n.MessagesApi
import play.api.libs.json.Json
import services.LogsService
import utils.Implicits._
import utils.MyEnv

import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by faiaz on 13.03.17.
  */
class LogsController @Inject()(
                                logService: LogsService,
                                val silhouette: Silhouette[MyEnv],
                                val messagesApi: MessagesApi
                              ) extends AuthController {

  def addLogNote = SecuredAction.async { implicit req =>
    log.info("Save new Log Note")

    val logNote = addLogForm.bindFromRequest.get
    logService.addLog(logNote.userId, logNote.content).map(l => Ok(Json.toJson(l)))
  }

  def updateLogNote(userId: Long) = SecuredAction.async { implicit req =>
    log.info(s"Update Log Note with id: $userId")
    val content = singleLogsUpdate.bindFromRequest.get
    logService.updateLogs(userId, content).map(res => Ok(s"Update status: $res"))
  }

  def getLogNote(userId: Long) = SecuredAction.async { implicit req =>
    log.info(s"Get Log Note by id: $userId")

    logService.getLogs(userId).map {
      case Some(l) => Ok(Json.toJson(l))
      case _ => BadRequest(s"LogNote with Client ID: $userId not exist")
    }
  }

  def deleteLogNote(userId: Long) = SecuredAction.async { implicit req =>
    log.info(s"Delete Log Note by id: $userId")

    logService.deleteLogs(userId).map(_ => NoContent)
  }

  def getAllLogsNote = SecuredAction.async { implicit req =>
    log.info("Get all Log Note")

    logService.getAllLogs.map(seq => Ok(Json.obj("logs" -> seq)))
  }
}
