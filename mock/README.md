This module provides a `RedditServiceMock` class that extends from the real `RedditService`. The purpose of this class is to provide a means for testing consumers of RedditService, as well as internal testing for this library, without hitting the actual reddit API.

Observables returned from this class do not hit the network, instead returning sample responses included in [/src/main/resources](/src/main/resources). Because this is a simple mock of the real service, parameters (e.g. sort, timespan) for most API methods will have no effect on the returned response.

## Gradle
```gradle
compile "com.github.damien5314.RxReddit:mock:0.+"
```