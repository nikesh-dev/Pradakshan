package com.example.pradakshan;

import android.app.Dialog;
import android.app.Service;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import javax.net.*;

public class second extends Service {

	SensorManager sm;
	// SensorEventListener q;
	// TextView l;
	Message msg;
	int flag2;
	SensorEventListener q;
	Sensor s;
	String d;
	TextView vi;
	int p, flag, x, count, state;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		flag2 = 0;
		return null;
	}

	@Override
	public int onStartCommand(Intent i, int flags, int startid) {

		final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		// final long[] pattern={2000,2000,2000};
		flag = 0;

		// x=0;
		p = 0;
		// l=(TextView)findViewById(R.id.textView2);
		// Toast.makeText(getBaseContext(),"service started",3).show();
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		s = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		state = 0;
		q = new SensorEventListener() {
			public void onSensorChanged(SensorEvent sensorEvent) { // TODO
																	// Monitor
																	// Sensor
																	// changes.
																	// Toast.makeText(getBaseContext(),
																	// "sensor started",
																	// 2).show();
				if (flag2 == 1) {
					if (MainActivity.flag1 == 0)// if not in pause state
					{
						int j = MainActivity.cou;
						// int x=0;
						// Toast.makeText(getBaseContext(), "x is"+x,
						// Toast.LENGTH_SHORT).show();
						if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
							// Toast.makeText(getBaseContext(),
							// "orientation sensor", 2).show();
							float degreepoint = sensorEvent.values[0];
							// Toast.makeText(getBaseContext(),
							// "degre is "+degreepoint,
							// Toast.LENGTH_SHORT).show();
							String direction = null;
							// Toast.makeText(getBaseContext(), degreepoint+"",
							// Toast.LENGTH_SHORT).show();
							if ((degreepoint <= 360 && degreepoint >= 315)
									|| (degreepoint <= 45 && degreepoint >= 0)) {
								x = 1;
								if (flag == 0)
									Toast.makeText(getBaseContext(), "x=" + x,
											Toast.LENGTH_LONG).show();

								direction = "N";
							}

							if (degreepoint <= 135 && degreepoint > 45) {
								x = 2;
								if (flag == 0)
									Toast.makeText(getBaseContext(), "x=" + x,
											Toast.LENGTH_LONG).show();

								direction = "E";
							}
							if (degreepoint <= 225 && degreepoint > 135) {
								x = 3;
								if (flag == 0)
									Toast.makeText(getBaseContext(), "x=" + x,
											Toast.LENGTH_LONG).show();

								direction = "S";
							}
							if (degreepoint < 315 && degreepoint > 225) {
								x = 4;
								if (flag == 0)
									Toast.makeText(getBaseContext(), "x=" + x,
											Toast.LENGTH_LONG).show();

								direction = "w";
							}
							// Toast.makeText(getBaseContext(),degreepoint+" "+direction,Toast.LENGTH_SHORT).show();
							if (x == 0)
								Toast.makeText(getBaseContext(), "error",
										Toast.LENGTH_LONG).show();
							if (flag == 0) // first instance
							{
								// Toast.makeText(getBaseContext(),
								// "x="+x+" "+"p="+p,Toast.LENGTH_LONG ).show();
								p = x;
								// Toast.makeText(getBaseContext(),
								// "x="+x+" "+"p="+p, Toast.LENGTH_LONG).show();
								// Toast.makeText(getBaseContext(),
								// "p is "+p+" "+direction+" FLAG="+flag,Toast.LENGTH_SHORT).show();
								// d=String.valueOf(direction);
								flag = 1;
								// Toast.makeText(getBaseContext(),
								// "HARDWARE LOADED state="+state,
								// Toast.LENGTH_SHORT).show();
							} else {

								if (flag == 1) // not first instance
								{

									if (state == 0 && x == p) // if current
																// state is
																// starting
																// state
									{
										// Toast.makeText(getBaseContext(),"reached state"+state,Toast.LENGTH_SHORT).show();
									}
									if (state == 0 && x != p) // MOVING AWAY
																// FROM STARTING
																// REGION

									{
										// Toast.makeText(getBaseContext(),
										// "state-1 x="+x+" p="+p,Toast.LENGTH_SHORT).show();
										state = 1; // NOT IN STARTING REGION
									}
									/*
									 * if(state==1&&x!=p) { //OUT OF STARTING
									 * POSITION }
									 * 
									 * if(state==2&&x==p) { //IN THE STARTING
									 * REGION }
									 */

									if (state == 2 && x != p) // MOVING AWAY
																// FROM START
																// REGION
									{
										state = 1;
										// Toast.makeText(getBaseContext(),"state-1",Toast.LENGTH_SHORT).show();
									}
									if (x == p && state == 1) // END OF A ROUND
									{
										state = 2;
										Toast.makeText(getBaseContext(),
												"x=" + x + " " + "p=" + p,
												Toast.LENGTH_SHORT).show();
										count++;
										msg = Message.obtain();
										msg.arg1 = count;
										MainActivity.handler.sendMessage(msg);

										if (count >= j) {
											// v.vibrate(pattern,0);
											v.vibrate(2000);
											// Toast.makeText(getBaseContext(),
											// count+" rounds done", 2).show();
											// sm.unregisterListener(q);
											// flag=0;
											// Toast.makeText(getBaseContext(),
											// "flag="+flag,
											// Toast.LENGTH_SHORT).show();
											// onDestroy();
										}

									}
								}
							}
						}
					}

					// Toast.makeText(getBaseContext(),direction+" "+sensorEvent.values[0]
					// ,1).show();
				}
			}

			public void onAccuracyChanged(Sensor sensor, int accuracy) {

			}
		};
		if (!sm.registerListener(q, s, SensorManager.SENSOR_DELAY_GAME))
			// Toast.makeText(getBaseContext(), "done", 7).show();

			Toast.makeText(getBaseContext(), "YOUR DEVICE NOT SUPP0RTED!!!", 2)
					.show();
		else {
			flag2 = 0;
			flag = 0;
			x = 0;

			// Toast.makeText(getBaseContext(), "loading",
			// Toast.LENGTH_SHORT).show();
			new CountDownTimer(5000, 1000) {

				public void onTick(long millisUntilFinished) {
					// mTextField.setText("seconds remaining: " +
					// millisUntilFinished / 1000);
				}

				public void onFinish() {
					// mTextField.setText("done!");
					flag2 = 1;
					Message msg1 = Message.obtain();
					msg1.arg1 = 1;
					MainActivity.handler2.sendMessage(msg1);
					// Toast.makeText(getBaseContext(), "started",
					// Toast.LENGTH_LONG).show();
				}
			}.start();
			// Toast.makeText(getBaseContext(), "flag="+flag+"x="+x,
			// Toast.LENGTH_SHORT).show();
		}
		return START_STICKY;

	}

	@Override
	public void onDestroy() {
		if (count < MainActivity.cou)
			Toast.makeText(getBaseContext(), "JOB INCOMPLETE",
					Toast.LENGTH_SHORT).show();
		// Toast.makeText(getBaseContext(), "service stopped", 1).show();
		sm.unregisterListener(q);
		flag = 0;
		// Toast.makeText(getBaseContext(), "service about to end",
		// Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}

}
