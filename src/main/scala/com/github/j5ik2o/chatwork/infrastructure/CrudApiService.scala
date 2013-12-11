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

import scala.concurrent.{Future, ExecutionContext}


/**
 * エンティティを永続化するためのAPIサービス。
 */
trait CrudApiService[ID] {

  /**
   * エンティティを作成する。
   *
   * 作成時は、エンティティの識別子は必要ありません。
   *
   * @param entity エンティティを表すマップ
   * @param executor [[scala.concurrent.ExecutionContext]]
   * @return 作成したアプリケーション情報
   */
  def create(entity: Map[String, Any])(implicit executor: ExecutionContext): Future[Map[String, Any]]

  /**
   * エンティティを取得する。
   *
   * @param identity エンティティのID
   * @param executor [[scala.concurrent.ExecutionContext]]
   * @return アプリケーション
   */
  def get(identity: ID)(implicit executor: ExecutionContext): Future[Map[String, Any]]

  /**
   * エンティティを更新する。
   *
   * 更新時は、エンティティには必ず識別子が必要になります。
   *
   * @param entity エンティティを表すマップ
   * @param executor [[scala.concurrent.ExecutionContext]]
   * @return 更新したエンティティ
   */
  def update(entity: Map[String, Any])(implicit executor: ExecutionContext): Future[Map[String, Any]]

  /**
   * エンティティを廃棄する。
   *
   * @param identity エンティティのID
   * @param executor [[scala.concurrent.ExecutionContext]]
   * @return [[scala.Unit]]
   */
  def destroy(identity: ID)(implicit executor: ExecutionContext): Future[Map[String, Any]]

}
