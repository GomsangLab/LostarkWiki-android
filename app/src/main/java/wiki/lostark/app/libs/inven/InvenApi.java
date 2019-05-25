package wiki.lostark.app.libs.inven;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InvenApi {
    @GET("lostark/timer")
    Call<String> getTimer();
}