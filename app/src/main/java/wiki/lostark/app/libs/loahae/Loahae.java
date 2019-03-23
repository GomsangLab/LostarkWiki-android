package wiki.lostark.app.libs.loahae;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Loahae {
    private static final Loahae ourInstance = new Loahae();
    private LoahaeApi loahaeApi;

    public static Loahae getInstance() {
        return ourInstance;
    }

    private Loahae() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://loahae.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loahaeApi = retrofit.create(LoahaeApi.class);
    }

    public static LoahaeApi getApi() {
        return getInstance().loahaeApi;
    }
}
