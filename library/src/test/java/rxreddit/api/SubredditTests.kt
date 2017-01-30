package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test
import retrofit2.adapter.rxjava.HttpException
import rx.observers.TestSubscriber
import rxreddit.model.*
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
    fun testGetSubredditRules() {
        val observable = service.getSubredditRules("gaming")
        assertNotNull("observable == null", observable)

        val observer = TestSubscriber<SubredditRules>()
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_subreddit_about_rules.json"))
        observable.subscribe(observer)

        observer.assertSuccessfulEvents(1)
        assertNotNull("response == null", observer.onNextEvents[0])
    }

    @Test
    fun testGetSubredditRules_null() {
        val observable = service.getSubredditRules(null)
        val test = TestSubscriber<SubredditRules>()
        observable.subscribe(test)

        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testGetSubredditRules_httpError() {
        val observable = service.getSubredditRules("gaming")
        assertNotNull("observable == null", observable)

        val test = TestSubscriber<SubredditRules>()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        observable.subscribe(test)

        test.assertErrorEvents(1)
        assertEquals("HttpException expected",
                HttpException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test @Ignore("/r/subreddit/about/sidebar is broken")
    fun testGetSubredditSidebar() {

    }

    @Test @Ignore("/r/subreddit/about/sidebar is broken")
    fun testGetSubredditSidebar_null() {

    }

    @Test @Ignore("/r/subreddit/about/sidebar is broken")
    fun testGetSubredditSidebar_httpError() {

    }

    @Test
    fun testGetSubredditSticky() {
        val observable = service.getSubredditSticky("gaming")
        assertNotNull("observable == null", observable)

        val observer = TestSubscriber<List<ListingResponse>>()
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_subreddit_about_sticky.json"))
        observable.subscribe(observer)

        observer.assertSuccessfulEvents(1)
        assertNotNull("response == null", observer.onNextEvents[0])
    }

    @Test
    fun testGetSubredditSticky_null() {
        val observable = service.getSubredditSticky(null)
        val test = TestSubscriber<List<ListingResponse>>()
        observable.subscribe(test)

        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testGetSubredditSticky_httpError() {
        val observable = service.getSubredditSticky("gaming")
        assertNotNull("observable == null", observable)

        val test = TestSubscriber<List<ListingResponse>>()
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
    fun testGetReportForm() {
        authenticateService()
        val observable = service.getReportForm("t3_foo")
        assertNotNull("observable == null", observable)

        val test = TestSubscriber<ReportForm>()
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_report_form.json"))

        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
    }

    @Test
    fun testGetReportForm_noauth() {
//        authenticateService()
        val observable = service.getReportForm("t3_foo")
        assertNotNull("observable == null", observable)

        val test = TestSubscriber<ReportForm>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
    }

    @Test
    fun testGetReportForm_noId() {
        authenticateService()
        val observable = service.getReportForm(null)

        val test = TestSubscriber<ReportForm>()
        observable.subscribe(test)

        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testReport() {
        authenticateService()
        val observable = service.report("t3_foo", "", "", "tomfoolery")
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<Void>()
        mockServer.enqueue(MockResponse())
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
    }

    @Test
    fun testReport_noId() {
        authenticateService()
        val observable = service.report(null, null, null, "tomfoolery")
        val test = TestSubscriber<Void>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testReport_noReason() {
        authenticateService()
        val observable = service.report("", null, null, null)
        val test = TestSubscriber<Void>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("NullPointerException expected",
                NullPointerException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testReport_noauth() {
//        authenticateService()
        val observable = service.report("t3_foo", "", "", "")
        val test = TestSubscriber<Void>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
    }

    @Test
    fun testReport_httpError() {
        authenticateService()
        val observable = service.report("t3_linkId", null, null, "tomfoolery")
        val test = TestSubscriber<Void>()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("HttpException expected",
                HttpException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun submit_successful() {
        authenticateService()
        val observable = service.submit(
                "damien5314apitest", "link", "foo", "foo://127.0.0.1", "bar", false, false
        )
        val observer = TestSubscriber<Void>()
        mockServer.enqueue(MockResponse()) // TODO: Input correct body response

        observable.subscribe(observer)

        observer.assertValueCount(1)
        observer.assertCompleted()
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