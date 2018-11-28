package wiki.lostark.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import wiki.lostark.app.databinding.ActivityMainBinding;
import wiki.lostark.app.datas.MococoCategory;
import wiki.lostark.app.libs.MococoRequest;

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

        new MococoRequest(result -> {
            Toast.makeText(this, result.size()+"", Toast.LENGTH_SHORT).show();
        }).execute();
    }
}