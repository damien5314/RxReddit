package rxreddit.api

import org.junit.*
import org.junit.Assert.assertNotNull
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import rxreddit.test.getRedditService

@RunWith(MockitoJUnitRunner::class)
class RedditServiceTests {

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

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testGetRedirectUri() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testGetAuthorizationUrl() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testIsUserAuthorized() {

  }

  @Test @Throws(Exception::class) @Ignore("Not implemented")
  fun testProcessAuthenticationCallback() {

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
    val service = getRedditService()
    val observable = service.loadLinks("", "", "", "", "")
    assertNotNull(observable)
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
