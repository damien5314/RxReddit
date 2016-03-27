## Android Extensions

Seeing as how this library was extracted from one of my Android apps, it seems fitting to include an extension module providing some Android-specific APIs that make it easier to integrate RxReddit. This library requires a minSdkVersion of 11 or higher.

Add the dependency via gradle:

```gradle
compile "com.github.damien5314.RxReddit:library:0.+"
compile "com.github.damien5314.RxReddit:android:0.+"
```

To generate a device ID that is automatically stored in your app's `SharedPreferences` and reused on subsequent launches, you can use `AndroidUtil.getDeviceId(Context)`.

[AndroidAccessTokenManager](/android/src/main/java/rxreddit/android/AndroidAccessTokenManager.java) is an `AccessTokenManager` implementation you can pass into the constructor of `RedditService` to persist access tokens through `SharedPreferences`.

Launching `SignInActivity` will display a `WebView` using the authorization URL passed as an extra.

```java
Intent data = new Intent(this, SignInActivity.class);
data.putExtra(SignInActivity.EXTRA_AUTH_URL, mRedditService.getAuthorizationUrl());
startActivityForResult(data, REQUEST_SIGN_IN);
```

Listen for a successful result and extract the callback URL from the result Intent. With this you can get an `Observable` from `RedditService` that will process the callback URL and authenticate the user.

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case REQUEST_SIGN_IN:
        if (resultCode == Activity.RESULT_OK) {
          String callbackUrl = data.getStringExtra(SignInActivity.EXTRA_CALLBACK_URL);
          mRedditService.processAuthenticationCallback(callbackUrl)
              .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
              .subscribe(onAuthenticated(), onError());
        }
        break;
    }
}
```

Make sure you have `android.permission.INTERNET` and `SignInActivity` declared in your AndroidManifest.xml.

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
  <uses-permission android:name="android.permission.INTERNET" />
  <application>
    <activity android:name="rxreddit.android.SignInActivity" />
  </application>
</manifest>
```