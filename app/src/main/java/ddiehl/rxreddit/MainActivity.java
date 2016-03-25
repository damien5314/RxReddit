package ddiehl.rxreddit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import java.util.UUID;

import ddiehl.rxreddit.sample.BuildConfig;
import ddiehl.rxreddit.sample.R;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rxreddit.android.AndroidAccessTokenManager;
import rxreddit.api.RedditService;
import rxreddit.model.Link;

public class MainActivity extends AppCompatActivity {

  private List<Link> mData = new ArrayList<>();
  private LinkAdapter mLinkAdapter = new LinkAdapter(mData);

  private RedditService mRedditService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    RecyclerView listView = (RecyclerView) findViewById(R.id.recycler_view);
    if (listView != null) {
      listView.setLayoutManager(new LinearLayoutManager(this));
      listView.setAdapter(mLinkAdapter);
    }

    mRedditService = new RedditService(
        BuildConfig.REDDIT_APP_ID,
        BuildConfig.REDDIT_REDIRECT_URI,
        getDeviceId(),
        String.format("android:ddiehl.rxreddit.sampleapp:%s", BuildConfig.VERSION_NAME),
        new AndroidAccessTokenManager(this)
    );
  }

  @Override
  protected void onStart() {
    super.onStart();

    // Get Observable from RedditService
    mRedditService.loadLinks("androiddev", "top", "week", null, null)

//        .timeout(5, TimeUnit.SECONDS)

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
      mData.addAll(links);
      mLinkAdapter.notifyDataSetChanged();
    };
  }

  private Action1<Throwable> onError() {
    return error -> Toast.makeText(this, "an error occurred", Toast.LENGTH_SHORT);
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

  private String getDeviceId() {
    final String PREFS_DEVICE_ID = "prefs_device_id";
    final String PREF_DEVICE_ID = "device_id";
    SharedPreferences sp = getSharedPreferences(PREFS_DEVICE_ID, Context.MODE_PRIVATE);
    String deviceId = sp.getString(PREF_DEVICE_ID, null);
    if (deviceId == null) {
      deviceId = UUID.randomUUID().toString();
      sp.edit().putString(PREF_DEVICE_ID, deviceId).apply();
    }
    return deviceId;
  }
}
