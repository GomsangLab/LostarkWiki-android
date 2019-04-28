package wiki.lostark.app.ui.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final String BASE_URL = "https://lostark.game.onstove.com:8888";
    private String grade[] = {"전체 등급", "고급", "희귀", "영웅", "전설", "유물"};
    private String job[] = {"전체 직업", "전사", "버서커", "디스트로이어", "워로드", "마법사", "아르카나", "서머너", "바드", "무도가", "배틀마스터", "인파이터", "기공사", "헌터", "호크아이", "데빌헌터", "블래스터"};

    private String gradeRequest = "0";
    private String jobRequeset = "0";
    private String searchString = "";

    private String minItemLevel = "1";
    private String maxItemLevel = "1000";

    private ItemDictionaryMainAdapter mAdapter;

    private Map<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_dictionary);

        // load admob banner
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        init();
        loadSearchItem();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        gradeSpinner(binding.gradeSpinner, grade);
        jobSpinner(binding.jobSpinner, job);
        binding.gradeSpinner.setEnabled(true);
        binding.jobSpinner.setEnabled(true);

        binding.itemNameInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchString = charSequence.toString();
                loadSearchItem();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.maxLevelInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                maxItemLevel = charSequence.toString();
                loadSearchItem();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.minLevelInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                minItemLevel = charSequence.toString();
                loadSearchItem();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        gradeRequest = "0";
                        loadSearchItem();
                        break;
                    case 1:
                        gradeRequest = "1";
                        loadSearchItem();
                        break;
                    case 2:
                        gradeRequest = "2";
                        loadSearchItem();
                        break;
                    case 3:
                        gradeRequest = "3";
                        loadSearchItem();
                        break;
                    case 4:
                        gradeRequest = "4";
                        loadSearchItem();
                        break;
                    case 5:
                        gradeRequest = "5";
                        loadSearchItem();
                        break;
                    default:
                        gradeRequest = "0";
                        loadSearchItem();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.jobSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        jobRequeset = "0";
                        loadSearchItem();
                        break;
                    case 1:
                        jobRequeset = "101";
                        loadSearchItem();
                        break;
                    case 2:
                        jobRequeset = "102";
                        loadSearchItem();
                        break;
                    case 3:
                        jobRequeset = "103";
                        loadSearchItem();
                        break;
                    case 4:
                        jobRequeset = "104";
                        loadSearchItem();
                        break;
                    case 5:
                        jobRequeset = "201";
                        loadSearchItem();
                        break;
                    case 6:
                        jobRequeset = "202";
                        loadSearchItem();
                        break;
                    case 7:
                        jobRequeset = "203";
                        loadSearchItem();
                        break;
                    case 8:
                        jobRequeset = "204";
                        loadSearchItem();
                        break;
                    case 9:
                        jobRequeset = "301";
                        loadSearchItem();
                        break;
                    case 10:
                        jobRequeset = "302";
                        loadSearchItem();
                        break;
                    case 11:
                        jobRequeset = "303";
                        loadSearchItem();
                        break;
                    case 12:
                        jobRequeset = "304";
                        loadSearchItem();
                        break;
                    case 13:
                        jobRequeset = "501";
                        loadSearchItem();
                        break;
                    case 14:
                        jobRequeset = "502";
                        loadSearchItem();
                        break;
                    case 15:
                        jobRequeset = "503";
                        loadSearchItem();
                        break;
                    case 16:
                        jobRequeset = "504";
                        loadSearchItem();
                        break;
                    default:
                        jobRequeset = "0";
                        loadSearchItem();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    public void gradeSpinner(Spinner spinner, String[] datas) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item_white,
                        datas); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    public void jobSpinner(Spinner spinner, String[] datas) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item_white,
                        datas); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    public void loadBestItem(String best) {
        binding.textView3.setVisibility(View.VISIBLE);
        ItemDictionaryRequest service = retrofit.create(ItemDictionaryRequest.class);
        Call<BestItemData> itemDataCall = service.params(best);
        itemDataCall.enqueue(new Callback<BestItemData>() {
            @Override
            public void onResponse(Call<BestItemData> call, Response<BestItemData> response) {
                if (response.isSuccessful()) {
                    BestItemData bestItemData = response.body();

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

    private void loadSearchItem() {
        if (searchString.equals("") && gradeRequest.equals("0") && jobRequeset.equals("0") && minItemLevel.equals("1") && maxItemLevel.equals("1000")) {
            loadBestItem("Best");
        } else {
            binding.textView3.setVisibility(View.GONE);
            map.put("name", searchString);
            map.put("grade", gradeRequest);
            map.put("classNo", jobRequeset);
            map.put("itemMinLevel", minItemLevel);
            map.put("itemMaxLevel", maxItemLevel);

            ItemDictionaryRequest service = retrofit.create(ItemDictionaryRequest.class);
            Call<BestItemData> itemDataCall = service.searchQuery(map);
            itemDataCall.enqueue(new Callback<BestItemData>() {
                @Override
                public void onResponse(Call<BestItemData> call, Response<BestItemData> response) {
                    if (response.isSuccessful()) {
                        BestItemData bestItemData = response.body();

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
}
