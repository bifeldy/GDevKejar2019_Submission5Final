package id.ac.umn.made_basiliusbias_submission5.databases;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

public class UsersHelper {

    // Shared Preferences
    private static final String PREFERENCES_FILENAME = "USER_INFORMATION";
    private static final int PREFERENCES_MODE = Context.MODE_PRIVATE;
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_PASSWORD = "PASSWORD";

    private SharedPreferences userInfo;
    private SharedPreferences.Editor editor;

    private static final String TABLE_USERS = "users";

    // SELECT COLUMN1, COLUMN2, COLUMN3, ...
    private String[] TABLE_COLUMN = { "user_id", "user_name", "user_password" };

    private Context context;
    private DatabaseHelper dataBaseHelper;

    public UsersHelper(Context context) {
        this.context = context;
        dataBaseHelper = new DatabaseHelper(context);
    }

    public DatabaseHelper getDataBaseHelper() {
        return dataBaseHelper;
    }

    public void addUser(String username, String password) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("user_name", username);
        contentValues.put("user_password", password);

        dataBaseHelper.openDatabase();

        dataBaseHelper.queryInsert("users", "null", contentValues);

        dataBaseHelper.closeDatabase();
    }

    public boolean userExist(String username) {

        // WHERE fname="Lucida" AND lname="Console"
        String selection = "user_name=?"; // MISAL: "fname=? AND lname=?"
        String[] selectionArgs = { username }; // MISAL: {"Lucida", "Console"}

        dataBaseHelper.openDatabase();

        Cursor cursor = dataBaseHelper.querySelect(
                TABLE_USERS,
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

        return count > 0;
    }

    public boolean login(String username, String password) {

        // WHERE fname="Lucida" AND lname="Console"
        String selection = "user_name=? AND user_password=?"; // MISAL: "fname=? AND lname=?"
        String[] selectionArgs = { username, password }; // MISAL: {"Lucida", "Console"}

        dataBaseHelper.openDatabase();

        Cursor cursor = dataBaseHelper.querySelect(
                TABLE_USERS,
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

        if(count == 1) {

            // Get Data From Shared Preferences
            userInfo = context.getSharedPreferences(PREFERENCES_FILENAME, PREFERENCES_MODE);
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
        userInfo = context.getSharedPreferences(PREFERENCES_FILENAME, PREFERENCES_MODE);
        editor = userInfo.edit();

        editor.putString(KEY_USERNAME, "");
        editor.putString(KEY_PASSWORD, "");

        // editor.commit();
        editor.apply();
    }

}
