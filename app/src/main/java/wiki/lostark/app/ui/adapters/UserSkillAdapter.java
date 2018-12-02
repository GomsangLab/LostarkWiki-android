package wiki.lostark.app.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ItemUserskillBinding;
import wiki.lostark.app.datas.characterprofile.CharacterProfileSkill;

public class UserSkillAdapter extends RecyclerView.Adapter<UserSkillAdapter.UserSkillViewHodler> {

    private Context context;
    private ArrayList<CharacterProfileSkill> profileSkill;

    public UserSkillAdapter(Context context, ArrayList<CharacterProfileSkill> profileSkills) {
        this.context = context;
        this.profileSkill = profileSkills;
    }

    @NonNull
    @Override
    public UserSkillViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserSkillViewHodler(ItemUserskillBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserSkillViewHodler holder, int position) {
        holder.bind(profileSkill.get(position));
        holder.itemView.setOnClickListener(view -> Toast.makeText(context, "스킬 상세보기,,, 진짜 조금만 기달려주세요!", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return profileSkill.size();
    }

    public class UserSkillViewHodler extends RecyclerView.ViewHolder {

        private ItemUserskillBinding binding;

        public UserSkillViewHodler(@NonNull ItemUserskillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CharacterProfileSkill profileSkill) {

            Glide.with(context).load(profileSkill.getThumb()).into(binding.itemimage);
            GradientDrawable imageRound = (GradientDrawable) context.getDrawable(R.drawable.bg_imageround);
            binding.itemimage.setBackground(imageRound);
            binding.itemimage.setClipToOutline(true);

            if (profileSkill.getLevel() == 10) {
                binding.itemskilllevel.setText("최고");
                binding.itemskilllevel.setTextColor(Color.parseColor("#1cc6f8"));
                binding.itemtvskilllevel.setTextColor(Color.parseColor("#1cc6f8"));
            } else {
                binding.itemskilllevel.setText(String.valueOf(profileSkill.getLevel()));
                binding.itemskilllevel.setTextColor(Color.parseColor("#f8e71c"));
                binding.itemtvskilllevel.setTextColor(Color.parseColor("#f8e71c"));
            }

            if (profileSkill.getEnableTier() == -1) {
                Glide.with(context).load(R.drawable.img_enabletier0).into(binding.itemcharacteristic);
            }
            if (profileSkill.getEnableTier() == 0) {
                Glide.with(context).load(R.drawable.img_enabletier1).into(binding.itemcharacteristic);
            }
            if (profileSkill.getEnableTier() == 1) {
                Glide.with(context).load(R.drawable.img_enabletier2).into(binding.itemcharacteristic);
            }
            if (profileSkill.getEnableTier() == 2) {
                Glide.with(context).load(R.drawable.img_enabletier3).into(binding.itemcharacteristic);
            }

            binding.itemcategory.setText(Html.fromHtml(profileSkill.getCategory()));
            binding.itemname.setText(profileSkill.getName());

        }
    }
}
