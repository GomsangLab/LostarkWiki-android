package wiki.lostark.app.ui.activities;

import android.content.Intent;
import android.graphics.Color;
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
import eu.amirs.JSON;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ActivityItemDetailBinding;
import wiki.lostark.app.libs.AdvertisementManager;
import wiki.lostark.app.libs.ItemDictionaryRequest;

public class ItemDetailActivity extends AppCompatActivity {

    ActivityItemDetailBinding binding;

    private Retrofit retrofit;
    private final String BASE_URL = "http://lostark.game.onstove.com:8888";
    private final String CDN_SERVER_URL = "https://cdn-lostark.game.onstove.com/";
    private String iconPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_detail);
        AdvertisementManager.getInstance().showAdFront(0.5f);
        init();

        ItemDictionaryRequest service = retrofit.create(ItemDictionaryRequest.class);
        Call<ResponseBody> itemDataCall = service.key(getIntent().getLongExtra("key", 0));
        itemDataCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //Log.v("RESULT!", response.body());
                    try {
                        //TODO 각 단계에서도 Element가 갯수에 따라 데이터가 다름.
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSON type = new JSON(jsonObject.getString("ItemInfo"));
                        JSONObject itemName = new JSONObject(type.key("BasicInfo").stringValue());

                        Log.v("CATEGORY", String.valueOf(type.key("BasicInfo").count()));

                        switch (type.key("BasicInfo").key("minQualityIndex").stringValue()) {
                            case "4":
                                iconPath = CDN_SERVER_URL + type.key("BasicInfo").key("Tooltip_Item_006").key("Element_001").key("value").key("slotData").key("iconPath").stringValue();
                                binding.textView4.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_006").key("Element_001").key("value").key("leftStr0").stringValue()
                                        + "<br>" + type.key("BasicInfo").key("Tooltip_Item_006").key("Element_001").key("value").key("leftStr1").key("title").stringValue()));
                                binding.textView5.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_006").key("Element_003").key("value").stringValue()));
                                binding.textView6.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_006").key("Element_006").key("value").key("Element_000").stringValue()
                                        + "<br>" + Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_006").key("Element_006").key("value").key("Element_001").stringValue())));
                                binding.textView7.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_006").key("Element_007").key("value").stringValue()));
                                break;
                            case "2":
                                iconPath = CDN_SERVER_URL + type.key("BasicInfo").key("Tooltip_Item_004").key("Element_001").key("value").key("slotData").key("iconPath").stringValue();
                                binding.textView4.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_004").key("Element_001").key("value").key("leftStr0").stringValue()
                                        + "<br>" + type.key("BasicInfo").key("Tooltip_Item_004").key("Element_001").key("value").key("leftStr1").key("title").stringValue()));
                                binding.textView5.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_004").key("Element_003").key("value").stringValue()));
                                binding.textView6.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_004").key("Element_006").key("value").key("Element_000").stringValue()
                                        + "<br>" + Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_004").key("Element_006").key("value").key("Element_001").stringValue())));
                                binding.textView7.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_004").key("Element_007").key("value").stringValue()));
                                break;
                            case "-1":
                                iconPath = CDN_SERVER_URL + type.key("BasicInfo").key("Tooltip_Item_001").key("Element_001").key("value").key("slotData").key("iconPath").stringValue();
                                binding.textView4.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_001").key("Element_001").key("value").key("leftStr0").stringValue()));
                                binding.textView5.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_001").key("Element_003").key("value").stringValue()));
                                binding.textView6.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_001").key("Element_006").key("value").key("Element_000").stringValue()));
                                binding.textView7.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_001").key("Element_006").key("value").key("Element_001").stringValue()));
                                break;
                            default:  //연마 단계 없는 것.
                                if (type.key("BasicInfo").key("categoryType").stringValue().equals("400")) {
                                    iconPath = CDN_SERVER_URL + type.key("BasicInfo").key("Tooltip_Item_000").key("Element_001").key("value").key("slotData").key("iconPath").stringValue();
                                    binding.textView4.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_000").key("Element_001").key("value").key("leftStr0").stringValue()));
                                    binding.textView5.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_000").key("Element_002").key("value").stringValue()));
                                    binding.textView6.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_000").key("Element_003").key("value").key("Element_000").stringValue()
                                            + "<br>" + type.key("BasicInfo").key("Tooltip_Item_000").key("Element_003").key("value").key("Element_001").stringValue()
                                            + "<br>" + type.key("BasicInfo").key("Tooltip_Item_000").key("Element_004").key("value").key("Element_000").stringValue()
                                            + "<br>" + type.key("BasicInfo").key("Tooltip_Item_000").key("Element_004").key("value").key("Element_001").stringValue()));
                                    binding.textView7.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_000").key("Element_005").key("value").key("Element_000").stringValue()
                                            + "<br>" + type.key("BasicInfo").key("Tooltip_Item_000").key("Element_005").key("value").key("Element_001").stringValue()
                                            + "<br>" + type.key("BasicInfo").key("Tooltip_Item_000").key("Element_006").key("value").stringValue()));
                                } else {
                                    iconPath = CDN_SERVER_URL + type.key("BasicInfo").key("Tooltip_Item_000").key("Element_001").key("value").key("slotData").key("iconPath").stringValue();
                                    binding.textView4.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_000").key("Element_001").key("value").key("leftStr0").stringValue()));
                                    binding.textView5.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_000").key("Element_002").key("value").stringValue()));
                                    binding.textView6.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_000").key("Element_003").key("value").stringValue()));
                                    binding.textView7.setText(Html.fromHtml(type.key("BasicInfo").key("Tooltip_Item_000").key("Element_004").key("value").stringValue()));
                                }
                                break;
                        }

                        binding.titleTextView.setText(itemName.getString("itemName"));
                        Glide.with(getApplicationContext()).load(iconPath).into(binding.imageView2);
                        Log.v("ICON PATH", iconPath);

                    } catch (IOException | JSONException e) {
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
