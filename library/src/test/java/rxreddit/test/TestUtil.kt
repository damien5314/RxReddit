package rxreddit.test

import okhttp3.mockwebserver.MockResponse
import okio.Buffer
import rxreddit.api._RedditServiceTests

fun MockResponse.setBodyFromFile(filename: String): MockResponse = setBody(
    Buffer().readFrom(_RedditServiceTests::class.java.classLoader.getResourceAsStream(filename))
)
