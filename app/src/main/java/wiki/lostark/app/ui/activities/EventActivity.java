package wiki.lostark.app.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ActivityEventBinding;
import wiki.lostark.app.datas.event.EventData;
import wiki.lostark.app.ui.adapters.EventRecyclerAdapter;

import android.os.Bundle;
import android.os.CountDownTimer;

public class EventActivity extends AppCompatActivity {

    private ActivityEventBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event);

        binding.timeRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(this);
        eventRecyclerAdapter.add(new EventData("태스트 섬 (250)", "2018-12-20 14:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-22 11:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (350)", "2018-12-23 10:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (550)", "2018-12-24 9:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (250)", "2018-12-24 10:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-26 11:20"));
        eventRecyclerAdapter.add(new EventData("태스트 섬 (250)", "2018-12-20 14:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-22 11:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (350)", "2018-12-23 10:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (550)", "2018-12-24 9:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (250)", "2018-12-24 10:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-26 11:20"));
        eventRecyclerAdapter.add(new EventData("태스트 섬 (250)", "2018-12-20 14:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-22 11:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (350)", "2018-12-23 10:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (550)", "2018-12-24 9:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (250)", "2018-12-24 10:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-26 11:20"));
        eventRecyclerAdapter.add(new EventData("태스트 섬 (250)", "2018-12-20 14:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-22 11:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (350)", "2018-12-23 10:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (550)", "2018-12-24 9:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (250)", "2018-12-24 10:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-26 11:20"));
        eventRecyclerAdapter.add(new EventData("태스트 섬 (250)", "2018-12-20 14:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-22 11:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (350)", "2018-12-23 10:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (550)", "2018-12-24 9:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (250)", "2018-12-24 10:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-26 11:20"));
        eventRecyclerAdapter.add(new EventData("태스트 섬 (250)", "2018-12-20 14:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-22 11:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (350)", "2018-12-23 10:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (550)", "2018-12-24 9:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (250)", "2018-12-24 10:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-26 11:20"));
        eventRecyclerAdapter.add(new EventData("태스트 섬 (250)", "2018-12-20 14:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-22 11:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (350)", "2018-12-23 10:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (550)", "2018-12-24 9:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (250)", "2018-12-24 10:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-26 11:20"));
        eventRecyclerAdapter.add(new EventData("태스트 섬 (250)", "2018-12-20 14:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-22 11:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (350)", "2018-12-23 10:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (550)", "2018-12-24 9:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (250)", "2018-12-24 10:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-26 11:20"));
        eventRecyclerAdapter.add(new EventData("태스트 섬 (250)", "2018-12-20 14:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-22 11:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (350)", "2018-12-23 10:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (550)", "2018-12-24 9:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (250)", "2018-12-24 10:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-26 11:20"));
        eventRecyclerAdapter.add(new EventData("태스트 섬 (250)", "2018-12-20 14:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-22 11:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (350)", "2018-12-23 10:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (550)", "2018-12-24 9:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (250)", "2018-12-24 10:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-26 11:20"));
        eventRecyclerAdapter.add(new EventData("태스트 섬 (250)", "2018-12-20 14:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-22 11:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (350)", "2018-12-23 10:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (550)", "2018-12-24 9:20"));
        eventRecyclerAdapter.add(new EventData("테스트 섬 (250)", "2018-12-24 10:20"));
        eventRecyclerAdapter.add(new EventData("신월의 섬 (250)", "2018-12-26 11:20"));
        binding.timeRecycler.setAdapter(eventRecyclerAdapter);

        new CountDownTimer(Long.MAX_VALUE, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                eventRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
}
