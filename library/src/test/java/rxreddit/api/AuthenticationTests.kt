package rxreddit.api

import io.reactivex.subscribers.TestSubscriber
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.*
import org.junit.Test
import retrofit2.HttpException
import rxreddit.model.ListingResponse

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
        val test = observable.test()
        test.assertValueCount(1)
        assertTrue("service is not authorized", service.isUserAuthorized)
    }

    @Test
    fun testProcessAuthenticationCallback_denied() {
        val observable = service.processAuthenticationCallback(TEST_AUTH_CALLBACK_DENIED)
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
        assertFalse("service is authorized", service.isUserAuthorized)
    }

    @Test
    fun testProcessAuthenticationCallback_badState() {
        val observable = service.processAuthenticationCallback(TEST_AUTH_CALLBACK_BAD_STATE)
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
        assertFalse("service is authorized", service.isUserAuthorized)
    }

    @Test
    fun testProcessAuthenticationCallback_nullURL() {
        val observable = service.processAuthenticationCallback(null)
        val test = observable.test()
        test.assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun testProcessAuthenticationCallback_httpError() {
        val observable = service.processAuthenticationCallback(TEST_AUTH_CALLBACK)
        mockAuthServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testProcessAuthenticationCallback_noAuthCode() {
        val observable = service.processAuthenticationCallback("")
        val test = observable.test()
        test.assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun testProcessAuthenticationCallback_invalidURL() {
        val observable = service.processAuthenticationCallback("@#$%^&*")
        val test = observable.test()
        test.assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun testRevokeAuthentication() {
        authenticateService()
        val observable = service.revokeAuthentication()
        assertNotNull(observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testRevokeAuthentication_noauth() {
//    authenticateService()
        val observable = service.revokeAuthentication()
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testGetValidToken() {
        val test = TestSubscriber<ListingResponse>()
        mockServer.enqueue(MockResponse())
        service.loadLinks(null, null, null, null, null).test()
        // Token should be valid now
        mockServer.enqueue(MockResponse())
        service.loadLinks(null, null, null, null, null).test()
    }
}
