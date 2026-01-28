package org.navigatebyfaith.rrreadings.day;

import java.util.Calendar;

public interface DayModel {

    public void load(DayPresenter presenter, DayView dayView, Calendar calendar);
}
