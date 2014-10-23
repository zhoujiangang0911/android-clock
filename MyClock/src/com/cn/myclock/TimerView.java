package com.cn.myclock;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

public class TimerView extends LinearLayout {
	private Button btnStart, btnPause, btnResume, btnRest;
	private EditText etHour, etMin, etSec;
	private Timer timer = new Timer();
	private TimerTask timerTask = null;
	private int AlltimerCount = 0;

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TimerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		btnStart = (Button) findViewById(R.id.btnStart);
		btnPause = (Button) findViewById(R.id.btnPaus);
		btnPause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopTimer();
				btnPause.setVisibility(View.GONE);
				btnResume.setVisibility(View.VISIBLE);
			}
		});
		
		btnResume = (Button) findViewById(R.id.btnResume);
		btnResume.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("1232132131");
				startTimer();
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
			}
		});
		btnRest = (Button) findViewById(R.id.btnReset);
		btnRest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				stopTimer();
				etHour.setText("0");
				etMin.setText("0");
				etSec.setText("0");
				btnRest.setVisibility(View.GONE);
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.GONE);
				btnStart.setVisibility(View.VISIBLE);
				
			}
		});
		btnStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("asdfasfd");
				startTimer();
				btnStart.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
				btnRest.setVisibility(View.VISIBLE);
				
			}
		});

		etHour = (EditText) findViewById(R.id.etHour);
		etMin = (EditText) findViewById(R.id.etMin);
		etSec = (EditText) findViewById(R.id.etSec);

		etHour.setText("00");
		etHour.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				if (!TextUtils.isEmpty(s)) {
					int value = Integer.parseInt(s.toString());

					if (value > 59) {
						etHour.setText("59");
					} else if (value < 0) {
						etHour.setText("0");
					}
				}
				checkToEnableBtnStart();

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

		etMin.setText("00");
		etMin.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				if (!TextUtils.isEmpty(s)) {
					int value = Integer.parseInt(s.toString());

					if (value > 59) {
						etMin.setText("59");
					} else if (value < 0) {
						etMin.setText("0");
					}
				}
				checkToEnableBtnStart();

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

		etSec.setText("00");
		etSec.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(s)) {
					int value = Integer.parseInt(s.toString());

					if (value > 59) {
						etSec.setText("59");
					} else if (value < 0) {
						etSec.setText("0");
					}
				}
				checkToEnableBtnStart();

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

		btnStart.setVisibility(View.VISIBLE);
		btnStart.setEnabled(false);
		btnPause.setVisibility(View.GONE);
		btnResume.setVisibility(View.GONE);
		btnRest.setVisibility(View.GONE);

	}

	private void checkToEnableBtnStart() {
		btnStart.setEnabled((!TextUtils.isEmpty(etHour.getText()) && Integer
				.parseInt(etHour.getText().toString()) > 0)
				|| (!TextUtils.isEmpty(etMin.getText()) && Integer
						.parseInt(etMin.getText().toString()) > 0)
				|| (!TextUtils.isEmpty(etSec.getText()) && Integer
						.parseInt(etSec.getText().toString()) > 0));
	}

	private void startTimer() {
		if (timerTask == null) {
			AlltimerCount = Integer.parseInt(etHour.getText().toString()) * 60
					* 60 + Integer.parseInt(etMin.getText().toString()) * 60
					+ Integer.parseInt(etSec.getText().toString());
			timerTask = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					AlltimerCount--;
					
					handler.sendEmptyMessage(MSG_WHAT_TIME_TICK);
					
					if (AlltimerCount == 0) {
						handler.sendEmptyMessage(MSG_WHAT_TIME_IS_UP);
						stopTimer();
						
					}
				}
			};
			timer.schedule(timerTask, 1000, 1000);
	

		}

	}
	private void stopTimer(){
		if(timerTask !=null){
			timerTask.cancel();
			timerTask = null;
		}
	}
	private static final int MSG_WHAT_TIME_IS_UP = 1; 
	private static final int MSG_WHAT_TIME_TICK = 2; 
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_WHAT_TIME_IS_UP:
				new AlertDialog.Builder(getContext())
				.setTitle("Time is up")
				.setMessage("Time is up")
				.setNegativeButton("Cancel", null).show();
				btnRest.setVisibility(View.GONE);
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.GONE);
				btnStart.setVisibility(View.VISIBLE);
				break;
			case MSG_WHAT_TIME_TICK:
         	     int hour=AlltimerCount/60/60;
         	     int min = (AlltimerCount/60)%60;
         	     int sec = AlltimerCount%60;
         	     
         	     etHour.setText(hour+"");
         	     etMin.setText(min+"");
         	     etSec.setText(sec+"");
				break;
			default:
				break;
			}
		};
	};
	
	
	
}
