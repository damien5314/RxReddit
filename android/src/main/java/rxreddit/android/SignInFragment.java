package rxreddit.android;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.Map;

import rxreddit.util.RxRedditUtil;

import static android.app.Activity.RESULT_OK;
import static rxreddit.android.SignInActivity.EXTRA_CALLBACK_URL;

public class SignInFragment extends Fragment {

    private static final String ARG_AUTH_URL = "ARG_AUTH_URL";

    private String authorizationUrl;
    private String redirectUri;
    private WebView webView;

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
        authorizationUrl = args.getString(ARG_AUTH_URL);
        Map<String, String> params = RxRedditUtil.getQueryParametersFromUrl(authorizationUrl);
        redirectUri = params.get("redirect_uri");
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rxr_web_view_fragment, container, false);
        webView = view.findViewById(R.id.rxr_web_view);
        configureWebView(webView);
        webView.setWebViewClient(new RxRedditWebViewClient(this, redirectUri));
        final ProgressBar progressBar = view.findViewById(R.id.rxr_progress_bar);
        webView.setWebChromeClient(getProgressBarChromeClient(progressBar));
        webView.setOnKeyListener(getBackKeyListener());
        webView.loadUrl(authorizationUrl);
        return view;
    }

    private void configureWebView(@NonNull WebView webView) {
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(null);
        }

        WebSettings settings = webView.getSettings();
        settings.setSaveFormData(false);
        settings.setSavePassword(false); // Not needed for API level 18 or greater (deprecated)
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
    }

    @Override
    public void onDestroyView() {
        if (webView != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.removeAllViews();
            webView.destroy();
        }
        super.onDestroyView();
    }

    private void onCallbackUrlReceived(String url) {
        finish(url);
    }

    private void finish(String url) {
        Intent data = new Intent();
        data.putExtra(EXTRA_CALLBACK_URL, url);

        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, data);
        } else if (getActivity() instanceof SignInActivity) {
            getActivity().setResult(RESULT_OK, data);
            getActivity().finish();
        }
    }

    protected WebChromeClient getProgressBarChromeClient(final ProgressBar progressBar) {
        return new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }
        };
    }

    protected View.OnKeyListener getBackKeyListener() {
        return (v1, keyCode, event) -> {
            // Check if the key event was the Back button and if there's history
            if (event.getAction() == KeyEvent.ACTION_UP
                    && (keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            return false;
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.rxr_web_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            webView.reload();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected static class RxRedditWebViewClient extends WebViewClient {

        private final SignInFragment mSignInFragment;
        private final String mRedirectUri;

        public RxRedditWebViewClient(
                @NonNull SignInFragment fragment,
                @NonNull String redirectUri) {
            mSignInFragment = fragment;
            mRedirectUri = redirectUri;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Map<String, String> params = RxRedditUtil.getQueryParametersFromUrl(url);
            if (url.startsWith(mRedirectUri) && params.containsKey("code")) {
                mSignInFragment.onCallbackUrlReceived(url);
                return true;
            }
            return false;
        }
    }
}
