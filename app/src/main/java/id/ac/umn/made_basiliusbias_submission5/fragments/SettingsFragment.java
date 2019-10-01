package id.ac.umn.made_basiliusbias_submission5.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import id.ac.umn.made_basiliusbias_submission5.R;
import id.ac.umn.made_basiliusbias_submission5.Utility;
import id.ac.umn.made_basiliusbias_submission5.alarms.AlarmReceiver;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    // String Language Code
    private String language;

    // Alarm
    private AlarmReceiver alarmReceiver;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        // Set Preference To Show
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // Get Shared Preference
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();

        // Find UI
        ListPreference languageList = (ListPreference) findPreference(getResources().getString(R.string.pref_language_list_key));

        // Get Language Preferences
        language = sharedPreferences.getString(
                getResources().getString(R.string.pref_language_list_key),
                Utility.getSystemLocale()
        );

        // Get Language Label & Value
        String[] language_label = getResources().getStringArray(R.array.pref_language_label);
        String[] language_value = getResources().getStringArray(R.array.pref_language_value);

        // Set Language Entry & Summary
        for (int i = 0; i< language_value.length; i++) {
            if (language_value[i].equalsIgnoreCase(language)) {
                languageList.setSummary(language_label[i]);
                languageList.setValueIndex(i);
            }
        }

        alarmReceiver = new AlarmReceiver();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        // Get Preference
        Preference preference = findPreference(key);

        // Custom Language Switch
        if (key.equals(getResources().getString(R.string.pref_language_switch_key))) {

            // Switch OFF
            if(!((SwitchPreference) preference).isChecked()) {

                // Get Android System Language
                language = Utility.getSystemLocale();

                // Edit Shared Preference
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.pref_language_list_key), language);
                editor.apply();
            }
        }
        // Choose Language List
        else if (key.equals(getResources().getString(R.string.pref_language_list_key))) {

            // Update Summary Of List Language Summary Selected
            preference.setSummary(((ListPreference) preference).getEntry());
        }
        // Daily Reminder
        else if (key.equals(getResources().getString(R.string.pref_daily_reminder_key))) {

            // Switch ON
            if(((SwitchPreference) preference).isChecked()) {
                alarmReceiver.setAlarmDaily(
                    getContext(),
                    AlarmReceiver.TYPE_DAILY,
                    "07:00"
                );
            }
            // Switch OFF
            else {
                alarmReceiver.cancelAlarm(Objects.requireNonNull(getContext()), AlarmReceiver.TYPE_DAILY);
            }
        }
        // New Release Reminder
        else if (key.equals(getResources().getString(R.string.pref_movie_release_reminder_key))) {

            // Switch ON
            if(((SwitchPreference) preference).isChecked()) {
                alarmReceiver.setAlarmDaily(
                    getContext(),
                    AlarmReceiver.TYPE_RELEASE,
                    "08:00"
                );
            }
            // Switch OFF
            else {
                alarmReceiver.cancelAlarm(Objects.requireNonNull(getContext()), AlarmReceiver.TYPE_RELEASE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Register
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Unregister
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
