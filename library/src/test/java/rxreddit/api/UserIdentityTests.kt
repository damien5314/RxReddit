package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.HttpException
import rxreddit.test.setBodyFromFile
import java.util.*

class UserIdentityTests : _RedditServiceTests() {

    @Test
    fun testGetUserIdentity() {
        authenticateService()
        val observable = service.getUserIdentity()
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_me.json"))
        val test = observable.test()
        test.assertValueCount(1)
        assertNotNull(test.values()[0])
    }

    @Test
    fun testGetUserIdentity_noauth() {
//    authenticateService()
        val observable = service.getUserIdentity()
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testGetUserIdentity_httpError() {
        authenticateService()
        val observable = service.getUserIdentity()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testGetUserSettings() {
        authenticateService()
        val observable = service.getUserSettings()
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_user_settings.json"))
        val test = observable.test()
        test.assertValueCount(1)
        assertNotNull(test.values()[0])
    }

    @Test
    fun testGetUserSettings_noauth() {
//    authenticateService()
        val observable = service.getUserSettings()
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testGetUserSettings_httpError() {
        authenticateService()
        val observable = service.getUserSettings()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }

    @Test
    fun testUpdateUserSettings() {
        authenticateService()
        val map = HashMap<String, String>()
        val observable = service.updateUserSettings(map)
        assertNotNull("observable == null", observable)
        mockServer.enqueue(MockResponse())
        val test = observable.test()
        test.assertComplete()
    }

    @Test
    fun testUpdateUserSettings_noauth() {
//    authenticateService()
        val map = HashMap<String, String>()
        val observable = service.updateUserSettings(map)
        val test = observable.test()
        test.assertError(IllegalStateException::class.java)
    }

    @Test
    fun testUpdateUserSettings_httpError() {
        authenticateService()
        val map = HashMap<String, String>()
        val observable = service.updateUserSettings(map)
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        val test = observable.test()
        test.assertError(HttpException::class.java)
    }
}
