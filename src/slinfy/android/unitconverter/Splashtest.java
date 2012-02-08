package slinfy.android.unitconverter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Splashtest extends Activity {

	/**
	 * The thread to process splash screen events
	 */
	private Thread mSplashThread;
	private Button btnT, btnC, btnB;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashadd);

		btnT = (Button) findViewById(R.id.btnTop);
		btnC = (Button) findViewById(R.id.btnCenter);
		btnB = (Button) findViewById(R.id.btnBelow);

		final Splashtest sPlashScreen = this;
		// The thread to wait for splash screen events
		mSplashThread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						// Wait given period of time or exit on touch
						wait(10000);
					}
				} catch (InterruptedException ex) {
				}

				finish();

				// Run next activity
				Intent intent = new Intent();
				intent.setClass(sPlashScreen, WheelDemo.class);
				startActivity(intent);
				// stop();
			}
		};

		mSplashThread.start();

		
		btnT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {
                    // Start the activity
					String url = "http://www.yahoo.com";
					Intent i = new Intent(Intent.ACTION_VIEW);
					Uri u = Uri.parse(url);
					i.setData(u);
                    startActivity(i);
                  } catch (Exception e) {
                    // Raise on activity not found
                    Toast toast = Toast.makeText(sPlashScreen, "Browser not found.", Toast.LENGTH_SHORT);
                  }
                } 
		});

		btnC.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				synchronized (mSplashThread) {
					mSplashThread.notifyAll();
				}
			}
		});

		btnB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
                    // Start the activity
					String url = "http://www.google.com";
					Intent i = new Intent(Intent.ACTION_VIEW);
					Uri u = Uri.parse(url);
					i.setData(u);
                    startActivity(i);
                  } catch (Exception e) {
                    // Raise on activity not found
                    Toast toast = Toast.makeText(sPlashScreen, "Browser not found.", Toast.LENGTH_SHORT);
                  }
			}
		});
	}

	/**
	 * Processes splash screen touch events
	 */
	@Override
	public boolean onTouchEvent(MotionEvent evt) {
		if (evt.getAction() == MotionEvent.ACTION_DOWN) {
			synchronized (mSplashThread) {
				mSplashThread.notifyAll();
			}
		}
		return true;
	}

}