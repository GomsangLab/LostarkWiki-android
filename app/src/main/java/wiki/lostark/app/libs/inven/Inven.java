package wiki.lostark.app.libs.inven;

import org.jsoup.Jsoup;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Inven {
    public static final String URL_MOBILE_INVEN = "http://m.inven.co.kr/";

    private static final Inven ourInstance = new Inven();
    private InvenApi invenApi;

    public static Inven getInstance() {
        return ourInstance;
    }

    private Inven() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_MOBILE_INVEN)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        invenApi = retrofit.create(InvenApi.class);
    }

    public static InvenApi getApi() {
        return getInstance().invenApi;
    }
}
