package controllers

import events.controllers.EventController
import events.logging.{InMemoryPublishLogging, InMemoryConsumerLogging}
import events.publish.{Publish, ServiceDiscovery}
import play.api._
import play.api.libs.json.{Json, JsValue}
import play.api.mvc._

object Application
  extends Controller
  with EventController
  with InMemoryConsumerLogging
  with InMemoryPublishLogging
  with ServiceDiscovery
  with Publish {


  override def publishTo: Seq[String] = Seq("http://localhost:9000")

  override implicit val application: Application = play.api.Play.maybeApplication.getOrElse(throw new RuntimeException("No Play instance running"))

  override def handleEvent: Application.HandleEvent = {
    case ("Name",event) =>
      log("Name", event)
      Accepted
    case ("Age",event) =>
      log("Age", event)
      Accepted
  }

  override def handledEvents: Seq[String] = Seq("Name", "Age")

  def consumedEvents = Action {

    Ok(Json.toJson(retrieveLogs))
  }

  def sendAge(age:Int) = Action {
    sendEvent("Age", Json.obj("howOld" -> age))
    Ok("Sent")
  }

}