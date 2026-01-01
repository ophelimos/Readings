package org.sfchristadelphian.readings;

import org.sfchristadelphian.readings.day.DayModel;
import org.sfchristadelphian.readings.day.DayModel1;
import org.sfchristadelphian.readings.day.DayPresenter;
import org.sfchristadelphian.readings.day.DayPresenter1;
import org.sfchristadelphian.readings.day.DayView;
import org.sfchristadelphian.readings.day.DayFragment;

public class Injector {
    public static DayPresenter getDayPresenter(DayFragment dayFragment) {
        return new DayPresenter1(dayFragment);
    }

    public static DayModel getDayModel(DayPresenter1 dayPresenter1, DayView dayView) {
        return new DayModel1();
    }
}
