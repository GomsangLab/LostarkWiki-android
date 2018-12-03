package wiki.lostark.app.ui.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wiki.lostark.app.ui.adapters.ItemDictionaryMainAdapter;
import wiki.lostark.app.R;
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

        binding.itemNameInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    mAdapter.clear();
                    loadBestItem("BEST");
                }
                loadSearchItem(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
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

    private void loadSearchItem(String itemName) {
        ItemDictionaryRequest service = retrofit.create(ItemDictionaryRequest.class);
        Call<BestItemData> itemDataCall = service.search(itemName);
        itemDataCall.enqueue(new Callback<BestItemData>() {
            @Override
            public void onResponse(Call<BestItemData> call, Response<BestItemData> response) {
                if (response.isSuccessful()) {
                    BestItemData bestItemData = response.body();

                    mAdapter.clear();
                    List<Datum> datumList = bestItemData.getData();
                    mAdapter = new ItemDictionaryMainAdapter(getApplicationContext(), datumList);
                    binding.recyclerView.setAdapter(mAdapter);
                } else {
                    Toast.makeText(DictionaryActivity.this, "에러 발생!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BestItemData> call, Throwable t) {
                Toast.makeText(DictionaryActivity.this, "에러발생!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
