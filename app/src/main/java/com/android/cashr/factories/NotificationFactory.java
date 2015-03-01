package com.android.cashr.factories;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.android.cashr.R;

public class NotificationFactory extends Service {

	private static final boolean DEBUG = false;

	@Override
	public void onCreate() {
		LogFactory.log(DEBUG);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		LogFactory.log(DEBUG);
		return null;
	}

	@Override
	public void onDestroy() {
		LogFactory.log(DEBUG);
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogFactory.log(DEBUG);

		NotificationCompat.Builder notificationBuilder =
				new NotificationCompat.Builder(this)
						.setSmallIcon(R.drawable.ic_launcher)
						.setContentTitle("M Go Laundry!")
						.setContentText("Laundry's Done");

		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notificationBuilder.build());

		this.stopSelf();

		return START_NOT_STICKY;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		LogFactory.log(DEBUG);
		return super.onUnbind(intent);
	}
}
