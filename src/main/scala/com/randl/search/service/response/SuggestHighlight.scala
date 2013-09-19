package com.randl.search.service.response

import com.randl.search.service.resquest.RentItem

/**
 * JSON model for the service response.
 */
case class SuggestHighlight(text: String, highlighted: Boolean)

case class SuggestHighlights(anyHighlighted: Boolean, parts: List[SuggestHighlight])

case class SuggestResponse(content: Map[String, SuggestResult])

case class SuggestResult(totalCount: Long, items: List[SuggestSearchHit])

case class SuggestSearchHit(id: String, link: List[String], score: Float,
                            title: SuggestHighlights, subTitle: SuggestHighlights)

case class TotalSearchResponse(totalHits: Long, list: List[RentItem])

