package id.ac.umn.made_bbac_submission5;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utility {

    public static int calcNumOfCols(Context context, float colWidthDp) {

        // For example column Width dp = 180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;

        // +0.5 for correct rounding to int.
        return (int) (screenWidthDp / colWidthDp + 0.5);
    }
}