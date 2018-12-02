package wiki.lostark.app.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import wiki.lostark.app.databinding.ItemUserstatsBinding;
import wiki.lostark.app.datas.characterprofile.CharacterProfileStat;
import wiki.lostark.app.ui.dialogs.StatDetailDialog;

public class UserStatsAdapter extends RecyclerView.Adapter<UserStatsAdapter.UserBasicStatsViewHodler> {

    private Context context;
    private ArrayList<CharacterProfileStat> profileStat;

    public UserStatsAdapter(Context context, ArrayList<CharacterProfileStat> profileStats) {
        this.context = context;
        this.profileStat = profileStats;
    }

    @NonNull
    @Override
    public UserBasicStatsViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserBasicStatsViewHodler(ItemUserstatsBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserBasicStatsViewHodler holder, int position) {
        holder.bind(profileStat.get(position));
    }

    @Override
    public int getItemCount() {
        return profileStat.size();
    }

    public class UserBasicStatsViewHodler extends RecyclerView.ViewHolder {

        private ItemUserstatsBinding binding;

        public UserBasicStatsViewHodler(@NonNull ItemUserstatsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CharacterProfileStat profileStat) {
            binding.itemtitle.setText(profileStat.getName());
            binding.itemtext.setText(profileStat.getValue());

            itemView.setOnClickListener(v -> {
                StatDetailDialog statDetailDialog = new StatDetailDialog(context, profileStat);
                statDetailDialog.setCanceledOnTouchOutside(true);
                statDetailDialog.show();
            });
        }
    }
}