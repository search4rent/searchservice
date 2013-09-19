package com.randl.search

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 9/19/13
 * Time: 6:59 PM
 * To change this template use File | Settings | File Templates.
 */
package object service {
  /**
   * Chars that must be escaped in a query.
   */
  val SPECIAL_SEQUENCES = """+ - && || ! ( ) { } [ ] ^ " ~ * ? :""".split(' ')
  val ESCAPED_SEQUENCES = Map(SPECIAL_SEQUENCES.map(seq => (seq, seq.map("\\" + _).foldLeft("")(_ + _))): _*)

}
