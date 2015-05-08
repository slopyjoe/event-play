package events.logging

import play.api.libs.json.JsValue

/**
 * ConsumerLogging - TODO Brief explanation 
 *
 */
trait ConsumerLogging {
  def log(name:String, event:JsValue):Unit
  def retrieveLogs:Map[String,Seq[JsValue]]
}

trait InMemoryConsumerLogging extends ConsumerLogging {
  import scala.collection.mutable

  lazy val logs:mutable.Map[String, Seq[JsValue]] = mutable.Map()

  override def retrieveLogs: Map[String, Seq[JsValue]] = {
    logs.toMap//switch it from mutable to immutable
  }

  override def log(name: String, event: JsValue): Unit = {

    val events:Seq[JsValue] = logs.getOrElse(name, Seq())

    logs.put(name, events ++ Seq(event))
  }
}