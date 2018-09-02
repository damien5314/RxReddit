package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test
import retrofit2.HttpException
import rxreddit.test.setBodyFromFile

class SubredditTests : _RedditServiceTests() {

    @Test
    fun testLoadLinks() {
        val observable = service.loadLinks(null, null, null, null, null)
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_hot.json"))
        val test = observable.test()
        test.assertValueCount(1)
        test.values()[0].apply {
            assertEquals(25, data.children.size)
        }
    }

    @Test
    fun testLoadLinks_invalidSubreddit() {
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_subreddit-doesnotexist.json"))

        val test = service.loadLinks(null, null, null, null, null).test()

        test.assertError(NoSuchSubredditException::class.java)
    }

    @Test
    fun testLoadLinks_httpError() {
        val observable = service.loadLinks(null, null, null, null, null)
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testLoadLinkComments() {
        val observable = service.loadLinkComments("pics", "commentId", null, null)
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_submission_comments.json"))
        val test = observable.test()
        test.assertValueCount(1)
        test.values()[0][0].apply {
            assertEquals("unexpected number of links loaded", 1, data.children.size)
        }
        test.values()[0][1].apply {
            assertNotEquals("no comments loaded", 0, data.children.size)
        }
    }

    @Test
    fun testLoadLinkComments_noSubreddit() {
        val observable = service.loadLinkComments(null, "", null, null)
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testLoadLinkComments_noLink() {
        val observable = service.loadLinkComments("", null, null, null)
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testLoadLinkComments_httpError() {
        val observable = service.loadLinkComments("pics", "commentId", null, null)
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testLoadMoreChildren() {
        val observable = service.loadMoreChildren("linkId", listOf("comment1", "comment2"), null)
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_more_children.json"))
        val test = observable.test()
        test.assertValueCount(1)
        test.values()[0].apply {
            assertNotEquals("no children loaded", 0, childComments.size)
        }
    }

    @Test
    fun testLoadMoreChildren_noLink() {
        val observable = service.loadMoreChildren(null, listOf("comment1", "comment2"), null)
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testLoadMoreChildren_noComments() {
        val observable = service.loadMoreChildren("linkId", emptyList(), null)
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testLoadMoreChildren_httpError() {
        val observable = service.loadMoreChildren("linkId", listOf("comment1", "comment2"), "top")
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testGetSubredditInfo() {
        val observable = service.getSubredditInfo("AskReddit")
        assertNotNull("observable == null", observable)

        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_subreddit_info.json"))
        val test = observable.test()

        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
    }

    @Test
    fun testGetSubredditInfo_noSubreddit() {
        val observable = service.getSubredditInfo(null)
        val test = observable.test()

        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testGetSubredditInfo_invalidSubreddit() {
        val gibberish = "ljawdkljawdkawdawhkdawkdchawcdhakjw"
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_subreddit-doesnotexist.json"))

        val test = service.getSubredditInfo(gibberish).test()

        test.assertError(NoSuchSubredditException::class.java)
    }

    @Test
    fun testGetSubredditInfo_httpError() {
        val observable = service.getSubredditInfo("")
        assertNotNull("observable == null", observable)

        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()

        test.assertError(HttpException::class.java)
    }

    @Test
    fun testGetSubredditRules() {
        val observable = service.getSubredditRules("gaming")
        assertNotNull("observable == null", observable)

        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_subreddit_about_rules.json"))
        val observer = observable.test()

        observer.assertValueCount(1)
        assertNotNull("response == null", observer.values()[0])
    }

    @Test
    fun testGetSubredditRules_null() {
        val observable = service.getSubredditRules(null)
        val test = observable.test()

        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testGetSubredditRules_invalidSubreddit() {
        val gibberish = "ljawdkljawdkawdawhkdawkdchawcdhakjw"
        val observable = service.getSubredditRules(gibberish)
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_subreddit-doesnotexist.json"))

        val test = observable.test()

        test.assertError(NoSuchSubredditException::class.java)
    }

    @Test
    fun testGetSubredditRules_httpError() {
        val observable = service.getSubredditRules("gaming")
        assertNotNull("observable == null", observable)

        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()

        test.assertError(HttpException::class.java)
    }

    @Test
    @Ignore("/r/subreddit/about/sidebar is broken")
    fun testGetSubredditSidebar() {

    }

    @Test
    @Ignore("/r/subreddit/about/sidebar is broken")
    fun testGetSubredditSidebar_null() {

    }

    @Test
    @Ignore("/r/subreddit/about/sidebar is broken")
    fun testGetSubredditSidebar_httpError() {

    }

    @Test
    fun testGetSubredditSticky() {
        val observable = service.getSubredditSticky("gaming")
        assertNotNull("observable == null", observable)

        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_subreddit_about_sticky.json"))
        val observer = observable.test()

        observer.assertValueCount(1)
        assertNotNull("response == null", observer.values()[0])
    }

    @Test
    fun testGetSubredditSticky_null() {
        val observable = service.getSubredditSticky(null)
        val test = observable.test()

        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testGetSubredditSticky_httpError() {
        val observable = service.getSubredditSticky("gaming")
        assertNotNull("observable == null", observable)

        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()

        test.assertError(HttpException::class.java)
    }

    @Test
    fun testVote() {
        authenticateService()
        val observable = service.vote("t3_linkId", 0)
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testVote_noID() {
        authenticateService()
        val observable = service.vote(null, 0)
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testVote_noauth() {
//    authenticateService()
        val observable = service.vote("t3_linkId", 0)
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testVote_httpError() {
        authenticateService()
        val observable = service.vote("t3_linkId", 0)
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testSave() {
        authenticateService()
        val observable = service.save("t3_linkId", null, true)
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testUnsave() {
        authenticateService()
        val observable = service.save("t3_linkId", null, false)
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testSave_noID() {
        authenticateService()
        val observable = service.save(null, null, true)
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testSave_noauth() {
//    authenticateService()
        val observable = service.save("t3_linkId", null, true)
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testHide() {
        authenticateService()
        val observable = service.hide("t3_linkId", true)
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testUnhide() {
        authenticateService()
        val observable = service.hide("t3_linkId", false)
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testHide_noID() {
        authenticateService()
        val observable = service.hide(null, true)
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testHide_noauth() {
//    authenticateService()
        val observable = service.hide("t3_linkId", true)
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testHide_httpError() {
        authenticateService()
        val observable = service.hide("t3_linkId", true)
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testGetReportForm() {
        authenticateService()
        val observable = service.getReportForm("t3_foo")
        assertNotNull("observable == null", observable)

        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_report_form.json"))

        val test = observable.test()
        test.assertValueCount(1)
    }

    @Test
    fun testGetReportForm_noauth() {
//        authenticateService()
        val observable = service.getReportForm("t3_foo")
        assertNotNull("observable == null", observable)

        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testGetReportForm_noId() {
        authenticateService()
        val observable = service.getReportForm(null)

        val test = observable.test()

        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testReport() {
        authenticateService()
        val observable = service.report("t3_foo", "", "", "tomfoolery")
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testReport_noId() {
        authenticateService()
        val observable = service.report(null, null, null, "tomfoolery")
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testReport_noReason() {
        authenticateService()
        val observable = service.report("", null, null, null)
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testReport_noauth() {
//        authenticateService()
        val observable = service.report("t3_foo", "", "", "")
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testReport_httpError() {
        authenticateService()
        val observable = service.report("t3_linkId", null, null, "tomfoolery")
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    private fun getSubmitObservable() =
        service.submit(
            "damien5314apitest", "link", "foo", "foo://127.0.0.1", "bar", false, false
        )

    @Test
    fun submit_successful() {
        authenticateService()
        val observable = getSubmitObservable()
        mockServer.enqueue(MockResponse().setBodyFromFile("test/POST_submit.json"))

        val observer = observable.test()

        observer.assertValueCount(1)
        observer.assertComplete()
    }

    @Test
    fun submit_nullSubreddit_throwsError() {
        val observable = service.submit(null, "link", "foo", "foo://127.0.0.1", "bar", false, false)
        val observer = observable.test()
        observer.assertError(NullPointerException::class.java)
    }

    @Test
    fun submit_nullKind_throwsError() {
        val observable =
            service.submit("AskReddit", null, "foo", "foo://127.0.0.1", "bar", false, false)
        val observer = observable.test()
        observer.assertError(NullPointerException::class.java)
    }

    @Test
    fun submit_nullTitle_throwsError() {
        val observable =
            service.submit("AskReddit", "link", null, "foo://127.0.0.1", "bar", false, false)
        val observer = observable.test()
        observer.assertError(NullPointerException::class.java)
    }

    @Test
    fun submit_noAuth_throwsError() {
//        authenticateService()
        val observable = getSubmitObservable()

        val observer = observable.test()

        observer.assertError(IllegalStateException::class.java)
    }

    @Test
    fun submit_httpError_throwsError() {
        authenticateService()
        val observable = getSubmitObservable()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))

        val observer = observable.test()

        observer.assertError(HttpException::class.java)
    }

    @Test
    fun testAddComment() {
        authenticateService()
        val observable = service.addComment("linkId", "I like cats")
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse().setBodyFromFile("test/POST_add_comment.json"))
        val test = observable.test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
    }

    @Test
    fun testAddComment_noParent() {
        authenticateService()
        val observable = service.addComment(null, "I like cats")
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testAddComment_noText() {
        authenticateService()
        val observable = service.addComment("linkId", null)
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testAddComment_noauth() {
//    authenticateService()
        val observable = service.addComment("", "")
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testAddComment_httpError() {
        authenticateService()
        val observable = service.addComment("", "")
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testAddComment_addCommentError() {
        authenticateService()
        val observable = service.addComment("", "")
        mockServer.enqueue(MockResponse().setBodyFromFile("model/add_comment_error.json"))
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }
}
