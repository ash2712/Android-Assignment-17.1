package com.example.ayush.musicplayer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by admin on 11/5/2017.
 */

public class musicService extends Service {
    MediaPlayer mediaPlayer;

    public musicService() {
    }

    @Override
    public void onCreate() {
        //creating media player
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Music Started", Toast.LENGTH_SHORT).show();
        //starting music
        mediaPlayer.start();
        //set looping to true in case the music gets over
        mediaPlayer.setLooping(true);
        //notification builder for building notifications
        NotificationCompat.Builder builder = new NotificationCompat.Builder(musicService.this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Notification");
        builder.setContentText("Music Playing");
        Intent i = new Intent(musicService.this, MainActivity.class);
        //pending intent for starting the main activity when clicked on the notification
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        // NOTIFICATION
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Music Stopped", Toast.LENGTH_SHORT).show();
        //releasing media player object
        mediaPlayer.release();
        super.onDestroy();
    }
}
