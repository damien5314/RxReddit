package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.*
import org.junit.Test
import rx.observers.TestSubscriber
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
  fun testProcessAuthenticationCallback_nullURL() {
    val observable = service.processAuthenticationCallback(null)
    val test = TestSubscriber<UserAccessToken>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("IllegalArgumentException expected",
        IllegalArgumentException::class.java, test.onErrorEvents[0].javaClass)
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
}
