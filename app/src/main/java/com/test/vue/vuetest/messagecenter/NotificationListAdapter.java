package com.test.vue.vuetest.messagecenter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.vue.vuetest.R;


public class NotificationListAdapter extends BaseAdapter {
    ArrayList<NotificationAisle> mNotificationList;
    Context mContext;
    Typeface typeface;
    
    public NotificationListAdapter(Context context,
            ArrayList<NotificationAisle> notificationList,Typeface typeface) {
        this.mContext = context;
        this.mNotificationList = new ArrayList<NotificationAisle>();
        this.typeface = typeface;
    }
    
    @Override
    public int getCount() {
        try {
            return 10;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public Object getItem(int position) {
        return null;
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    public NotificationAisle removeItem(int position) {
        NotificationAisle notificatinAisle = null;
        notifyDataSetChanged();
        return notificatinAisle;
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_row, parent, false);
            holder = new ViewHolder();
            holder.notificationDescription = (TextView) convertView
                    .findViewById(R.id.notification_description);
            holder.notificationAisleTitle = (TextView) convertView
                    .findViewById(R.id.notification_aisle_title);
            holder.notificationDescription.setTypeface(typeface);
            holder.notificationAisleTitle.setTypeface(typeface);


            holder.userImage = (ImageView) convertView
                    .findViewById(R.id.user_image);
            holder.overflow_listlayout_layout = (RelativeLayout) convertView
                    .findViewById(R.id.overflow_listlayout_layout);

            holder.swipe_button1 = (ImageView) convertView
                    .findViewById(R.id.swipe_button1);
            holder.swipe_button2 = (ImageView) convertView
                    .findViewById(R.id.swipe_button2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.swipe_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (mNotificationList.size() == 1
                && mNotificationList.get(position).ismEmptyNotification() == true) {
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            int pixel = 4;
            holder.notificationAisleTitle.setMaxLines(4);
            params.setMargins(pixel, pixel, pixel, pixel);
            holder.notificationAisleTitle.setSingleLine(false);
            holder.notificationAisleTitle.setLayoutParams(params);
            ((ImageView) holder.userImage).setVisibility(View.GONE);
        } else {

            ((ImageView) holder.userImage).setImageResource(R.drawable.vuetest);

        }
        return convertView;
    }
    
    public int getSerialNumber(int position) {
        if (mNotificationList != null && mNotificationList.size() > 0) {
            return mNotificationList.get(position).getId();
        }
        return -1;
    }
    
    public void loadScreenForNotificationAisle(int position) {
    }
    
    class ViewHolder {
        ImageView userImage,swipe_button1,swipe_button2;
        RelativeLayout overflow_listlayout_layout;
        TextView notificationDescription,
                notificationAisleTitle;

    }
}
