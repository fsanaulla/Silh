package models

import slick.driver.PostgresDriver.api._
import slick.lifted.TableQuery
/**
  * Created by faiaz on 12.03.17.
  */
object Models {

  val clients: TableQuery[ClientTable] = TableQuery[ClientTable]
  val logs: TableQuery[LogNoteTable] = TableQuery[LogNoteTable]

  final case class Client(first_name: String, last_name: String, id: Long = 0)
  final case class LogNote(client_id: Long, content: String, id: Long = 0)

  final case class ClientTable(tag: Tag) extends Table[Client](tag, "test_clients2") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def first_name = column[String]("first_name")
    def last_name = column[String]("last_name")

    override def * = (first_name, last_name, id) <> (Client.tupled, Client.unapply)
  }

  final case class LogNoteTable(tag: Tag) extends Table[LogNote](tag, "logs_test2") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def client_id = column[Long]("client_id")
    def content = column[String]("content")

    override def * = (client_id, content, id) <> (LogNote.tupled, LogNote.unapply)

    def client = foreignKey("client_fk", client_id, clients)(_.id)
  }
}
