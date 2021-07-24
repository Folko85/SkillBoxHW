package model;

import java.text.SimpleDateFormat;

public class TimePeriod implements Comparable<TimePeriod> {

    private long from;
    private long to;
    private static long day = 86400000;
    private static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public TimePeriod(long from, long to) {
        this.from = from;
        this.to = to;
        if (from / day != to / day) {
            throw new IllegalArgumentException("Dates 'from' and 'to' must be within ONE day!");
        }
    }

    public void appendTime(long visitTime) {
        if (from / day != visitTime / day) {
            throw new IllegalArgumentException(
                    "Visit time must be within the same day as the current TimePeriod!");
        }

        if (visitTime < from) {
            from = visitTime;
        }
        if (visitTime > to) {
            to = visitTime;
        }
    }

    public String toString() {
        return dayFormat.format(this.from) +
                " " + timeFormat.format(this.from) + "-" + timeFormat.format(this.to);
    }

    @Override
    public int compareTo(TimePeriod period) {
        Long current = from/day;
        Long compared = period.from/day;
        return current.compareTo(compared);
    }
}
