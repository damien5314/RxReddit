package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertNotEquals
import org.junit.Test
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
    fun getSubreddits_noAuth() {
//    authenticateService()
        val observable = service.getSubreddits("subscriber", null, null)
        val test = TestSubscriber<ListingResponse>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
    }
}
