package org.navigatebyfaith.rrreadings.day;

import org.navigatebyfaith.rrreadings.model.Passage;

public interface DayView {
    void clearList();

    void addItem(Passage passage);

    void notifyDataSetChanged();
}
