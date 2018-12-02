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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.databinding.DataBindingUtil;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.DialogEquipmentDetailBinding;
import wiki.lostark.app.datas.characterprofile.CharacterProfileEquipment;
import wiki.lostark.app.datas.characterprofile.CharacterProfileSkill;
import wiki.lostark.app.ui.activities.MainActivity;
import wiki.lostark.app.utils.ViewUtils;

public class EquipmentDetailDialog extends Dialog {
    private Context context;
    private DialogEquipmentDetailBinding binding;
    private CharacterProfileEquipment equipment;

    public EquipmentDetailDialog(Context context, CharacterProfileEquipment equipment) {
        super(context);
        this.context = context;
        this.equipment = equipment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_equipment_detail, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setCanceledOnTouchOutside(true);

        binding.name.setText(Html.fromHtml(equipment.getName().replace("ALIGN='CENTER'", "")
                .replace("<P >", "")
                .replace("</P>", "")));
        binding.sort.setText(Html.fromHtml(equipment.getSort()));
        binding.equip.setText(Html.fromHtml(equipment.getEquiped()));
        binding.itemLevel.setText(Html.fromHtml(equipment.getItemLevel()));
        binding.requireLevel.setText(Html.fromHtml(equipment.getRequireLevel()));

        if (equipment.getGrindLevel() == 0) {
            binding.grindLayout.setVisibility(View.GONE);
        } else {
            for (int gi = 0; gi < equipment.getGrindLevel(); gi++)
                ViewUtils.addImageViewToLinearLayout(context, binding.grindLayout, R.drawable.ico_polish_enabled);
        }

        for (String str : equipment.getDetailDescs()) {
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setText(Html.fromHtml(str));
            textView.setLayoutParams(params);
            textView.setTextColor(Color.WHITE
            );
            binding.detailDescsLayout.addView(textView);
        }

        if (equipment.getIconGrade() == 1) {
            binding.thumb.setBackgroundResource(R.drawable.bg_itemgrade1);
        } else if (equipment.getIconGrade() == 2) {
            binding.thumb.setBackgroundResource(R.drawable.bg_itemgrade2);
        } else if (equipment.getIconGrade() == 3) {
            binding.thumb.setBackgroundResource(R.drawable.bg_itemgrade3);
        }

        Glide.with(context).load(equipment.getThumb()).into(binding.thumb);

    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}