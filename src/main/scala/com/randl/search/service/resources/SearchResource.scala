package com.randl.search.service.resources

import com.randl.search.service._
import javax.ws.rs.core.{Response, Context, HttpHeaders}
import javax.ws.rs._
import scala.Array
import java.util.Locale
import javax.ws.rs.core.MediaType._
import com.randl.search.service.resquest.RentItem

@Path("/search")
@Produces(Array(APPLICATION_JSON))
@Consumes(Array(APPLICATION_JSON))
class SearchResource extends TotalSearch with ItemSearch  {



  @GET
  @Path("-/item/{id}")
  def getItem(
               @PathParam("id") id: String,
               @Context headers: HttpHeaders) = {
    val locale = if (headers.getLanguage() == null) Locale.US else headers.getLanguage()
    val result = getItemES(id)
    Response.ok(result).build()
  }

  @GET
  @Path("-/search/{input}/{init}/{end}")
  def getItems(
                @PathParam("input") input: String,
                @PathParam("init") init: Int,
                @PathParam("end") end: Int,
                @Context headers: HttpHeaders
                ) = {
    val locale = if (headers.getLanguage() == null) Locale.US else headers.getLanguage()
    val result = totalSearch(locale, input, init, end)
    Response.ok(result).build()
  }


  @GET
  @Path("-/suggest/{input}")
  def search(
              @PathParam("input") input: String,
              @Context headers: HttpHeaders) = {
    val locale = if (headers.getLanguage() == null) Locale.US else headers.getLanguage()

    val result = suggestSearch(locale, input)

    val suggestResponse = result
    suggestResponse
    Response.ok(suggestResponse).build()
  }

}
