package com.test.vue.vuetest.messagecenter;

import java.util.ArrayList;
import java.util.Random;


public class NotificationManager implements NotificationInterface {
    private ArrayList<NotificationAisle> mNotificationList = new ArrayList<NotificationAisle>();
    
    public NotificationManager() {
        // TODO Auto-generated constructor stub
    }
    
    public ArrayList<NotificationAisle> getUserNotifications() {
        // TODO: get the notifications from the db.
        for (int i = 0; i < 10; i++) {
            NotificationAisle notificationAisle = new NotificationAisle();
            notificationAisle.setNotificationText("Notification Text");
            notificationAisle.setReadStatus(false);
            notificationAisle.setAisleId("");
            notificationAisle.setmEmptyNotification(false);
            notificationAisle.setUserName("stefen hock");
            notificationAisle.setAisleTitle("Looking for pool party");
            notificationAisle.setCommentsCount(randInt(0, 20));
            notificationAisle.setLikeCount(randInt(0, 30));
            notificationAisle.setBookmarkCount(randInt(0, 10));
            mNotificationList.add(notificationAisle);
        }
        return mNotificationList;
    }
    
    @Override
    public boolean removeNotification(long notificationId) {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public void aggrigateNotifications(
            ArrayList<NotificationAisle> notificationAisles) {
        // TODO Auto-generated method stub
        
    }
    public  int randInt(int min, int max) {

        // Usually this should be a field rather than a method variable so
        // that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive

        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
