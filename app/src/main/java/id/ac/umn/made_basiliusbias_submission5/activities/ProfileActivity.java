package id.ac.umn.made_basiliusbias_submission5.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import id.ac.umn.made_basiliusbias_submission5.LangApp;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.adapters.ProfileAdapter;
import id.ac.umn.made_basiliusbias_submission5.fragments.ProfileFragment;

public class ProfileActivity extends LangApp implements ProfileFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Default User Account
        // int userId = 0;
        String userName = getResources().getString(R.string.nav_user_name);

        // Get Passed Data From Main Fragment
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            // userId = bundle.getInt("userId");
            userName = bundle.getString("userName");
        }

        // Change Activity Page UI Toolbar
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Change Toolbar Title
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(userName);

        // Adding Item To Tab
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("About Me!"));
        tabLayout.addTab(tabLayout.newTab().setText("Favorite Movies"));
        tabLayout.addTab(tabLayout.newTab().setText("Favorite TV Shows"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.view_pager);
        final ProfileAdapter tabAdapter = new ProfileAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

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
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // For Open Intent
        Intent intent;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.action_profile_view_online) {

            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.Bifeldy.tk/"));
            startActivity(intent);
        }

        // Back To Parent Activity
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}
}
