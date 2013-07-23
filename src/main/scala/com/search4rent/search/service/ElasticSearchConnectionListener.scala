package com.search4rent.search.service

import javax.servlet.{ServletContextEvent, ServletContextListener}

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 7/22/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
class ElasticSearchConnectionListener extends ServletContextListener{
  def contextInitialized(p1: ServletContextEvent) {
    ElasticSearchClient.init()
  }

  def contextDestroyed(p1: ServletContextEvent) {
    ElasticSearchClient.close()
  }
}
