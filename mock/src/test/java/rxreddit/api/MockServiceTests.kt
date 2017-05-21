package rxreddit.api

import io.reactivex.subscribers.TestSubscriber
import org.junit.*
import org.junit.Assert.*
import rxreddit.model.Comment
import rxreddit.model.ListingResponse
import rxreddit.model.Subreddit
import rxreddit.model.UserIdentity
import java.net.URI
import java.util.*

class MockServiceTests {

    companion object {
        @BeforeClass @JvmStatic
        fun setUpClass() {
        }

        @AfterClass @JvmStatic
        fun tearDownClass() {
        }
    }

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    private fun getRedditServiceMock() = RedditServiceMock()

    @Test
    fun testInitialization() {
        val mockService = getRedditServiceMock()
        assertNotNull("gson == null", mockService.gson)
    }

    @Test
    fun testGetRedirectUri() {
        val mockService = getRedditServiceMock()
        val redirectUri = mockService.redirectUri
        assertEquals("unexpected redirectUri", "http://127.0.0.1/", redirectUri)
    }

    @Test
    fun testGetAuthorizationUrl() {
        val mockService = getRedditServiceMock()
        val authUrl = mockService.authorizationUrl
        val uri = URI(authUrl)
        assertNotNull("uri == null", uri)
    }

    @Test
    fun testIsUserAuthorized() {
        val mockService = getRedditServiceMock()
        val authorized = mockService.isUserAuthorized
        assertEquals("user is authorized unexpectedly", false, authorized)
    }

    @Test
    fun testProcessAuthenticationCallback() {
        val mockService = getRedditServiceMock()
        val observable = mockService.processAuthenticationCallback("")
        assertNotNull(observable)
        val test = observable.test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
    }

    @Test
    fun testGetUserIdentity() {
        val mockService = getRedditServiceMock()
        mockService.processAuthenticationCallback("").blockingSubscribe()
        val test = TestSubscriber<UserIdentity>()
        mockService.getUserIdentity().test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
    }

    @Test
    fun testGetUserSettings() {
        val mockService = getRedditServiceMock()
        mockService.processAuthenticationCallback("").blockingSubscribe()
        val test = mockService.getUserSettings().test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
    }

    @Test
    fun testUpdateUserSettings() {
        val mockService = getRedditServiceMock()
        mockService.processAuthenticationCallback("").blockingSubscribe()
        val map = HashMap<String, String>()
        val test = mockService.updateUserSettings(map).test()
        test.assertValueCount(1)
    }

    @Test
    fun testLoadLinks() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<ListingResponse>()
        mockService.loadLinks("", "", "", "", "").test()
        test.assertValueCount(1)
        test.values()[0].apply {
            assertNotEquals("no links loaded", 0, data.children.size)
        }
    }

    @Test
    fun testLoadLinkComments() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<List<ListingResponse>>()
        mockService.loadLinkComments("", "", "", "").test()
        test.assertValueCount(1)
        test.values()[0][0].apply {
            assertEquals("unexpected number of links loaded", 1, data.children.size)
        }
        test.values()[0][1].apply {
            assertNotEquals("no comments loaded", 0, data.children.size)
        }
    }

    @Test
    fun testLoadMoreChildren() {
        val mockService = getRedditServiceMock()
        val test = mockService.loadMoreChildren(null, null, null).test()
        test.assertValueCount(1)
        test.values()[0].apply {
            assertNotEquals("no children loaded", 0, childComments.size)
        }
    }

    @Test
    fun testGetUserInfo() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<UserIdentity>()
        mockService.getUserInfo("").test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
    }

    @Test
    fun testGetFriendInfo() {
        val mockService = getRedditServiceMock()
        val test = mockService.getFriendInfo("").test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
    }

    @Test
    fun testGetUserTrophies() {
        val mockService = getRedditServiceMock()
        val test = mockService.getUserTrophies("").test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
        test.values()[0].apply {
            assertNotEquals("no trophies loaded", 0, size)
        }
    }

    private fun testLoadUserProfile(show: String) {
        val mockService = getRedditServiceMock()
        val test = mockService.loadUserProfile("overview", "", "", "", "", "").test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
        test.values()[0].apply {
            assertNotEquals("no listings loaded", 0, data.children.size)
        }
    }

    @Test
    fun testLoadUserProfile_overview() = testLoadUserProfile("overview")

    @Test
    fun testLoadUserProfile_comments() = testLoadUserProfile("comments")

    @Test
    fun testLoadUserProfile_submitted() = testLoadUserProfile("submitted")

    @Test
    fun testLoadUserProfile_saved() = testLoadUserProfile("saved")

    @Test
    fun testLoadUserProfile_upvoted() = testLoadUserProfile("upvoted")

    @Test
    fun testLoadUserProfile_downvoted() = testLoadUserProfile("downvoted")

    @Test
    fun testLoadUserProfile_hidden() = testLoadUserProfile("hidden")

    @Test
    fun testLoadUserProfile_gilded() = testLoadUserProfile("gilded")

    @Test
    fun testAddFriend() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.addFriend("").test()
        test.assertValueCount(1)
    }

    @Test
    fun testDeleteFriend() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.deleteFriend("").test()
        test.assertValueCount(1)
    }

    @Test
    fun testSaveFriendNote() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.saveFriendNote("", "").test()
        test.assertValueCount(1)
    }

    @Test
    fun testGetSubredditInfo() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Subreddit>()
        mockService.getSubredditInfo("").test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
    }

    @Test
    fun testGetSubredditRules() {
        val mockService = getRedditServiceMock()

        val observer = mockService.getSubredditRules("").test()

        observer.assertValueCount(1)
        assertNotNull("response == null", observer.values()[0])
    }

    @Test @Ignore("/r/subreddit/about/sidebar endpoint is broken")
    fun testGetSubredditSidebar() {
        val mockService = getRedditServiceMock()

        val observer = mockService.getSubredditSidebar("").test()

        observer.assertValueCount(1)
        assertNotNull("response == null", observer.values()[0])
    }

    @Test
    fun testGetSubredditSticky() {
        val mockService = getRedditServiceMock()

        val observer = mockService.getSubredditSticky("").test()

        observer.assertValueCount(1)
        assertNotNull("response == null", observer.values()[0])
    }

    @Test
    fun testVote() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.vote(null, 0).test()
        test.assertValueCount(1)
    }

    @Test
    fun testSave() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.save(null, null, true).test()
        test.assertValueCount(1)
    }

    @Test
    fun testHide() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.hide(null, true).test()
        test.assertValueCount(1)
    }

    @Test
    fun testReport() {
        val mockService = getRedditServiceMock()
        val observable = mockService.report("", "", "", "")
        assertNotNull("observable == null", observable)
    }

    @Test
    fun testSubmit() {
        val mockService = getRedditServiceMock()
        val observable = mockService.submit("", "", "", "", "", false, false)
        assertNotNull("observable == null", observable)
    }

    @Test
    fun testAddComment() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Comment>()
        mockService.addComment("", "").test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
    }

    private fun testGetInbox(show: String) {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<ListingResponse>()
        mockService.getInbox(show, null, null).test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
        test.values()[0].apply {
            assertNotEquals("no listings loaded", 0, data.children.size)
        }
    }

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

    @Test
    fun testMarkAllMessagesRead() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.markAllMessagesRead().test()
        test.assertValueCount(1)
    }

    @Test
    fun testMarkMessagesRead() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.markMessagesRead("").test()
        test.assertValueCount(1)
    }

    @Test
    fun testMarkMessagesUnread() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.markMessagesUnread("").test()
        test.assertValueCount(1)
    }

    @Test
    fun testGetSubreddits() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<ListingResponse>()
        mockService.getSubreddits("subscriber", null, null).test()
        test.assertValueCount(1)
    }

    @Test
    fun testSubscribe() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.subscribe("AskReddit").test()
        test.assertValueCount(1)
    }

    @Test
    fun testSubscribeAll() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.subscribe(listOf("AskReddit", "sports")).test()
        test.assertValueCount(1)
    }

    @Test
    fun testUnsubscribe() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.unsubscribe("AskReddit").test()
        test.assertValueCount(1)
    }

    @Test
    fun testUnsubscribeAll() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.unsubscribe(listOf("AskReddit", "sports")).test()
        test.assertValueCount(1)
    }

    @Test
    fun testRevokeAuthentication() {
        val mockService = getRedditServiceMock()
        val test = TestSubscriber<Void>()
        mockService.revokeAuthentication().test()
        test.assertValueCount(1)
    }

    @Test
    fun testGetOkHttpClient() {
        val mockService = getRedditServiceMock()
        val userAgent = "sample-user-agent"
        val client = mockService.getOkHttpClient(userAgent, 0, null, true)
        assertNotNull(client)
    }

    @Test
    fun testGetGson() {
        val mockService = getRedditServiceMock()
        val gson = mockService.gson
        assertNotNull(gson)
    }
}
