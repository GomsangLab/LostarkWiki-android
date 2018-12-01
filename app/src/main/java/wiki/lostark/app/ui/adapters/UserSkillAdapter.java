package wiki.lostark.app.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ItemUserequipmentBinding;
import wiki.lostark.app.databinding.ItemUserskillBinding;
import wiki.lostark.app.datas.characterprofile.CharacterProfileEquipment;
import wiki.lostark.app.datas.characterprofile.CharacterProfileSkill;

public class UserSkillAdapter extends RecyclerView.Adapter<UserSkillAdapter.UserSkillViewHodler> {

    private Context context;
    private ArrayList<CharacterProfileSkill> detailProfiles;

    public UserSkillAdapter(Context context, ArrayList<CharacterProfileSkill> detailProfiles) {
        this.context = context;
        this.detailProfiles = detailProfiles;
    }

    @NonNull
    @Override
    public UserSkillViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserSkillViewHodler(ItemUserskillBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserSkillViewHodler holder, int position) {
        holder.bind(detailProfiles.get(position));
    }

    @Override
    public int getItemCount() {
        return detailProfiles.size();
    }

    public class UserSkillViewHodler extends RecyclerView.ViewHolder {

        private ItemUserskillBinding binding;

        public UserSkillViewHodler(@NonNull ItemUserskillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CharacterProfileSkill profileSkill) {

            Glide.with(context)
                    .load(profileSkill.getThumb())
                    .into(binding.itemimage);

            if (profileSkill.getLevel() == 10) {
                binding.itemskilllevel.setText("최고");
                binding.itemskilllevel.setTextColor(Color.parseColor("#1cc6f8"));
                binding.itemtvskilllevel.setTextColor(Color.parseColor("#1cc6f8"));
            } else {
                binding.itemskilllevel.setText(String.valueOf(profileSkill.getLevel()));
                binding.itemskilllevel.setTextColor(Color.parseColor("#f8e71c"));
                binding.itemtvskilllevel.setTextColor(Color.parseColor("#f8e71c"));
            }

            binding.itemcategory.setText(Html.fromHtml(profileSkill.getCategory()));
            binding.itemname.setText(profileSkill.getName());

        }
    }
}
