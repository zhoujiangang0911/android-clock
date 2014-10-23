package com.cn.myclock;

import java.util.Timer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StopWatchView extends LinearLayout {
public StopWatchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}


	public StopWatchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}


	private TextView tvHour,tvSec,tvMsec,tvMin;
	private Button start ,resume,reset,pause,lap;
	private ListView lvTimelist;
	private ArrayAdapter<String> adapter;
	private Timer timer  = new Timer();
	
	
	public StopWatchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
		@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		tvHour = (TextView) findViewById(R.id.timerHour);
		tvHour.setText("0");
		tvSec = (TextView) findViewById(R.id.timeSce);
		tvSec.setText("0");
		tvMsec = (TextView) findViewById(R.id.timeMsec);
		tvMsec.setText("0");
		tvMin = (TextView) findViewById(R.id.timerMin);
		tvMin.setText("0");
		
		start = (Button) findViewById(R.id.start);
		resume = (Button) findViewById(R.id.resume);
		reset = (Button) findViewById(R.id.reset);
		pause = (Button) findViewById(R.id.paus);
		lap = (Button) findViewById(R.id.lap);
		resume.setVisibility(View.GONE);
		reset.setVisibility(View.GONE);
		pause.setVisibility(View.GONE);
		lap.setVisibility(View.GONE);
		lvTimelist = (ListView) findViewById(R.id.watchTime);
		adapter  = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1);
		lvTimelist.setAdapter(adapter);
		
		
	}

}
