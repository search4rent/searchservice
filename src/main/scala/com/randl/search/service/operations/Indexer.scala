package com.randl.search.service.operations

import com.randl.core.servicelib.elasticsearch.ESClient
import com.randl.search.service.resquest.RentItem
import com.codahale.jerkson.Json
import java.util.UUID
import scala.collection.JavaConversions._
import java.lang.{Double => jDouble}
import org.elasticsearch.action.bulk.BulkRequestBuilder

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 10/7/13
 * Time: 9:01 AM
 * To change this template use File | Settings | File Templates.
 */
trait Indexer extends ESClient {


  def toRentItem(entry: Any): RentItem = {
    val map = entry.asInstanceOf[java.util.HashMap[String, Any]].toMap
    RentItem(
      id = UUID.fromString(map.getOrElse("id", "-").asInstanceOf[String]),
      description = map.getOrElse("description", "-").asInstanceOf[String],
      location = null,
      name = map.getOrElse("name", "-").asInstanceOf[String],
      picture = map.getOrElse("picture", new java.util.ArrayList).asInstanceOf[java.util.ArrayList[String]].toList,
      price = map.getOrElse("price", "0") match {
        case y: java.lang.Long => new jDouble(y * 1D)
        case y: jDouble => y
        case _ => 0
      },
      category = map.getOrElse("category", new java.util.ArrayList).asInstanceOf[java.util.ArrayList[String]].toList,
      user = UUID.fromString(map.getOrElse("user", "-").asInstanceOf[String])
    )

  }


  def indexer(entry: Any): BulkRequestBuilder = {
    val item = toRentItem(entry)
    val writeRequest = client.prepareIndex()
      .setIndex(indexES)
      .setType(typeES)
      .setId(item.id.toString)
      .setSource(Json.generate(item))
    val bulk = client.prepareBulk()
    bulk.add(writeRequest)

  }

  def delete(entry: Any): BulkRequestBuilder = {
    val item = toRentItem(entry)
    val deleteRequest = client.prepareDelete().setId(item.id.toString)
    val bulk = client.prepareBulk()
    bulk.add(deleteRequest)
  }


}
