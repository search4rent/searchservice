package com.randl.search.service.listeners

import com.randl.core.servicelib.elasticsearch.ElasticSearchConnectionListener
import javax.servlet.ServletContextEvent

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 10/7/13
 * Time: 10:18 AM
 * To change this template use File | Settings | File Templates.
 */
class SearchConexionListener extends ElasticSearchConnectionListener {

  override def contextInitialized(p1: ServletContextEvent) {
    super.contextInitialized(p1)
    FireBaseListener.init()
  }

  override def contextDestroyed(p1: ServletContextEvent) {
    super.contextDestroyed(p1)
  }
}
