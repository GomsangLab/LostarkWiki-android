package wiki.lostark.app.libs;

import java.util.ArrayList;

import io.paperdb.Paper;
import wiki.lostark.app.datas.event.EventData;

public class EventLabel {
    public static ArrayList<String> getActiveLabels() {
        ArrayList<String> labeledEvents = Paper.book().read("labeledEvents", new ArrayList<>());
        return labeledEvents;
    }

    public static void setActiveLabels(ArrayList<String> activeLabels) {

        Paper.book().write("labeledEvents", activeLabels);
    }


    public static void activeLabel(EventData eventData) {
        ArrayList<String> labeledEvents = Paper.book().read("labeledEvents", new ArrayList<>());
        final String event = eventData.getName() + eventData.getTime();
        if (!labeledEvents.contains(event))
            labeledEvents.add(event);
        Paper.book().write("labeledEvents", labeledEvents);
    }

    public static void deactiveLabel(EventData eventData) {
        ArrayList<String> labeledEvents = Paper.book().read("labeledEvents", new ArrayList<>());
        final String event = eventData.getName() + eventData.getTime();
        if (labeledEvents.contains(event))
            labeledEvents.remove(eventData.getName() + eventData.getTime());
        Paper.book().write("labeledEvents", labeledEvents);
    }

    public static boolean isLabeled(EventData eventData) {
        ArrayList<String> labeledEvents = Paper.book().read("labeledEvents", new ArrayList<>());
        final String event = eventData.getName() + eventData.getTime();
        return labeledEvents.contains(event);
    }
}
