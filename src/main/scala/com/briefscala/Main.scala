package com.briefscala

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
    val head = rawblocks.take(10)
    val noheader = rawblocks.filter(!isHeader(_))
    val mds = head.filter(!isHeader(_)).flatMap(parse)
    val parsed = noheader.flatMap(parse).cache()
    val grouped = mds.groupBy(md => md.matched)
    val matchCounts = parsed.map(_.matched).countByValue()
    val matchCountsSeq = matchCounts.toSeq
    val nasRDD = parsed.map(md => {
      md.scores.map(d => NAStatCounter(d))
    })
    val reduced = nasRDD.reduce((n1, n2) => {
      n1.zip(n2).map { case (a, b) => a.merge(b) }
    })
    reduced.foreach(println)
  }

  def isHeader(line: String): Boolean = line.contains("id_1")

  def toDouble(s: String): Double =
    Try(s.toDouble) match {
      case Failure(_) => Double.NaN
      case Success(d) => d
    }

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
}
