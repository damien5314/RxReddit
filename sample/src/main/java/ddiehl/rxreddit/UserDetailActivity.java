package ddiehl.rxreddit;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ddiehl.rxreddit.sample.R;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rxreddit.api.RedditService;
import rxreddit.model.UserIdentity;

public class UserDetailActivity extends AppCompatActivity {

    private RedditService mRedditService;

    private TextView mUsername;
    private TextView mJoinDate;
    private TextView mLinkKarma;
    private TextView mCommentKarma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        mRedditService = RedditServiceProvider.get(this);

        mUsername = (TextView) findViewById(R.id.username);
        mJoinDate = (TextView) findViewById(R.id.join_date);
        mLinkKarma = (TextView) findViewById(R.id.link_karma);
        mCommentKarma = (TextView) findViewById(R.id.comment_karma);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mRedditService.isUserAuthorized()) {
            mRedditService.getUserIdentity()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onUserIdentityRetrieved(), onError());
        }
    }

    private Action1<UserIdentity> onUserIdentityRetrieved() {
        return identity -> {
            mUsername.setText(identity.getName());
            mJoinDate.setText(
                    String.format(
                            getString(R.string.username_formatter),
                            SimpleDateFormat.getDateInstance()
                                    .format(new Date(identity.getCreatedUTC() * 1000))));
            mLinkKarma.setText(
                    String.format(
                            getString(R.string.link_karma_formatter),
                            identity.getLinkKarma()));
            mCommentKarma.setText(
                    String.format(
                            getString(R.string.comment_karma_formatter),
                            identity.getCommentKarma()));
        };
    }

    private Action1<Throwable> onError() {
        return error -> {
            Snackbar.make(mUsername, R.string.get_identity_error, Snackbar.LENGTH_SHORT).show();
            Log.e("RxReddit", "error retrieving user identity", error);
        };
    }
}
