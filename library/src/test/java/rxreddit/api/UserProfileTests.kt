package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.HttpException
import rxreddit.test.setBodyFromFile

class UserProfileTests : _RedditServiceTests() {

    @Test
    fun testGetUserInfo() {
        val observable = service.getUserInfo("")
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_user_info.json"))
        val test = observable.test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
    }

    @Test
    fun testGetUserInfo_httpError() {
        val observable = service.getUserInfo("")
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testGetFriendInfo() {
        authenticateService()
        val observable = service.getFriendInfo("")
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_friend_info.json"))
        val test = observable.test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
    }

    @Test
    fun testGetFriendInfo_noauth() {
//    authenticateService()
        val observable = service.getFriendInfo("")
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testGetFriendInfo_httpError() {
        authenticateService()
        val observable = service.getFriendInfo("")
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testGetUserTrophies() {
        val observable = service.getUserTrophies("dadmachine")
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_user_trophies.json"))
        val test = observable.test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
        test.values()[0].apply {
            assertNotEquals("no trophies loaded", 0, size)
        }
    }

    @Test
    fun testGetUserTrophies_noUser() {
        val observable = service.getUserTrophies(null)
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
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
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_profile_$show.json"))
        val test = observable.test()
        test.assertValueCount(1)
        assertNotNull("response == null", test.values()[0])
        test.values()[0].apply {
            assertNotEquals("no listings loaded", 0, data.children.size)
        }
    }

    @Test
    fun testLoadUserProfile_noShow() {
        val observable = service.loadUserProfile(null, "", "", "", "", "")
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testLoadUserProfile_noUser() {
        val observable = service.loadUserProfile("", null, "", "", "", "")
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testLoadUserProfile_httpError() {
        val observable = service.loadUserProfile("", "", "", "", "", "")
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testAddFriend() {
        authenticateService()
        val observable = service.addFriend("")
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testAddFriend_noUser() {
        authenticateService()
        val observable = service.addFriend(null)
        val test = observable.test()
        test.assertError(NullPointerException::class.java)
    }

    @Test
    fun testAddFriend_noauth() {
//    authenticateService()
        val observable = service.addFriend("")
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testAddFriend_httpError() {
        authenticateService()
        val observable = service.addFriend("")
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testDeleteFriend() {
        authenticateService()
        val observable = service.deleteFriend("")
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testDeleteFriend_noUser() {
        authenticateService()
        val observable = service.deleteFriend(null)
        val test = observable.test()
        test.assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun testDeleteFriend_noauth() {
//    authenticateService()
        val observable = service.deleteFriend("")
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testDeleteFriend_httpError() {
        authenticateService()
        val observable = service.deleteFriend("")
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testSaveFriendNote() {
        authenticateService()
        val observable = service.saveFriendNote("dadmachine", "likes dank memes")
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testSaveFriendNote_noUser() {
        authenticateService()
        val observable = service.saveFriendNote(null, "likes dank memes")
        val test = observable.test()
        test.assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun testSaveFriendNote_noNote() {
        authenticateService()
        val observable = service.saveFriendNote("dadmachine", null)
        val test = observable.test()
        test.assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun testSaveFriendNote_emptyNote() {
        authenticateService()
        val observable = service.saveFriendNote("dadmachine", "")
        val test = observable.test()
        test.assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun testSaveFriendNote_noauth() {
//    authenticateService()
        val observable = service.saveFriendNote("dadmachine", "likes dank memes")
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testSaveFriendNote_httpError() {
        authenticateService()
        val observable = service.saveFriendNote("dadmachine", "likes dank memes")
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }
}
