package rxreddit.api

import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.adapter.rxjava.HttpException
import rx.observers.TestSubscriber
import rxreddit.model.UserIdentity
import rxreddit.model.UserSettings
import rxreddit.test.assertErrorEvents
import rxreddit.test.assertSuccessfulEvents
import rxreddit.test.setBodyFromFile
import java.util.*

class UserIdentityTests : _RedditServiceTests() {

    @Test
    fun testGetUserIdentity() {
        authenticateService()
        val observable = service.getUserIdentity()
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<UserIdentity>()
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_me.json"))
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
        assertNotNull("response == null", test.onNextEvents[0])
    }

    @Test
    fun testGetUserIdentity_noauth() {
//    authenticateService()
        val observable = service.getUserIdentity()
        val test = TestSubscriber<UserIdentity>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
    }

    @Test
    fun testGetUserIdentity_httpError() {
        authenticateService()
        val observable = service.getUserIdentity()
        val test = TestSubscriber<UserIdentity>()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("HttpException expected",
                HttpException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testGetUserSettings() {
        authenticateService()
        val observable = service.getUserSettings()
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<UserSettings>()
        mockServer.enqueue(MockResponse().setBodyFromFile("test/GET_user_settings.json"))
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
        assertNotNull("response == null", test.onNextEvents[0])
    }

    @Test
    fun testGetUserSettings_noauth() {
//    authenticateService()
        val observable = service.getUserSettings()
        val test = TestSubscriber<UserSettings>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
    }

    @Test
    fun testGetUserSettings_httpError() {
        authenticateService()
        val observable = service.getUserSettings()
        val test = TestSubscriber<UserSettings>()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("HttpException expected",
                HttpException::class.java, test.onErrorEvents[0].javaClass)
    }

    @Test
    fun testUpdateUserSettings() {
        authenticateService()
        val map = HashMap<String, String>()
        val observable = service.updateUserSettings(map)
        assertNotNull("observable == null", observable)
        val test = TestSubscriber<Void>()
        mockServer.enqueue(MockResponse())
        observable.subscribe(test)
        test.assertSuccessfulEvents(1)
    }

    @Test
    fun testUpdateUserSettings_noauth() {
//    authenticateService()
        val map = HashMap<String, String>()
        val observable = service.updateUserSettings(map)
        val test = TestSubscriber<Void>()
        observable.subscribe(test)
        test.assertErrorEvents(1)
    }

    @Test
    fun testUpdateUserSettings_httpError() {
        authenticateService()
        val map = HashMap<String, String>()
        val observable = service.updateUserSettings(map)
        val test = TestSubscriber<Void>()
        mockServer.enqueue(MockResponse().setResponseCode(HTTP_ERROR_CODE))
        observable.subscribe(test)
        test.assertErrorEvents(1)
        assertEquals("HttpException expected",
                HttpException::class.java, test.onErrorEvents[0].javaClass)
    }
}
