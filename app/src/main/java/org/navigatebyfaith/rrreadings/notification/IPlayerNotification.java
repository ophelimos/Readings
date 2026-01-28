package org.navigatebyfaith.rrreadings.notification;

public interface IPlayerNotification {
    void show();
    void update(int passageId);
    void destroy();
}
