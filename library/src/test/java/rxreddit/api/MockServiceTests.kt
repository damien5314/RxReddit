package rxreddit.api

import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import rx.observers.TestSubscriber
import rxreddit.model.ListingResponse
import rxreddit.test.getRedditServiceMock
import java.net.URI

@RunWith(MockitoJUnitRunner::class)
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

  @Test @Throws(Exception::class)
  fun testInitialization() {
    val mockService = getRedditServiceMock()
    assertNotNull("gson == null", mockService.gson)
  }

  @Test @Throws(Exception::class)
  fun testGetRedirectUri() {
    val mockService = getRedditServiceMock()
    val redirectUri = mockService.redirectUri
    assertEquals("unexpected redirectUri", "http://127.0.0.1/", redirectUri)
  }

  @Test @Throws(Exception::class)
  fun testGetAuthorizationUrl() {
    val mockService = getRedditServiceMock()
    val authUrl = mockService.authorizationUrl
    val uri = URI(authUrl)
    assertNotNull("uri == null", uri)
  }

  @Test @Throws(Exception::class)
  fun testIsUserAuthorized() {
    val mockService = getRedditServiceMock()
    val authorized = mockService.isUserAuthorized
    assertEquals("user is authorized unexpectedly", false, authorized)
  }

  @Test @Throws(Exception::class)
  fun testProcessAuthenticationCallback() {
    val mockService = getRedditServiceMock()
    val callbackUrl = ""
    val observable = mockService.processAuthenticationCallback(callbackUrl)
    assertNotNull("observable == null", observable)
  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testGetUserIdentity() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testGetUserSettings() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testUpdateUserSettings() {

  }

  @Test @Throws(Exception::class)
  fun testLoadLinks() {
    val mockService = getRedditServiceMock()
    val testSub = TestSubscriber<ListingResponse>()
    mockService.loadLinks("", "", "", "", "").subscribe(testSub)

    testSub.assertNoErrors()
    testSub.assertCompleted()
    testSub.assertUnsubscribed()

    assertEquals("unexpected number of onNext events", 1, testSub.onNextEvents.size)

    testSub.onNextEvents[0].apply {
      assertEquals("unexpected number of listings loaded", 25, data.children.size)
    }
  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testLoadLinkComments() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testLoadMoreChildren() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testGetUserInfo() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testGetFriendInfo() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testGetUserTrophies() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testLoadUserProfile() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testAddFriend() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testDeleteFriend() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testSaveFriendNote() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testGetSubredditInfo() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testVote() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testSave() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testHide() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testReport() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testAddComment() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testGetInbox() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testMarkAllMessagesRead() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testMarkMessagesRead() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testMarkMessagesUnread() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testRevokeAuthentication() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testGetOkHttpClient() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testGetGson() {

  }
}
