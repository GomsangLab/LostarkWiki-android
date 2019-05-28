package wiki.lostark.app.libs.eventalarm;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import wiki.lostark.app.R;
import wiki.lostark.app.datas.alarm.EventAlarmData;
import wiki.lostark.app.ui.activities.EventActivity;
import wiki.lostark.app.utils.TimeUtils;
import wiki.lostark.app.utils.ViewUtils;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        for (EventAlarmData eventAlarmData : EventAlarm.getMustAlarms()) {
            Intent push = new Intent();
            push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            push.setClass(context, EventActivity.class);
            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0, push, PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context, "lostarkwikievents")
                            .setSmallIcon(R.drawable.ic_alarm_white_24dp)
                            .setContentTitle(eventAlarmData.getEventData().getName())
                            .setContentText(String.format("%s 이벤트 %s전 입니다.", eventAlarmData.getEventData().getName(),
                                    TimeUtils.printDifference(new Date(eventAlarmData.getAlarmDate() - eventAlarmData.getAlarmTimeUntilEvent()),
                                            new Date(eventAlarmData.getAlarmDate()))))
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setFullScreenIntent(fullScreenPendingIntent, true)
                            .setPriority(Notification.PRIORITY_HIGH);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify((int) (Math.random() * 50000 + 1), mBuilder.build());
        }

        // 재부팅시 알람매니저가 풀리므로
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            for (EventAlarmData eventAlarmData : EventAlarm.getFutureAlarms()) {
                EventAlarm.reqNotifiBack(context, eventAlarmData.getAlarmDate());
            }
        }
    }
}