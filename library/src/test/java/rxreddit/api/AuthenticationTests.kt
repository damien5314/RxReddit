package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.*
import org.junit.Test
import retrofit2.adapter.rxjava.HttpException
import rx.observers.TestSubscriber
import rxreddit.model.ListingResponse
import rxreddit.model.UserAccessToken
import rxreddit.test.assertErrorEvents
import rxreddit.test.assertSuccessfulEvents

class AuthenticationTests : _RedditServiceTests() {

    @Test
    fun testIsUserAuthorized() {
        assertFalse("service is authorized", service.isUserAuthorized)
        authenticateService()
        assertTrue("service is not authorized", service.isUserAuthorized)
        deauthenticateService()
        assertEquals("user is still authenticated", false, service.isUserAuthorized)
    }

    @Test
    fun testProcessAuthenticationCallback_allowed() {
        val observable = service.processAuthenticationCallback(TEST_AUTH_CALLBACK)
        val test = TestSubscriber<UserAccessToken>()
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
        assertTrue("service is not authorized", service.isUserAuthorized)
    }

    @Test
    fun testProcessAuthenticationCallback_denied() {
        val observable = service.processAuthenticationCallback(TEST_AUTH_CALLBACK_DENIED)
        val test = TestSubscriber<UserAccessToken>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("IllegalStateException expected",
                IllegalStateException::class.java, test.onErrorEvents[0].javaClass)
        assertFalse("service is authorized", service.isUserAuthorized)
    }

    @Test
    fun testProcessAuthenticationCallback_badState() {
        val observable = service.processAuthenticationCallback(TEST_AUTH_CALLBACK_BAD_STATE)
        val test = TestSubscriber<UserAccessToken>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("IllegalStateException expected",
                IllegalStateException::class.java, test.onErrorEvents[0].javaClass)
        assertFalse("service is authorized", service.isUserAuthorized)
    }

    @Test
    fun testProcessAuthenticationCallback_nullURL() {
        val observable = service.processAuthenticationCallback(null)
        val test = TestSubscriber<UserAccessToken>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("IllegalArgumentException expected",
                IllegalArgumentException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testProcessAuthenticationCallback_httpError() {
        val observable = service.processAuthenticationCallback(TEST_AUTH_CALLBACK)
        val test = TestSubscriber<UserAccessToken>()
        mockAuthServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("HttpException expected",
                HttpException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testProcessAuthenticationCallback_noAuthCode() {
        val observable = service.processAuthenticationCallback("")
        val test = TestSubscriber<UserAccessToken>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("IllegalArgumentException expected",
                IllegalArgumentException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testProcessAuthenticationCallback_invalidURL() {
        val observable = service.processAuthenticationCallback("@#$%^&*")
        val test = TestSubscriber<UserAccessToken>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("IllegalArgumentException expected",
                IllegalArgumentException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testRevokeAuthentication() {
        authenticateService()
        val observable = service.revokeAuthentication()
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<Void>()
        mockServer.enqueue(MockResponse())
        observable.subscribe(test)
        test.assertSuccessfulEvents(2)
    }

    @Test
    fun testRevokeAuthentication_noauth() {
//    authenticateService()
        val observable = service.revokeAuthentication()
        val test = TestSubscriber<Void>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
    }

    @Test
    fun testGetValidToken() {
        val test = TestSubscriber<ListingResponse>()
        mockServer.enqueue(MockResponse())
        service.loadLinks(null, null, null, null, null).subscribe(test)
        // Token should be valid now
        mockServer.enqueue(MockResponse())
        service.loadLinks(null, null, null, null, null).subscribe(test)
    }
}
