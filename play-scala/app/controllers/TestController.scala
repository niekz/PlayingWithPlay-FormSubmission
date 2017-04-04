package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */


@Singleton
class TestController @Inject() extends Controller {



  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */





  def test = Action {


    Ok(views.html.test("This is a test message"))
  }

//  def testForm = Action {
//
//    Ok(views.html.testForm(userForm))
//  }





}
