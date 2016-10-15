package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.*
import org.junit.Test
import retrofit2.adapter.rxjava.HttpException
import rx.observers.TestSubscriber
import rxreddit.model.Comment
import rxreddit.model.ListingResponse
import rxreddit.model.MoreChildrenResponse
import rxreddit.model.Subreddit
import rxreddit.test.assertErrorEvents
import rxreddit.test.assertSuccessfulEvents
import rxreddit.test.setBodyFromFile

class SubredditTests : _RedditServiceTests() {

    @Test
    fun testLoadLinks() {
        val observable = service.loadLinks(null, null, null, null, null)
        val test = TestSubscriber<ListingResponse>()
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_hot.json"))
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
        test.onNextEvents[0].apply {
            assertNotEquals("no links loaded", 0, data.children.size)
        }
    }

    @Test
    fun testLoadLinks_httpError() {
        val observable = service.loadLinks(null, null, null, null, null)
        val test = TestSubscriber<ListingResponse>()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("HttpException expected",
                HttpException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testLoadLinkComments() {
        val observable = service.loadLinkComments("pics", "commentId", null, null)
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<List<ListingResponse>>()
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_submission_comments.json"))
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
        test.onNextEvents[0][0].apply {
            assertEquals("unexpected number of links loaded", 1, data.children.size)
        }
        test.onNextEvents[0][1].apply {
            assertNotEquals("no comments loaded", 0, data.children.size)
        }
    }

    @Test
    fun testLoadLinkComments_noSubreddit() {
        val observable = service.loadLinkComments(null, "", null, null)
        val test = TestSubscriber<List<ListingResponse>>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testLoadLinkComments_noLink() {
        val observable = service.loadLinkComments("", null, null, null)
        val test = TestSubscriber<List<ListingResponse>>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testLoadLinkComments_httpError() {
        val observable = service.loadLinkComments("pics", "commentId", null, null)
        val test = TestSubscriber<List<ListingResponse>>()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("HttpException expected",
                HttpException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testLoadMoreChildren() {
        val observable = service.loadMoreChildren("linkId", listOf("comment1", "comment2"), null)
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<MoreChildrenResponse>()
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_more_children.json"))
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
        test.onNextEvents[0].apply {
            assertNotEquals("no children loaded", 0, childComments.size)
        }
    }

    @Test
    fun testLoadMoreChildren_noLink() {
        val observable = service.loadMoreChildren(null, listOf("comment1", "comment2"), null)
        val test = TestSubscriber<MoreChildrenResponse>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testLoadMoreChildren_noComments() {
        val observable = service.loadMoreChildren("linkId", emptyList(), null)
        val test = TestSubscriber<MoreChildrenResponse>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testLoadMoreChildren_httpError() {
        val observable = service.loadMoreChildren("linkId", listOf("comment1", "comment2"), "top")
        val test = TestSubscriber<MoreChildrenResponse>()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("HttpException expected",
                HttpException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testGetSubredditInfo() {
        val observable = service.getSubredditInfo("")
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<Subreddit>()
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_subreddit_info.json"))
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
        assertNotNull("response == null", test.onNextEvents[0])
    }

    @Test
    fun testGetSubredditInfo_noSubreddit() {
        val observable = service.getSubredditInfo(null)
        val test = TestSubscriber<Subreddit>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testGetSubredditInfo_httpError() {
        val observable = service.getSubredditInfo("")
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<Subreddit>()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("HttpException expected",
                HttpException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testVote() {
        authenticateService()
        val observable = service.vote("t3_linkId", 0)
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<Void>()
        mockServer.enqueue(MockResponse())
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
    }

    @Test
    fun testVote_noID() {
        authenticateService()
        val observable = service.vote(null, 0)
        val test = TestSubscriber<Void>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testVote_noauth() {
//    authenticateService()
        val observable = service.vote("t3_linkId", 0)
        val test = TestSubscriber<Void>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
    }

    @Test
    fun testVote_httpError() {
        authenticateService()
        val observable = service.vote("t3_linkId", 0)
        val test = TestSubscriber<Void>()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("HttpException expected",
                HttpException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testSave() {
        authenticateService()
        val observable = service.save("t3_linkId", null, true)
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<Void>()
        mockServer.enqueue(MockResponse())
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
    }

    @Test
    fun testUnsave() {
        authenticateService()
        val observable = service.save("t3_linkId", null, false)
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<Void>()
        mockServer.enqueue(MockResponse())
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
    }

    @Test
    fun testSave_noID() {
        authenticateService()
        val observable = service.save(null, null, true)
        val test = TestSubscriber<Void>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testSave_noauth() {
//    authenticateService()
        val observable = service.save("t3_linkId", null, true)
        val test = TestSubscriber<Void>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
    }

    @Test
    fun testHide() {
        authenticateService()
        val observable = service.hide("t3_linkId", true)
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<Void>()
        mockServer.enqueue(MockResponse())
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
    }

    @Test
    fun testUnhide() {
        authenticateService()
        val observable = service.hide("t3_linkId", false)
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<Void>()
        mockServer.enqueue(MockResponse())
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
    }

    @Test
    fun testHide_noID() {
        authenticateService()
        val observable = service.hide(null, true)
        val test = TestSubscriber<Void>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testHide_noauth() {
//    authenticateService()
        val observable = service.hide("t3_linkId", true)
        val test = TestSubscriber<Void>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
    }

    @Test
    fun testHide_httpError() {
        authenticateService()
        val observable = service.hide("t3_linkId", true)
        val test = TestSubscriber<Void>()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("HttpException expected",
                HttpException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testReport() {
        val observable = service.report("", "")
        assertNotNull("observable == null", observable)
    }

    @Test
    fun testAddComment() {
        authenticateService()
        val observable = service.addComment("linkId", "I like cats")
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<Comment>()
        mockServer.enqueue(MockResponse().setBodyFromFile("test/POST_add_comment.json"))
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
        assertNotNull("response == null", test.onNextEvents[0])
    }

    @Test
    fun testAddComment_noParent() {
        authenticateService()
        val observable = service.addComment(null, "I like cats")
        val test = TestSubscriber<Comment>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testAddComment_noText() {
        authenticateService()
        val observable = service.addComment("linkId", null)
        val test = TestSubscriber<Comment>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testAddComment_noauth() {
//    authenticateService()
        val observable = service.addComment("", "")
        val test = TestSubscriber<Comment>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
    }

    @Test
    fun testAddComment_httpError() {
        authenticateService()
        val observable = service.addComment("", "")
        val test = TestSubscriber<Comment>()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("HttpException expected",
                HttpException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testAddComment_addCommentError() {
        authenticateService()
        val observable = service.addComment("", "")
        val test = TestSubscriber<Comment>()
        mockServer.enqueue(MockResponse().setBodyFromFile("model/add_comment_error.json"))
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("IllegalStateException expected",
                IllegalStateException::class.java, test.onErrorEvents[0].javaClass)
    }

}