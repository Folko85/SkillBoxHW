package model;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class TimePeriod implements Comparable<TimePeriod> {

    private long from;
    private long to;
    SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");

    public TimePeriod(long from, long to) {
        this.from = from;
        this.to = to;
        if (!Instant.ofEpochMilli(from).atZone(ZoneId.systemDefault()).toLocalDate().equals(Instant.ofEpochMilli(to).atZone(ZoneId.systemDefault()).toLocalDate())) {
            throw new IllegalArgumentException("Dates 'from' and 'to' must be within ONE day!");
        }
    }

    public void appendTime(long visitTime) {
        if (!Instant.ofEpochMilli(from).atZone(ZoneId.systemDefault()).toLocalDate()
                .equals(Instant.ofEpochMilli(visitTime).atZone(ZoneId.systemDefault()).toLocalDate())) {
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
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return dayFormat.format(this.from) +
                " " + timeFormat.format(this.from) + "-" + timeFormat.format(this.to);
    }

    @Override
    public int compareTo(TimePeriod period) {
        LocalDate current = Instant.ofEpochMilli(from).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate compared = Instant.ofEpochMilli(period.from).atZone(ZoneId.systemDefault()).toLocalDate();
        return current.compareTo(compared);
    }
}
