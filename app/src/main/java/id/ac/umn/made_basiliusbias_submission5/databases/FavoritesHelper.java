package id.ac.umn.made_basiliusbias_submission5.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class FavoritesHelper {

    private DatabaseHelper dataBaseHelper;
    public static final String TABLE_FAVORITES = "favorites";
    private static FavoritesHelper INSTANCE;

    // SELECT COLUMN1, COLUMN2, COLUMN3, ...
    private String[] TABLE_COLUMN = {
            "data_id",
            "data_type",
            "data_title",
            "data_img",
            "data_overview",
            "data_score",
            "data_popularity",
            "user_name"
    };

    public static final String AUTHORITY = "id.ac.umn.made_basiliusbias_submission5";
    private static final String SCHEME = "content";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(TABLE_FAVORITES).build();

    public FavoritesHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoritesHelper getInstance(Context context) {
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null) {
                    INSTANCE = new FavoritesHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public boolean isUserFavorited(int data_id, String data_type, String user_name) {

        // WHERE fname="Lucida" AND lname="Console"
        String selection = "data_id=? AND data_type=? AND user_name=?"; // MISAL: "fname=? AND lname=?"
        String[] selectionArgs = { Integer.toString(data_id), data_type, user_name }; // MISAL: {"Lucida", "Console"}

        dataBaseHelper.openDatabase();

        Cursor cursor = dataBaseHelper.querySelect(
                TABLE_FAVORITES,
                TABLE_COLUMN,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();

        cursor.close();
        dataBaseHelper.closeDatabase();

        return count == 1;
    }

    public void addUserFavorite(int data_id, String data_type, String data_title, String data_img, String data_overview, Double data_score, Double data_popularity, String user_name) {
        dataBaseHelper.openDatabase();
        dataBaseHelper.queryExecSQL(
                "INSERT INTO favorites (`data_id`, `data_type`, `data_title`, `data_img`, `data_overview`, `data_score`, `data_popularity`, `user_name`) " +
                        "VALUES (" + data_id + ", \"" + data_type + "\", \"" + data_title + "\", \"" + data_img + "\", \"" + data_overview.replaceAll("\"", "") + "\", " + data_score + ", " + data_popularity + ", \"" + user_name + "\")"
        );
        dataBaseHelper.closeDatabase();
    }

    public void removeUserFavorite(int data_id, String data_type, String user_name) {
        dataBaseHelper.openDatabase();
        dataBaseHelper.queryExecSQL("DELETE FROM favorites WHERE data_id = " + data_id + " AND data_type = \"" + data_type + "\" AND user_name = \"" + user_name + "\"");
        dataBaseHelper.closeDatabase();
    }

    public Cursor getUserFavoritesType(String data_type, String user_name) {

        // WHERE fname="Lucida" AND lname="Console"
        String selection = "data_type=? AND user_name=?"; // MISAL: "fname=? AND lname=?"
        String[] selectionArgs = { data_type, user_name }; // MISAL: {"Lucida", "Console"}

        dataBaseHelper.openDatabase();

        return dataBaseHelper.querySelect(
                TABLE_FAVORITES,
                TABLE_COLUMN,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        /*
        *   Entah Harus Di Close Ato Engga ..
        * */
    }

}
