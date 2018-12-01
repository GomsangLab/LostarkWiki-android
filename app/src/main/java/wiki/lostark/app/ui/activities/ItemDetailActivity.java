package wiki.lostark.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ActivityItemDetailBinding;
import wiki.lostark.app.libs.ItemDictionaryRequest;

public class ItemDetailActivity extends AppCompatActivity {

    ActivityItemDetailBinding binding;

    private Retrofit retrofit;
    private final String BASE_URL = "http://lostark.game.onstove.com:8888";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_detail);
        init();

        ItemDictionaryRequest service = retrofit.create(ItemDictionaryRequest.class);
        Call<ResponseBody> itemDataCall = service.key(getIntent().getLongExtra("key", 0));
        itemDataCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //Log.v("RESULT!", response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject jsonObject1 = new JSONObject(jsonObject.getString("ItemInfo"));
                        JSONObject basicInfo = new JSONObject(jsonObject1.getString("BasicInfo"));
                        JSONObject element = new JSONObject(basicInfo.getString("Tooltip_Item_00"));

                        JSONObject element01 = new JSONObject(element.getString("Element_01"));
                        JSONObject element01Value = new JSONObject(element01.getString("value"));
                        JSONObject element01Data = new JSONObject(element01Value.getString("slotData"));

                        //JSONObject element04 = new JSONObject(element.getString("Element_04"));

                        binding.titleTextView.setText(basicInfo.getString("itemName"));
                        binding.textView4.setText(Html.fromHtml(element01Value.getString("leftStr0")));
                        //binding.textView7.setText(Html.fromHtml(element04.getString("value")));
                        Glide.with(getApplicationContext()).load("https://cdn-lostark.game.onstove.com/" + element01Data.getString("iconPath")).into(binding.imageView2);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR!", t.getMessage());
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
}
