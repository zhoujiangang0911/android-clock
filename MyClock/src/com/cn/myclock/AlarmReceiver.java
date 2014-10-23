package com.cn.myclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context content, Intent arg1) {
		System.out.println("ƒ÷÷”÷¥––¡À");
		AlarmManager am = (AlarmManager) content.getSystemService(Context.ALARM_SERVICE);
		
		am.cancel(PendingIntent.getBroadcast(content, getResultCode(),new Intent(content, AlarmReceiver.class), 0));
		Intent i  = new Intent(content, PlayAlarmAty.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
								
		content.startActivity(i);
		
	}

}
