package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertNotEquals
import org.junit.Test
import rx.observers.TestSubscriber
import rxreddit.model.ListingResponse
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
    fun testGetSubscriberSubreddits() {
        // Get mockito to verify the helper methods were called?
        service.getSubreddits("subscriber", null, null)
                .subscribe()

    }

    @Test
    fun testGetModeratorSubreddits() {

    }

    @Test
    fun getContributorSubreddits() {

    }

    @Test
    fun getSubreddits_noAuth() {

    }
}
