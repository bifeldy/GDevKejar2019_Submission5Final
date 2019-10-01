package id.ac.umn.made_basiliusbias_submission5;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.os.ConfigurationCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import id.ac.umn.made_basiliusbias_submission5.apis.DiscoverSearchAPI;
import id.ac.umn.made_basiliusbias_submission5.databases.DatabaseHelper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.List;
import java.util.Locale;

public class Utility {

    public static int calcNumOfCols(Context context, float colWidthDp) {

        // For example column Width dp = 180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;

        // +0.5 for correct rounding to int.
        return (int) (screenWidthDp / colWidthDp + 0.5);
    }

    // https://stackoverflow.com/questions/39705739/android-n-change-language-programmatically
    static ContextWrapper changeLanguage(Context context, String lang_code) {

        Resources rs = context.getResources();
        Configuration config = rs.getConfiguration();
        Locale locale = new Locale(lang_code);

        Locale.setDefault(locale);
        config.setLocale(locale);

        return new ContextWrapper(context.createConfigurationContext(config));
    }

    public static String getSystemLocale() {
        return ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).toLanguageTags();
    }

    public static String getAppLanguage(Context context) {

        // Get Language Shared Preference
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(
                context.getResources().getString(R.string.pref_language_list_key),
                Utility.getSystemLocale()
        );
    }

    private static boolean copyDatabase(Context context) {

        try {

            InputStream inputStream = context.getAssets().open(DatabaseHelper.DB_NAME);
            String outFileName = DatabaseHelper.DB_LOCATION + DatabaseHelper.DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length;

            while((length = inputStream.read(buff)) > 0) {

                outputStream.write(buff, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            return true;
        }
        catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public static void importDatabase(Context context, DatabaseHelper mDBHelper) {

        File database = context.getDatabasePath(DatabaseHelper.DB_NAME);
        if(!database.exists()) {
            mDBHelper.getReadableDatabase();

            // Copy DB
            if(!copyDatabase(context)) {
                Toast.makeText(context, "Gagal Membuka Database", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void keyboardListener(final LinearLayout rootView, final ImageView logo, final TextView title) {

        // Check If Keyboard Is Opened Or Not
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(
                () -> {

                    // Rectangle
                    Rect r = new Rect();
                    rootView.getWindowVisibleDisplayFrame(r);
                    int screenHeight = rootView.getRootView().getHeight();

                    // r.bottom is the position above soft keypad or device button.
                    // If keypad is shown, the r.bottom is smaller than that before.
                    int keypadHeight = screenHeight - r.bottom;

                    // 0.15 ratio is perhaps enough to determine keypad height.
                    if (keypadHeight > screenHeight * 0.15) {

                        // Keyboard Opened
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                Gravity.NO_GRAVITY

                        );
                        params.setMargins(0, 144, 0, 0);
                        rootView.setLayoutParams(params);

                        // Hide Logo & Title
                        logo.setVisibility(View.GONE);
                        title.setVisibility(View.GONE);
                    }
                    else {

                        // Keyboard Closed
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                Gravity.CENTER
                        );
                        params.setMargins(0, 0, 0, 0);
                        rootView.setLayoutParams(params);

                        // Show Logo & Title
                        logo.setVisibility(View.VISIBLE);
                        title.setVisibility(View.VISIBLE);
                    }
                }
        );
    }

    public static void InitializeRecyclerGridList(View v, RecyclerView recyclerView, int mNoOfColumns, String recyclerType) {
        if(recyclerType != null && !recyclerType.equals("") && recyclerType.equals("GridLayout")) {
            recyclerView.setLayoutManager(new GridLayoutManager(v.getContext(), mNoOfColumns));
        }
        else if(recyclerType != null && !recyclerType.equals("") && recyclerType.equals("LinearLayout")) {
            recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        }
    }

    public static CacheReq discoverSearch(String urlApi, DiscoverSearchAPI dsAPI, View v, ImageView loadingImage, TextView loadingText, MutableLiveData<List<?>> list, String Jenis) {
        return new CacheReq(0, urlApi,
                response -> {
                    try {

                        // Create Object For DiscoverMovie Response
                        final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                        JSONObject jsonObject = new JSONObject(jsonString);

                        if(Jenis.equalsIgnoreCase("Movies")) dsAPI.createDiscoverMovie(jsonObject);
                        else if(Jenis.equalsIgnoreCase("Tvs")) dsAPI.createDiscoverTV(jsonObject);

                        // Hide Loading Animation
                        loadingImage.setVisibility(View.GONE);
                        loadingText.setVisibility(View.GONE);

                        // Add Data & Show
                        if(Jenis.equalsIgnoreCase("Movies")) list.postValue(dsAPI.getDiscoverMovie().getResults());
                        else if(Jenis.equalsIgnoreCase("Tvs")) list.postValue(dsAPI.getDiscoverTV().getResults());
                        v.findViewById(R.id.recycler_fragment).setVisibility(View.VISIBLE);
                    }
                    catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                    // Show Error Animation
                    Glide.with(v.getContext())
                            .load(R.drawable.maido3)
                            .override(256, 256)
                            .transition(DrawableTransitionOptions.withCrossFade(125))
                            .into(loadingImage)
                    ;

                    // Error Messages
                    loadingText.setText(error.toString());
                    loadingText.setTextColor(Color.RED);
                });
    }
}