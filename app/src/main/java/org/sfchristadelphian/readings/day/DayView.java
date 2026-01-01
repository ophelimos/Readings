package org.sfchristadelphian.readings.day;

import org.sfchristadelphian.readings.model.Passage;

public interface DayView {
    void clearList();

    void addItem(Passage passage);

    void notifyDataSetChanged();
}
