package id.ac.umn.made_bbac_submission5.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import id.ac.umn.made_bbac_submission5.R;
import id.ac.umn.made_bbac_submission5.adapters.FavoriteAdapter;
import id.ac.umn.made_bbac_submission5.fragments.FavoriteFragment;


public class FavoriteActivity extends AppCompatActivity implements FavoriteFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adding Item To Tab
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Favorite Movies"));
        tabLayout.addTab(tabLayout.newTab().setText("Favorite TV Shows"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.view_pager);
        final FavoriteAdapter tabAdapter = new FavoriteAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}
}

