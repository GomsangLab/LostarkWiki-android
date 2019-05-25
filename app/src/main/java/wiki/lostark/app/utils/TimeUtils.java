package wiki.lostark.app.utils;

import java.util.Date;

public class TimeUtils {
    public static String printDifference(Date startDate, Date endDate) {

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        final StringBuffer printBuffer = new StringBuffer();
        if (elapsedDays != 0) printBuffer.append(elapsedDays).append("일").append(", ");
        if (elapsedHours != 0) printBuffer.append(elapsedHours).append("시간").append(", ");
        if (elapsedMinutes != 0) printBuffer.append(elapsedMinutes).append("분").append(", ");
        printBuffer.append(elapsedSeconds).append("초");

        return printBuffer.toString();

    }
}
