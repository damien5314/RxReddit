# RxReddit

This is a Java API wrapper for reddit, extracted from one of my other projects.

You are still subject to reddit's API access rules while using RxReddit. Please read the [API documentation](https://github.com/reddit/reddit/wiki/API) before use to ensure you are complying with reddit's terms and conditions.

## Register your app
All apps must be registered with a reddit account in order to access the API. This process will get you your app ID and redirect URI which are required for using this library. Follow the instructions [here](https://www.reddit.com/prefs/apps) to register your app.

Note: So far I have only tested this library with apps of "installed" type. Apps with secrets will require an enhancement and should be supported eventually.

## Overview
This library depends internally on [Retrofit 2](http://square.github.io/retrofit/) and [RxJava](https://github.com/ReactiveX/RxJava). You will get these dependencies for free after importing RxReddit.

Clients can make requests to the reddit API through an instance of `RedditService`. The class is heavy, and authentication state is cached separately for each instance, so I recommend making your instance a singleton.

A `RedditService` instance can be configured via `RedditService.Builder`, which requires you to set a few fields:

`String redditAppId`  
ID obtained from registering your app

`String redirectUri`  
Uri supplied while registering your app

`String deviceId`  
A unique, per-device ID generated by your client, represented by a 20-30 character ASCII string. See the [FAQ](https://github.com/reddit/reddit/wiki/OAuth2) for instructions on generating this.

`String userAgent`  
A user agent string to be sent with all API requests. Get from `RxRedditUtil.getUserAgent` or supply a custom one.

`AccessTokenManager atm`  
(Optional) Pass an implementation of `AccessTokenManager` which can save and load `AccessToken` instances between process restarts.

## Using authenticated endpoints

In order to use endpoints requiring a user context (voting on submissions, submitting new comments, etc) you must present the authorization page to your users, then pass the callback URL back to `RedditService` once the user authorizes your app. The library will automatically extract the authorization code from the callback URL and use this to authenticate the user.

Attempting to access an authenticated API without being in authenticated will result in an `IllegalStateException` that can be handled in the Observable's `onError` callback. You can verify the service is in authenticated state by calling `RedditService::isUserAuthorized`.

## Gradle

[![Release](https://jitpack.io/v/damien5314/RxReddit.svg)](https://jitpack.io/#damien5314/RxReddit)

```gradle
allprojects {
  repositories {
    maven { url "https://jitpack.io" }
  }
}
dependencies {
  compile "com.github.damien5314.RxReddit:library:0.+"
}
```

Check out the [Android extension module](/android) for information about using classes and utilities related to Android applications.

Check out the [mock service](/mock) module for information about using a mocked version of `RedditService` in tests or debugging.