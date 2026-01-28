package org.navigatebyfaith.rrreadings.test.startup;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;

import org.navigatebyfaith.rrreadings.R;
import org.navigatebyfaith.rrreadings.activity.ReadingsActivity;
import org.navigatebyfaith.rrreadings.test.readingsActivity.ReadingsActivityTestBase;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

public class ShowWhatsNewOnUpgradeTest extends ReadingsActivityTestBase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getInstrumentation ().getTargetContext());
        preferences.edit().clear().commit();
        getActivity(); //Start the activity
    }

    public void testStartupWithWhatsNew() {
        onView(withId(R.id.whatsNewLinearLayout)).check(matches(isDisplayed()));
    }

}