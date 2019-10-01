package id.ac.umn.made_basiliusbias_submission5.databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "tm.db";

    @SuppressLint("SdCardPath")
    public static String DB_LOCATION = "/data/data/id.ac.umn.made_basiliusbias_submission5/databases/";

    private Context mContext;
    private SQLiteDatabase mDatabase;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    void openDatabase() {
        String dbPath = mContext.getDatabasePath(DB_NAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    void closeDatabase() {
        if(mDatabase != null) {
            mDatabase.close();
        }
    }

    void queryInsert(String table, String nullColumnHack, ContentValues contentValues) {
        mDatabase.insert("users", "null", contentValues);
    }

    Cursor querySelect(String table, String[] column, String selection, String[] selectionArgs, String groupBy, String having, String order) {
        return mDatabase.query(
                table,
                column,
                selection,
                selectionArgs,
                groupBy,
                having,
                order
        );
    }

    void queryExecSQL(String sqlQuery) {
        mDatabase.execSQL(sqlQuery);
    }
}
