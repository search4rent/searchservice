package com.search4rent.search.service.resources

import com.search4rent.search.service.AcceptLanguage
import javax.ws.rs.core.{MediaType, Context, HttpHeaders}
import javax.ws.rs._
import scala.Array

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 7/22/13
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/search")
class SearchResource extends AcceptLanguage{
  @Context
  var headers: HttpHeaders = _

  @POST
  @Consumes (Array(MediaType.APPLICATION_JSON))
  @Produces(Array(MediaType.APPLICATION_JSON))
  def search(

              )={

  }

}
