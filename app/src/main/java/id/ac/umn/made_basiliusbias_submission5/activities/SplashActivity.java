package id.ac.umn.made_basiliusbias_submission5.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import id.ac.umn.made_basiliusbias_submission5.DbHelper;
import id.ac.umn.made_basiliusbias_submission5.LangApp;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.Utility;

public class SplashActivity extends LangApp implements View.OnClickListener {

    // Shared Preferences
    private static final String PREFERENCES_FILENAME = "USER_INFORMATION";
    private static final int PREFERENCES_MODE = Context.MODE_PRIVATE;
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_PASSWORD = "PASSWORD";
    private String userNameInfo, userPassInfo;

    // UI Object
    private TextView splash_login_info;
    private EditText splash_txt_user, splash_txt_password;
    private Button splash_btn_login, splash_btn_register, splash_btn_about;

    // Database Helper
    private DbHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Find UI Elements
        ImageView splash_picture = findViewById(R.id.splash_picture);
        TextView splash_text = findViewById(R.id.splash_text);
        splash_login_info = findViewById(R.id.splash_login_info);
        splash_txt_user = findViewById(R.id.splash_txt_user);
        splash_txt_password = findViewById(R.id.splash_txt_password);
        splash_btn_login = findViewById(R.id.splash_btn_login);
        splash_btn_register = findViewById(R.id.splash_btn_register);
        splash_btn_about = findViewById(R.id.splash_btn_about);
        LinearLayout rootViewSplashScreen = findViewById(R.id.rootViewSplashScreen);

        // Click Listener
        splash_btn_login.setOnClickListener(this);
        splash_btn_register.setOnClickListener(this);
        splash_btn_about.setOnClickListener(this);

        // Default
        splash_login_info.setVisibility(View.GONE);
        splash_txt_user.setVisibility(View.GONE);
        splash_txt_password.setVisibility(View.GONE);
        splash_btn_login.setVisibility(View.GONE);
        splash_btn_register.setVisibility(View.GONE);
        splash_btn_about.setVisibility(View.GONE);

        // Import Database
        mDBHelper = new DbHelper(this);
        Utility.importDatabase(this, mDBHelper);

        // Keyboard Listener
        Utility.keyboardListener(rootViewSplashScreen, splash_picture, splash_text);

        // Load Custom Animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Run Animate
        splash_picture.startAnimation(animation);
        splash_text.startAnimation(animation);

        // Get Data Shared Preferences For Login
        SharedPreferences userInfo = getSharedPreferences(PREFERENCES_FILENAME, PREFERENCES_MODE);
        userNameInfo = userInfo.getString(KEY_USERNAME, "");
        userPassInfo = userInfo.getString(KEY_PASSWORD, "");

        // Put It Into Form
        splash_txt_user.setText(userNameInfo);
        splash_txt_password.setText(userPassInfo);

        // Multi-Thread For Delay Animating Splash Screen
        new Thread(() -> {
            try {

                // Delay 1.234 Second Showing Logo
                Thread.sleep(1234);

                // Check Login Status
                if(!isLogin()) {

                    // Show Info Text
                    splash_login_info.post(() -> {

                        // Show TextView
                        splash_login_info.startAnimation(animation);
                        splash_login_info.setVisibility(View.VISIBLE);
                    });

                    // Show TextBox Username
                    splash_txt_user.post(() -> {

                        // Show TextBox
                        splash_txt_user.startAnimation(animation);
                        splash_txt_user.setVisibility(View.VISIBLE);
                    });

                    // Show TextBox Password
                    splash_txt_password.post(() -> {

                        // Show TextBox
                        splash_txt_password.startAnimation(animation);
                        splash_txt_password.setVisibility(View.VISIBLE);
                    });


                    // Show Button Login
                    splash_btn_login.post(() -> {

                        // Show Button
                        splash_btn_login.startAnimation(animation);
                        splash_btn_login.setVisibility(View.VISIBLE);
                    });

                    // Show Button Register
                    splash_btn_register.post(() -> {

                        // Show Button
                        splash_btn_register.startAnimation(animation);
                        splash_btn_register.setVisibility(View.VISIBLE);
                    });

                    // Show Button About
                    splash_btn_about.post(() -> {

                        // Show Button
                        splash_btn_about.startAnimation(animation);
                        splash_btn_about.setVisibility(View.VISIBLE);
                    });
                }
                else {
                    loginSuccess();
                }

            }
            catch(InterruptedException e) {

                // Print Error
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onClick(View v) {

        // For Open New Activity
        Intent intent;
        switch (v.getId()) {

            // Button About
            case R.id.splash_btn_about:

                // Go To About Activity
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;

            // Button Login
            case R.id.splash_btn_login:
                loginAccount(
                    splash_txt_user.getText().toString().trim().toLowerCase(),
                    splash_txt_password.getText().toString().trim().toLowerCase()
                );
                break;

            // Button Register
            case R.id.splash_btn_register:

                // Go To Register Activity
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean isLogin() {
        return !userNameInfo.equals("") && !userPassInfo.equals("");
    }

    private void loginAccount(String username, String password) {

        // Get Login Info
        String information;

        // Form Validation
        if(username.equals("") || password.equals("")) {
            information = getResources().getString(R.string.infoSplash1);
        }
        else {

            // Check User If Exist Try Login
            if (mDBHelper.userExist(username)) {
                if(mDBHelper.login(username, password)) {
                    information = getResources().getString(R.string.infoSplash2) + username + "! ^_^.~";
                    Toast.makeText(SplashActivity.this, information, Toast.LENGTH_LONG).show();
                    loginSuccess();
                }
                else {
                    information = getResources().getString(R.string.infoSplash3);
                }
            }
            else {
                information = getResources().getString(R.string.infoSplash4);
            }
        }
        splash_login_info.setText(information);
    }

    private void loginSuccess() {

        // Go To Main Apps
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}