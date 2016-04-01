package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.*
import org.junit.Test
import retrofit2.adapter.rxjava.HttpException
import rx.observers.TestSubscriber
import rxreddit.model.FriendInfo
import rxreddit.model.Listing
import rxreddit.model.ListingResponse
import rxreddit.model.UserIdentity
import rxreddit.test.assertErrorEvents
import rxreddit.test.assertSuccessfulEvents
import rxreddit.test.setBodyFromFile

class UserProfileTests : _RedditServiceTests() {

  @Test
  fun testGetUserInfo() {
    val observable = service.getUserInfo("")
    assertNotNull("observable == null", observable)
    val test = TestSubscriber<UserIdentity>()
    mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_user_info.json"))
    observable.subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
  }

  @Test
  fun testGetUserInfo_httpError() {
    val observable = service.getUserInfo("")
    assertNotNull("observable == null", observable)
    val test = TestSubscriber<UserIdentity>()
    mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("HttpException expected",
        HttpException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testGetFriendInfo() {
    authenticateService()
    val observable = service.getFriendInfo("")
    assertNotNull("observable == null", observable)
    val test = TestSubscriber<FriendInfo>()
    mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_friend_info.json"))
    observable.subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
  }

  @Test
  fun testGetFriendInfo_noauth() {
//    authenticateService()
    val observable = service.getFriendInfo("")
    val test = TestSubscriber<FriendInfo>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
  }

  @Test
  fun testGetFriendInfo_httpError() {
    authenticateService()
    val observable = service.getFriendInfo("")
    val test = TestSubscriber<FriendInfo>()
    mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("HttpException expected",
        HttpException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testGetUserTrophies() {
    val observable = service.getUserTrophies("dadmachine")
    assertNotNull("observable == null", observable)
    val test = TestSubscriber<List<Listing>>()
    mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_user_trophies.json"))
    observable.subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
    test.onNextEvents[0].apply {
      assertNotEquals("no trophies loaded", 0, size)
    }
  }

  @Test
  fun testGetUserTrophies_noUser() {
    val observable = service.getUserTrophies(null)
    val test = TestSubscriber<List<Listing>>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("IllegalArgumentException expected",
        IllegalArgumentException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testLoadUserProfile_overview() = testLoadUserProfile("overview")
  @Test
  fun testLoadUserProfile_comments() = testLoadUserProfile("comments")
  @Test
  fun testLoadUserProfile_submitted() = testLoadUserProfile("submitted")
  @Test
  fun testLoadUserProfile_saved() = testLoadUserProfile("saved")
  @Test
  fun testLoadUserProfile_upvoted() = testLoadUserProfile("upvoted")
  @Test
  fun testLoadUserProfile_downvoted() = testLoadUserProfile("downvoted")
  @Test
  fun testLoadUserProfile_hidden() = testLoadUserProfile("hidden")
  @Test
  fun testLoadUserProfile_gilded() = testLoadUserProfile("gilded")

  private fun testLoadUserProfile(show: String) {
    val observable = service.loadUserProfile(show, "", "", "", "", "")
    assertNotNull("observable == null", observable)
    val test = TestSubscriber<ListingResponse>()
    mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_profile_$show.json"))
    observable.subscribe(test)
    test.assertSuccessfulEvents(1)
    assertNotNull("response == null", test.onNextEvents[0])
    test.onNextEvents[0].apply {
      assertNotEquals("no listings loaded", 0, data.children.size)
    }
  }

  @Test
  fun testLoadUserProfile_noShow() {
    val observable = service.loadUserProfile(null, "", "", "", "", "")
    val test = TestSubscriber<ListingResponse>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("IllegalArgumentException expected",
        IllegalArgumentException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testLoadUserProfile_noUser() {
    val observable = service.loadUserProfile("", null, "", "", "", "")
    val test = TestSubscriber<ListingResponse>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("IllegalArgumentException expected",
        IllegalArgumentException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testLoadUserProfile_httpError() {
    val observable = service.loadUserProfile("", "", "", "", "", "")
    val test = TestSubscriber<ListingResponse>()
    mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("HttpException expected",
        HttpException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testAddFriend() {
    authenticateService()
    val observable = service.addFriend("")
    assertNotNull("observable == null", observable)
    val test = TestSubscriber<Void>()
    mockServer.enqueue(MockResponse())
    observable.subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testAddFriend_noUser() {
    authenticateService()
    val observable = service.addFriend(null)
    val test = TestSubscriber<Void>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("IllegalArgumentException expected",
        IllegalArgumentException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testAddFriend_noauth() {
//    authenticateService()
    val observable = service.addFriend("")
    val test = TestSubscriber<Void>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
  }

  @Test
  fun testAddFriend_httpError() {
    authenticateService()
    val observable = service.addFriend("")
    val test = TestSubscriber<Void>()
    mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("HttpException expected",
        HttpException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testDeleteFriend() {
    authenticateService()
    val observable = service.deleteFriend("")
    assertNotNull("observable == null", observable)
    val test = TestSubscriber<Void>()
    mockServer.enqueue(MockResponse())
    observable.subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testDeleteFriend_noUser() {
    authenticateService()
    val observable = service.deleteFriend(null)
    val test = TestSubscriber<Void>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("IllegalArgumentException expected",
        IllegalArgumentException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testDeleteFriend_noauth() {
//    authenticateService()
    val observable = service.deleteFriend("")
    val test = TestSubscriber<Void>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
  }

  @Test
  fun testDeleteFriend_httpError() {
    authenticateService()
    val observable = service.deleteFriend("")
    val test = TestSubscriber<Void>()
    mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("HttpException expected",
        HttpException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testSaveFriendNote() {
    authenticateService()
    val observable = service.saveFriendNote("dadmachine", "likes dank memes")
    assertNotNull("observable == null", observable)
    val test = TestSubscriber<Void>()
    mockServer.enqueue(MockResponse())
    observable.subscribe(test)
    test.assertSuccessfulEvents(1)
  }

  @Test
  fun testSaveFriendNote_noUser() {
    authenticateService()
    val observable = service.saveFriendNote(null, "likes dank memes")
    val test = TestSubscriber<Void>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("IllegalArgumentException expected",
        IllegalArgumentException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testSaveFriendNote_noNote() {
    authenticateService()
    val observable = service.saveFriendNote("dadmachine", null)
    val test = TestSubscriber<Void>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("IllegalArgumentException expected",
        IllegalArgumentException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testSaveFriendNote_emptyNote() {
    authenticateService()
    val observable = service.saveFriendNote("dadmachine", "")
    val test = TestSubscriber<Void>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("IllegalArgumentException expected",
        IllegalArgumentException::class.java, test.onErrorEvents[0].javaClass)
  }

  @Test
  fun testSaveFriendNote_noauth() {
//    authenticateService()
    val observable = service.saveFriendNote("dadmachine", "likes dank memes")
    val test = TestSubscriber<Void>()
    observable.subscribe(test)
    test.assertErrorEvents(1)
  }

  @Test
  fun testSaveFriendNote_httpError() {
    authenticateService()
    val observable = service.saveFriendNote("dadmachine", "likes dank memes")
    val test = TestSubscriber<Void>()
    mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
    observable.subscribe(test)
    test.assertErrorEvents(1)
    assertEquals("HttpException expected",
        HttpException::class.java, test.onErrorEvents[0].javaClass)
  }
}
