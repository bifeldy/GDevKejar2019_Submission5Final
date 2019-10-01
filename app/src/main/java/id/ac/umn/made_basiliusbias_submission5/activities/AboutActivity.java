package id.ac.umn.made_basiliusbias_submission5.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import id.ac.umn.made_basiliusbias_submission5.LangApp;
import id.ac.umn.made_basiliusbias_submission5.R;

public class AboutActivity extends LangApp {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Change Activity Page UI Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_activity_about);
        }

        // Find UI Object
        ImageView aboutImage = findViewById(R.id.about_image);
        TextView aboutLibrary = findViewById(R.id.about_library);

        // Set Application Picture Icon Logo
        Glide.with(this)
            .load(getResources().getString(R.string.animated_gif))
            .transition(DrawableTransitionOptions.withCrossFade(125))
            .into(aboutImage);

        // Change UI text
        aboutLibrary.setText(
            "android.arch.lifecycle:extensions:1.1.1\n" +
            "com.android.support:design:28.0.0\n" +
            "\n" +
            "com.android.support:recyclerview-v7:28.0.0\n" +
            "com.android.support:cardview-v7:28.0.0\n" +
            "com.android.support:preference-v7:28.0.0\n" +
            "\n" +
            "com.codesgood:justifiedtextview:1.1.0\n" +
            "com.android.volley:volley:1.1.1\n" +
            "com.github.bumptech.glide:glide:4.9.0\n"
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // For Open Intent
        Intent intent;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.action_about_linkedin:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/bifeldy/"));
                startActivity(intent);
                break;

            case R.id.action_about_twitter:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Bifeldy"));
                startActivity(intent);
                break;

            case R.id.action_about_facebook:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Bifeldy"));
                startActivity(intent);
                break;

            case R.id.action_about_github:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/bifeldy"));
                startActivity(intent);
                break;

            case R.id.action_about_line:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://line.me/ti/p/~Bifeldy"));
                startActivity(intent);
                break;

            default:
                // Back To Parent Activity
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}