package wiki.lostark.app.libs.eventalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import io.paperdb.Paper;
import wiki.lostark.app.datas.alarm.EventAlarmData;
import wiki.lostark.app.datas.event.EventData;

import static android.content.Context.ALARM_SERVICE;

public class EventAlarm {
    public static ArrayList<EventAlarmData> getEventAlarms() {
        ArrayList<EventAlarmData> eventAlarms = Paper.book().read("eventAlarms", new ArrayList<>());
        return eventAlarms;
    }

    public static void registerAlarm(Context context, EventData eventData, long alarmDate, long alarmTimeUntilEvent) {
        EventAlarmData eventAlarmData = new EventAlarmData();
        eventAlarmData.setAlarmDate(alarmDate);
        eventAlarmData.setAlarmTimeUntilEvent(alarmTimeUntilEvent);
        eventAlarmData.setEventData(eventData);

        ArrayList<EventAlarmData> eventAlarms = Paper.book().read("eventAlarms", new ArrayList<>());
        eventAlarms.add(0, eventAlarmData);
        Paper.book().write("eventAlarms", eventAlarms);

        reqNotifiBack(context, alarmDate);
    }

    public static void setEventAlarms(final ArrayList<EventAlarmData> eventAlarmDatas) {
        Paper.book().write("eventAlarms", eventAlarmDatas);
    }

    public static void reqNotifiBack(Context context, long alarmDate) {
        final AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        final Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2000, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmDate, pendingIntent); //10초뒤 알람
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC_WAKEUP, alarmDate, pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, alarmDate, pendingIntent);
        }
    }

    /**
     * 알람이 울려야 되는 데이터들을 모두 리턴하고, 저장소에는 울렸다고 저장한다.
     *
     * @return
     */

    public static ArrayList<EventAlarmData> getMustAlarms() {
        ArrayList<EventAlarmData> eventAlarms = Paper.book().read("eventAlarms", new ArrayList<>());
        ArrayList<EventAlarmData> popAlarms = new ArrayList<>();

        for (EventAlarmData eventAlarmData : eventAlarms) {
            if (eventAlarmData.getAlarmDate() <= System.currentTimeMillis() && !eventAlarmData.isAlarmed()) {
                popAlarms.add(eventAlarmData);
                eventAlarmData.setAlarmed(true);
            }
        }

        Paper.book().write("eventAlarms", eventAlarms);
        return popAlarms;
    }

    /**
     * 당장 울리지 않아도 되는 알람들을 리턴한다
     *
     * @return
     */

    public static ArrayList<EventAlarmData> getFutureAlarms() {
        ArrayList<EventAlarmData> eventAlarms = Paper.book().read("eventAlarms", new ArrayList<>());
        ArrayList<EventAlarmData> futureAlarms = new ArrayList<>();

        for (EventAlarmData eventAlarmData : eventAlarms) {
            if (eventAlarmData.getAlarmDate() > System.currentTimeMillis() && eventAlarmData.isAlarmed()) {
                futureAlarms.add(eventAlarmData);
            }
        }

        return futureAlarms;
    }
}
