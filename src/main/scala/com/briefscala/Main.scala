package com.briefscala

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}
import scala.util.{Success, Failure, Try}
import scalaz._, Scalaz._

case class MatchData(id1: Double, id2: Double, scores: Array[Double], matched: Boolean)

object Main {
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setAppName("Advanced-Analytics-1")
      .setMaster("local[*]")
    val sc = new SparkContext(conf)
    val rawblocks = sc.textFile("/Users/yoel.garciadiaz/linkage/")
    val noheader = rawblocks.filter(!isHeader(_))
    val parsed = noheader.flatMap(parse).cache()
    val nasRDD = parsed.map( md => {
      md.scores.map(d => NAStatCounter(d))
    })
//    val reduced = nasRDD.reduce((n1, n2) => {
//      n1.zip(n2).map { case (a, b) => a.merge(b) }
//    })
    val statsm = statsWithMissing(parsed.filter(_.matched).map(_.scores))
    val statsn = statsWithMissing(parsed.filter(!_.matched).map(_.scores))
    statsm.zip(statsn).map { case (m, n) =>
      (m.missing + n.missing, m.stats.mean - n.stats.mean)
    }.foreach(println)
  }

  def isHeader(line: String): Boolean = line.contains("id_1")

  def toDouble(s: String): Double =
    Try(s.toDouble) match {
      case Failure(_) => Double.NaN
      case Success(d) => d
    }

  /**
    * this code has being modified.
    * the data contains plenty instances of bad data
    * @param line
    * @return
    */
  def parse(line: String) = {
    val pieces = line.split(',')
    for {
      _ <- (pieces.length > 11).option(true)
      matched <- Try(pieces(11).toBoolean).toOption
    } yield {
      val id1 = pieces(0).toInt
      val id2 = pieces(1).toInt
      val scores = pieces.slice(2, 10).map(toDouble)
      MatchData(id1, id2, scores, matched)
    }
  }

  /**
    * this method has been modified. The code in the book throws a runtime error
    * when it called `.next` on an empty iterator
    * @param rdd
    * @return
    */
  def statsWithMissing(rdd: RDD[Array[Double]]): Array[NAStatCounter] = {
    val nastats = rdd.mapPartitions {
      case iter if iter.hasNext =>
        val nas = iter.next.map(d => NAStatCounter(d))
        iter.foreach( arr => {
          nas.zip(arr).foreach { case (n, d) => n.add(d) }
        })
        Iterator(nas)
      case _ =>
        Iterator()
    }
    nastats.reduce((n1, n2) => {
      n1.zip(n2).map { case (a, b) => a.merge(b) }
    })
  }
}
