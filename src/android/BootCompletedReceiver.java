package com.tonikorin.cordova.plugin.autostart;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import it.peopletrust.octobike.MainActivity;
import it.peopletrust.octobike.R;

public class BootCompletedReceiver extends BroadcastReceiver {
  static final String CHANNEL_ID = "default-channel-id";
  public static final int NOTIFICATION_ID = 0;

  @Override
  public void onReceive(Context context, Intent intent) {
    Intent mainIntent = new Intent(context, MainActivity.class);
    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);


    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_notification)
      .setContentTitle("Bici non connessa")
      .setContentText("Tocca per connettere la tua bici")
      .setStyle(new NotificationCompat.BigTextStyle()
        .bigText("Apri l'applicazione per permettere la localizzazione della tua bici"))
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .setContentIntent(pendingIntent)
      .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
      .setAutoCancel(true);

    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
    notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
  }
}
