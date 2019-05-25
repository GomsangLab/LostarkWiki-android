package wiki.lostark.app.datas.alarm;

import wiki.lostark.app.datas.event.EventData;

public class EventAlarmData {
    private EventData eventData;
    private long alarmDate;

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
}
