package wiki.lostark.app.ui.activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import wiki.lostark.app.utils.BlurBuilder;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ActivityMococoBinding;
import wiki.lostark.app.datas.mococo.MococoContinent;
import wiki.lostark.app.datas.mococo.MococoRegion;
import wiki.lostark.app.libs.MococoRequest;

public class MococoActivity extends AppCompatActivity {

    private ActivityMococoBinding binding;
    private final HashMap<String, MococoContinent> continentHashMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mococo);
        binding.setActivity(this);
        binding.regionSpinner.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        binding.continentSpinner.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE); //네트워크 연결 확인
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                new MococoRequest(this::constructSpinners).execute();
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                new MococoRequest(this::constructSpinners).execute();
            }
        } else {
            finish();
            Toast.makeText(getApplicationContext(), "네트워크 연결이 필요합니다.", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Bitmap resultBmp = BlurBuilder.blur(this, BitmapFactory.decodeResource(getResources(), R.drawable.map_world));
            binding.img.setImageBitmap(resultBmp); //백그라운드 지도 블러처리
        }

        binding.btnClose.setOnClickListener(view -> finish());
    }

    // construct continent spinner when Mococo datas fetched from api server.
    private void constructSpinners(List<MococoContinent> result) {
        final ArrayList<String> continentNames = new ArrayList<>();

        // on selected nothing, spinner show this message
        continentNames.add("대륙 선택");

        // add all continent names to spinner.
        for (MococoContinent category : result) {
            continentNames.add(category.getCategoryName());
            continentHashMap.put(category.getCategoryName(), category);
        }

        workSpinner(binding.continentSpinner, continentNames.toArray(new String[continentNames.size()]));
        binding.continentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String categoryName = (String) binding.continentSpinner.getItemAtPosition(position);
                syncRegionSpinner(categoryName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MococoActivity.this, "noting", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // when continent spinner's item selected, re-sync region spinner's item.
    private void syncRegionSpinner(String categoryName) {
        // if no region items on category, show message
        if (!continentHashMap.containsKey(categoryName)) {
            workSpinner(binding.regionSpinner, new String[]{"해당 없음"});
            binding.regionSpinner.setEnabled(false);
            return;
        }

        final HashMap<String, MococoRegion> regionHashMap = new HashMap<>();
        final ArrayList<String> regionNames = new ArrayList<>();
        // on selected nothing, spinner show this message
        regionNames.add("지역 선택");

        for (MococoRegion mococoRegion : continentHashMap.get(categoryName).getDatas()) {
            regionNames.add(mococoRegion.getName());
            regionHashMap.put(mococoRegion.getName(), mococoRegion);
        }

        workSpinner(binding.regionSpinner, regionNames.toArray(new String[regionNames.size()]));
        binding.regionSpinner.setEnabled(true);
        binding.regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String regionName = (String) binding.regionSpinner.getItemAtPosition(position);
                if (!regionHashMap.containsKey(regionName)) {
                    return;
                }
                ProgressDialog imageLoadDialog = ProgressDialog.show(MococoActivity.this, "Image Load", " Please wait...");
                Glide.with(MococoActivity.this)
                        .asBitmap()
                        .load("http://lab-seoul-mococo.gomsang.com/images/" + regionHashMap.get(regionName).getFilename())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                binding.photoView.setImageBitmap(resource);
                                imageLoadDialog.dismiss();
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void workSpinner(Spinner spinner, String[] datas) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item_white,
                        datas); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }
}