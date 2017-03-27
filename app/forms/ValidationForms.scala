package forms


import play.api.data.Form
import play.api.data.Forms._

/**
  * Created by faiaz on 12.03.17.
  */
object ValidationForms {

  case class AddClientForm(first_name: String, last_name: String)
  case class UpdateClientForm(first_name: Option[String], last_name: Option[String])
  case class AddLogForm(userId: Long, content: String)

  val addClientForm = Form(
    mapping(
      "first_name" -> nonEmptyText,
      "last_name" -> nonEmptyText
    )(AddClientForm.apply)(AddClientForm.unapply)
  )

  val addLogForm = Form(
    mapping(
      "userId" -> longNumber,
      "content" -> nonEmptyText
    )(AddLogForm.apply)(AddLogForm.unapply)
  )

  val singleLogsUpdate = Form(
    single(
      "content" -> optional(nonEmptyText)
    )
  )

  val updateClientForm = Form(
    mapping(
      "first_name" -> optional(text),
      "last_name" -> optional(text)
    )(UpdateClientForm.apply)(UpdateClientForm.unapply)
  )
}
