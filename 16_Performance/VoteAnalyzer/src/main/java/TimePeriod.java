import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimePeriod implements Comparable<TimePeriod> {

    private long from;
    private long to;

    /**
     * Time period within one day
     *
     * @param from
     * @param to
     */
    public TimePeriod(long from, long to) {
        this.from = from;
        this.to = to;
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        if (!dayFormat.format(new Date(from)).equals(dayFormat.format(new Date(to)))) {
            throw new IllegalArgumentException("Dates 'from' and 'to' must be within ONE day!");
        }
    }

    public TimePeriod(Date from, Date to) {
        this.from = from.getTime();
        this.to = to.getTime();
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        if (!dayFormat.format(from).equals(dayFormat.format(to))) {
            throw new IllegalArgumentException("Dates 'from' and 'to' must be within ONE day!");
        }
    }

    public void appendTime(Date visitTime) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        if (!dayFormat.format(new Date(from))
            .equals(dayFormat.format(new Date(visitTime.getTime())))) {
            throw new IllegalArgumentException(
                "Visit time must be within the same day as the current TimePeriod!");
        }
        long visitTimeTs = visitTime.getTime();
        if (visitTimeTs < from) {
            from = visitTimeTs;
        }
        if (visitTimeTs > to) {
            to = visitTimeTs;
        }
    }

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String from = dateFormat.format(this.from);
        String to = timeFormat.format(this.to);
        return from + "-" + to;
    }

    @Override
    public int compareTo(TimePeriod period) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date current = new Date();
        Date compared = new Date();
        try {
            current = dayFormat.parse(dayFormat.format(new Date(from)));
            compared = dayFormat.parse(dayFormat.format(new Date(period.from)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return current.compareTo(compared);
    }
}
