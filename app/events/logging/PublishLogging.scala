package events.logging

import play.api.libs.ws.WSResponse

import scala.util.{Failure, Success, Try}

/**
 * PublishLogging - TODO Brief explanation 
 *
 */
trait PublishLogging {

  def handleResponse(who:String)(response:Try[WSResponse]):Unit

}

trait InMemoryPublishLogging extends PublishLogging {
  lazy val logger = play.api.Logger("InMemoryPublishedEvents")
  override def handleResponse(who:String)(response: Try[WSResponse]): Unit = response match {
    case Success(response) => logger.info(s"Event consumed by $who with ${response.status}")
    case Failure(error) => logger.error(s"Event failed to be consumed by $who",error)
  }
}