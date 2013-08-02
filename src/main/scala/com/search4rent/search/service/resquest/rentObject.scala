package com.search4rent.search.service.resquest

import java.util.UUID
import org.elasticsearch.common.geo.GeoPoint
import javax.xml.bind.annotation.XmlRootElement

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 7/22/13
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
case class SuggestResponseObject(
                                  id: String,
                                  link: List[String],
                                  score: Double,
                                  title: Map[String, List[String]])

case class SuggestList(
                        total: Long,
                        items: List[SuggestResponseObject]
                        )


case class RentItem(
                     id: UUID,
                     description: String,
                     location: GeoPoint,
                     name: String,
                     picture: List[String],
                     price: Double,
                     category: List[String],
                     user: UUID
                     )