package tk.zielony.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import tk.zielony.naturaldateformat.AbsoluteDateFormat;
import tk.zielony.naturaldateformat.NaturalDateFormat;
import tk.zielony.naturaldateformat.RelativeDateFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.text);
        Date now = new Date();
        Random random = new Random();
        long year2 = 1000L * 60 * 60 * 24 * 365 * 2;
        long hour3 = 1000L * 60 * 60 * 3;
        long month = 1000L * 60 * 60 * 24 * 30;
        StringBuffer buffer = new StringBuffer();
        DateFormat format = SimpleDateFormat.getDateTimeInstance();
        long[] mod = new long[]{
                year2,
                hour3,
                year2,
                month
        };
        NaturalDateFormat[] formats = new NaturalDateFormat[]{
                new RelativeDateFormat(this, NaturalDateFormat.DATE),
                new RelativeDateFormat(this, NaturalDateFormat.TIME),
                new AbsoluteDateFormat(this, NaturalDateFormat.WEEKDAY | NaturalDateFormat.DATE),
                new AbsoluteDateFormat(this, NaturalDateFormat.DATE | NaturalDateFormat.HOURS | NaturalDateFormat.MINUTES)
        };

        ((AbsoluteDateFormat)formats[2]).setAbbreviated(true);
        ((AbsoluteDateFormat)formats[3]).setTwelveHour(true);

        for (int j = 0; j < formats.length; j++) {
            for (int i = 0; i < 5; i++) {
                Date date = new Date(now.getTime() + random.nextLong() % mod[j]);
                buffer.append(format.format(date)).
                        append("  -  ").
                        append(formats[j].format(date.getTime())).
                        append("\n");
            }
            buffer.append("\n");
        }
        tv.setText(buffer.toString());
    }
}
