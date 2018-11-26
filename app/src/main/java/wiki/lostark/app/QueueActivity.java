package wiki.lostark.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import wiki.lostark.app.databinding.ActivityQueueBinding;

public class QueueActivity extends AppCompatActivity {

    ActivityQueueBinding binding;
    String original;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_queue);

        process();
    }

    private void process() {
        new Thread() {
            @Override
            public void run() {
                Handler Progress = new Handler(Looper.getMainLooper());
                Progress.postDelayed(() -> {
                }, 0);

                String Link = "https://rubystarashe.github.io/lostark";
                try {
                    Document doc = Jsoup.connect(Link).get();
                    original = doc.getElementsByAttributeValue("class", "__nuxt").eq(0).text();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.postDelayed(() -> {
                    Toast.makeText(getApplicationContext(), original, Toast.LENGTH_SHORT).show();
                }, 0);
            }
        }.start();
    }
}