package id.ac.umn.made_basiliusbias_submission5.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import id.ac.umn.made_basiliusbias_submission5.LangApp;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.Utility;
import id.ac.umn.made_basiliusbias_submission5.databases.UsersHelper;

public class RegisterActivity extends LangApp implements View.OnClickListener {

    // UI Object
    private TextView txtInfoRegisterScreen;
    private EditText txtUserRegisterScreen, txtPassRegisterScreen, txtRePassRegisterScreen;

    // Users Helper
    private UsersHelper usersHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Change Activity Page UI Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_activity_register);
        }

        // Find UI
        ImageView imgRegisterScreen = findViewById(R.id.imgRegisterScreen);
        TextView txtTitleRegisterScreen = findViewById(R.id.txtTitleRegisterScreen);
        txtInfoRegisterScreen = findViewById(R.id.txtInfoRegisterScreen);
        txtUserRegisterScreen = findViewById(R.id.register_txt_user_register);
        txtPassRegisterScreen = findViewById(R.id.register_txt_password_register);
        txtRePassRegisterScreen = findViewById(R.id.register_txt_user_register_re);
        Button btnLoginRegisterScreen = findViewById(R.id.register_btn_login);
        Button btnRegisterRegisterScreen = findViewById(R.id.register_btn_register);
        LinearLayout rootViewRegisterScreen = findViewById(R.id.rootViewRegisterScreen);

        // Click Listener
        btnLoginRegisterScreen.setOnClickListener(this);
        btnRegisterRegisterScreen.setOnClickListener(this);

        // Keyboard Listener
        Utility.keyboardListener(rootViewRegisterScreen, imgRegisterScreen, txtTitleRegisterScreen);

        // Show Loading Animation
        Glide.with(this)
                .load(getResources().getString(R.string.animated_loading_data))
                .transition(DrawableTransitionOptions.withCrossFade(125))
                .override(256, 256)
                .into(imgRegisterScreen)
        ;

        // Initialize Database
        usersHelper = new UsersHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_default_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // For Open Intent
        Intent intent;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.default_activity_menu_about) {

            // Go To About Activity
            intent = new Intent(RegisterActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        else {
            // Back To Parent Activity
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            // Button Login
            case R.id.register_btn_login:

                // Go Back To Login Activity
                finish();
                break;

            // Button Register
            case R.id.register_btn_register:
                RegisterNewAccount(
                        txtUserRegisterScreen.getText().toString().trim().toLowerCase(),
                        txtPassRegisterScreen.getText().toString().trim().toLowerCase(),
                        txtRePassRegisterScreen.getText().toString().trim().toLowerCase()
                );
                break;
        }
    }

    private void RegisterNewAccount(String username, String password, String rePassword) {

        // Get Login Info
        String information;

        // Form Validation
        if(username.equals("") || password.equals("") || rePassword.equals("")) {
            information = getResources().getString(R.string.infoRegis1);
        }
        else {

            // Check User If Exist Try Login
            if(usersHelper.userExist(username)) {
                information = getResources().getString(R.string.infoRegis2);
            }
            else if (!password.equals(rePassword)) {
                information = getResources().getString(R.string.infoRegis3);
            }
            else {
                usersHelper.addUser(username, password);
                information = getResources().getString(R.string.infoRegis4) + username + " ^_^.~";
                Toast.makeText(RegisterActivity.this, information, Toast.LENGTH_LONG).show();
                finish();
            }
        }
        txtInfoRegisterScreen.setText(information);
    }
}
