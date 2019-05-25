package wiki.lostark.app.ui.adapters;

import android.content.Context;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.objectweb.asm.Label;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.paperdb.Paper;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ItemEventBinding;
import wiki.lostark.app.datas.event.EventData;
import wiki.lostark.app.libs.EventLabel;
import wiki.lostark.app.ui.dialogs.MakeAlarmDialog;
import wiki.lostark.app.utils.ShareUtils;
import wiki.lostark.app.utils.TimeUtils;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.EventViewHolder> {

    private ArrayList<EventData> copiedEventDatas = new ArrayList<>();

    private ArrayList<EventData> labelEvents = new ArrayList<>();
    private ArrayList<EventData> regularEvents = new ArrayList<>();

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
        holder.bind(getShownEvents().get(position), position);
    }

    public ArrayList<EventData> getShownEvents() {
        final ArrayList shownEvents = new ArrayList();
        shownEvents.addAll(labelEvents);
        shownEvents.addAll(regularEvents);
        return shownEvents;
    }

    @Override
    public int getItemCount() {
        return getShownEvents().size();
    }

    public void applyEvents(ArrayList<EventData> eventDatas) {
        this.copiedEventDatas.addAll(eventDatas);

        ArrayList<String> labeledEvents = EventLabel.getActiveLabels();
        ArrayList<String> activeLabels = new ArrayList<>();

        for (EventData eventData : eventDatas) {
            final String event = eventData.getName() + eventData.getTime();
            if (labeledEvents.contains(event)) {
                this.labelEvents.add(0, eventData);
                activeLabels.add(event);
            } else {
                this.regularEvents.add(eventData);
            }
        }

        EventLabel.setActiveLabels(activeLabels);
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        private ItemEventBinding binding;

        public EventViewHolder(@NonNull ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(EventData eventData, int position) {
            binding.eventName.setText(eventData.getName());
            binding.eventDate.setText(eventData.getTime());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            try {
                long endBidTime = sdf.parse(eventData.getTime()).getTime();
                long currentTime = System.currentTimeMillis();
                String remaining = TimeUtils.printDifference(new Date(currentTime), new Date(endBidTime));
                binding.remainingTime.setText(remaining);

                binding.shareButton.setOnClickListener(v -> {
                    ShareUtils.shareText(context, String.format("[%s]\n%s\n%s 남았어요!\n\n-로스트아크 위키-",
                            eventData.getName(), eventData.getTime(), remaining));
                });
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (EventLabel.isLabeled(eventData)) {
                binding.labelButton.setImageResource(R.drawable.ic_label_white_24dp);
            } else {
                binding.labelButton.setImageResource(R.drawable.ic_label_outline_white_24dp);
            }

            binding.addAlarmButton.setOnClickListener(v -> {
                MakeAlarmDialog makeAlarmDialog = new MakeAlarmDialog(context, eventData);
                makeAlarmDialog.show();
            });

            binding.labelButton.setOnClickListener(v -> {
                if (EventLabel.isLabeled(eventData)) {
                    labelEvents.remove(eventData);

                    ArrayList<EventData> e = new ArrayList<>();
                    e.addAll(copiedEventDatas);
                    e.removeAll(labelEvents);
                    regularEvents = e;

                    EventLabel.deactiveLabel(eventData);

                    if(getShownEvents().indexOf(eventData) <= 0){
                        notifyItemChanged(0);
                    }else{
                        notifyItemRangeChanged(0, getShownEvents().indexOf(eventData));
                    }
                } else {
                    regularEvents.remove(eventData);
                    labelEvents.add(0, eventData);
                    EventLabel.activeLabel(eventData);

                    if (position <= 0) {
                        notifyItemChanged(0);
                    } else {
                        notifyItemMoved(position, 0);
                    }
                }
            });
        }
    }
}
