Change Log
==========

Version 0.8 *(2016-06-25)*
----------------------------------
* Updated Android module targetSdkVersion to 24 (N).
* Exposed Gson instance used by RedditService Retrofit instance via public getter.
* Added configuration for using a File-based cache for OkHttp.

Version 0.7 *(2016-04-07)*
----------------------------------
* RedditService is now constructed via a `Builder`
* API methods now return an `IllegalArgumentException` in `onError` when required parameters are missing
* API calls now check for HTTP errors consistently, and return these in `onError`
* Reddit server endpoints can now be configured in RedditService, to substitute mock servers for testing
* MoreChildren, vote, save, and hide endpoints now take `String`s instead of model objects, for flexibility
* Added default no-op AccessTokenManager implementation `AccessTokenManager.NONE`
* Fixed Gson configuration which was causing some fields to be ignored during deserialization of model objects

Version 0.6 *(2016-03-31)*
----------------------------------
* Fixed null response from `RedditServiceMock::processAuthenticationCallback`

Version 0.5 *(2016-03-30)*
----------------------------------
* Added mock service module
* Switched to using `android.support.v4.app.Fragment` for Android WebView

Version 0.4 *(2016-03-26)*
----------------------------------
* Added Android extension module

Version 0.3 *(2016-03-25)*
----------------------------------
* Fixed issue clearing AccessToken when calling `RedditService::revokeAuthentication`.

Version 0.2 *(2016-03-25)*
----------------------------------
* Now checking for proper `state` parameter in the authorization callback URL.

Version 0.1 *(2016-03-24)*
----------------------------------
* Initial release