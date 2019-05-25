package wiki.lostark.app;

import android.app.Application;

import io.paperdb.Paper;
import wiki.lostark.app.libs.ad.AdvertisementManager;

public class LostarkWikiApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
        AdvertisementManager.getInstance().init(this);
    }
}
