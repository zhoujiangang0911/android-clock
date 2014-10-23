package com.cn.myclock;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

public class AlarmView extends LinearLayout {

	public AlarmView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}

	public AlarmView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}

	public AlarmView(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}

	private void init() {
		alarmManager = (AlarmManager) getContext().getSystemService(
				Context.ALARM_SERVICE);

	}

	private ArrayAdapter<AlarmDater> adapter;
	private Button btnAddAlarm;
	private ListView lvAlarmlist;
	private static final String KTY_ALARM_LIST = "alarmList";
	private AlarmManager alarmManager;

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		lvAlarmlist = (ListView) findViewById(R.id.lvAlarmlist);
		btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);

		adapter = new ArrayAdapter<AlarmView.AlarmDater>(getContext(),
				android.R.layout.simple_list_item_1);
		lvAlarmlist.setAdapter(adapter);
		readSaveAlarmList();
		btnAddAlarm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				addAlarm();
			}
		});

		lvAlarmlist
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, final int position, long arg3) {
						new AlertDialog.Builder(getContext())
								.setTitle("操作选项")
								.setItems(new CharSequence[] { "删除" },
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub
												switch (which) {
												case 0:
													deleteAlarm(position);
													break;

												default:
													break;
												}
											}
										}).setNegativeButton("取消", null).show();

						return true;
					}

				});
	}

	private void deleteAlarm(int position) {
		AlarmDater ad = adapter.getItem(position);
		adapter.remove(ad);
		saveAlarmlist();
		alarmManager.cancel(PendingIntent.getBroadcast(getContext(),
				ad.getId(), new Intent(getContext(), AlarmReceiver.class), 0));
	}

	private void addAlarm() {
		Calendar c = Calendar.getInstance();

		new TimePickerDialog(getContext(),
				new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker arg0, int hourofDay,
							int minute) {
						// TODO Auto-generated method stub

						Calendar calendar = Calendar.getInstance();

						calendar.set(Calendar.HOUR_OF_DAY, hourofDay);
						calendar.set(Calendar.MINUTE, minute);
						calendar.set(Calendar.SECOND, 0);
						calendar.set(Calendar.MILLISECOND, 0);
						Calendar ca = Calendar.getInstance();
						if (calendar.getTimeInMillis() <= ca.getTimeInMillis()) {
							calendar.setTimeInMillis(calendar.getTimeInMillis()
									+ 24 * 60 * 60 * 1000);
						}
						AlarmDater ad = new AlarmDater(calendar
								.getTimeInMillis());
						adapter.add(ad);
						alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, ad
								.getTime(), 5 * 60 * 1000, PendingIntent
								.getBroadcast(getContext(), ad.getId(),
										new Intent(getContext(),
												AlarmReceiver.class), 0));
						saveAlarmlist();
					}
				}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true)
				.show();
	}

	private void saveAlarmlist() {
		Editor editor = getContext().getSharedPreferences(
				AlarmView.class.getName(), Context.MODE_PRIVATE).edit();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < adapter.getCount(); i++) {
			sb.append(adapter.getItem(i).getTime()).append(",");
		}
		if (sb.length() > 1) {
			String content = sb.toString().substring(0, sb.length() - 1);
			editor.putString(KTY_ALARM_LIST, content);

			System.out.println(content);
		} else {
			editor.putString(KTY_ALARM_LIST, null);
		}
		editor.commit();
	}

	private void readSaveAlarmList() {
		SharedPreferences sp = getContext().getSharedPreferences(
				AlarmView.class.getName(), Context.MODE_PRIVATE);
		String content = sp.getString(KTY_ALARM_LIST, null);

		if (content != null) {
			String[] timeStrings = content.split(",");
			for (String string : timeStrings) {
				adapter.add(new AlarmDater(Long.parseLong(string)));
			}
		}
	}

	private static class AlarmDater {
		public AlarmDater(long time) {
			// TODO Auto-generated constructor stub
			this.time = time;
			date = Calendar.getInstance();

			timelabel = String.format("%d月%d日 %d:%d",
					date.get(Calendar.MONTH) + 1,
					date.get(Calendar.DAY_OF_MONTH),
					date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));

		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return getTimelabel();
		}

		private long time = 0;
		private String timelabel = "";
		private Calendar date;

		public int getId() {
			return (int) (getTime() / 1000 / 60);
		}

		public long getTime() {
			return time;
		}

		public String getTimelabel() {
			return timelabel;
		}

	}

}
