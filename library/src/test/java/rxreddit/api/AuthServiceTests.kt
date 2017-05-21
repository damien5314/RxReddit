package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Ignore
import org.junit.Test
import rxreddit.test.setBodyFromFile

class AuthServiceTests : _RedditServiceTests() {

    private val authService = service.authService;

    @Test
    fun testRefreshAccessToken_noRefreshToken() {
        mockAuthServer.enqueue(
                MockResponse().setBodyFromFile("model/user_access_token_no_refresh.json"))
        authenticateService()
        val test = authService.refreshUserAccessToken().test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test @Ignore("incomplete")
    fun testRefreshAccessToken_expiredToken() {
        authenticateService()
    }
}
