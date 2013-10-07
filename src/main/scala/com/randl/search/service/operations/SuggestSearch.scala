package com.randl.search.service.operations

import java.util.Locale
import scala.math.min
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator
import com.randl.search.service.resquest.{SuggestList, SuggestResponseObject}
import com.randl.core.servicelib.elasticsearch.ESClient
import com.randl.search.service._
import com.randl.search.service.resquest.SuggestResponseObject
import com.randl.search.service.resquest.SuggestList
import scala.Some

/**
 * Trait that performs a search in the suggest index and returns the result.
 */


trait SuggestSearch extends ESClient {
  private val MAX_LIMIT = 100
  protected val SRC_NAME = "name"
  private val SRC_PARENTS_NAME = "description"
  val limit = 3


  def escape(str: String) = {
    if (str == null)
      str

    var escapedStr = str
    for (seq <- ESCAPED_SEQUENCES) {
      escapedStr = escapedStr.replace(seq._1, seq._2)
    }
    escapedStr
  }

  /**
   * Creates a normalized query string by escaping and trimming it
   */
  def normalizeQueryString(str: String) = {
    escape(str).trim()
  }

  def queryBoth(searchStr: String) = QueryBuilders
    .queryString(normalizeQueryString(searchStr))
    .field(SRC_NAME, 2.0f).field(SRC_PARENTS_NAME)
    .analyzer("name_analyzer_search")
    .defaultOperator(Operator.AND)

  def queryName(searchStr: String) = QueryBuilders
    .queryString(normalizeQueryString(searchStr))
    .field(SRC_NAME)
    .analyzer("name_analyzer_search")
    .defaultOperator(Operator.OR)

  def queryBool(searchStr: String) = QueryBuilders
    .boolQuery()
    .must(queryName(searchStr))
    .must(queryBoth(searchStr))

  // start search for each supplied type


  /**
   * Performs a query
   *
   * @param locale the locale to use for the search (i.e. the index)
   * @param searchStr the string to search for, all words are chained with an <code>and</code>
   * @return a result of the search per type.
   */
  def suggestSearch(
                     locale: Locale,
                     searchStr: String) = {
    print("Executing suggest search for locale: {}, searchStr: {}, types: {}, limit: {}",
      Array(locale, searchStr, limit))

    val search1 = client
      .prepareSearch("rendl")
      .setTypes("item")
      .setQuery(queryBool(searchStr))
      .addHighlightedField(SRC_NAME)
      .addHighlightedField(SRC_PARENTS_NAME)
      .setSize(min(limit, MAX_LIMIT))
    println(search1)
    val search = search1.execute();
    val hits = search.actionGet().getHits();

    val suggestResult = SuggestList(hits.getTotalHits(), hits.getHits().toList.map(hit => {
      val name = Option(hit.getHighlightFields.get("name")) match {
        case Some(x) => x.fragments().map(_.toString).toList
        case _ => List[String]()
      }
      val description = Option(hit.getHighlightFields.get("description")) match {
        case Some(x) => x.fragments().map(_.toString).toList
        case _ => List[String]()
      }
      val map = Map(
        "name" -> name,
        "description" -> description

      )
      SuggestResponseObject(
        id = hit.getId(),
        link = List(), //hit.getSource().get(SRC_URL_KEY).asInstanceOf[java.util.List[String]].toList,
        score = hit.getScore(),
        title = map)
    }))
    suggestResult

  }

}
