package id.ac.umn.made_basiliusbias_submission5.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import id.ac.umn.made_basiliusbias_submission5.fragments.SearchFragment;

public class SearchAdapter extends FragmentStatePagerAdapter {

    private int noTabItem;

    public SearchAdapter(FragmentManager fm, int noTabItem) {
        super(fm);
        this.noTabItem = noTabItem;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {

            case 0:
                return SearchFragment.newInstance("SearchMovies", "GridLayout");

            case 1:
                return SearchFragment.newInstance("SearchTV", "LinearLayout");

            case 2:
            case 3:
                return SearchFragment.newInstance("NoData", null);

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noTabItem;
    }
}