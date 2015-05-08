package events.controllers

import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, Controller, Result}

/**
 * EventController - TODO Brief explanation 
 *
 */


trait EventController {
  self: Controller =>

  type HandleEvent = PartialFunction[(String, JsValue), Result]

  def default: HandleEvent = {
    case _ => NoContent
  }

  def subscribedEvents = Action{
    Ok(Json.obj("events" -> handledEvents))
  }

  def event(eventName:String) = Action(parse.json){implicit request =>
    handleEvent.applyOrElse((eventName, request.body), default)
  }

  def handleEvent: HandleEvent

  def handledEvents:Seq[String]

}
