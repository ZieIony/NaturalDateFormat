package tk.zielony.naturaldateformat;

import android.content.Context;
import android.content.ContextWrapper;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;

import java.util.TimeZone;

/**
 * Created by Marcin on 2016-04-02.
 */
public class RelativeDateFormat extends NaturalDateFormat {
    public RelativeDateFormat(Context context, Chronology chronology, int format) {
        super(context, chronology, format);
    }

    public RelativeDateFormat(Context context, int format) {
        super(context, format);
    }

    public RelativeDateFormat(Context context, TimeZone zone, int format) {
        super(context, zone, format);
    }

    @Override
    protected String formatTime(DateTime now, DateTime then) {
        StringBuilder text = new StringBuilder();

        if (hasFormat(HOURS)) {
            formatHours(now, then, text);
        } else if (hasFormat(MINUTES)) {
            formatMinutes(now, then, text);
        } else if (hasFormat(SECONDS)) {
            formatSeconds(now, then, text);
        }

        return text.toString();
    }

    private void formatHours(DateTime now, DateTime then, StringBuilder text) {
        int hoursBetween = Hours.hoursBetween(now, then).getHours();
        if (hoursBetween == 0) {
            if (hasFormat(MINUTES)) {
                formatMinutes(now, then, text);
            } else {
                text.append(context.getString(R.string.now));
            }
        } else if (hoursBetween > 0) {    // in N hours
            text.append(context.getResources().getQuantityString(R.plurals.carbon_inHours, hoursBetween, hoursBetween));
        } else {    // N hours ago
            text.append(context.getResources().getQuantityString(R.plurals.carbon_hoursAgo, -hoursBetween, -hoursBetween));
        }
    }

    private void formatMinutes(DateTime now, DateTime then, StringBuilder text) {
        int minutesBetween = Minutes.minutesBetween(now, then).getMinutes();
        if (minutesBetween == 0) {
            if (hasFormat(SECONDS)) {
                formatSeconds(now, then, text);
            } else {
                text.append(context.getString(R.string.now));
            }
        } else if (minutesBetween > 0) {    // in N hours
            text.append(context.getResources().getQuantityString(R.plurals.carbon_inMinutes, minutesBetween, minutesBetween));
        } else {    // N hours ago
            text.append(context.getResources().getQuantityString(R.plurals.carbon_minutesAgo, -minutesBetween, -minutesBetween));
        }
    }

    private void formatSeconds(DateTime now, DateTime then, StringBuilder text) {
        int secondsBetween = Seconds.secondsBetween(now, then).getSeconds();
        if (secondsBetween == 0) {
            text.append(context.getString(R.string.now));
        } else if (secondsBetween > 0) {    // in N seconds
            text.append(context.getResources().getQuantityString(R.plurals.carbon_inSeconds, secondsBetween, secondsBetween));
        } else {    // N seconds ago
            text.append(context.getResources().getQuantityString(R.plurals.carbon_secondsAgo, -secondsBetween, -secondsBetween));
        }
    }

    @Override
    protected String formatDate(DateTime now, DateTime then) {
        StringBuffer text = new StringBuffer();

        if (hasFormat(YEARS)) {
            formatYears(now, then, text);
        } else if (hasFormat(MONTHS)) {
            formatMonths(now, then, text);
        } else if (hasFormat(DAYS)) {
            formatDays(now, then, text);
        }

        if (hasFormat(TIME)) {
            StringBuilder pattern = new StringBuilder();
            if ((format & HOURS) != 0)
                pattern.append("hh");
            if ((format & MINUTES) != 0)
                pattern.append(pattern.length() == 0 ? "mm" : ":mm");
            if ((format & SECONDS) != 0)
                pattern.append(pattern.length() == 0 ? "ss" : ":ss");
            text.append(", " + DateTimeFormat.forPattern(pattern.toString()).print(then.toInstant()));
        }

        return text.toString();
    }

    private void formatYears(DateTime now, DateTime then, StringBuffer text) {
        int yearsBetween = Years.yearsBetween(now, then).getYears();
        if (yearsBetween == 0) {
            if ((format & MONTHS) != 0) {
                formatMonths(now, then, text);
            } else {
                text.append(context.getString(R.string.thisYear));
            }
        } else if (yearsBetween > 0) {    // in N years
            text.append(context.getResources().getQuantityString(R.plurals.carbon_inYears, yearsBetween, yearsBetween));
        } else {    // N years ago
            text.append(context.getResources().getQuantityString(R.plurals.carbon_yearsAgo, -yearsBetween, -yearsBetween));
        }
    }

    private void formatMonths(DateTime now, DateTime then, StringBuffer text) {
        int monthsBetween = Months.monthsBetween(now, then).getMonths();
        if (monthsBetween == 0) {
            if ((format & DAYS) != 0) {
                formatDays(now, then, text);
            } else {
                text.append(context.getString(R.string.thisMonth));
            }
        } else if (monthsBetween > 0) {    // in N months
            text.append(context.getResources().getQuantityString(R.plurals.carbon_inMonths, monthsBetween, monthsBetween));
        } else {    // N months ago
            text.append(context.getResources().getQuantityString(R.plurals.carbon_monthsAgo, -monthsBetween, -monthsBetween));
        }
    }

    private void formatDays(DateTime now, DateTime then, StringBuffer text) {
        int daysBetween = Days.daysBetween(now, then).getDays();
        if (daysBetween == 0) {
            text.append(context.getString(R.string.today));
        } else if (daysBetween > 0) {    // in N days
            text.append(context.getResources().getQuantityString(R.plurals.carbon_inDays, daysBetween, daysBetween));
        } else {    // N days ago
            text.append(context.getResources().getQuantityString(R.plurals.carbon_daysAgo, -daysBetween, -daysBetween));
        }
    }
}
