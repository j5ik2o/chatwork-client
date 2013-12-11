/*
 * Copyright 2013 Junichi Kato. (http://j5ik2o.me/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.github.j5ik2o.chatwork.infrastructure

import scala.concurrent.ExecutionContext

import com.twitter.util.{Future => TFuture, Promise => TPromise, Try => TTry}
import scala.concurrent.{Future => SFuture, Promise => SPromise}
import scala.util.{Try => STry}
import TryUtil._

/**
 * `scala.concurrent.Future` と `com.twitter.util.Future` を相互に変換するためのユーティリティ。
 */
object FutureUtil {

  /**
   * `com.twitter.util.Future` を `scala.concurrent.Future` に変換するための暗黙的値クラス。
   *
   * @param future [[com.twitter.util.Future]]
   * @tparam T 値の型
   */
  implicit class TFutureToSFuture[T](val future: TFuture[T]) extends AnyVal {

    /**
     * `scala.concurrent.Future` を取得する。
     *
     * @return `scala.concurrent.Future`
     */
    def toScala: SFuture[T] = {
      val prom = SPromise[T]
      future.respond {
        t: TTry[T] =>
          prom.complete(t.toScala)
      }
      prom.future
    }

  }

  /**
   * `scala.concurrent.Future` から `com.twitter.util.Future` に変換するための暗黙的値クラス。
   *
   * @param future `scala.concurrent.Future`
   * @tparam T 値の型
   */
  implicit class SFutureToTFuture[T](val future: SFuture[T]) extends AnyVal {

    /**
     * `com.twitter.util.Future` を取得する。
     *
     * @param executor `scala.concurrent.ExecutionContext`
     * @return `com.twitter.util.Future`
     */
    def toTwitter(implicit executor: ExecutionContext): TFuture[T] = {
      val prom = TPromise[T]
      future.onComplete {
        t: STry[T] =>
          prom.update(t.toTwitter)
      }
      prom
    }

  }


}
