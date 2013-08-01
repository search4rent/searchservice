package com.search4rent.search.service.resources

import com.search4rent.search.service._
import javax.ws.rs.core.{Response, Context, HttpHeaders}
import javax.ws.rs._
import scala.Array
import java.util.Locale
import javax.ws.rs.core.MediaType._
import com.codahale.jerkson.Json

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 7/22/13
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/search4rent")
@Produces(Array(APPLICATION_JSON))
@Consumes(Array(APPLICATION_JSON))
class SearchResource extends SuggestSearch {

  @GET
  @Path("-/input/{input}")
  def search(
              @PathParam("input") input: String,
              @Context headers: HttpHeaders) = {
    println("ASDFASDFASDF‘¶‘∑€®±“#Ç§æ¶§–…««««+")
    val locale = if (headers.getLanguage() == null) Locale.US else headers.getLanguage()

    val result = suggestSearch(locale, input)

    val suggestResponse = result
    suggestResponse
    Response.ok(Json.generate(suggestResponse)).build()
  }

}
