package my.study.planner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment(this))
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        final SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreference.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals("password")){
                    if(sharedPreferences.getBoolean("password", false)) {
                        Intent i = new Intent(SettingsActivity.this, PasswordSettingActivity.class);
                        startActivity(i);
                    }
                }
            }

        });

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if(newValue.equals(true)) {
            Intent i = new Intent(SettingsActivity.this, PasswordSettingActivity.class);
            startActivity(i);
        }
        return true;
    }
    public static class SettingsFragment extends PreferenceFragmentCompat {
        Context context;

        public SettingsFragment(Context context) {
            this.context = context;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            Preference preference = findPreference("changePassword");
            preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent i = new Intent(context, ChangePasswordActivity.class);
                    startActivity(i);
                    return true;
                }
            });
        }

    }
}