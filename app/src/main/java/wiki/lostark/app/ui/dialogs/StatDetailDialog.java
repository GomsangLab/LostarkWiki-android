package wiki.lostark.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import wiki.lostark.app.databinding.DialogEquipmentDetailBinding;
import wiki.lostark.app.databinding.DialogStatDetailBinding;
import wiki.lostark.app.datas.characterprofile.CharacterProfileEquipment;
import wiki.lostark.app.datas.characterprofile.CharacterProfileStat;
import wiki.lostark.app.utils.ViewUtils;

public class StatDetailDialog extends Dialog {

    private Context context;
    private DialogStatDetailBinding binding;
    private CharacterProfileStat stat;
    private String description, replacedDescription;

    public StatDetailDialog(Context context, CharacterProfileStat stat) {
        super(context);
        this.context = context;
        this.stat = stat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_stat_detail, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(true);

        ViewUtils.changeViewBackgroundColor(binding.statName, Color.parseColor("#4c4c4c"));

        binding.statName.setText(stat.getName());
        binding.statNum.setText(stat.getValue());

        description = stat.getDescription().replace("<li>", "· ").replace("</li>", "").replace("다.", "다.<br>");
        replacedDescription = description.substring(0, description.length() - 4);

        binding.desc.setText(Html.fromHtml(replacedDescription));
    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}