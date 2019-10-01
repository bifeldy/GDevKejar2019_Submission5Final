package id.ac.umn.made_basiliusbias_submission5.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import id.ac.umn.made_basiliusbias_submission5.databases.FavoritesHelper;

import java.util.List;

import static id.ac.umn.made_basiliusbias_submission5.databases.FavoritesHelper.AUTHORITY;
import static id.ac.umn.made_basiliusbias_submission5.databases.FavoritesHelper.TABLE_FAVORITES;

public class FavoriteProvider extends ContentProvider {

    private static final int FAVORITE = 1;
    private static final int FAVORITE_USER = 2;
    private static final int FAVORITE_USER_TYPE = 3;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private FavoritesHelper favoritesHelper;

    static {
        // content://id.ac.umn.made_basiliusbias_submission5/favorites
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITES, FAVORITE);

        // content://id.ac.umn.made_basiliusbias_submission5/favorites/bifeldy
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITES + "/*", FAVORITE_USER);

        // content://id.ac.umn.made_basiliusbias_submission5/favorites/bifeldy/tv
        // content://id.ac.umn.made_basiliusbias_submission5/favorites/bifeldy/movie
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITES + "/*" + "/*", FAVORITE_USER_TYPE);
    }

    @Override
    public boolean onCreate() {
        favoritesHelper = FavoritesHelper.getInstance(getContext());
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,  String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case FAVORITE:
            case FAVORITE_USER:
                return null;
            case FAVORITE_USER_TYPE:
                // uri.getPathSegments().get(0) => favorites
                // uri.getPathSegments().get(1) => userId
                // uri.getPathSegments().get(2) => typeData
                return favoritesHelper.getUserFavoritesType(uri.getPathSegments().get(2), uri.getPathSegments().get(1));
            default:
                return null;
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri,  ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
