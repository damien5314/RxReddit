package ddiehl.rxreddit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ddiehl.rxreddit.sample.BuildConfig;
import ddiehl.rxreddit.sample.R;
import rxreddit.api.RedditService;
import rxreddit.model.Link;

public class MainActivity extends AppCompatActivity {

  private RedditService mRedditService =
      new RedditService(
          BuildConfig.REDDIT_APP_ID,
          BuildConfig.REDDIT_REDIRECT_URI,
          "DEVICE_ID",
          String.format("android:ddiehl.rxreddit.sampleapp:%s", BuildConfig.VERSION_NAME),
          new AndroidAccessTokenManager(this)
      );

  private List<Link> mData = new ArrayList<>();
  private LinkAdapter mLinkAdapter = new LinkAdapter(mData);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    RecyclerView listView = (RecyclerView) findViewById(R.id.recycler_view);
    listView.setAdapter(mLinkAdapter);
  }

  @Override
  protected void onStart() {
    super.onStart();

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
              .inflate(R.layout.link_layout, parent));
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

    private TextView mTitleView;

    public LinkViewHolder(View itemView) {
      super(itemView);
      mTitleView = (TextView) itemView.findViewById(R.id.title_view);
    }

    public void bind(Link link) {
      mTitleView.setText(link.getTitle());
    }
  }
}
