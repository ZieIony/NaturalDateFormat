package tk.zielony.naturaldateformat;

import android.content.Context;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.TimeZone;

/**
 * Created by Marcin on 2016-04-02.
 */
public class AbsoluteDateFormat extends NaturalDateFormat {
    protected DateTimeFormatter timeFormat;
    protected DateTimeFormatter dateFormat, weekdayFormat, yearFormat;
    DateTimeFieldType omitTime = DateTimeFieldType.monthOfYear(), omitWeekday = DateTimeFieldType.weekOfWeekyear();
    protected boolean twelveHour = false, abbreviated = false;

    public AbsoluteDateFormat(Context context, Chronology chronology, int format) {
        super(context, chronology, format);
    }

    public AbsoluteDateFormat(Context context, int format) {
        super(context, format);
    }

    public AbsoluteDateFormat(Context context, TimeZone zone, int format) {
        super(context, zone, format);
    }

    private void buildDateFormat() {
        if (!hasFormat(DATE))
            return;
        weekdayFormat = DateTimeFormat.forPattern(abbreviated ? "EEE" : "EEEEE");
        if (zone != null)
            weekdayFormat.withZone(DateTimeZone.forTimeZone(zone));
        if (chronology != null)
            weekdayFormat.withChronology(chronology);
        StringBuilder pattern = new StringBuilder();
        if ((format & DAYS) != 0)
            pattern.append("d");
        if ((format & MONTHS) != 0) {
            pattern.append(pattern.length() == 0 ? "" : " ");
            pattern.append(abbreviated ? "MMM" : "MMMMM");
        }
        dateFormat = DateTimeFormat.forPattern(pattern.toString());
        if (zone != null)
            dateFormat.withZone(DateTimeZone.forTimeZone(zone));
        if (chronology != null)
            dateFormat.withChronology(chronology);
        yearFormat = DateTimeFormat.forPattern(" YYYY");
        if (zone != null)
            yearFormat.withZone(DateTimeZone.forTimeZone(zone));
        if (chronology != null)
            yearFormat.withChronology(chronology);
    }

    private void buildTimeFormat() {
        if (!hasFormat(TIME))
            return;
        StringBuilder pattern = new StringBuilder();
        if ((format & HOURS) != 0)
            pattern.append(twelveHour ? "h" : "H");
        if ((format & MINUTES) != 0)
            pattern.append(pattern.length() == 0 ? "mm" : ":mm");
        if ((format & SECONDS) != 0)
            pattern.append(pattern.length() == 0 ? "ss" : ":ss");
        if (hasFormat(HOURS) && twelveHour)
            pattern.append(" a");
        timeFormat = DateTimeFormat.forPattern(pattern.toString());
    }

    public boolean isAbbreviated() {
        return abbreviated;
    }

    public void setAbbreviated(boolean abbreviated) {
        this.abbreviated = abbreviated;
        dateFormat = null;
    }

    public boolean isTwelveHour() {
        return twelveHour;
    }

    public void setTwelveHour(boolean twelveHour) {
        this.twelveHour = twelveHour;
        timeFormat = null;
    }

    public DateTimeFormatter getYearFormat() {
        return yearFormat;
    }

    public void setYearFormat(DateTimeFormatter yearFormat) {
        this.yearFormat = yearFormat;
    }

    public DateTimeFormatter getWeekdayFormat() {
        return weekdayFormat;
    }

    public void setWeekdayFormat(DateTimeFormatter weekdayFormat) {
        this.weekdayFormat = weekdayFormat;
    }

    public DateTimeFormatter getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateTimeFormatter dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DateTimeFieldType getOmitTime() {
        return omitTime;
    }

    public void setOmitTime(DateTimeFieldType omitTime) {
        this.omitTime = omitTime;
    }

    public DateTimeFieldType getOmitWeekday() {
        return omitWeekday;
    }

    public void setOmitWeekday(DateTimeFieldType omitWeekday) {
        this.omitWeekday = omitWeekday;
    }

    @Override
    protected String formatTime(DateTime now, DateTime then) {
        if (timeFormat == null)
            buildTimeFormat();

        return timeFormat.print(then);
    }

    @Override
    protected String formatDate(DateTime now, DateTime then) {
        if (dateFormat == null)
            buildDateFormat();

        StringBuilder builder = new StringBuilder();
        if (hasFormat(WEEKDAY) && now.get(omitWeekday) == then.get(omitWeekday))
            builder.append(weekdayFormat.print(then));
        if (hasFormat(DAYS | MONTHS)) {
            if (builder.length() > 0)
                builder.append(", ");
            builder.append(dateFormat.print(then));
        }
        if (hasFormat(YEARS) && Years.yearsBetween(now, then).getYears() != 0)
            builder.append(yearFormat.print(then));

        if (hasFormat(TIME) && now.get(omitTime) == then.get(omitTime)) {
            builder.append(", ");
            builder.append(formatTime(now, then));
        }

        return builder.toString();
    }
}
