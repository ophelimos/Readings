package org.navigatebyfaith.rrreadings.service;

public interface IPlayerUi {
    void onPassageChange(int passageId);
    void onEndAll();
    void onPassageEnding(int passageId);
}
