package ddiehl.rxreddit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ddiehl.rxreddit.sample.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxreddit.android.SignInActivity;
import rxreddit.api.NoSuchSubredditException;
import rxreddit.api.RedditService;
import rxreddit.model.Link;
import rxreddit.model.UserAccessToken;

public final class SubredditActivity extends AppCompatActivity {

    private static final String TAG = SubredditActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 0x0000001;

    private static final String SUBREDDIT = "androiddev";
    private static final String SORT = "top";
    private static final String TIMESPAN = "week";

    private FloatingActionButton fab;
    private RecyclerView recyclerView;

    private List<Link> data = new ArrayList<>();
    private LinkAdapter linkAdapter;
    private RedditService redditService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            linkAdapter = new LinkAdapter(data);
            recyclerView.setAdapter(linkAdapter);
        }

        fab = findViewById(R.id.sign_in_button);
        if (fab != null) {
            fab.setOnClickListener(view -> {
                Intent i = new Intent(this, SignInActivity.class);
                i.putExtra(SignInActivity.EXTRA_AUTH_URL, redditService.getAuthorizationUrl());
                startActivityForResult(i, RC_SIGN_IN);
            });
        }

        redditService = RedditServiceProvider.get(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Get Observable from RedditService
        redditService.loadLinks(SUBREDDIT, SORT, TIMESPAN, null, null)

                // Set timeout if you wish
                .timeout(15, TimeUnit.SECONDS)

                // Subscribe on proper threads
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                // Transform ListingResponse into list of Links
                .flatMap(response -> Observable.fromIterable(response.getData().getChildren()))
                .map(listing -> (Link) listing)
                .toList()

                // Subscribe with callbacks
                .subscribe(this::onLinksLoaded, this::onError);
    }

    void onLinksLoaded(List<Link> links) {
        data.clear();
        data.addAll(links);
        linkAdapter.notifyDataSetChanged();
    }

    void onError(Throwable error) {
        if (error instanceof NoSuchSubredditException) {
            Toast.makeText(this, "no such subreddit", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Error loading links", error);
            Toast.makeText(this, "an error occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGN_IN:
                handleSignIn(data);
                break;
        }
    }

    private void handleSignIn(Intent data) {
        if (data == null) return;
        String url = data.getStringExtra(SignInActivity.EXTRA_CALLBACK_URL);
        if (url != null) {
            redditService.processAuthenticationCallback(url)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onAuthenticated, this::onAuthenticationError);
        }
    }

    private void onAuthenticated(UserAccessToken token) {
        Intent intent = new Intent(this, UserDetailActivity.class);
        startActivity(intent);
    }

    private void onAuthenticationError(Throwable error) {
        Log.e(TAG, "Error during authentication", error);
        Snackbar.make(fab, R.string.rxr_error_during_authentication, Snackbar.LENGTH_SHORT)
                .show();
    }
}
