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

  @Test
  fun test_initialization() {
    val mockService = getRedditServiceMock()
    assertNotNull("gson is null", mockService.gson)
  }

  @Test
  fun test_getRedirectUri() {
    val mockService = getRedditServiceMock()
    val redirectUri = mockService.redirectUri
    assertEquals("unexpected redirectUri", "http://127.0.0.1/", redirectUri)
  }

  @Test
  fun test_getAuthorizationUrl() {
    val mockService = getRedditServiceMock()
    val authUrl = mockService.authorizationUrl
    val uri = URI(authUrl)
    assertNotNull("uri is null", uri)
  }

  @Test
  fun test_isUserAuthorized() {
    val mockService = getRedditServiceMock()
    val authorized = mockService.isUserAuthorized
    assertEquals("user is authorized unexpectedly", false, authorized)
  }

  @Test
  fun test_processAuthenticationCallback() {
    val mockService = getRedditServiceMock()
    val callbackUrl = ""
    val observable = mockService.processAuthenticationCallback(callbackUrl)
    assertNotNull("observable is null", observable)
  }

  @Test
  fun test_loadLinks() {
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
}
