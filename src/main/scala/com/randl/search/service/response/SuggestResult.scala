package com.randl.search.service.response

/**
 * JSON model for the service response.
 */
case class SuggestResult(totalCount: Long, items: List[SuggestSearchHit])