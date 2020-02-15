package ddiehl.rxreddit;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ddiehl.rxreddit.sample.R;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import rxreddit.api.RedditService;
import rxreddit.model.UserIdentity;

public final class UserDetailActivity extends AppCompatActivity {

    private RedditService redditService;

    private TextView username;
    private TextView joinDate;
    private TextView linkKarma;
    private TextView commentKarma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        redditService = RedditServiceProvider.get(this);

        username = findViewById(R.id.username);
        joinDate = findViewById(R.id.join_date);
        linkKarma = findViewById(R.id.link_karma);
        commentKarma = findViewById(R.id.comment_karma);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (redditService.isUserAuthorized()) {
            redditService.getUserIdentity()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onUserIdentityRetrieved(), onError());
        }
    }

    private Consumer<UserIdentity> onUserIdentityRetrieved() {
        return identity -> {
            username.setText(identity.getName());
            joinDate.setText(
                    String.format(
                            getString(R.string.username_formatter),
                            SimpleDateFormat.getDateInstance()
                                    .format(new Date(identity.getCreatedUTC() * 1000))
                    ));
            linkKarma.setText(
                    String.format(
                            getString(R.string.link_karma_formatter),
                            identity.getLinkKarma()
                    ));
            commentKarma.setText(
                    String.format(
                            getString(R.string.comment_karma_formatter),
                            identity.getCommentKarma()
                    ));
        };
    }

    private Consumer<Throwable> onError() {
        return error -> {
            Snackbar.make(username, R.string.get_identity_error, Snackbar.LENGTH_SHORT).show();
            Log.e("RxReddit", "error retrieving user identity", error);
        };
    }
}
