package wiki.lostark.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
    private CharacterProfileSkill skills;
    private ArrayList<ArrayList<CharacterProfileSkill.Tripod>> tripods;   // 모든 트리팟 리스트 (tripods 0번째 리스트의 0번째 => 첫번째 티어 첫번째 스킬)

    public SkillTierDialog(Context context, ArrayList<ArrayList<CharacterProfileSkill.Tripod>> tripods, CharacterProfileSkill skills) {
        super(context);
        this.context = context;
        this.tripods = tripods;
        this.skills = skills;
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

        applyEnablerTier(skills.getEnableTier());
        int tripodTurn;
        for (int ti = 0; ti < tripods.size(); ti++) {
            tripodTurn = 0;
            for (CharacterProfileSkill.Tripod eachTripod : tripods.get(ti)) {
                tripodTurn++;
                ImageView imageView = new ImageView(context);

                int px = Math.round(TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 56, context.getResources().getDisplayMetrics()));
                LinearLayout.LayoutParams params = new LinearLayout
                        .LayoutParams(px, px);
                params.setMargins(px / 3, 4, px / 3, 4);
                imageView.setLayoutParams(params);

                int finalTi = ti;
                imageView.setOnClickListener(v -> new SkillTierDetailDialog(context, eachTripod, finalTi).show());
                Glide.with(context).load(eachTripod.getIcon()).into(imageView);

                TextView textView = new TextView(context);
                textView.setLayoutParams(params);
                textView.setTypeface(null, Typeface.BOLD);
                textView.setTextSize(11);
                textView.setTextColor(Color.WHITE);

                if (eachTripod.getName().replace("<font color='#FFBB63'>", "")
                        .replace("</font>", "").replace(" ", "").length() >= 6) {
                    textView.setSingleLine(false);
                    textView.setText(eachTripod.getName().replace("<font color='#FFBB63'>", "")
                            .replace("</font>", "").replace(" ", "\n"));
                } else {
                    textView.setSingleLine(true);
                    textView.setText(eachTripod.getName().replace("<font color='#FFBB63'>", "")
                            .replace("</font>", ""));
                }

                textView.setGravity(Gravity.CENTER);

                if (ti == 0) {
                    binding.tier1Layout.addView(imageView);
                    binding.tier1TextLayout.addView(textView);

                    // 모든 티어가 열리지 않았을때 - 모든 티어 암전
                    if (skills.getEnableTier() == -1)
                        imageView.setColorFilter(Color.parseColor("#eeeeee"), PorterDuff.Mode.SRC_IN);


                    if (tripodTurn == skills.getSelectedTripodTier()[0])
                        textView.setTextColor(Color.parseColor("#217ed3"));

                }

                if (ti == 1) {
                    binding.tier2Layout.addView(imageView);
                    binding.tier2TextLayout.addView(textView);

                    // 2티어가 열리지 않았을 경우 - 2 티어, 3 티어 암전
                    if (skills.getEnableTier() < 1)
                        imageView.setColorFilter(Color.parseColor("#eeeeee"), PorterDuff.Mode.SRC_IN);


                    if (tripodTurn == skills.getSelectedTripodTier()[1])
                        textView.setTextColor(Color.parseColor("#7ed321"));

                }

                if (ti == 2) {
                    binding.tier3Layout.addView(imageView);
                    binding.tier3TextLayout.addView(textView);

                    // 3티어가 열리지 않았을 경우 - 3 티어 티어 암전
                    if (skills.getEnableTier() < 2)
                        imageView.setColorFilter(Color.parseColor("#eeeeee"), PorterDuff.Mode.SRC_IN);


                    if (tripodTurn == skills.getSelectedTripodTier()[2])
                        textView.setTextColor(Color.parseColor("#d3a821"));

                }
            }
        }
    }

    private void applyEnablerTier(int enablerTier) {
        // 모든 티어가 열리지 않았을때 - 모든 티어 암전
        if (enablerTier == -1) {
            binding.imgTier1.setColorFilter(Color.parseColor("#eeeeee"), PorterDuff.Mode.SRC_IN);
            binding.imgTier2.setColorFilter(Color.parseColor("#eeeeee"), PorterDuff.Mode.SRC_IN);
            binding.imgTier3.setColorFilter(Color.parseColor("#eeeeee"), PorterDuff.Mode.SRC_IN);
        }

        // 2티어가 열리지 않았을 경우 - 2 티어, 3 티어 암전
        if (enablerTier < 1) {
            binding.imgTier2.setColorFilter(Color.parseColor("#eeeeee"), PorterDuff.Mode.SRC_IN);
            binding.imgTier3.setColorFilter(Color.parseColor("#eeeeee"), PorterDuff.Mode.SRC_IN);
        }

        // 3티어가 열리지 않았을 경우 - 3 티어 티어 암전
        if (enablerTier < 2) {
            binding.imgTier3.setColorFilter(Color.parseColor("#eeeeee"), PorterDuff.Mode.SRC_IN);
        }

    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}