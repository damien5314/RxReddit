package rxreddit.test

import okhttp3.mockwebserver.MockResponse
import okio.Buffer
import org.junit.Assert.assertEquals
import rx.observers.TestSubscriber
import rxreddit.api._RedditServiceTests

fun <T> TestSubscriber<T>.assertSuccessfulEvents(expected: Int) {
  assertNoErrors()
  assertCompleted()
  assertUnsubscribed()
  assertEquals("unexpected number of onNext events", expected, onNextEvents.size)
}

fun <T> TestSubscriber<T>.assertErrorEvents(expected: Int) {
  assertNoValues()
  assertUnsubscribed()
  assertEquals("unexpected number of onError events", expected, onErrorEvents.size)
}

fun MockResponse.setBodyFromFile(filename: String): MockResponse = setBody(
    Buffer().readFrom(_RedditServiceTests::class.java.classLoader.getResourceAsStream(filename)))
