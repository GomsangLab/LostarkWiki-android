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
        binding.skilltype.setText(Html.fromHtml(skill.getSkillType().replace("[", "").replace("]", "").replace(" 스킬", "")));

        if (skill.getMiddleText().length() != 0) {
            binding.middleText.setText(Html.fromHtml(skill.getMiddleText()));
        } else {
            binding.cooltime.setText(Html.fromHtml(skill.getCooltime()));
        }

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
                .replace("필요 스킬 포인트", "<BR>필요 스킬 포인트")
                .replace("필요 레벨</FONT><BR>", "필요 레벨 </FONT>")
                .replace("필요 스킬 포인트 </FONT><BR>", "필요 스킬 포인트 </FONT>")
                .replace("스킬 레벨 10", "스킬 최고레벨<BR>")
                .replace("스킬 레벨 1", "스킬 레벨 1<BR>")
                .replace("스킬 레벨 2", "스킬 레벨 2<BR>")
                .replace("스킬 레벨 3", "스킬 레벨 3<BR>")
                .replace("스킬 레벨 4", "스킬 레벨 4<BR>")
                .replace("스킬 레벨 5", "스킬 레벨 5<BR>")
                .replace("스킬 레벨 6", "스킬 레벨 6<BR>")
                .replace("스킬 레벨 7", "스킬 레벨 7<BR>")
                .replace("스킬 레벨 8", "스킬 레벨 8<BR>")
                .replace("스킬 레벨 9", "스킬 레벨 9<BR>")
                .replace("<BR><BR>내공", "<BR>내공")
                .replace("기력", "<BR>내공")
                .replace("<BR><BR>기력", "<BR>기력")
                .replace("양팔", "<BR>양팔")
                .replace("%</FONT><BR>", "% </FONT>")
                .replace("6</FONT><BR>", "6</FONT>")
                .replace("808080", "FFFFFF")
                .replace("EEA839", "FFFFFF");

        binding.detaildescs.setText(Html.fromHtml(replaceDetailDescs.substring(0, replaceDetailDescs.length() - 4)));
    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}