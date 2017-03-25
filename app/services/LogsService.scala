package services

import com.google.inject.Inject
import models.Models.{LogNote, logs}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by faiaz on 13.03.17.
  */
class LogsService @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]{

  def addLog(userId: Long, content: String): Future[LogNote] = {
    val log = LogNote(userId, content)
    db.run(logs += log).map(_ => log)
      .recover { case ex: Exception => throw new InterruptedException(ex.getMessage) }
  }

  def getLogs(userId: Long): Future[Option[LogNote]] = {
    db.run(logs.filter(_.id === userId).result.headOption)
  }

  def updateLogs(userId: Long, content: Option[String]): Future[Int] = {
    content match {
      case Some(c) =>
        db.run(logs.filter(_.client_id === userId)
          .map(l => l.content)
          .update(c))
      case None => Future.successful(0)
    }
  }

  def deleteLogs(userId: Long): Future[Int] = {
    db.run(logs.filter(_.client_id === userId).delete)
  }

  def getAllLogs: Future[Seq[LogNote]] = {
    db.run(logs.result)
  }
}
