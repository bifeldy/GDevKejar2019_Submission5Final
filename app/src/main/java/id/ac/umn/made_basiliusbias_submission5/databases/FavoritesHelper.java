package id.ac.umn.made_basiliusbias_submission5.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import id.ac.umn.made_basiliusbias_submission5.pojos.Movie;
import id.ac.umn.made_basiliusbias_submission5.pojos.Tv;

import java.util.ArrayList;
import java.util.List;

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

    public DatabaseHelper getDataBaseHelper() {
        return dataBaseHelper;
    }

    public boolean isUserFavorited(int data_id, String data_type, String user_name) {
        dataBaseHelper.openDatabase();

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

    public List<?> getUserFavoritesListType(String data_type, String user_name) {
        List<Movie> movies = new ArrayList<>();
        List<Tv> tvs = new ArrayList<>();

        // WHERE fname="Lucida" AND lname="Console"
        String selection = "data_type=? AND user_name=?"; // MISAL: "fname=? AND lname=?"
        String[] selectionArgs = { data_type, user_name }; // MISAL: {"Lucida", "Console"}

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
        dataBaseHelper.closeDatabase();

        if(data_type.equalsIgnoreCase("Movie")) return movies;
        else if(data_type.equalsIgnoreCase("TV")) return tvs;
        else return null;
    }

    public Cursor getUserFavoritesType(String data_type, String user_name) {

        dataBaseHelper.openDatabase();

        // WHERE fname="Lucida" AND lname="Console"
        String selection = "data_type=? AND user_name=?"; // MISAL: "fname=? AND lname=?"
        String[] selectionArgs = { data_type, user_name }; // MISAL: {"Lucida", "Console"}

        return dataBaseHelper.querySelect(
                TABLE_FAVORITES,
                TABLE_COLUMN,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

}
