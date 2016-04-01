package rxreddit.test

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockAuthServerDispatcher : Dispatcher() {
  override fun dispatch(request: RecordedRequest): MockResponse? {
    when (request.path) {
      "/api/v1/access_token" -> {
        // TODO How can we read fields from the request to return either application or user access token as appropriate?
        return MockResponse()
            .setResponseCode(200)
            .setBodyFromFile("model/user_access_token.json")
      }
      "/api/v1/revoke_token" -> {
        return MockResponse()
            .setResponseCode(204)
      }
      else -> System.err.println("Unrecognized path: ${request.path}")
    }
    return null
  }
}
