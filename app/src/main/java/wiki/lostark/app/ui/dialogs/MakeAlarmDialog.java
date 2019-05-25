package wiki.lostark.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import androidx.databinding.DataBindingUtil;
import me.gujun.android.taggroup.TagGroup;
import wiki.lostark.app.R;
import wiki.lostark.app.databinding.DialogMakeAlarmBinding;
import wiki.lostark.app.databinding.DialogStatDetailBinding;
import wiki.lostark.app.datas.characterprofile.CharacterProfileStat;
import wiki.lostark.app.datas.event.EventData;
import wiki.lostark.app.utils.ShareUtils;
import wiki.lostark.app.utils.TimeUtils;
import wiki.lostark.app.utils.ViewUtils;

public class MakeAlarmDialog extends Dialog {

    private Context context;
    private DialogMakeAlarmBinding binding;
    private EventData eventData;

    private static Long MINUTE_MILLISECONDS = 60 * 1000L;
    private static Long HOUR_MILLISECONDS = 60 * 60 * 1000L;

    final String[] timeInText = new String[]{"정시", "2분 전", "5분 전", "10분 전", "20분 전", "30분 전", "40분 전"
            , "50분 전", "1시간 전", "2시간 전", "3시간 전", "4시간 전", "5시간 전", "6시간 전"};

    final Long[] timeInMilliseconds = new Long[]{0L, 2 * MINUTE_MILLISECONDS, 5 * MINUTE_MILLISECONDS,
            10 * MINUTE_MILLISECONDS, 20 * MINUTE_MILLISECONDS, 30 * MINUTE_MILLISECONDS, 40 * MINUTE_MILLISECONDS
            , 50 * MINUTE_MILLISECONDS, HOUR_MILLISECONDS, 2 * HOUR_MILLISECONDS, 3 * HOUR_MILLISECONDS, 4 * HOUR_MILLISECONDS,
            5 * HOUR_MILLISECONDS, 6 * HOUR_MILLISECONDS};


    public MakeAlarmDialog(Context context, EventData eventData) {
        super(context);
        this.context = context;
        this.eventData = eventData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_make_alarm, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(true);

        binding.tagGroup.setTags(timeInText);
        binding.tagGroup.setOnTagClickListener(tag -> {
            try {
                Long selectedTime = timeInMilliseconds[Arrays.asList(timeInText).indexOf(tag)];
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                long endBidTime = sdf.parse(eventData.getTime()).getTime();

                if (endBidTime - selectedTime < System.currentTimeMillis()){
                    Toast.makeText(context, "알람을 요청한 시간이 이미 지나가 알람 설정이 불가합니다!", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });

        binding.eventName.setText(eventData.getName());
        binding.eventDate.setText(eventData.getTime());
        updateRemainingTime();

        new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateRemainingTime();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void updateRemainingTime() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            long endBidTime = sdf.parse(eventData.getTime()).getTime();
            long currentTime = System.currentTimeMillis();
            String remaining = TimeUtils.printDifference(new Date(currentTime), new Date(endBidTime));
            binding.remainingTime.setText(remaining);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}