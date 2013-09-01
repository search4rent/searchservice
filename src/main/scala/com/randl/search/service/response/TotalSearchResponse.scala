package com.randl.search.service.response

import com.randl.search.service.resquest.RentItem

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 9/1/13
 * Time: 7:38 PM
 * To change this template use File | Settings | File Templates.
 */
case class TotalSearchResponse(totalHits: Long, list: List[RentItem])
