package wiki.lostark.app.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ActivityMainBinding;
import wiki.lostark.app.libs.CharacterProfileRequest;
import wiki.lostark.app.ui.adapters.HistoriesAdapter;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HistoriesAdapter historiesAdapter = new HistoriesAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        binding.btnSeedmap.setOnClickListener(view -> {
            startActivity(new Intent(this, MococoActivity.class));
        });

        binding.charNickInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search();
                return true;
            }
            return false;
        });

        binding.btnSkill.setOnClickListener(v -> Toast.makeText(MainActivity.this, "아직 준비중인 기능이에용", Toast.LENGTH_SHORT).show());
        binding.btnQueue.setOnClickListener(v -> Toast.makeText(MainActivity.this, "아직 준비중인 기능이에용", Toast.LENGTH_SHORT).show());

        binding.searchHistoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.searchHistoryRecycler.setAdapter(historiesAdapter);
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


