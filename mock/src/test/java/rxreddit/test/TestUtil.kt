package rxreddit.test

import org.junit.Assert.assertEquals
import rx.observers.TestSubscriber

fun <T> TestSubscriber<T>.assertSuccessfulEvents(expected: Int) {
  assertNoErrors()
  assertCompleted()
  assertUnsubscribed()
  assertEquals("unexpected number of onNext events", expected, onNextEvents.size)
}
