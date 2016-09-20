package rxreddit.api

import org.junit.*
import org.junit.Assert.*
import rx.observers.TestSubscriber
import rxreddit.model.*
import rxreddit.test.assertSuccessfulEvents
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
    assertNotNull("observable == null", observable)
    val test = TestSubscriber<UserAccessToken>()
    observable.subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
  }

  @Test
  fun testGetUserIdentity() {
    val mockService = getRedditServiceMock()
    mockService.processAuthenticationCallback("")
        .toBlocking().subscribe()
    val test = TestSubscriber<UserIdentity>()
    mockService.getUserIdentity().subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
  }

  @Test
  fun testGetUserSettings() {
    val mockService = getRedditServiceMock()
    mockService.processAuthenticationCallback("")
        .toBlocking().subscribe()
    val test = TestSubscriber<UserSettings>()
    mockService.getUserSettings().subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
  }

  @Test
  fun testUpdateUserSettings() {
    val mockService = getRedditServiceMock()
    mockService.processAuthenticationCallback("")
        .toBlocking().subscribe()
    val test = TestSubscriber<Void>()
    val map = HashMap<String, String>()
    mockService.updateUserSettings(map).subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testLoadLinks() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<ListingResponse>()
    mockService.loadLinks("", "", "", "", "").subscribe(test)
    test.assertSuccessfulEvents(1)
    test.onNextEvents[0].apply {
      assertNotEquals("no links loaded", 0, data.children.size)
    }
  }

  @Test
  fun testLoadLinkComments() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<List<ListingResponse>>()
    mockService.loadLinkComments("", "", "", "").subscribe(test)
    test.assertSuccessfulEvents(1)
    test.onNextEvents[0][0].apply {
      assertEquals("unexpected number of links loaded", 1, data.children.size)
    }
    test.onNextEvents[0][1].apply {
      assertNotEquals("no comments loaded", 0, data.children.size)
    }
  }

  @Test
  fun testLoadMoreChildren() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<MoreChildrenResponse>()
    mockService.loadMoreChildren(null, null, null).subscribe(test)
    test.assertSuccessfulEvents(1)
    test.onNextEvents[0].apply {
      assertNotEquals("no children loaded", 0, childComments.size)
    }
  }

  @Test
  fun testGetUserInfo() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<UserIdentity>()
    mockService.getUserInfo("").subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
  }

  @Test
  fun testGetFriendInfo() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<FriendInfo>()
    mockService.getFriendInfo("").subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
  }

  @Test
  fun testGetUserTrophies() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<List<Listing>>()
    mockService.getUserTrophies("").subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
    test.onNextEvents[0].apply {
      assertNotEquals("no trophies loaded", 0, size)
    }
  }

  private fun testLoadUserProfile(show: String) {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<ListingResponse>()
    mockService.loadUserProfile("overview", "", "", "", "", "").subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
    test.onNextEvents[0].apply {
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
    mockService.addFriend("").subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testDeleteFriend() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<Void>()
    mockService.deleteFriend("").subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testSaveFriendNote() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<Void>()
    mockService.saveFriendNote("", "").subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testGetSubredditInfo() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<Subreddit>()
    mockService.getSubredditInfo("").subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
  }

  @Test
  fun testVote() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<Void>()
    mockService.vote(null, 0).subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testSave() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<Void>()
    mockService.save(null, null, true).subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testHide() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<Void>()
    mockService.hide(null, true).subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testReport() {
    val mockService = getRedditServiceMock()
    val observable = mockService.report("", "")
    assertNotNull("observable == null", observable)
  }

  @Test
  fun testAddComment() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<Comment>()
    mockService.addComment("", "").subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
  }

  private fun testGetInbox(show: String) {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<ListingResponse>()
    mockService.getInbox(show, null, null).subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
    test.onNextEvents[0].apply {
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
    mockService.markAllMessagesRead().subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testMarkMessagesRead() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<Void>()
    mockService.markMessagesRead("").subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testMarkMessagesUnread() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<Void>()
    mockService.markMessagesUnread("").subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testRevokeAuthentication() {
    val mockService = getRedditServiceMock()
    val test = TestSubscriber<Void>()
    mockService.revokeAuthentication().subscribe(test)
    test.assertSuccessfulEvents(1)
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
