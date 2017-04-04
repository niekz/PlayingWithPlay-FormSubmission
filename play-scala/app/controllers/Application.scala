package controllers

import javax.inject._

import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import play.api.data.validation
import play.api.i18n.Messages



case class UserData(name: String, age: Int, contactNumber: String, email: String)

@Singleton
class Application @Inject() extends Controller {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */

  val userForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "age" -> number(min = 0, max = 130),
      "contactNumber" -> nonEmptyText,
      "email" -> nonEmptyText
    )(UserData.apply)(UserData.unapply)
  )

  def userPostSuccess() = Action {
    Ok(views.html.success())
  }

  def getUserPost() = Action { implicit request =>
    Ok(views.html.testForm(userForm, false, None))
  }

  def userPost = Action { implicit request =>
//    val form = userForm.bindFromRequest().get
//    val name = form.name
//    val age = form.age
//    val contactNumber = form.contactNumber
//    val email = form.email
//
//     Ok(name + " : " + age + " : " + contactNumber + " : " + email)

    userForm.bindFromRequest.fold(
      hasErrors => {
        BadRequest(views.html.testForm(hasErrors, false, None))
      }, userData => {
        //    val form = userForm.bindFromRequest().get
        val name = userData.name
        val age = userData.age
        val contactNumber = userData.contactNumber
        val email = userData.email
        // @* Redirect(routes.Application.userPostSuccess()) *@
        //Ok(routes.Application.getUserPost(/*"Data gathered successfully!"*/))
        Ok(views.html.testForm(userForm, true, Some(userData.name)))
        //Redirect(routes.Application.getUserPost()).flashing("success" -> "Data passed successfully!")
      }
    )
    //Ok(views.html.testForm(userForm))
  }
}
