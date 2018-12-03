package wiki.lostark.app.libs;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import wiki.lostark.app.datas.dictionary.BestItemData;
import wiki.lostark.app.datas.dictionary.ItemInfoData;

public interface ItemDictionaryRequest {
    // GET/POST/DELETE/PUT 메소드들을 인터페이스에 구현하여 사용할 수 있다.
    @GET("/ItemDictionary/{parameter}")
    // JSON Array를 리턴하므로 List<>가 되었다
    Call<BestItemData> params(
            @Path("parameter") String parameter);

    @GET("/ItemDictionary/Select/{key}")
    Call<ResponseBody> key(@Path("key") Long key);

    @GET("/ItemDictionary/Search")
    Call<BestItemData> search(@Query("name") String name);
}