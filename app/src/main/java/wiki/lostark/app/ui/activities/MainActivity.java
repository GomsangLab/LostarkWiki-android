package wiki.lostark.app.ui.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ActivityMainBinding;
import wiki.lostark.app.datas.event.EventData;
import wiki.lostark.app.libs.eventalarm.AlarmBroadcastReceiver;
import wiki.lostark.app.libs.eventalarm.EventAlarm;
import wiki.lostark.app.ui.adapters.HistoriesAdapter;
import wiki.lostark.app.utils.BlurBuilder;

/**
 * 메인 액티비티
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HistoriesAdapter historiesAdapter = new HistoriesAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        binding.btnSeedmap.setOnClickListener(view -> startActivity(new Intent(this, MapActivity.class)));

        binding.charNickInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
                return true;
            }
            return false;
        });

        binding.btnDictionary.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, DictionaryActivity.class)));

        binding.btnEvent.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, EventActivity.class));
        });

        binding.searchHistoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.searchHistoryRecycler.setAdapter(historiesAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Bitmap resultBmp = BlurBuilder.blur(this, BitmapFactory.decodeResource(getResources(), R.drawable.map_world));
            binding.img.setImageBitmap(resultBmp); //백그라운드 지도 블러처리
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        historiesAdapter.resync();
    }

    public void search() {
        if (binding.charNickInput.getText().toString().length() < 2) {
            Toast.makeText(this, "검색하시는 캐릭터명은 최소 두 글자 이상이여야 합니다", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent searchIntent = new Intent(this, CharacterProfileActivity.class);
        searchIntent.putExtra("nickname", binding.charNickInput.getText().toString());
        startActivity(searchIntent);

        binding.charNickInput.setText("");
        binding.charNickInput.clearFocus();
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(binding.charNickInput.getWindowToken(), 0);
    }
}


