package rxreddit.api

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.net.URI

class InitializationTests : _RedditServiceTests() {

  @Test
  fun testInitialization() {
    val service = RedditService.Builder()
        .appId("")
        .redirectUri("")
        .deviceId("")
        .userAgent("")
        .build()
    assertNotNull("service == null", service)
  }

  @Test(expected = IllegalStateException::class)
  fun testInitializationError_noAppId() {
    val service = RedditService.Builder()
//        .appId("")
        .redirectUri("")
        .deviceId("")
        .userAgent("")
        .build()
  }

  @Test(expected = IllegalStateException::class)
  fun testInitializationError_noRedirectUri() {
    val service = RedditService.Builder()
        .appId("")
//        .redirectUri("")
        .deviceId("")
        .userAgent("")
        .build()
  }

  @Test(expected = IllegalStateException::class)
  fun testInitializationError_noDeviceId() {
    val service = RedditService.Builder()
        .appId("")
        .redirectUri("")
//        .deviceId("")
        .userAgent("")
        .build()
  }

  @Test(expected = IllegalStateException::class)
  fun testInitializationError_noUserAgent() {
    val service = RedditService.Builder()
        .appId("")
        .redirectUri("")
        .deviceId("")
//        .userAgent("")
        .build()
  }

  @Test
  fun testGetRedirectUri() {
    val redirectUri = service.redirectUri
    assertEquals("unexpected redirectUri", "http://127.0.0.1/", redirectUri)
  }

  @Test
  fun testGetAuthorizationUrl() {
    val authUrl = service.authorizationUrl
    val uri = URI(authUrl)
    assertNotNull("uri == null", uri)
  }

  @Test
  fun testGetOkHttpClient() {
    val userAgent = "sample-user-agent"
    val client = service.getOkHttpClient(userAgent, 0, null, false)
    assertNotNull("okhttpclient == null", client)
  }

  @Test
  fun testGetGson() {
    val gson = service.getGson()
    assertNotNull("gson == null", gson)
  }

}
