package com.search4rent.search.service.resources

import com.search4rent.search.service._
import javax.ws.rs.core.{MediaType, Response, Context, HttpHeaders}
import javax.ws.rs._
import scala.Array
import java.util.Locale
import javax.ws.rs.core.MediaType._
import com.codahale.jerkson.Json
import org.elasticsearch.client.Client
import com.search4rent.search.service.elasticsearch.ElasticSearchClient
import com.search4rent.search.service.resquest.RentItem

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
class SearchResource extends SuggestSearch with ItemSearch with Indexer {


  @POST
  @Path("-/insert/")
  def setItem(item: String) = {
    //val locale = if (headers.getLanguage() == null) Locale.US else headers.getLanguage()

    val rentItem = Json.parse[RentItem](item)
    indexer(rentItem).execute().actionGet()
    Response.ok(rentItem.id).build()
  }

  @GET
  @Path("-/item/{id}")
  def getItem(
               @PathParam("id") id: String,
               @Context headers: HttpHeaders) = {
    val locale = if (headers.getLanguage() == null) Locale.US else headers.getLanguage()
    val result = getItemES(id)
    Response.ok(Json.generate(result)).build()
  }


  @GET
  @Path("-/input/{input}")
  def search(
              @PathParam("input") input: String,
              @Context headers: HttpHeaders) = {
    val locale = if (headers.getLanguage() == null) Locale.US else headers.getLanguage()

    val result = suggestSearch(locale, input)

    val suggestResponse = result
    suggestResponse
    Response.ok(Json.generate(suggestResponse)).build()
  }

  def client: Client = ElasticSearchClient.client
}
