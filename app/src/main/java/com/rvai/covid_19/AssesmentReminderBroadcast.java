package com.rvai.covid_19;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.rvai.covid_19.utils.Utils;

import org.threeten.bp.LocalDateTime;

public class AssesmentReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        SharedPreferences preferences =context.getSharedPreferences(Constants.APP_PREFERENCE_NAME,Context.MODE_PRIVATE);
        LocalDateTime obj= Utils.getLocalDateTime(Utils.getCurrentDateTime());
        SharedPreferences.Editor editor=preferences.edit();
        {
            switch (obj.getHour())
            {
                case 10:
                    if(preferences.getBoolean(Constants.P,false)==false)
                    {
                        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, Constants.CHANNELID)
                                .setSmallIcon(R.drawable.ic_notification_reminder)
                                .setContentTitle("ASSESMENT")
                                .setContentText("Your ASSesment is Pending")
                                .setPriority(NotificationCompat.PRIORITY_HIGH);

                        NotificationManagerCompat notificationmanager = NotificationManagerCompat.from(context);
                        notificationmanager.notify(200, notification.build());
                    }
                case 12:
                    if(preferences.getBoolean(Constants.P,false)==false)
                    {
                        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, Constants.CHANNELID)
                                .setSmallIcon(R.drawable.ic_notification_reminder)
                                .setContentTitle("ASSESMENT")
                                .setContentText("Your ASSesment is Pending")
                                .setPriority(NotificationCompat.PRIORITY_HIGH);

                        NotificationManagerCompat notificationmanager = NotificationManagerCompat.from(context);
                        notificationmanager.notify(200, notification.build());
                    }
                case 14:
                    if(preferences.getBoolean(Constants.P,false)==false)
                    {
                        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, Constants.CHANNELID)
                                .setSmallIcon(R.drawable.ic_notification_reminder)
                                .setContentTitle("ASSESMENT")
                                .setContentText("Your ASSesment is Pending")
                                .setPriority(NotificationCompat.PRIORITY_HIGH);

                        NotificationManagerCompat notificationmanager = NotificationManagerCompat.from(context);
                        notificationmanager.notify(200, notification.build());
                    }

                case 16:
                    if(preferences.getBoolean(Constants.P,false)==false)
                    {
                        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, Constants.CHANNELID)
                                .setSmallIcon(R.drawable.ic_notification_reminder)
                                .setContentTitle("ASSESMENT")
                                .setContentText("Your ASSesment is Pending")
                                .setPriority(NotificationCompat.PRIORITY_HIGH);

                        NotificationManagerCompat notificationmanager = NotificationManagerCompat.from(context);
                        notificationmanager.notify(200, notification.build());
                    }

                case 23:
                    editor.putBoolean(Constants.P,false);
                    editor.putBoolean(Constants.Q,false);
                    editor.putBoolean(Constants.R,false);
                    editor.putBoolean(Constants.S,false);




            }

        }
        editor.apply();


    }
}
