package com.randl.search.service.response

/**
 * JSON model for the service response.
 */
case class SuggestHighlights(anyHighlighted: Boolean, parts: List[SuggestHighlight])