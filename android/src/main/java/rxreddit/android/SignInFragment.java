package rxreddit.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.Map;

import rxreddit.RxRedditUtil;

public class SignInFragment extends Fragment {

  private static final String ARG_AUTH_URL = "arg_url";

  private String mAuthorizationUrl;
  private String mRedirectUri;
  private WebView mWebView;

  public static SignInFragment newInstance(String url) {
    Bundle args = new Bundle();
    args.putString(ARG_AUTH_URL, url);
    SignInFragment fragment = new SignInFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    setHasOptionsMenu(true);

    Bundle args = getArguments();

    mAuthorizationUrl = args.getString(ARG_AUTH_URL);
    Map<String, String> params = RxRedditUtil.getQueryParametersFromUrl(mAuthorizationUrl);
    mRedirectUri = params.get("redirect_uri");
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.rxr_web_view_fragment, container, false);

    mWebView = (WebView) v.findViewById(R.id.rxr_web_view);

    final ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.rxr_progress_bar);
    progressBar.setMax(100);

    mWebView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.contains(mRedirectUri)
            && !url.equals(mAuthorizationUrl)) {
          Intent data = new Intent();
          data.putExtra(SignInActivity.EXTRA_CALLBACK_URL, url);
          getActivity().setResult(Activity.RESULT_OK, data);
          getActivity().finish();
          return true;
        }
        return false;
      }
    });

    mWebView.setWebChromeClient(new WebChromeClient() {
      @Override
      public void onProgressChanged(WebView view, int progress) {
        if (progress == 100) {
          progressBar.setVisibility(View.INVISIBLE);
        } else {
          progressBar.setVisibility(View.VISIBLE);
          progressBar.setProgress(progress);
        }
      }
    });

    mWebView.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if (event.getAction() == KeyEvent.ACTION_UP
            && (keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
          mWebView.goBack();
          return true;
        }
        return false;
      }
    });

    mWebView.loadUrl(mAuthorizationUrl);

    return v;
  }

  @Override
  public void onDestroyView() {
    if (mWebView != null) {
      ((ViewGroup) mWebView.getParent()).removeView(mWebView);
      mWebView.removeAllViews();
      mWebView.destroy();
    }
    super.onDestroyView();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.rxr_web_view, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int i = item.getItemId();
    if (i == R.id.action_refresh) {
      mWebView.reload();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
