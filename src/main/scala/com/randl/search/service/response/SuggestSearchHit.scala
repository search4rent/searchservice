package com.randl.search.service.response

/**
 * JSON model for the service response.
 */
case class SuggestSearchHit(id: String, link: List[String], score: Float,
  title: SuggestHighlights, subTitle: SuggestHighlights)