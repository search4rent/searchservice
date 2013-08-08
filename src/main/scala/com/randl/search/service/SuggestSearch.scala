package com.randl.search.service

import java.util.Locale
import scala.math.min
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator
import com.randl.search.service.elasticsearch._
import com.randl.search.service.resquest.{SuggestList, SuggestResponseObject}
import org.elasticsearch.client.Client

/**
 * Trait that performs a search in the suggest index and returns the result.
 */


trait ESClient {
  def client: Client
}


trait SuggestSearch extends ESClient{
  private val MAX_LIMIT = 100
  private val SRC_URL_KEY = "picture"
  private val SRC_NAME = "name"
  private val SRC_PARENTS_NAME = "description"
  private val HIGHLIGHT_START = "<em>";
  private val HIGHLIGHT_END = "</em>";
  val limit = 3


  def escape(str: String) = {
    if (str == null)
      str

    var escapedStr = str
    for (seq <- ElasticSearchClient.ESCAPED_SEQUENCES) {
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

    val queryBoth = QueryBuilders
      .queryString(normalizeQueryString(searchStr))
      .field(SRC_NAME, 2.0f).field(SRC_PARENTS_NAME)
      .analyzer("name_analyzer_search")
      .defaultOperator(Operator.AND)

    val queryName = QueryBuilders
      .queryString(normalizeQueryString(searchStr))
      .field(SRC_NAME)
      .analyzer("name_analyzer_search")
      .defaultOperator(Operator.OR)

    val queryBool = QueryBuilders
      .boolQuery()
      .must(queryName)
      .must(queryBoth)

    // start search for each supplied type 
    val search1 = client
      .prepareSearch("rendl")
      .setTypes("item")
      .setQuery(queryBool)
      .addHighlightedField(SRC_NAME)
      .addHighlightedField(SRC_PARENTS_NAME)
      .setSize(min(limit, MAX_LIMIT))
    println(search1)
    val  search = search1.execute();
    val hits = search.actionGet().getHits();

    import scala.collection.JavaConversions._
    val suggestResult = SuggestList(hits.getTotalHits(), hits.getHits().toList.map(hit => {
      val name = Option(hit.getHighlightFields.get("name")) match {
        case Some(x)=> x.fragments().map(_.toString).toList
        case _ => List[String]()
      }
      val description = Option(hit.getHighlightFields.get("description")) match {
        case Some(x)=> x.fragments().map(_.toString).toList
        case _ => List[String]()
      }
      val map =  Map(
        "name"->  name,
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
