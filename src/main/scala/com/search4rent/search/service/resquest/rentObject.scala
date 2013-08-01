package com.search4rent.search.service.resquest

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