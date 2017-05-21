package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertNotEquals
import org.junit.Test
import rxreddit.test.setBodyFromFile

class SubscriptionManagerTests : _RedditServiceTests() {

    @Test
    fun testGetSubreddits() {
        authenticateService()

        val observable = service.getSubreddits("subscriber", null, null)

        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_subreddits_mine_subscriber.json"))
        val observer = observable.test()

        observer.assertValueCount(1)
        observer.values()[0].apply {
            assertNotEquals("no data loaded", 0, data.children.size)
        }
    }

    @Test
    fun testGetSubreddits_noAuth() {
//    authenticateService()
        val observable = service.getSubreddits("subscriber", null, null)
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testSubscribe() {
        authenticateService()

        val observable = service.subscribe("AskReddit")

        mockServer.enqueue(MockResponse())
        val observer = observable.test()

        observer.assertComplete()
    }

    @Test
    fun testSubscribe_noAuth() {
//        authenticateService()

        val observable = service.subscribe("AskReddit")

        val observer = observable.test()

        observer.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testSubscribeAll() {
        authenticateService()

        val observable = service.subscribe(listOf("AskReddit", "gaming", "news"))

        mockServer.enqueue(MockResponse())
        val observer = observable.test()

        observer.assertComplete()
    }

    @Test
    fun testSubscribeAll_noauth() {
//        authenticateService()

        val observable = service.subscribe(listOf("AskReddit", "gaming", "news"))

        val observer = observable.test()

        observer.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testUnsubscribe() {
        authenticateService()

        val observable = service.unsubscribe("AskReddit")

        mockServer.enqueue(MockResponse())
        val observer = observable.test()

        observer.assertComplete()
    }

    @Test
    fun testUnsubscribe_noauth() {
//        authenticateService()

        val observable = service.unsubscribe("AskReddit")

        val observer = observable.test()

        observer.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testUnsubscribeAll() {
        authenticateService()

        val observable = service.unsubscribe(listOf("AskReddit", "gaming", "news"))

        mockServer.enqueue(MockResponse())
        val observer = observable.test()

        observer.assertComplete()
    }

    @Test
    fun testUnsubscribeAll_noauth() {
//        authenticateService()

        val observable = service.unsubscribe(listOf("AskReddit", "gaming", "news"))

        val observer = observable.test()

        observer.assertError(IllegalStateException::class.java)
    }
}
