package ddiehl.rxreddit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ddiehl.rxreddit.sample.R;
import rxreddit.android.WebViewFragment;

public class SignInActivity extends AppCompatActivity {
  public static final String EXTRA_AUTH_URL = "rxr_extra_auth_url";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_in);

    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      String authUrl = extras.getString(EXTRA_AUTH_URL);
      showSignInFragment(authUrl);
    }
  }

  private void showSignInFragment(String authUrl) {
    if (authUrl != null) {
      getFragmentManager().beginTransaction()
          .replace(R.id.fragment, WebViewFragment.newInstance(authUrl))
          .commit();
    }
  }
}
