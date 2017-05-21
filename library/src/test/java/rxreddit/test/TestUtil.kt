package rxreddit.test

import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import okhttp3.mockwebserver.MockResponse
import okio.Buffer
import org.junit.Assert.assertEquals
import rxreddit.api._RedditServiceTests

fun <T> TestObserver<T>.assertSuccessfulEvents(expected: Int) {
    assertNoErrors()
    assertComplete()
    assertEquals("unexpected number of onNext events", expected, values().size)
}

fun <T> TestObserver<T>.assertErrorEvents(expected: Int) {
    assertNoValues()
    assertEquals("unexpected number of onError events", expected, errors().size)
}

fun MockResponse.setBodyFromFile(filename: String): MockResponse = setBody(
        Buffer().readFrom(_RedditServiceTests::class.java.classLoader.getResourceAsStream(filename)))
