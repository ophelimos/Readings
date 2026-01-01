package org.sfchristadelphian.readings.day;

import org.sfchristadelphian.readings.model.Passage;

public interface DayPresenter {
    void reLoad();

    void setCalendar(int year, int month, int day);
    
    void addItem(Passage passage);

    void notifyDataSetChanged();
}
