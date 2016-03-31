package rxreddit.test

import rxreddit.RxRedditUtil
import rxreddit.api.RedditService
import rxreddit.api.RedditServiceMock

fun getRedditService(): RedditService {
  return RedditService(
      "AmkOVyT8Zl5ZIg", // fake app id
      "http://127.0.0.1/", // redirect uri
      "dd076025-1631-49a6-b52f-612ba75a4023", // random UUID for device ID
      RxRedditUtil.getUserAgent("java", "rxreddit", "0.1", "damien5314"),
      null
  )
}

fun getRedditServiceMock(): RedditServiceMock {
  return RedditServiceMock(
      "AmkOVyT8Zl5ZIg", // fake app id
      "http://127.0.0.1/", // redirect uri
      "dd076025-1631-49a6-b52f-612ba75a4023", // random UUID for device ID
      RxRedditUtil.getUserAgent("java", "rxreddit", "0.1", "damien5314"),
      null
  )
}
