Change Log
==========

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