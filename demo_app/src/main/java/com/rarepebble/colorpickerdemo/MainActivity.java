package com.rarepebble.colorpickerdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.rarepebble.colorpicker.ColorPreferenceFragmentDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new DemoPreferenceFragment())
                    .commit();
        }
    }

    static public class DemoPreferenceFragment extends PreferenceFragmentCompat implements PreferenceFragmentCompat.OnPreferenceDisplayDialogCallback {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

        }

        @Override
        public void onCreatePreferences(final Bundle bundle, final String s) {

        }

        @Override
        public Fragment getCallbackFragment() {
            return this;
        }

        @Override
        public boolean onPreferenceDisplayDialog(final PreferenceFragmentCompat preferenceFragmentCompat, final Preference preference) {
            ColorPreferenceFragmentDialog colorPreferenceFragmentDialog = ColorPreferenceFragmentDialog.newInstance(preference.getKey());
            colorPreferenceFragmentDialog.setTargetFragment(this, 0);
            colorPreferenceFragmentDialog.show(this.getFragmentManager(), "android.support.v7.preference.PreferenceFragment.DIALOG");
            return true;
        }
    }
}
