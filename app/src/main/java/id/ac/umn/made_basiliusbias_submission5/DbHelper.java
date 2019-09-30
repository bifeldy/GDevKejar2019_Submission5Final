package id.ac.umn.made_basiliusbias_submission5;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import id.ac.umn.made_basiliusbias_submission5.pojos.Movie;
import id.ac.umn.made_basiliusbias_submission5.pojos.Tv;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "tm.db";

    @SuppressLint("SdCardPath")
    static String DB_LOCATION = "/data/data/id.ac.umn.made_basiliusbias_submission5/databases/";

    // Shared Preferences
    private static final String PREFERENCES_FILENAME = "USER_INFORMATION";
    private static final int PREFERENCES_MODE = Context.MODE_PRIVATE;
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_PASSWORD = "PASSWORD";

    private SharedPreferences userInfo;
    private SharedPreferences.Editor editor;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void openDatabase() {
        String dbPath = mContext.getDatabasePath(DB_NAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private void closeDatabase() {
        if(mDatabase != null) {
            mDatabase.close();
        }
    }

    public void addUser(String username, String password) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("user_name", username);
        contentValues.put("user_password", password);

        openDatabase();

        mDatabase.insert("users", "null", contentValues);

        closeDatabase();
    }

    public boolean userExist(String username) {

        // Table Name
        String table = "users";

        // SELECT COLUMN1, COLUMN2, COLUMN3, ...
        String[] columns = { "user_id", "user_name" };

        // WHERE fname="Lucida" AND lname="Console"
        String selection = "user_name=?"; // MISAL: "fname=? AND lname=?"
        String[] selectionArgs = { username }; // MISAL: {"Lucida", "Console"}

        openDatabase();

        Cursor cursor = mDatabase.query(
                table,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();

        cursor.close();
        closeDatabase();

        return count > 0;
    }

    public boolean login(String username, String password) {

        // Table Name
        String table = "users";

        // SELECT COLUMN1, COLUMN2, COLUMN3, ...
        String[] columns = { "user_id", "user_name", "user_password" };

        // WHERE fname="Lucida" AND lname="Console"
        String selection = "user_name=? AND user_password=?"; // MISAL: "fname=? AND lname=?"
        String[] selectionArgs = { username, password }; // MISAL: {"Lucida", "Console"}

        openDatabase();

        Cursor cursor = mDatabase.query(
                table,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();

        cursor.close();
        closeDatabase();

        if(count == 1) {

            // Get Data From Shared Preferences
            userInfo = mContext.getSharedPreferences(PREFERENCES_FILENAME, PREFERENCES_MODE);
            editor = userInfo.edit();

            editor.putString(KEY_USERNAME, username);
            editor.putString(KEY_PASSWORD, password);

            // editor.commit();
            editor.apply();

            return true;
        }
        return false;
    }

    public void logout() {

        // Get Data From Shared Preferences
        userInfo = mContext.getSharedPreferences(PREFERENCES_FILENAME, PREFERENCES_MODE);
        editor = userInfo.edit();

        editor.putString(KEY_USERNAME, "");
        editor.putString(KEY_PASSWORD, "");

        // editor.commit();
        editor.apply();
    }

    public boolean isFavorited(int data_id, String data_type, String user_name) {
        openDatabase();

        // Table Name
        String table = "favorites";

        // SELECT COLUMN1, COLUMN2, COLUMN3, ...
        String[] columns = {
                "data_id",
                "data_type",
                "data_title",
                "data_img",
                "data_overview",
                "data_score",
                "data_popularity",
                "user_name"
        };

        // WHERE fname="Lucida" AND lname="Console"
        String selection = "data_id=? AND data_type=? AND user_name=?"; // MISAL: "fname=? AND lname=?"
        String[] selectionArgs = { Integer.toString(data_id), data_type, user_name }; // MISAL: {"Lucida", "Console"}

        openDatabase();

        Cursor cursor = mDatabase.query(
                table,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();

        cursor.close();
        closeDatabase();

        return count == 1;
    }

    public void addFavorite(int data_id, String data_type, String data_title, String data_img, String data_overview, Double data_score, Double data_popularity, String user_name) {
        openDatabase();
        mDatabase.execSQL(
            "INSERT INTO favorites (`data_id`, `data_type`, `data_title`, `data_img`, `data_overview`, `data_score`, `data_popularity`, `user_name`) " +
            "VALUES (" + data_id + ", \"" + data_type + "\", \"" + data_title + "\", \"" + data_img + "\", \"" + data_overview.replaceAll("\"", "") + "\", " + data_score + ", " + data_popularity + ", \"" + user_name + "\")"
        );
        closeDatabase();
    }

    public void removeFavorite(int data_id, String data_type, String user_name) {
        openDatabase();
        mDatabase.execSQL("DELETE FROM favorites WHERE data_id = " + data_id + " AND data_type = \"" + data_type + "\" AND user_name = \"" + user_name + "\"");
        closeDatabase();
    }

    public List<?> getFavorites(String data_type, String user_name) {
        List<Movie> movies = new ArrayList<>();
        List<Tv> tvs = new ArrayList<>();

        // Table Name
        String table = "favorites";

        // SELECT COLUMN1, COLUMN2, COLUMN3, ...
        String[] columns = {
                "data_id",
                "data_type",
                "data_title",
                "data_img",
                "data_overview",
                "data_score",
                "data_popularity",
                "user_name"
        };

        // WHERE fname="Lucida" AND lname="Console"
        String selection = "data_type=? AND user_name=?"; // MISAL: "fname=? AND lname=?"
        String[] selectionArgs = { data_type, user_name }; // MISAL: {"Lucida", "Console"}

        openDatabase();

        Cursor cursor = mDatabase.query(
                table,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while(cursor.moveToNext()) {
            if(data_type.equalsIgnoreCase("Movie")) {
                movies.add(
                        new Movie(
                                cursor.getInt(0),
                                cursor.getString(3),
                                cursor.getString(2),
                                cursor.getInt(6),
                                cursor.getInt(5)
                        )
                );
            }
            else if(data_type.equalsIgnoreCase("TV")) {
                tvs.add(
                        new Tv(
                                cursor.getString(3),
                                cursor.getInt(0),
                                cursor.getDouble(5),
                                cursor.getDouble(6),
                                cursor.getString(2),
                                cursor.getString(4)
                        )
                );
            }
        }
        cursor.close();

        cursor.close();
        closeDatabase();

        if(data_type.equalsIgnoreCase("Movie")) return movies;
        else if(data_type.equalsIgnoreCase("TV")) return tvs;
        else return null;
    }
}
