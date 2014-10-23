package com.cn.myclock;

import java.util.Calendar;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimeView extends LinearLayout {

	public TimeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public TimeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TimeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private TextView tvTime;

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		tvTime = (TextView) findViewById(R.id.tvTime);
		timeHandler.sendEmptyMessage(0);

	}

	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		// TODO Auto-generated method stub
		super.onVisibilityChanged(changedView, visibility);
		if (getVisibility() == View.VISIBLE) {
			timeHandler.sendEmptyMessage(0);
		} else {
			timeHandler.removeMessages(0);
		}

	}

	private void refreshTime() {
		Calendar c = Calendar.getInstance();
		tvTime.setText(String.format("%d:%d:%d", c.get(Calendar.HOUR_OF_DAY),
				c.get(Calendar.MINUTE), c.get(Calendar.SECOND)));

	}

	private Handler timeHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			refreshTime();
			if (getVisibility() == View.VISIBLE) {
				timeHandler.sendEmptyMessageDelayed(0, 1000);
			}
		};
	};

}
