package com.example.pradakshan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	PowerManager.WakeLock wl;
	static Handler handler;
	TextView v;
	EditText e, e2;
	// Button b2,b3,b4,b5,b6;
	static SensorEventListener w;
	static int cou, flag1;
	Intent i;
	int r, c, fla, flag;
	ProgressDialog pdialog;
	static Handler handler2;

	// ***FOR SET BUTTON

	public void set(View z) {
		e2 = (EditText) findViewById(R.id.editText2);
		try {
			r = Integer.parseInt(e2.getText().toString());
			if (r > c) {
				cou = r;
				// Toast.makeText(getBaseContext(), ""+r+"/"+cou,
				// Toast.LENGTH_SHORT).show();
			} else
				Toast.makeText(getBaseContext(),
						"New value is lower than current Pradakshan count",
						Toast.LENGTH_LONG).show();
			setContentView(R.layout.first);
			v = (TextView) findViewById(R.id.textView3);
			v.setText(c + "/" + cou);
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), "Enter a number",
					Toast.LENGTH_LONG).show();
			setContentView(R.layout.second);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		e2 = (EditText) findViewById(R.id.editText2);
		setContentView(R.layout.activity_main);
		flag1 = 0;
		flag = 0;
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
		wl.acquire();
		i = new Intent(this, second.class);
		// k=0;

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// Toast.makeText(getBaseContext(), "round done"+msg.arg1,
				// Toast.LENGTH_SHORT).show();
				c = msg.arg1;
				v.setText("" + msg.arg1 + "/" + cou);
			}
		};
		handler2 = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// Toast.makeText(getBaseContext(), "round done"+msg.arg1,
				// Toast.LENGTH_SHORT).show();
				pdialog.dismiss();
				// c=msg.arg1;
				// v.setText(""+msg.arg1+"/"+cou);
			}
		};
		e = (EditText) findViewById(R.id.input);
	}

	// ***FOR START BUTTON***

	public void start(View o) {
		// Toast.makeText(getBaseContext(), "start invoked",
		// Toast.LENGTH_LONG).show();
		e = (EditText) findViewById(R.id.star);
		setContentView(R.layout.first);
		v = (TextView) findViewById(R.id.textView3);
		// int l;

		if (e.getText() != null) {
			// Toast.makeText(getBaseContext(), "cou="+cou,
			// Toast.LENGTH_LONG).show();

			try {
				cou = Integer.parseInt(e.getText().toString());

				if (cou > 0) {
					// Toast.makeText(getBaseContext(), "0/"+cou,
					// Toast.LENGTH_LONG).show();
					v.setText("0/" + cou);
					startService(i);
					pdialog = new ProgressDialog(this);
					pdialog.setCancelable(true);
					pdialog.setMessage("Please wait....");
					pdialog.show();
				}

				else
					Toast.makeText(getBaseContext(),
							"ENTER PROPER NUMBER" + e.getText().toString(),
							Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(getBaseContext(), "ENTER A NUMBER",
						Toast.LENGTH_LONG).show();
				setContentView(R.layout.activity_main);
			}
		}
	}

	// ***FOR RESET BUTTON***
	public void reset(View z) {
		setContentView(R.layout.second);
		v = (TextView) findViewById(R.id.textView3);
		v.setText("" + c + "/" + cou);
	}

	// ***FOR STOP BUTTON***\\
	public void stop(View v) {
		stopService(i);
		setContentView(R.layout.activity_main);
		c = 0;
		// ou=0;
		// Toast.makeText(getBaseContext(), ""+cou, Toast.LENGTH_LONG).show();
	}

	// ***FOR PAUSE BUTTON
	public void pause(View o) {
		flag1 = 1;
		setContentView(R.layout.third);
		v = (TextView) findViewById(R.id.textView3);
		v.setText(c + "/" + cou);
		Toast.makeText(getBaseContext(), "paused", Toast.LENGTH_LONG).show();
		// flag1=0;
	}

	// ***FOR RESUME BUTTON
	public void resume(View o) {

		setContentView(R.layout.first);
		v = (TextView) findViewById(R.id.textView3);
		v.setText(c + "/" + cou);
		Toast.makeText(getBaseContext(), "resumed", Toast.LENGTH_LONG).show();
		flag1 = 0;
	}

	@Override
	public void onResume() {
		// resume(v);
		super.onResume();

	}

	@Override
	public void onPause() {
		// pause(v);
		super.onPause();
	}

	@Override
	public void onStop() {
		Toast.makeText(getBaseContext(), "finished", Toast.LENGTH_SHORT).show();
		wl.release();
		stopService(i);
		super.onStop();
		// sm.unregisterListener(mylistener);
	}
}
