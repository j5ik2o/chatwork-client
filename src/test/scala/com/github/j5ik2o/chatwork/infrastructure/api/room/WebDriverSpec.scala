package com.github.j5ik2o.chatwork.infrastructure.api.room

import org.specs2.mutable.Specification
import org.openqa.selenium.By
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import com.gargoylesoftware.htmlunit.BrowserVersion
import com.github.j5ik2o.chatwork.infrastructure.selenium.SeleniumService

class WebDriverSpec extends Specification {

  "" should {
    "" in {
      val ss = new SeleniumService()
      ss.login(System.getProperty("email"), System.getProperty("password"))
     // val roomIds = ss.getRoomIds
     // ss.sendMessage(roomIds(0), "hogehoge")
     // ss.createRoom("hoge")
      //ss.dispose
//      //val driver = new ChromeDriver()
//      val driver = new HtmlUnitDriver(BrowserVersion.CHROME)
//      driver.get("http://www.google.com")
//      val element = driver.findElement(By.name("q"))
//      element.sendKeys("Cheese!")
//      element.submit()
//      println("Page title is: " + driver.getTitle)
//      driver.quit()
      true must beTrue
    }
  }

}
