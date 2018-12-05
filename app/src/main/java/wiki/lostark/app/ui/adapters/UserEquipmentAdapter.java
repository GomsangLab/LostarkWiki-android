package wiki.lostark.app.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ItemUserequipmentBinding;
import wiki.lostark.app.datas.characterprofile.CharacterProfileEquipment;
import wiki.lostark.app.ui.dialogs.EquipmentDetailDialog;
import wiki.lostark.app.utils.ViewUtils;

public class UserEquipmentAdapter extends RecyclerView.Adapter<UserEquipmentAdapter.UserEquipmentViewHodler> {

    private Context context;
    private ArrayList<CharacterProfileEquipment> profileEquipments;

    public UserEquipmentAdapter(Context context, ArrayList<CharacterProfileEquipment> profileEquipments) {
        this.context = context;
        this.profileEquipments = profileEquipments;
    }

    @NonNull
    @Override
    public UserEquipmentViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserEquipmentViewHodler(ItemUserequipmentBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserEquipmentViewHodler holder, int position) {
        holder.bind(profileEquipments.get(position));
    }

    @Override
    public int getItemCount() {
        return profileEquipments.size();
    }

    public class UserEquipmentViewHodler extends RecyclerView.ViewHolder {

        private ItemUserequipmentBinding binding;

        public UserEquipmentViewHodler(@NonNull ItemUserequipmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CharacterProfileEquipment profileEquipment) {
            ViewUtils.setFadeAnimation(itemView, 500L);
            if (profileEquipment.getIconGrade() == 1) {
                binding.itemimage.setBackgroundResource(R.drawable.bg_itemgrade1);
            } else if (profileEquipment.getIconGrade() == 2) {
                binding.itemimage.setBackgroundResource(R.drawable.bg_itemgrade2);
            } else if (profileEquipment.getIconGrade() == 3) {
                binding.itemimage.setBackgroundResource(R.drawable.bg_itemgrade3);
            } else if (profileEquipment.getIconGrade() == 4) {
                binding.itemimage.setBackgroundResource(R.drawable.bg_itemgrade4);
            }

            Glide.with(context).load(profileEquipment.getThumb()).into(binding.itemimage);

            if (!profileEquipment.isAvailable()) {
                itemView.setOnClickListener(null);
                binding.itemtitle.setText("");
                binding.itemvalue.setText("");
            } else {
                itemView.setOnClickListener(v -> {
                    EquipmentDetailDialog equipmentDetailDialog = new EquipmentDetailDialog(context, profileEquipment);
                    equipmentDetailDialog.setCanceledOnTouchOutside(true);
                    equipmentDetailDialog.show();
                });

                binding.itemtitle.setText(Html.fromHtml(profileEquipment.getName()
                        .replace("ALIGN='CENTER'", "")
                        .replace("<P >", "")
                        .replace("</P>", "")));

                binding.itemvalue.setText(Html.fromHtml(profileEquipment.getItemLevel()
                        .replace("<FONT SIZE='14'>", "")
                        .replace("</FONT>", "")
                        .replace("아이템 레벨", "Lv.")
                        .replace("(티어 1)", "")
                        .replace("(티어 2)", "")));
            }
        }
    }
}