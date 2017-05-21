package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertNotEquals
import org.junit.Test
import rxreddit.test.assertErrorEvents
import rxreddit.test.assertSuccessfulEvents
import rxreddit.test.setBodyFromFile

class SubscriptionManagerTests : _RedditServiceTests() {

    @Test
    fun testGetSubreddits() {
        authenticateService()

        val observable = service.getSubreddits("subscriber", null, null)

        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_subreddits_mine_subscriber.json"))
        val observer = observable.test()

        observer.assertSuccessfulEvents(1)
        observer.values()[0].apply {
            assertNotEquals("no data loaded", 0, data.children.size)
        }
    }

    @Test
    fun testGetSubreddits_noAuth() {
//    authenticateService()
        val observable = service.getSubreddits("subscriber", null, null)
        val test = observable.test()
        test.assertErrorEvents(1)
    }

    @Test
    fun testSubscribe() {
        authenticateService()

        val observable = service.subscribe("AskReddit")

        mockServer.enqueue(MockResponse())
        val observer = observable.test()

        observer.assertSuccessfulEvents(1)
    }

    @Test
    fun testSubscribe_noAuth() {
//        authenticateService()

        val observable = service.subscribe("AskReddit")

        val observer = observable.test()

        observer.assertErrorEvents(1)
    }

    @Test
    fun testSubscribeAll() {
        authenticateService()

        val observable = service.subscribe(listOf("AskReddit", "gaming", "news"))

        mockServer.enqueue(MockResponse())
        val observer = observable.test()

        observer.assertSuccessfulEvents(1)
    }

    @Test
    fun testSubscribeAll_noauth() {
//        authenticateService()

        val observable = service.subscribe(listOf("AskReddit", "gaming", "news"))

        val observer = observable.test()

        observer.assertErrorEvents(1)
    }

    @Test
    fun testUnsubscribe() {
        authenticateService()

        val observable = service.unsubscribe("AskReddit")

        mockServer.enqueue(MockResponse())
        val observer = observable.test()

        observer.assertSuccessfulEvents(1)
    }

    @Test
    fun testUnsubscribe_noauth() {
//        authenticateService()

        val observable = service.unsubscribe("AskReddit")

        val observer = observable.test()

        observer.assertErrorEvents(1)
    }

    @Test
    fun testUnsubscribeAll() {
        authenticateService()

        val observable = service.unsubscribe(listOf("AskReddit", "gaming", "news"))

        mockServer.enqueue(MockResponse())
        val observer = observable.test()

        observer.assertSuccessfulEvents(1)
    }

    @Test
    fun testUnsubscribeAll_noauth() {
//        authenticateService()

        val observable = service.unsubscribe(listOf("AskReddit", "gaming", "news"))

        val observer = observable.test()

        observer.assertErrorEvents(1)
    }
}
