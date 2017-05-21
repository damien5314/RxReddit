package rxreddit.api

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import rxreddit.RxRedditUtil
import rxreddit.test.MockAuthServerDispatcher

abstract class _RedditServiceTests {

    val mockServer = MockWebServer()
    val mockAuthServer = MockWebServer().apply { setDispatcher(MockAuthServerDispatcher()) }

    val service =
            RedditService.Builder()
                    .baseUrl(mockServer.url("/").url().toExternalForm())
                    .baseAuthUrl(mockAuthServer.url("/").url().toExternalForm())
                    .appId("AmkOVyT8Zl5ZIg") // fake app id
                    .redirectUri("http://127.0.0.1/") // redirect uri
                    .deviceId("dd076025-1631-49a6-b52f-612ba75a4023") // random UUID for device ID
                    .userAgent(RxRedditUtil.getUserAgent("java", "rxreddit", "0.1", "damien5314"))
                    .build()

    val HTTP_ERROR_CODE = 500

    @After
    fun tearDown() {
        mockServer.shutdown()
        mockAuthServer.shutdown()
    }

    protected val TEST_AUTH_CODE = "-JSQo32iHcZmfwQ6bevdHHrJ1Nw"
    protected val TEST_AUTH_CALLBACK =
            "${service.redirectUri}?state=${RedditAuthService.STATE}&code=$TEST_AUTH_CODE"

    protected val TEST_AUTH_CALLBACK_DENIED =
            "${service.redirectUri}?state=${RedditAuthService.STATE}&error=access_denied"

    protected val TEST_AUTH_CALLBACK_BAD_STATE =
            "${service.redirectUri}?state=longcat&code=$TEST_AUTH_CODE"

    protected fun authenticateService() {
        service.processAuthenticationCallback(TEST_AUTH_CALLBACK)
                .blockingSubscribe()
    }

    protected fun deauthenticateService() {
        service.revokeAuthentication()
                .blockingSubscribe()
    }
}
