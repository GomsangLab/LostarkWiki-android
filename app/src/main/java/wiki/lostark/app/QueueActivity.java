package wiki.lostark.app;

import android.os.Bundle;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import wiki.lostark.app.databinding.ActivityQueueBinding;

public class QueueActivity extends AppCompatActivity {

    ActivityQueueBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_queue);

        ArrayList<QueueViewModel> items = new ArrayList<>();
        items.add(new QueueViewModel("", "", ""));
        QueueAdapter adapter = new QueueAdapter(this, items);
        binding.listView.setAdapter(adapter);
    }
}