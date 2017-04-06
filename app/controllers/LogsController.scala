package controllers

import com.google.inject.Inject
import forms.ValidationForms.{addLogForm, singleLogsUpdate}
import play.api.libs.json.Json
import play.api.mvc.Action
import services.LogsService
import utils.Implicits._

import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by faiaz on 13.03.17.
  */
class LogsController @Inject()(logService: LogsService) extends BaseController {

  def addLogNote = Action.async { implicit req =>
    val log = addLogForm.bindFromRequest.get
    logService.addLog(log.userId, log.content).map(l => Ok(Json.toJson(l)))
  }

  def updateLogNote(userId: Long) = Action.async { implicit req =>
    val content = singleLogsUpdate.bindFromRequest.get
    logService.updateLogs(userId, content).map(res => Ok(s"Update status: $res"))
  }

  def getLogNote(userId: Long) = Action.async { implicit req =>
    logService.getLogs(userId).map {
      case Some(l) => Ok(Json.toJson(l))
      case _ => BadRequest(s"LogNote with Client ID: $userId not exist")
    }
  }

  def deleteLogNote(userId: Long) = Action.async { implicit req =>
    logService.deleteLogs(userId).map(_ => NoContent)
  }

  def getAllLogsNote = Action.async { implicit req =>
    logService.getAllLogs.map(seq => Ok(Json.obj("logs" -> seq)))
  }
}
