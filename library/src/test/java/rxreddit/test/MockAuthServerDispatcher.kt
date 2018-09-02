package rxreddit.test

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.QueueDispatcher
import okhttp3.mockwebserver.RecordedRequest

class MockAuthServerDispatcher : QueueDispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse? {
        if (!responseQueue.isEmpty()) return super.dispatch(request)
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
