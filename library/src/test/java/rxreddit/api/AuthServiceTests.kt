package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import rx.observers.TestSubscriber
import rxreddit.model.UserAccessToken
import rxreddit.test.assertErrorEvents
import rxreddit.test.setBodyFromFile

class AuthServiceTests : _RedditServiceTests() {

  private val authService = service.authService;

  @Test
  fun testRefreshAccessToken_noRefreshToken() {
    mockAuthServer.enqueue(
        MockResponse().setBodyFromFile("model/user_access_token_no_refresh.json"))
    authenticateService()
    val test = TestSubscriber<UserAccessToken>()
    authService.refreshUserAccessToken().subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("IllegalStateException expected",
        IllegalStateException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test @Ignore("incomplete")
  fun testRefreshAccessToken_expiredToken() {
    authenticateService()
  }
}
