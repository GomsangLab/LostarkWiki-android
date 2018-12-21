package wiki.lostark.app.libs;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AdvertisementManager {
    private static final AdvertisementManager ourInstance = new AdvertisementManager();

    public static AdvertisementManager getInstance() {
        return ourInstance;
    }

    private static final String ID_ADMOB = "ca-app-pub-3166847582867636~7520870409";
    private static final String ID_AD_FRONT_LOSTARKWIKI = "ca-app-pub-3166847582867636/7233095799";

    private InterstitialAd adFrontLostarkwiki;

    private Context context;

    private int repeatLimit = 0;

    private AdvertisementManager() {
    }

    public void init(Context context) {
        this.context = context;

        MobileAds.initialize(context, ID_ADMOB);

        adFrontLostarkwiki = new InterstitialAd(context);
        adFrontLostarkwiki.setAdUnitId(ID_AD_FRONT_LOSTARKWIKI);
        adFrontLostarkwiki.loadAd(new AdRequest.Builder().build());
        adFrontLostarkwiki.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                adFrontLostarkwiki.loadAd(new AdRequest.Builder().build());
            }
        });
    }


    public void showAdFront(float probability) {
        // prevent hard repeat advertisement.
        repeatLimit--;
        if (repeatLimit >= 0) return;

        // if interstitial ad has loaded, show ad (probability apply) and init repeat limit
        if (adFrontLostarkwiki.isLoaded() && Math.random() <= probability) {
            adFrontLostarkwiki.show();
            repeatLimit = 2;
        } else {
            Log.d("ADVERTISEMENTMANAGERLOG", "The interstitial wasn't loaded yet.");
        }
    }

}
