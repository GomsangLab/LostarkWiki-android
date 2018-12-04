package wiki.lostark.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;

import androidx.databinding.DataBindingUtil;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.DialogSkillTierBinding;
import wiki.lostark.app.datas.characterprofile.CharacterProfileSkill;

public class SkillTierDialog extends Dialog {

    private Context context;
    private DialogSkillTierBinding binding;
    private CharacterProfileSkill skill;
    private ArrayList<ArrayList<CharacterProfileSkill.Tripod>> tripods = new ArrayList<>();   // 모든 트리팟 리스트 (tripods 0번째 리스트의 0번째 => 첫번째 티어 첫번째 스킬)

    public SkillTierDialog(Context context, ArrayList<ArrayList<CharacterProfileSkill.Tripod>> tripods) {
        super(context);
        this.context = context;
        this.tripods = tripods;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_skill_tier, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(true);

        for (int ti = 0; ti < tripods.size(); ti++) {
            for (CharacterProfileSkill.Tripod eachTripod : tripods.get(ti)) {
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams params = new LinearLayout
                        .LayoutParams(56, 56);
                Glide.with(context).load(eachTripod.getIcon()).into(imageView);

                if (ti == 0) binding.tier1Layout.addView(imageView);
                if (ti == 1) binding.tier2Layout.addView(imageView);
                if (ti == 2) binding.tier3Layout.addView(imageView);

            }
        }
    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}