package com.search4rent.search.service

import com.search4rent.search.service.elasticsearch.ElasticSearchClient
import com.search4rent.search.service.resquest.RentItem
import org.apache.lucene.queryparser.flexible.core.builders.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import java.util.UUID
import org.elasticsearch.common.geo.GeoPoint

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 8/2/13
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
trait ItemSearch extends ESClient{


  import scala.collection.JavaConversions._

  def getItemES(id: String): RentItem = {
    val search = client.prepareSearch("rendl").setTypes("item")
      .setQuery(QueryBuilders.fieldQuery("id",id)).execute().actionGet()
    search.getHits.getHits.toList match {
      case head :: tail => RentItem(
        id = UUID.fromString(head.id()),
        description = head.getSource.get("description").asInstanceOf[String],
        location = head.getSource.get("location").asInstanceOf[GeoPoint],
        name = head.getSource.get("name").asInstanceOf[String],
        picture = head.getSource.get("picture").asInstanceOf[java.util.List[String]].toList,
        price = head.getSource.get("price").asInstanceOf[Double],
        category = head.getSource.get("picture").asInstanceOf[java.util.List[String]].toList,
        user = UUID.fromString(head.getSource.get("user").asInstanceOf[String])
      )
      case _ => null
    }

  }
}
