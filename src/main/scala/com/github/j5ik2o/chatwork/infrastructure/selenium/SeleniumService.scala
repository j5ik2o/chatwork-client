package com.github.j5ik2o.chatwork.infrastructure.selenium

import org.openqa.selenium.htmlunit.HtmlUnitDriver
import com.gargoylesoftware.htmlunit.BrowserVersion
import org.openqa.selenium.{Cookie, By}
import org.openqa.selenium.chrome.ChromeDriver
import scala.collection.JavaConverters._
import java.util.concurrent.TimeUnit

class SeleniumService {

  private val driver = newWebDriver(classOf[HtmlUnitDriver])

  private def newWebDriver(clazz: Class[_]) = {
    val result = if (clazz == classOf[HtmlUnitDriver]) {
      new HtmlUnitDriver(BrowserVersion.CHROME)
    } else {
      new ChromeDriver()
    }
    result.manage.timeouts().implicitlyWait(50, TimeUnit.SECONDS)
    result
  }

  val homeUrl = "https://www.chatwork.com/"

  private var session: Cookie = _
  private var accessToken: String = _
  private var myId: String = _

  def login(email: String, password: String) = {
    driver.get("https://www.chatwork.com/login.php")
    session = driver.manage().getCookieNamed("cwssid")
    val form = driver.findElementByName("login")
    val emailElement = form.findElement(By.name("email"))
    val passwordElement = form.findElement(By.name("password"))
    emailElement.clear()
    passwordElement.clear()
    emailElement.sendKeys(email)
    passwordElement.sendKeys(password)
    form.submit()
    val accessTokenRegex = """var ACCESS_TOKEN = \'(\w+)\';""".r
    val myIdRegex = """var myid = \'(\d+)\';""".r
    val page = driver.getPageSource
    accessToken = accessTokenRegex.findFirstMatchIn(page).map(m => m.group(1)).get
    myId = myIdRegex.findFirstMatchIn(page).map(m => m.group(1)).get
    println(accessToken, myId)
  }

//  def getRoomIds: Seq[Int] = {
//    val rlist = driver.findElementById("_roomListItems")
//    val lis = rlist.findElements(By.tagName("li")).asScala
//    lis.flatMap{
//      e =>
//        Option(e.getAttribute("data-rid")).toList
//    }.map(_.toInt).toSeq
//  }
//
//  def createRoom(name: String) = {
//    driver.findElement(By.cssSelector("span.icoSizeLarge.icoFontAddBtn")).click()
//    driver.findElement(By.cssSelector("li._cwDDList")).click()
//    driver.findElement(By.id("_roomInfoName")).clear()
//    driver.findElement(By.id("_roomInfoName")).sendKeys(name)
//
//    driver.findElement(By.xpath("//*[@id='_wrapper']/div[6]/div/div[2]/div[1]")).click()
//    driver.get(driver.getCurrentUrl)
//  }
//
//  def sendMessage(roomId: Int, text: String) = {
//    driver.get(s"https://www.chatwork.com/#!rid${roomId}")
//    val chatText = driver.findElementById("_chatText")
//    val sendButton = driver.findElementById("_sendButton")
//    chatText.clear()
//    chatText.sendKeys(text)
//
//    sendButton.click()
//  }

  def dispose = driver.quit()

}
