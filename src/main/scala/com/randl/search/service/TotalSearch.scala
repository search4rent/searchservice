package com.randl.search.service

import java.util.{UUID, Locale}
import com.randl.search.service.resquest.RentItem
import org.elasticsearch.common.geo.GeoPoint
import java.lang.{Double => jDouble}
import scala.collection.JavaConversions._
import com.randl.search.service.response.TotalSearchResponse

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 9/1/13
 * Time: 7:20 PM
 * To change this template use File | Settings | File Templates.
 */
trait TotalSearch extends SuggestSearch {
  def totalSearch(locale: Locale, searchString: String, init: Int, end: Int): TotalSearchResponse = {
    val search = client
      .prepareSearch("rendl")
      .setTypes("item")
      .setQuery(queryBool(searchString))
      .setFrom(init).setSize(end).execute().actionGet()
    search.getHits.getHits.toList match {

      case head :: tail =>
        TotalSearchResponse(search.getHits.getTotalHits,
          (head :: tail).map(
            x => {
              val price: jDouble = x.getSource.get("price") match {
                case y: java.lang.Integer => new jDouble(y * 1.0)
                case _ => head.getSource.get("price").asInstanceOf[jDouble]
              }
              val description = x.getSource.get("description").asInstanceOf[String]
              RentItem(
                id = UUID.fromString(x.id()),
                description = if (description.size < 140) description else description.substring(0, 140).concat("..."),
                location = x.getSource.get("location").asInstanceOf[GeoPoint],
                name = x.getSource.get("name").asInstanceOf[String],
                picture = x.getSource.get("picture").asInstanceOf[java.util.List[String]].toList,
                price = price,
                category = x.getSource.get("picture").asInstanceOf[java.util.List[String]].toList,
                user = UUID.fromString(x.getSource.get("user").asInstanceOf[String])
              )
            }
          )
        )
      case _ => TotalSearchResponse(search.getHits.getTotalHits, List.empty)

    }
  }
}
