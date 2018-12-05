package wiki.lostark.app.ui.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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
    private final String BASE_URL = "http://lostark.game.onstove.com:8888";
    private String grade[] = {"전체 등급", "고급", "희귀", "영웅", "전설", "유물"};
    private String job[] = {"전체 직업", "전사", "버서커", "디스트로이어", "워로드", "마법사", "아르카나", "서머너", "바드", "무도가", "배틀마스터", "인파이터", "기공사", "헌터", "호크아이", "데빌헌터", "블래스터"};

    private ItemDictionaryMainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_dictionary);
        init();
        loadBestItem("BEST");

        Map<String, String> map = new HashMap<>();

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
                if (charSequence.length() == 0) {
                    mAdapter.clear();
                    loadBestItem("BEST");
                    binding.textView3.setVisibility(View.VISIBLE);
                } else {
                    map.put("name", charSequence.toString());
                    loadSearchItem(map);
                    binding.textView3.setVisibility(View.GONE);
                }
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
                if (charSequence.length() > 0) {
                    map.put("itemMinLevel", charSequence.toString());
                    loadSearchItem(map);
                    binding.textView3.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.maxLevelInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                map.put("itemMaxLevel", charSequence.toString());
                loadSearchItem(map);
                binding.textView3.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.gradeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.textView3.setVisibility(View.GONE);
                switch (i) {
                    case 1:
                        map.put("grade", "1");
                        loadSearchItem(map);
                        break;
                    case 2:
                        map.put("grade", "2");
                        loadSearchItem(map);
                        break;
                    case 3:
                        map.put("grade", "3");
                        loadSearchItem(map);
                        break;
                    case 4:
                        map.put("grade", "4");
                        loadSearchItem(map);
                        break;
                    case 5:
                        map.put("grade", "5");
                        loadSearchItem(map);
                        break;
                    default:
                        binding.textView3.setVisibility(View.VISIBLE);
                        loadBestItem("BEST");
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
                binding.textView3.setVisibility(View.GONE);
                switch (i) {
                    case 1:
                        map.put("classNo", "101");
                        loadSearchItem(map);
                        break;
                    case 2:
                        map.put("classNo", "102");
                        loadSearchItem(map);
                        break;
                    case 3:
                        map.put("classNo", "103");
                        loadSearchItem(map);
                        break;
                    case 4:
                        map.put("classNo", "104");
                        loadSearchItem(map);
                        break;
                    case 5:
                        map.put("classNo", "201");
                        loadSearchItem(map);
                        break;
                    case 6:
                        map.put("classNo", "202");
                        loadSearchItem(map);
                        break;
                    case 7:
                        map.put("classNo", "203");
                        loadSearchItem(map);
                        break;
                    case 8:
                        map.put("classNo", "204");
                        loadSearchItem(map);
                        break;
                    case 9:
                        map.put("classNo", "301");
                        loadSearchItem(map);
                        break;
                    case 10:
                        map.put("classNo", "302");
                        loadSearchItem(map);
                        break;
                    case 11:
                        map.put("classNo", "303");
                        loadSearchItem(map);
                        break;
                    case 12:
                        map.put("classNo", "304");
                        loadSearchItem(map);
                        break;
                    case 13:
                        map.put("classNo", "501");
                        loadSearchItem(map);
                        break;
                    case 14:
                        map.put("classNo", "502");
                        loadSearchItem(map);
                        break;
                    case 15:
                        map.put("classNo", "503");
                        loadSearchItem(map);
                        break;
                    case 16:
                        map.put("classNo", "504");
                        loadSearchItem(map);
                        break;
                    default:
                        binding.textView3.setVisibility(View.VISIBLE);
                        loadBestItem("BEST");
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

    private void loadSearchItem(Map<String, String> map) {
        ItemDictionaryRequest service = retrofit.create(ItemDictionaryRequest.class);
        Call<BestItemData> itemDataCall = service.searchQuery(map);
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
