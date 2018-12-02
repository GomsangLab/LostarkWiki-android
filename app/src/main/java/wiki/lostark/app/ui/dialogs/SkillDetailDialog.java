package wiki.lostark.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;

import java.util.Objects;

import androidx.databinding.DataBindingUtil;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.DialogSkillDetailBinding;
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
        binding.category.setText(Html.fromHtml(skill.getCategory()));
        binding.skilltype.setText(Html.fromHtml(skill.getSkillType()));
        binding.middleText.setText(Html.fromHtml(skill.getMiddleText()));
        binding.cooltime.setText(Html.fromHtml(skill.getCooltime()));

        for (String str : skill.getDetailDescs()) {
            detailDescs += str;
        }

        replaceDetailDescs = detailDescs
                .replace("null", "")
                .replace("<FONT SIZE='11'></FONT>", "")
                .replace("<FONT COLOR='#efefdf'></FONT>", "")
                .replace("<FONT COLOR='#efefdf'></FONT><BR>", "")
                .replace("</FONT><BR>", "</FONT>")
                .replace("</FONT>", "</FONT><BR>")
                .replace("</FONT><BR></FONT><BR>", "</FONT></FONT><BR>")
                .replace("다음 스킬", "<BR>다음 스킬")
                .replace("필요 레벨", "<BR>필요 레벨")
                .replace("필요 스킬 포인트", "<BR>필요 스킬 포인트")
                .replace("필요 레벨</FONT><BR>", "필요 레벨 </FONT>")
                .replace("필요 스킬 포인트 </FONT><BR>", "필요 스킬 포인트 </FONT>")
                .replace("마나", "<BR>마나")
                .replace("내공", "<BR>내공");

        binding.detaildescs.setText(Html.fromHtml(replaceDetailDescs.substring(0, replaceDetailDescs.length() - 4)));
    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}