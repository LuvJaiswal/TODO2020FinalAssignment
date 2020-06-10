//package com.example.todo2020.BraoadcastRecievers;
//
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.media.MediaPlayer;
//import android.view.View;
//import android.widget.Toast;
//
//import com.example.todo2020.R;
//
//
//public class AlarmReceiver extends BroadcastReceiver {
//    MediaPlayer mp;
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        mp=MediaPlayer.create(context,R.raw.audio);
//        mp.start();
//        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
//    }
//
//    public void stopSong(View v) {
//        mp.stop();
//    }
//}