package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.HttpException
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
        val observable = service.getInbox(show, true, null, null)
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_inbox_$show.json"))
        val test = observable.test().assertValueCount(1)
        test.values()[0].apply {
            assertNotEquals(0, data.children.size)
        }
    }

    @Test
    fun testGetInbox_noauth() {
//    authenticateService()
        val observable = service.getInbox("comments", true, null, null)
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testGetInbox_noShow() {
        authenticateService()
        val observable = service.getInbox(null, true, null, null)
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testGetInbox_httpError() {
        authenticateService()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val observable = service.getInbox("comments", true, null, null)
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testMarkAllMessagesRead() {
        authenticateService()
        val observable = service.markAllMessagesRead()
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testMarkAllMessagesRead_noauth() {
//    authenticateService()
        val observable = service.markAllMessagesRead()
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testMarkAllMessagesRead_httpError() {
        authenticateService()
        val observable = service.markAllMessagesRead()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testMarkMessagesRead() {
        authenticateService()
        val observable = service.markMessagesRead("")
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testMarkMessagesRead_noauth() {
//    authenticateService()
        val observable = service.markMessagesRead("")
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testMarkMessagesRead_httpError() {
        authenticateService()
        val observable = service.markMessagesRead("")
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testMarkMessagesUnread() {
        authenticateService()
        val observable = service.markMessagesUnread("")
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testMarkMessagesUnread_noauth() {
//    authenticateService()
        val observable = service.markMessagesUnread("")
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testMarkMessagesUnread_httpError() {
        authenticateService()
        val observable = service.markMessagesUnread("")
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }
}
