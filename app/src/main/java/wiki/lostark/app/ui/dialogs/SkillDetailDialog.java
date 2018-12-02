package wiki.lostark.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

import androidx.databinding.DataBindingUtil;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.DialogSkillDetailBinding;
import wiki.lostark.app.datas.characterprofile.CharacterProfile;
import wiki.lostark.app.datas.characterprofile.CharacterProfileSkill;

public class SkillDetailDialog extends Dialog {

    private Context context;
    private DialogSkillDetailBinding binding;
    private CharacterProfileSkill skill;
    private String detailDescs, replaceDetailDescs;

    public SkillDetailDialog(Context context, CharacterProfileSkill skill) {
        super(context);
        this.context = context;
        this.skill = skill;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_skill_detail, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(true);

        Glide.with(context).load(skill.getThumb()).into(binding.thumb);
        GradientDrawable imageRound = (GradientDrawable) context.getDrawable(R.drawable.bg_imageround);
        binding.thumb.setBackground(imageRound);
        binding.thumb.setClipToOutline(true);

        binding.name.setText(skill.getName());
        binding.skilltype.setText(Html.fromHtml(skill.getSkillType().replace("[", "").replace("]", "").replace(" 스킬", "")));

        if (skill.getMiddleText().length() != 0) {
            binding.middleText.setText(Html.fromHtml(skill.getMiddleText()));
        } else {
            binding.cooltime.setText(Html.fromHtml(skill.getCooltime()));
        }

        if (skill.getMasteratio() < 0) {
            binding.master.setVisibility(View.GONE);
        }else{
            binding.master.setText(skill.getJobname() +"들의 " + String.format("%.2f", skill.getMasteratio()) +"%가 이 스킬 마스터");
        }

        for (String str : skill.getDetailDescs()) {
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setText(Html.fromHtml(filter(str)));
            textView.setLayoutParams(params);
            textView.setTextColor(Color.WHITE);
            binding.detailDescsLayout.addView(textView);
        }
    }

    private String filter(String target){
        return target.replaceAll("다음 스킬 레벨", "<br>다음 스킬 레벨")
                .replaceAll("808080", "FFFFFF");
    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}