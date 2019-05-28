package wiki.lostark.app.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ActivityEventBinding;
import wiki.lostark.app.datas.event.EventData;
import wiki.lostark.app.libs.inven.Inven;
import wiki.lostark.app.ui.adapters.EventAlarmRecyclerAdapter;
import wiki.lostark.app.ui.adapters.EventRecyclerAdapter;
import wiki.lostark.app.utils.BlurBuilder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private ActivityEventBinding binding;
    private EventRecyclerAdapter eventRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event);

        binding.timeRecycler.setHasFixedSize(true);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.timeRecycler.setLayoutManager(linearLayoutManager);

        eventRecyclerAdapter = new EventRecyclerAdapter(this);
        binding.timeRecycler.setAdapter(eventRecyclerAdapter);
        binding.timeRecycler.setNestedScrollingEnabled(false);

        new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                eventRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFinish() {

            }
        }.start();

        Inven.getApi().getTimer().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                final String result = response.body();
                final Document wholeDoc = Jsoup.parse(result);
                final List<Element> infoElements = wholeDoc.getElementsByClass("info");

                ArrayList<EventData> eventDatas = new ArrayList<>();
                for (int ii = 0; ii < infoElements.size(); ii++) {
                    final Element info = infoElements.get(ii);
                    String name = info.getElementsByClass("npcname").get(0).text();
                    String date = info.getElementsByClass("gentime").get(0).text();

                    eventDatas.add(new EventData(name, date));
                }
                eventRecyclerAdapter.applyEvents(eventDatas);
                eventRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        binding.menuEvents.setOnClickListener(v -> switchList(true));
        binding.menuAlarms.setOnClickListener(v -> switchList(false));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Bitmap resultBmp = BlurBuilder.blur(this, BitmapFactory.decodeResource(getResources(), R.drawable.map_world));
            binding.img.setImageBitmap(resultBmp); //백그라운드 지도 블러처리
        }
    }

    public void switchList(boolean isAlarmListModeNow) {
        if (isAlarmListModeNow) {
            binding.timeRecycler.setAdapter(eventRecyclerAdapter);
            binding.menuEvents.setTextColor(Color.parseColor("#FFFFFF"));
            binding.menuAlarms.setTextColor(Color.parseColor("#73FFFFFF"));
        }else{
            EventAlarmRecyclerAdapter eventAlarmRecyclerAdapter = new EventAlarmRecyclerAdapter(this);
            binding.timeRecycler.setAdapter(eventAlarmRecyclerAdapter);

            binding.menuEvents.setTextColor(Color.parseColor("#73FFFFFF"));
            binding.menuAlarms.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }
}
