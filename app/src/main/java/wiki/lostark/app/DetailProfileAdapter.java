package wiki.lostark.app;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import wiki.lostark.app.databinding.ItemDetailprofileBinding;
import wiki.lostark.app.datas.characterprofile.CharacterProfileEquipment;

public class DetailProfileAdapter extends RecyclerView.Adapter<DetailProfileAdapter.DetailProfileViewHodler> {

    private Context context;
    private ArrayList<CharacterProfileEquipment> detailProfiles;

    public DetailProfileAdapter(Context context, ArrayList<CharacterProfileEquipment> detailProfiles) {
        this.context = context;
        this.detailProfiles = detailProfiles;
    }

    @NonNull
    @Override
    public DetailProfileViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailProfileViewHodler(ItemDetailprofileBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailProfileViewHodler holder, int position) {
        holder.bind(detailProfiles.get(position));
    }

    @Override
    public int getItemCount() {
        return detailProfiles.size();
    }

    public class DetailProfileViewHodler extends RecyclerView.ViewHolder {

        private ItemDetailprofileBinding binding;

        public DetailProfileViewHodler(@NonNull ItemDetailprofileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CharacterProfileEquipment detailProfile) {

            if (detailProfile.getIconGrade() == 1) {
                binding.itemimage.setBackgroundResource(R.drawable.bg_itemgrade1);
            } else if (detailProfile.getIconGrade() == 2) {
                binding.itemimage.setBackgroundResource(R.drawable.bg_itemgrade2);
            } else if (detailProfile.getIconGrade() == 3) {
                binding.itemimage.setBackgroundResource(R.drawable.bg_itemgrade3);
            }

            Glide.with(context)
                    .load(detailProfile.getThumb())
                    .into(binding.itemimage);
            if (!detailProfile.isAvailable()) return;
            binding.itemtitle.setText(Html.fromHtml(detailProfile.getName().replace("ALIGN='CENTER'", "").replace("<P >", "").replace("</P>", "")));
            binding.itemvalue.setText(Html.fromHtml(detailProfile.getItemLevel().replace("<FONT SIZE='14'>", "").replace("</FONT>", "").replace("아이템 레벨", "Lv.").replace("(티어 1)", "")));
        }
    }
}
