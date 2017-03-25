package services

import com.google.inject.Inject
import models.Models.{Client, clients}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._
import utils.ErrorHandler

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


/**
  * Created by faiaz on 12.03.17.
  */
class ClientService @Inject()(override protected val dbConfigProvider: DatabaseConfigProvider, err: ErrorHandler) extends HasDatabaseConfigProvider[JdbcProfile]{

  def insertClient(client: Client): Future[Client] = {
    db.run(clients += client).map(_ => client)
  }

  def getClient(id: Long): Future[Option[Client]] = {
    db.run(clients.filter(_.id === id).result.headOption)
  }

  def updateClient(id: Long, first_name: Option[String], last_name: Option[String]): Future[Int] = {
    (first_name, last_name) match {
      case (Some(f), Some(l)) =>
        db.run(clients.filter(_.id === id)
          .map(c => (c.first_name, c.last_name))
          .update((f, l)))
      case (Some(f), None) =>
        db.run(clients.filter(_.id === id)
          .map(c => c.first_name)
          .update(f))
      case (None, Some(l)) =>
        db.run(clients.filter(_.id === id)
          .map(c => c.last_name)
          .update(l))
      case _ => throw new Exception("No param for update defined")
    }
  }

  def deleteClient(id: Long): Future[Int] = {
    db.run(clients.filter(_.id === id).delete)
  }

  def allClients: Future[Seq[Client]] = {
    db.run(clients.result)
  }
}
