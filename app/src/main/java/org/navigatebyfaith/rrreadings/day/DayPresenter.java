package org.navigatebyfaith.rrreadings.day;

import org.navigatebyfaith.rrreadings.model.Passage;

public interface DayPresenter {
    void reLoad();

    void setCalendar(int year, int month, int day);
    
    void addItem(Passage passage);

    void notifyDataSetChanged();
}
