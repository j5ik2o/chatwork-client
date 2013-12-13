package com.github.j5ik2o.chatwork.infrastructure

import java.util.concurrent.{SynchronousQueue, TimeUnit, ThreadPoolExecutor}
import com.google.common.util.concurrent.RateLimiter

case class ApiThreadPoolExecutor(rate: Double = 0.25)
  extends ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue[Runnable]()) {

  private val rateLimiter = RateLimiter.create(rate)

  override def beforeExecute(t: Thread, r: Runnable): Unit = {
    rateLimiter.acquire()
    super.beforeExecute(t, r)
  }

}
