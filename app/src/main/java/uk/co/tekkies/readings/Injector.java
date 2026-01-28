package org.navigatebyfaith.rrreadings;

import org.navigatebyfaith.rrreadings.day.DayModel;
import org.navigatebyfaith.rrreadings.day.DayModel1;
import org.navigatebyfaith.rrreadings.day.DayPresenter;
import org.navigatebyfaith.rrreadings.day.DayPresenter1;
import org.navigatebyfaith.rrreadings.day.DayView;
import org.navigatebyfaith.rrreadings.day.DayFragment;

public class Injector {
    public static DayPresenter getDayPresenter(DayFragment dayFragment) {
        return new DayPresenter1(dayFragment);
    }

    public static DayModel getDayModel(DayPresenter1 dayPresenter1, DayView dayView) {
        return new DayModel1();
    }
}
