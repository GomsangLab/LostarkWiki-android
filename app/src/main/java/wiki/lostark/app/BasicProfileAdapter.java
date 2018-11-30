package wiki.lostark.app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import wiki.lostark.app.databinding.ItemBasicprofileBinding;
import wiki.lostark.app.datas.BasicProfile;
import wiki.lostark.app.utils.ViewUtils;

public class BasicProfileAdapter extends RecyclerView.Adapter<BasicProfileAdapter.BasicProfileViewHodler> {

    private Context context;
    private ArrayList<BasicProfile> basicProfiles;

    public BasicProfileAdapter(Context context, ArrayList<BasicProfile> basicProfiles) {
        this.context = context;
        this.basicProfiles = basicProfiles;
    }

    @NonNull
    @Override
    public BasicProfileViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BasicProfileViewHodler(ItemBasicprofileBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BasicProfileViewHodler holder, int position) {
        holder.bind(basicProfiles.get(position));
    }

    @Override
    public int getItemCount() {
        return basicProfiles.size();
    }

    public class BasicProfileViewHodler extends RecyclerView.ViewHolder {

        private ItemBasicprofileBinding binding;

        public BasicProfileViewHodler(@NonNull ItemBasicprofileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(BasicProfile basicProfile){
            binding.profilename.setText(basicProfile.getName());
            binding.profilevalue.setText(basicProfile.getValue());
            ViewUtils.changeViewBackgroundColor(binding.profilename, basicProfile.getPrimaryColor());
        }
    }

}
