package com.randl.search.service.listeners

import com.firebase.client.{Firebase, ChildEventListener, DataSnapshot}
import com.randl.search.service.operations.Indexer

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 10/7/13
 * Time: 8:55 AM
 * To change this template use File | Settings | File Templates.
 */
object FireBaseListener extends Indexer {

  val indexES = "rendl"
  val typeES= "items"
  def init() = {
    println("initialization fo the FireBase Listener")
    val usersRef = new Firebase("https://randl-backend.firebaseio.com/");
    usersRef.addChildEventListener(new ChildEventListener() {
      @Override
      def onChildAdded(snapshot: DataSnapshot, previousChildName: String) {
        println("item added: "+ snapshot.getName)
        indexer(snapshot.getValue)
      }

      @Override
      def onChildChanged(snapshot: DataSnapshot, previousChildName: String) {
        println("item update: "+ snapshot.getName)
        indexer(snapshot.getValue)
      }

      @Override
      def onChildRemoved(snapshot: DataSnapshot) {
        println("item delete: "+ snapshot.getName)
         delete(snapshot.getValue)
      }

      @Override
      def onChildMoved(snapshot: DataSnapshot, previousChildName: String) {

      }

      @Override
      def onCancelled() {

      }
    })
  }

  def destroy() = {

  }
}
