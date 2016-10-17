package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertNotEquals
import org.junit.Test
import retrofit2.Response
import rx.observers.TestSubscriber
import rxreddit.model.ListingResponse
import rxreddit.test.assertErrorEvents
import rxreddit.test.assertSuccessfulEvents
import rxreddit.test.setBodyFromFile

class SubscriptionManagerTests : _RedditServiceTests() {

    @Test
    fun testGetSubreddits() {
        authenticateService()

        val observable = service.getSubreddits("subscriber", null, null)
        val observer = TestSubscriber.create<ListingResponse>()

        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_subreddits_mine_subscriber.json"))
        observable.subscribe(observer)

        observer.assertSuccessfulEvents(1)
        observer.onNextEvents[0].apply {
            assertNotEquals("no data loaded", 0, data.children.size)
        }
    }

    @Test
    fun testGetSubreddits_noAuth() {
//    authenticateService()
        val observable = service.getSubreddits("subscriber", null, null)
        val test = TestSubscriber<ListingResponse>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
    }

    @Test
    fun testSubscribe() {
        authenticateService()

        val observable = service.subscribe("AskReddit")
        val observer = TestSubscriber.create<Response<Void>>()

        mockServer.enqueue(MockResponse())
        observable.subscribe(observer)

        observer.assertSuccessfulEvents(1)
    }

    @Test
    fun testSubscribe_noAuth() {
//        authenticateService()

        val observable = service.subscribe("AskReddit")
        val observer = TestSubscriber<Response<Void>>()

        observable.subscribe(observer)

        observer.assertErrorEvents(1)
    }

    @Test
    fun testSubscribeAll() {
        authenticateService()

        val observable = service.subscribe(listOf("AskReddit", "gaming", "news"))
        val observer = TestSubscriber.create<Response<Void>>()

        mockServer.enqueue(MockResponse())
        observable.subscribe(observer)

        observer.assertSuccessfulEvents(1)
    }

    @Test
    fun testSubscribeAll_noauth() {
//        authenticateService()

        val observable = service.subscribe(listOf("AskReddit", "gaming", "news"))
        val observer = TestSubscriber<Response<Void>>()

        observable.subscribe(observer)

        observer.assertErrorEvents(1)
    }

    @Test
    fun testUnsubscribe() {
        authenticateService()

        val observable = service.unsubscribe("AskReddit")
        val observer = TestSubscriber.create<Response<Void>>()

        mockServer.enqueue(MockResponse())
        observable.subscribe(observer)

        observer.assertSuccessfulEvents(1)
    }

    @Test
    fun testUnsubscribe_noauth() {
//        authenticateService()

        val observable = service.unsubscribe("AskReddit")
        val observer = TestSubscriber<Response<Void>>()

        observable.subscribe(observer)

        observer.assertErrorEvents(1)
    }

    @Test
    fun testUnsubscribeAll() {
        authenticateService()

        val observable = service.unsubscribe(listOf("AskReddit", "gaming", "news"))
        val observer = TestSubscriber.create<Response<Void>>()

        mockServer.enqueue(MockResponse())
        observable.subscribe(observer)

        observer.assertSuccessfulEvents(1)
    }

    @Test
    fun testUnsubscribeAll_noauth() {
//        authenticateService()

        val observable = service.unsubscribe(listOf("AskReddit", "gaming", "news"))
        val observer = TestSubscriber<Response<Void>>()

        observable.subscribe(observer)

        observer.assertErrorEvents(1)
    }
}
