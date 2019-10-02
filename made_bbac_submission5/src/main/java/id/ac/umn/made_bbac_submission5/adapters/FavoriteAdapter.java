package id.ac.umn.made_bbac_submission5.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import id.ac.umn.made_bbac_submission5.fragments.FavoriteFragment;

public class FavoriteAdapter extends FragmentStatePagerAdapter {

    private int noTabItem;

    public FavoriteAdapter(FragmentManager fm, int noTabItem) {
        super(fm);
        this.noTabItem = noTabItem;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return FavoriteFragment.newInstance("FavoriteMovies", "GridLayout");
            case 1:
                return FavoriteFragment.newInstance("FavoriteTV", "LinearLayout");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noTabItem;
    }
}