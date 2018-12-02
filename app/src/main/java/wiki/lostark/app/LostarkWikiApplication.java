package wiki.lostark.app;

import android.app.Application;

import io.paperdb.Paper;

public class LostarkWikiApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
    }
}
