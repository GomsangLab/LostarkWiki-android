package wiki.lostark.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import wiki.lostark.app.databinding.ActivityMainBinding;
import wiki.lostark.app.libs.CharacterProfileRequest;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        binding.btnSeedmap.setOnClickListener(view -> {
            startActivity(new Intent(this, MococoActivity.class));
        });

        new CharacterProfileRequest("가나다", characterProfile -> {

        }).execute();
    }
}


