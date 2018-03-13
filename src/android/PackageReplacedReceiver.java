package com.tonikorin.cordova.plugin.autostart;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import it.peopletrust.octobike.MainActivity;
import it.peopletrust.octobike.R;

public class PackageReplacedReceiver extends BroadcastReceiver {
  static final String CHANNEL_ID = "it.peopletrust.octobike.BikeNotification";
  public static final int NOTIFICATION_ID = 0;

  @Override
  public void onReceive(Context context, Intent intent) {
    Intent mainIntent = new Intent(context, MainActivity.class);
    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_notification)
      .setContentTitle("Bici non connessa")
      .setContentText("Tocca per connettere la tua bici")
      .setStyle(new NotificationCompat.BigTextStyle()
        .bigText("Tocca per connettere la tua bici"))
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .setContentIntent(pendingIntent)
      .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
      .setAutoCancel(true);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      createChannel(notificationManager);
      mBuilder.setChannelId(CHANNEL_ID);
    }

    notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
  }

  @TargetApi(26)
  private void createChannel(NotificationManager notificationManager) {
    String name = "BikeAlert";
    String description = "Notifications for bike connection";
    int importance = NotificationManager.IMPORTANCE_DEFAULT;

    NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
    mChannel.setDescription(description);
    notificationManager.createNotificationChannel(mChannel);
  }
}
