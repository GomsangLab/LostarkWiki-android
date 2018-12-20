package wiki.lostark.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import wiki.lostark.app.databinding.ItemEventBinding;
import wiki.lostark.app.datas.event.EventData;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.EventViewHolder> {

    private ArrayList<EventData> eventDatas = new ArrayList<>();
    private Context context;

    public EventRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventViewHolder(ItemEventBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bind(eventDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return eventDatas.size();
    }

    public void add(EventData eventData) {
        eventDatas.add(eventData);
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        private ItemEventBinding binding;

        public EventViewHolder(@NonNull ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(EventData eventData) {
            binding.eventName.setText(eventData.getName());
            binding.eventDate.setText(eventData.getTime());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            try {
                long endBidTime = sdf.parse(eventData.getTime()).getTime();
                long currentTime = System.currentTimeMillis();
                long remaining = endBidTime - currentTime;

                binding.remainingTime.setText(printDifference(new Date(currentTime), new Date(endBidTime)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public String printDifference(Date startDate, Date endDate) {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        final StringBuffer printBuffer = new StringBuffer();
        if (elapsedDays != 0) printBuffer.append(elapsedDays).append("일").append(", ");
        if (elapsedHours != 0) printBuffer.append(elapsedHours).append("시간").append(", ");
        if (elapsedMinutes != 0) printBuffer.append(elapsedMinutes).append("분").append(", ");
        printBuffer.append(elapsedSeconds).append("초");

        return printBuffer.toString();

    }
}
