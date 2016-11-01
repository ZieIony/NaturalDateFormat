package tk.zielony.naturaldateformat;

import android.content.Context;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.TimeZone;

/**
 * Created by Marcin on 29.01.2016.
 */
public abstract class NaturalDateFormat {
    public static final int DAYS = 1, MONTHS = 2, HOURS = 4, MINUTES = 8, YEARS = 16, SECONDS = 32, DATE = DAYS | MONTHS | YEARS, TIME = SECONDS | MINUTES | HOURS, WEEKDAY = 64;

    protected Context context;
    protected int format;
    protected TimeZone zone;
    protected Chronology chronology;

    public NaturalDateFormat(Context context, int format) {
        this.context = context;
        this.format = format;
    }

    public NaturalDateFormat(Context context, Chronology chronology, int format) {
        this.context = context;
        this.chronology = chronology;
        this.format = format;
    }

    public NaturalDateFormat(Context context, TimeZone zone, int format) {
        this.context = context;
        this.zone = zone;
        this.format = format;
    }

    protected boolean hasFormat(int f) {
        return (format & f) != 0;
    }

    public String format(long dateTime) {
        DateTime now;
        if (chronology != null) {
            now = DateTime.now(chronology);
        } else {
            now = DateTime.now(DateTimeZone.forTimeZone(zone));
        }
        DateTime then = new DateTime(dateTime);

        if ((format & DATE) != 0)
            return formatDate(now, then);
        return formatTime(now, then);
    }

    public String format(long nowDateTime, long dateTime) {
        DateTime now = new DateTime(nowDateTime);
        DateTime then = new DateTime(dateTime);
        if ((format & DATE) != 0)
            return formatDate(now, then);
        return formatTime(now, then);
    }

    protected abstract String formatTime(DateTime now, DateTime then);

    protected abstract String formatDate(DateTime now, DateTime then);

}
