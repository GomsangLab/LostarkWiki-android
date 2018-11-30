package wiki.lostark.app;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wiki.lostark.app.databinding.ActivityItemDictionaryBinding;
import wiki.lostark.app.datas.dictionary.BestItemData;
import wiki.lostark.app.datas.dictionary.Datum;
import wiki.lostark.app.libs.ItemDictionaryRequest;

public class DictionaryActivity extends AppCompatActivity {

    ActivityItemDictionaryBinding binding;

    private Retrofit retrofit;
    private final String BASE_URL = "http://lostark.game.onstove.com:8888";

    private ItemDictionaryMainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_dictionary);
        init();
        loadBestItem("BEST");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

    }

    public void init() {
        // GSON 컨버터를 사용하는 REST 어댑터 생성
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void loadBestItem(String best) {
        ItemDictionaryRequest service = retrofit.create(ItemDictionaryRequest.class);
        Call<BestItemData> itemDataCall = service.params(best);
        itemDataCall.enqueue(new Callback<BestItemData>() {
            @Override
            public void onResponse(Call<BestItemData> call, Response<BestItemData> response) {
                if (response.isSuccessful()) {
                    BestItemData bestItemData = response.body();

                    assert bestItemData != null;
                    List<Datum> datumList = bestItemData.getData();
                    mAdapter = new ItemDictionaryMainAdapter(getApplicationContext(), datumList);
                    binding.recyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<BestItemData> call, Throwable t) {

            }
        });
    }
}
