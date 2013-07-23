package com.search4rent.search

import scala.util.matching.Regex
import org.elasticsearch.node.{Node, NodeBuilder}
import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.ImmutableSettings
import javax.ws.rs.core.HttpHeaders
import java.util.Locale
import scala.collection.JavaConversions._

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 7/20/13
 * Time: 1:40 AM
 * To change this template use File | Settings | File Templates.
 */
package object service {

  object ElasticSearchClient {
    private var client: Client = _
    private var node: Node = _
    lazy val settingDev = ImmutableSettings.settingsBuilder();
    settingDev.put("node.name", "Lasher")
    settingDev.put("discovery.zen.ping.multicast.enabled",false)
    settingDev.put("discovery.zen.ping.unicast.hosts","localhost:9393")
    def init() {
      node = NodeBuilder.nodeBuilder().client(true).clusterName("localhost-testing").settings(settingDev).node()
      client = node.client()
    }
    def close() {
      client.close()
      node.close()
    }
  }

  trait AcceptLanguage {
    var headers: HttpHeaders
    def acceptLanguageLocale = {
      val potentialLocale = headers.getAcceptableLanguages.get(0).toString
      val locale = {
        // match against string => three cases de vs. de-DE vs. de_DE
        val regExFullLocale = new Regex("""^([a-z]{2})-([A-Z]{2})$""")
        val regExFullLocale2 = new Regex("""^([a-z]{2})_([A-Z]{2})$""")
        val regExFallbackLocale = new Regex("""^([a-z]{2})$""")
        potentialLocale match {
          case regExFullLocale(x, y) => new Locale(x, y)
          case regExFullLocale2(x, y) => new Locale(x, y)
          case regExFallbackLocale(x) => new Locale(x)
          case _ => throw new IllegalArgumentException("Provided locale is invalid!")
        }
      }
      if (!Locale.getAvailableLocales.find(_.equals(locale)).isDefined) {
        throw new IllegalArgumentException("Provided locale is invalid!")
      }
      locale
    }
    def acceptLanguage = {
      acceptLanguageLocale.toString
    }
  }
}
