package wiki.lostark.app.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.paperdb.Paper;
import wiki.lostark.app.databinding.ItemHistoryBinding;
import wiki.lostark.app.ui.activities.CharacterProfileActivity;
import wiki.lostark.app.utils.ViewUtils;

public class HistoriesAdapter extends RecyclerView.Adapter<HistoriesAdapter.HistoryViewHolder> {

    private ArrayList<String> histories = new ArrayList<>();
    private Context context;

    public HistoriesAdapter(Context context) {
        this.context = context;
        histories = Paper.book().read("histories", new ArrayList<>());
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(histories.get(position));
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public void resync() {
        histories = Paper.book().read("histories", new ArrayList<>());
        notifyDataSetChanged();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        private ItemHistoryBinding binding;


        public HistoryViewHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String nickname) {
            ViewUtils.setFadeAnimation(itemView, 1000L);
            binding.nickname.setText(nickname);
            binding.nickname.setOnClickListener(v -> {
                Intent searchIntent = new Intent(context, CharacterProfileActivity.class);
                searchIntent.putExtra("nickname", nickname);
                context.startActivity(searchIntent);
            });
            binding.cancelButton.setOnClickListener(v -> {
                histories.remove(nickname);
                Paper.book().write("histories", histories);
                resync();
            });
        }
    }
}
