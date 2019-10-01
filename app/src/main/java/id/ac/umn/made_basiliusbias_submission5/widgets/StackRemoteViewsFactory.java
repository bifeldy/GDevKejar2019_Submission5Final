package id.ac.umn.made_basiliusbias_submission5.widgets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.databases.FavoritesHelper;
import id.ac.umn.made_basiliusbias_submission5.pojos.Movie;
import id.ac.umn.made_basiliusbias_submission5.pojos.Tv;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static id.ac.umn.made_basiliusbias_submission5.databases.FavoritesHelper.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    // Shared Preferences
    private static final String PREFERENCES_FILENAME = "USER_INFORMATION";
    private static final int PREFERENCES_MODE = Context.MODE_PRIVATE;
    private static final String KEY_USERNAME = "USERNAME";

    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {}

    @Override
    public void onDataSetChanged() {

        final long identityToken = Binder.clearCallingIdentity();

        // Get Data Shared Preferences For Login
        SharedPreferences userInfo = mContext.getSharedPreferences(PREFERENCES_FILENAME, PREFERENCES_MODE);

        Uri uri1 = Uri.parse(CONTENT_URI + "/" + userInfo.getString(KEY_USERNAME, "") + "/movie");
        loadContentResolver(uri1);

        Uri uri2 = Uri.parse(CONTENT_URI + "/" + userInfo.getString(KEY_USERNAME, "") + "/tv");
        loadContentResolver(uri2);

        Binder.restoreCallingIdentity(identityToken);
    }

    private void loadContentResolver(Uri uri) {
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null, null);
        while(cursor != null && cursor.moveToNext()) {
            try {
                String url1 = cursor.getString(3);
                URL ulrn = new URL(url1);
                HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
                InputStream is = con.getInputStream();
                Bitmap bmp = BitmapFactory.decodeStream(is);
                if (null != bmp) mWidgetItems.add(bmp);
            }
            catch(Exception ignored) {}
        }
        if(cursor != null) cursor.close();
    }

    @Override
    public void onDestroy() {}

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));

        Bundle extras = new Bundle();
        extras.putInt(FavoriteMovieWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
