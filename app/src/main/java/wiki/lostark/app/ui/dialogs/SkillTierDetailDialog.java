package wiki.lostark.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;

import androidx.databinding.DataBindingUtil;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.DialogSkillTierBinding;
import wiki.lostark.app.databinding.DialogSkillTierDetailBinding;
import wiki.lostark.app.datas.characterprofile.CharacterProfileSkill;

public class SkillTierDetailDialog extends Dialog {

    private Context context;
    private DialogSkillTierDetailBinding binding;
    private CharacterProfileSkill.Tripod tripod;

    public SkillTierDetailDialog(Context context, CharacterProfileSkill.Tripod tripod) {
        super(context);
        this.context = context;
        this.tripod = tripod;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_skill_tier_detail, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(true);

        binding.desc.setText(Html.fromHtml(tripod.getDesc()));
        binding.title.setText(Html.fromHtml(tripod.getName()));
        Glide.with(context).load(tripod.getIcon()).into(binding.imageView);
    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}