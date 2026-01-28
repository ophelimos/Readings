package org.navigatebyfaith.rrreadings.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.preference.PreferenceManager;

import org.navigatebyfaith.rrreadings.day.DayFragment;
import org.navigatebyfaith.rrreadings.model.Prefs;
import org.navigatebyfaith.rrreadings.util.Analytics;

public class Utils {
    public static void setSummariesEnabled(Context context, boolean enabled) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Prefs.PREF_SHOW_SUMMARY, enabled);
        editor.commit();
    }
}
