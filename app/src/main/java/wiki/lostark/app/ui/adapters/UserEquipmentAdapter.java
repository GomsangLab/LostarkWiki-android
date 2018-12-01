package wiki.lostark.app.ui.adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.ItemUserequipmentBinding;
import wiki.lostark.app.datas.characterprofile.CharacterProfileEquipment;

public class UserEquipmentAdapter extends RecyclerView.Adapter<UserEquipmentAdapter.UserEquipmentViewHodler> {

    private Context context;
    private ArrayList<CharacterProfileEquipment> profileEquipment;

    public UserEquipmentAdapter(Context context, ArrayList<CharacterProfileEquipment> profileEquipments) {
        this.context = context;
        this.profileEquipment = profileEquipments;
    }

    @NonNull
    @Override
    public UserEquipmentViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserEquipmentViewHodler(ItemUserequipmentBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserEquipmentViewHodler holder, int position) {
        holder.bind(profileEquipment.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, holder.binding.itemtitle.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileEquipment.size();
    }

    public class UserEquipmentViewHodler extends RecyclerView.ViewHolder {

        private ItemUserequipmentBinding binding;

        public UserEquipmentViewHodler(@NonNull ItemUserequipmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CharacterProfileEquipment profileEquipment) {

            if (profileEquipment.getIconGrade() == 1) {
                binding.itemimage.setBackgroundResource(R.drawable.bg_itemgrade1);
            } else if (profileEquipment.getIconGrade() == 2) {
                binding.itemimage.setBackgroundResource(R.drawable.bg_itemgrade2);
            } else if (profileEquipment.getIconGrade() == 3) {
                binding.itemimage.setBackgroundResource(R.drawable.bg_itemgrade3);
            }

            Glide.with(context).load(profileEquipment.getThumb()).into(binding.itemimage);

            if (!profileEquipment.isAvailable()) {
                binding.itemtitle.setText("");
                binding.itemvalue.setText("");
            } else {
                binding.itemtitle.setText(Html.fromHtml(profileEquipment.getName()
                        .replace("ALIGN='CENTER'", "")
                        .replace("<P >", "")
                        .replace("</P>", "")));

                binding.itemvalue.setText(Html.fromHtml(profileEquipment.getItemLevel()
                        .replace("<FONT SIZE='14'>", "")
                        .replace("</FONT>", "")
                        .replace("아이템 레벨", "Lv.")
                        .replace("(티어 1)", "")));
            }
        }
    }
}
