package wiki.lostark.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import io.paperdb.Paper;
import wiki.lostark.app.libs.ad.AdvertisementManager;

public class LostarkWikiApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
        AdvertisementManager.getInstance().init(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "lostarkwikievents"; // 채널 아이디
            CharSequence channelName = "로스트아크 위키 이벤트"; //채널 이름
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}