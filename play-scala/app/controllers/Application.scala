package controllers

import javax.inject._

import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import play.api.data.validation
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}
import play.api.i18n.Messages



case class UserData(name: String, age: Int, contactNumber: String, email: String)

@Singleton
class Application @Inject() extends Controller {
  val userForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "age" -> number(min = 12, max = 130),
      "contactNumber" -> nonEmptyText,
      "email" -> nonEmptyText
    )(UserData.apply)(UserData.unapply)
  )

  val nameConstraint: Constraint[String] = Constraint("constraints.namecheck")( {
    input =>
      val errors = input match {
        case x: String if x.length>32  => Seq(ValidationError("Name must be shorter than 32 characters"))
        case x: String if x.forall(_.isDigit) => Seq(ValidationError("A name cannot contain numbers"))
        case _ => Nil
      }
      if(errors.isEmpty) {
        Valid
      } else {
        Invalid(errors)
      }
  })

  def userPostSuccess() = Action {
    Ok(views.html.success())
  }

//  def userPostFailure(data: Option[UserData] = None) = Action {
//    BadRequest(views.html.badPost(data.getOrElse(None)))
//  }

  def getUserPost() = Action { implicit request =>
    Ok(views.html.testForm(userForm, false, None))
  }

  def userPost = Action { implicit request =>
    userForm.bindFromRequest.fold(
      failure => {
        BadRequest(views.html.testForm(userForm, false, failure.value))
      }, success => {
        Ok(views.html.testForm(userForm, true, Some(success)))
      }
    )
    /*
    userForm.bindFromRequest.fold(
      hasErrors => {
        BadRequest(views.html.testForm(hasErrors, false, hasErrors.value))
        //BadRequest(views.html.userPostFailure(hasErrors.value))
        //Ok(views.html.testForm(userForm, false, hasErrors.value))
      }, userData => {
        //    val form = userForm.bindFromRequest().get
        val name = userData.name
        val age = userData.age
        val contactNumber = userData.contactNumber
        val email = userData.email
        // @* Redirect(routes.Application.userPostSuccess()) *@
        //Ok(routes.Application.getUserPost(/*"Data gathered successfully!"*/))
        Ok(views.html.testForm(userForm, true, Some(userData)))
        //Redirect(routes.Application.getUserPost()).flashing("success" -> "Data passed successfully!")
      }
    ) */
  }
}
