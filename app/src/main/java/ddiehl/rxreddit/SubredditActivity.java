package ddiehl.rxreddit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ddiehl.rxreddit.sample.R;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rxreddit.android.WebViewFragment;
import rxreddit.api.RedditService;
import rxreddit.model.Link;
import rxreddit.model.UserAccessToken;

public class SubredditActivity extends AppCompatActivity {
  private static final int RC_SIGN_IN = 0x0000001;

  private FloatingActionButton mFAB;
  private RecyclerView mRecyclerView;

  private List<Link> mData = new ArrayList<>();
  private LinkAdapter mLinkAdapter;
  private RedditService mRedditService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    if (mRecyclerView != null) {
      mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
      mLinkAdapter = new LinkAdapter(mData);
      mRecyclerView.setAdapter(mLinkAdapter);
    }

    mFAB = (FloatingActionButton) findViewById(R.id.sign_in_button);
    if (mFAB != null) {
      mFAB.setOnClickListener(view -> {
        Intent i = new Intent(this, SignInActivity.class);
        i.putExtra(SignInActivity.EXTRA_AUTH_URL, mRedditService.getAuthorizationUrl());
        startActivityForResult(i, RC_SIGN_IN);
      });
    }

    mRedditService = RedditServiceProvider.get(this);
  }

  @Override
  protected void onStart() {
    super.onStart();

    // Get Observable from RedditService
    mRedditService.loadLinks("androiddev", "top", "week", null, null)

        // Set timeout if you wish
        .timeout(5, TimeUnit.SECONDS)

        // Subscribe on proper threads
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

        // Transform ListingResponse into list of Links
        .flatMap(response -> Observable.from(response.getData().getChildren()))
        .map(listing -> (Link) listing)
        .toList()

        // Subscribe with callbacks
        .subscribe(onLinksLoaded(), onError());
  }

  private Action1<List<Link>> onLinksLoaded() {
    return links -> {
      mData.clear();
      mData.addAll(links);
      mLinkAdapter.notifyDataSetChanged();
    };
  }

  private Action1<Throwable> onError() {
    return error -> Toast.makeText(this, "an error occurred", Toast.LENGTH_SHORT);
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
    String url = data.getStringExtra(WebViewFragment.EXTRA_CALLBACK_URL);
    if (url != null) {
      mRedditService.processAuthenticationCallback(url)
          .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
          .subscribe(onAuthenticated(), onAuthenticationError());
    }
  }

  private Action1<UserAccessToken> onAuthenticated() {
    return token -> {
      Intent intent = new Intent(this, UserDetailActivity.class);
      startActivity(intent);
    };
  }

  private Action1<Throwable> onAuthenticationError() {
    return error ->
        Snackbar.make(mFAB, R.string.rxr_error_during_authentication, Snackbar.LENGTH_SHORT)
        .show();
  }

  private static final class LinkAdapter extends RecyclerView.Adapter<LinkViewHolder> {

    private List<Link> data;

    public LinkAdapter(List<Link> data) {
      this.data = data;
    }

    @Override
    public LinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new LinkViewHolder(
          LayoutInflater.from(parent.getContext())
              .inflate(R.layout.link_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(LinkViewHolder holder, int position) {
      holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
      return data.size();
    }
  }

  private static final class LinkViewHolder extends RecyclerView.ViewHolder {

    private TextView mTitleText;
    private TextView mAuthorText;

    public LinkViewHolder(View itemView) {
      super(itemView);
      mTitleText = (TextView) itemView.findViewById(R.id.title_view);
      mAuthorText = (TextView) itemView.findViewById(R.id.author_view);
    }

    public void bind(Link link) {
      mTitleText.setText(link.getTitle());
      mAuthorText.setText(
          String.format(
              itemView.getContext().getString(R.string.author_formatter), link.getAuthor()));
    }
  }
}
