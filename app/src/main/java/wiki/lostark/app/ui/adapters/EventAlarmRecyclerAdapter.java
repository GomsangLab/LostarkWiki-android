package wiki.lostark.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import wiki.lostark.app.databinding.ItemAlarmBinding;
import wiki.lostark.app.datas.alarm.EventAlarmData;
import wiki.lostark.app.libs.eventalarm.EventAlarm;
import wiki.lostark.app.utils.TimeUtils;

public class EventAlarmRecyclerAdapter extends RecyclerView.Adapter<EventAlarmRecyclerAdapter.AlarmViewHolder> {

    private ArrayList<EventAlarmData> eventAlarms = new ArrayList<>();
    private Context context;

    public EventAlarmRecyclerAdapter(Context context) {
        this.eventAlarms = EventAlarm.getEventAlarms();
        this.context = context;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlarmViewHolder(ItemAlarmBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.bind(eventAlarms.get(position));
    }

    @Override
    public int getItemCount() {
        return eventAlarms.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder {

        private ItemAlarmBinding binding;

        public AlarmViewHolder(@NonNull ItemAlarmBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(EventAlarmData eventAlarmData) {
            binding.eventName.setText(eventAlarmData.getEventData().getName());

            if (eventAlarmData.getAlarmTimeUntilEvent() == 0) {
                binding.alarmDate.setText("이벤트 정시에 알람");
            } else {
                binding.alarmDate.setText(String.format("이벤트 %s 전 알람", TimeUtils.printDifference(
                        new Date(0), new Date(eventAlarmData.getAlarmTimeUntilEvent()))));
            }

            if (eventAlarmData.isAlarmed()) {
                itemView.setAlpha(.5f);
                binding.alarmDate.setText("이미 울린 알림");
            } else {
                itemView.setAlpha(1f);
            }

            binding.deleteButton.setOnClickListener(v -> {
                int position = eventAlarms.indexOf(eventAlarmData);
                eventAlarms.remove(eventAlarmData);
                EventAlarm.setEventAlarms(eventAlarms);
                notifyItemRemoved(position);
            });
        }
    }
}
