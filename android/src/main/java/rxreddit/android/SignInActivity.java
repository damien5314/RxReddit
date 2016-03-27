package rxreddit.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {
  public static final String EXTRA_AUTH_URL = "rxr_extra_auth_url";
  public static final String EXTRA_CALLBACK_URL = "rxr_extra_callback_url";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.rxr_activity_sign_in);

    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      String authUrl = extras.getString(EXTRA_AUTH_URL);
      showSignInFragment(authUrl);
    }
  }

  private void showSignInFragment(String authUrl) {
    if (authUrl != null) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.fragment, SignInFragment.newInstance(authUrl))
          .commit();
    }
  }
}
