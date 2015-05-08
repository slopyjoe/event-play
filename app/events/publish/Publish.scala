package events.publish

import events.logging.PublishLogging
import play.api.Application
import play.api.libs.json.JsValue
import play.api.libs.ws.WS

/**
 * Publish - TODO Brief explanation 
 *
 */
trait Publish {
  self: ServiceDiscovery with PublishLogging =>

  implicit val application:Application

  def sendEvent(name:String, event:JsValue) = {
    import scala.concurrent.ExecutionContext.Implicits.global
    publishTo.foreach { uri =>
      WS
        .url(s"$uri/event/$name")
        .post(event)
        .onComplete(handleResponse(uri))
    }
  }


}
