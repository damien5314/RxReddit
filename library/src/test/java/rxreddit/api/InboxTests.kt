package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.*
import org.junit.Test
import retrofit2.adapter.rxjava.HttpException
import rx.observers.TestSubscriber
import rxreddit.model.ListingResponse
import rxreddit.test.assertErrorEvents
import rxreddit.test.assertSuccessfulEvents
import rxreddit.test.setBodyFromFile

class InboxTests : _RedditServiceTests() {

  @Test
  fun testGetInbox_comments() = testGetInbox("comments")
  @Test
  fun testGetInbox_inbox() = testGetInbox("inbox")
  @Test
  fun testGetInbox_mentions() = testGetInbox("mentions")
  @Test
  fun testGetInbox_messages() = testGetInbox("messages")
  @Test
  fun testGetInbox_selfreply() = testGetInbox("selfreply")
  @Test
  fun testGetInbox_sent() = testGetInbox("sent")
  @Test
  fun testGetInbox_unread() = testGetInbox("unread")

  private fun testGetInbox(show: String) {
    authenticateService()
    val observable = service.getInbox(show, null, null)
    assertNotNull("observable == null", observable)
    val test = TestSubscriber<ListingResponse>()
    mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_inbox_$show.json"))
    observable.subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
    test.onNextEvents[0].apply {
      assertNotEquals("no listings loaded", 0, data.children.size)
    }
  }

  @Test
  fun testGetInbox_noauth() {
//    authenticateService()
    val observable = service.getInbox("comments", null, null)
    val test = TestSubscriber<ListingResponse>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
  }

  @Test
  fun testGetInbox_noShow() {
    authenticateService()
    val observable = service.getInbox(null, null, null)
    val test = TestSubscriber<ListingResponse>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("IllegalArgumentException expected",
        IllegalArgumentException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testGetInbox_httpError() {
    authenticateService()
    val observable = service.getInbox("comments", null, null)
    val test = TestSubscriber<ListingResponse>()
    mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("HttpException expected",
        HttpException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testMarkAllMessagesRead() {
    authenticateService()
    val observable = service.markAllMessagesRead()
    assertNotNull("observable == null", observable)
    val test = TestSubscriber<Void>()
    mockServer.enqueue(MockResponse())
    observable.subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testMarkAllMessagesRead_noauth() {
//    authenticateService()
    val observable = service.markAllMessagesRead()
    val test = TestSubscriber<Void>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
  }

  @Test
  fun testMarkAllMessagesRead_httpError() {
    authenticateService()
    val observable = service.markAllMessagesRead()
    val test = TestSubscriber<Void>()
    mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("HttpException expected",
        HttpException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testMarkMessagesRead() {
    authenticateService()
    val observable = service.markMessagesRead("")
    assertNotNull("observable == null", observable)
    val test = TestSubscriber<Void>()
    mockServer.enqueue(MockResponse())
    observable.subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testMarkMessagesRead_noauth() {
//    authenticateService()
    val observable = service.markMessagesRead("")
    val test = TestSubscriber<Void>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
  }

  @Test
  fun testMarkMessagesRead_httpError() {
    authenticateService()
    val observable = service.markMessagesRead("")
    val test = TestSubscriber<Void>()
    mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("HttpException expected",
        HttpException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testMarkMessagesUnread() {
    authenticateService()
    val observable = service.markMessagesUnread("")
    assertNotNull("observable == null", observable)
    val test = TestSubscriber<Void>()
    mockServer.enqueue(MockResponse())
    observable.subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testMarkMessagesUnread_noauth() {
//    authenticateService()
    val observable = service.markMessagesUnread("")
    val test = TestSubscriber<Void>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
  }

  @Test
  fun testMarkMessagesUnread_httpError() {
    authenticateService()
    val observable = service.markMessagesUnread("")
    val test = TestSubscriber<Void>()
    mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("HttpException expected",
        HttpException::class.java, test.onErrorEvents[0].javaClass)
  }
}
