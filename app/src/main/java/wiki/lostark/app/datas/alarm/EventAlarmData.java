package wiki.lostark.app.datas.alarm;

import wiki.lostark.app.datas.event.EventData;

public class EventAlarmData {
    private boolean isAlarmed = false;
    private EventData eventData;
    private long alarmDate;
    private long alarmTimeUntilEvent;

    public EventData getEventData() {
        return eventData;
    }

    public void setEventData(EventData eventData) {
        this.eventData = eventData;
    }

    public long getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(long alarmDate) {
        this.alarmDate = alarmDate;
    }

    public long getAlarmTimeUntilEvent() {
        return alarmTimeUntilEvent;
    }

    public void setAlarmTimeUntilEvent(long alarmTimeUntilEvent) {
        this.alarmTimeUntilEvent = alarmTimeUntilEvent;
    }

    public boolean isAlarmed() {
        return isAlarmed;
    }

    public void setAlarmed(boolean alarmed) {
        isAlarmed = alarmed;
    }
}
