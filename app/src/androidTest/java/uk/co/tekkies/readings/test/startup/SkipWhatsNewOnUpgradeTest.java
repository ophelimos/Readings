package org.navigatebyfaith.rrreadings.test.startup;

import android.test.ActivityInstrumentationTestCase2;

import org.navigatebyfaith.rrreadings.R;
import org.navigatebyfaith.rrreadings.activity.ReadingsActivity;
import org.navigatebyfaith.rrreadings.test.Utils;
import org.navigatebyfaith.rrreadings.test.readingsActivity.ReadingsActivityTestBase;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class SkipWhatsNewOnUpgradeTest extends ReadingsActivityTestBase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        suppressStartupWhatsNew(getInstrumentation().getTargetContext());
        getActivity(); //Start the activity
    }

    public void testStartupWithoutWhatsNew() {
        onView(withId(R.id.whatsNewLinearLayout)).check(doesNotExist());
    }
}