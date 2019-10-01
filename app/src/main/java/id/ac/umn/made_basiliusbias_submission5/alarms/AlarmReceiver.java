package id.ac.umn.made_basiliusbias_submission5.alarms;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import id.ac.umn.made_basiliusbias_submission5.CacheReq;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.Utility;
import id.ac.umn.made_basiliusbias_submission5.apis.DiscoverSearchAPI;
import id.ac.umn.made_basiliusbias_submission5.pojos.Movie;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String EXTRA_TYPE = "type";

    public static final String TYPE_DAILY = "Daily";
    private final int ID_DAILY = 1000;

    public static final String TYPE_RELEASE = "Release";
    private final int ID_RELEASE = 1001;

    public AlarmReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {

        // Get Data
        String type = intent.getStringExtra(EXTRA_TYPE);

        /*
        *   Kadang Suka Telat Dah ..
        *   Di Prepare Jam 07:00 Malah Munculnya Pas 07:01 Lebih 20-30 Detikan
        *   Hilih .. Mungkin CPU Androidnya Lelet .. Wkwk
        */

        if(type.equalsIgnoreCase(TYPE_DAILY)) showSmallNotif(context, "Catalog Movie", "I Miss You!", 0);
        else {
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String urlApi = context.getResources().getString(R.string.tmdb_api) +
                    "discover/movie?api_key=" +
                    context.getResources().getString(R.string.tmdb_key) +
                    "&primary_release_date.gte=" +
                    currentDate +
                    "&primary_release_date.lte=" +
                    currentDate +
                    "&language=" +
                    Utility.getAppLanguage(context) +
                    "&page=1"
            ;

            DiscoverSearchAPI todayRelease = new DiscoverSearchAPI();
            todayRelease.setContext(context);

            // Fetching Data
            CacheReq cacheReq = new CacheReq(0, urlApi,
                response -> {
                    try {

                        // Create Object For DiscoverMovie Response
                        final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        JSONObject jsonObject = new JSONObject(jsonString);

                        todayRelease.createDiscoverMovie(jsonObject);

                        // Add Data & Show
                        List<Movie> movies = todayRelease.getDiscoverMovie().getResults();

                        for (Movie m : movies) {
                            showBigNotif(context, m);
                        }
                    }
                    catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {}
            );

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(cacheReq);
        }
        Toast.makeText(context, type, Toast.LENGTH_LONG).show();
    }

    private Calendar setCalendar(String[] timeArray) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public void setAlarmDaily(Context context, String type, String time) {
        String TIME_FORMAT = "HH:mm";
        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);

        Calendar calendar = setCalendar(time.split(":"));

        PendingIntent pendingIntent;
        if(type.equalsIgnoreCase(TYPE_DAILY)) {
            pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0);
        }
        else if(type.equalsIgnoreCase(TYPE_RELEASE)) {
            pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0);
        }
        else return;

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        }
        catch (ParseException e) {
            return true;
        }
    }

    private void showSmallNotif(Context context, String title, String message, int notifId) {

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Integer.toString(notifId))
            .setSmallIcon(R.drawable.maido)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000})
            .setSound(alarmSound)
        ;

        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }

    private void showBigNotif(Context context, Movie movie) {

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        String message = "#" + movie.getId() + " ~ " + movie.getPopularity() + " :: " + movie.getVote_count() + " Ppl.";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Integer.toString(movie.getId()))
            .setSmallIcon(R.drawable.maido)
            .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(movie.getOverview())
                .setBigContentTitle(movie.getTitle())
                .setSummaryText(message)
            )
            .setContentTitle(movie.getTitle())
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000})
            .setSound(alarmSound)
        ;

        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(movie.getId(), notification);
        }
    }

    public void cancelAlarm(Context context, String type) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);

        int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY : ID_RELEASE;

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
