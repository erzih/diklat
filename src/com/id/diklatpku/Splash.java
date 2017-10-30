package com.id.diklatpku;

import com.id.diklatpku.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		final int welcomeScren = 3000;
		Thread welcomeThread = new Thread() {
			int wait = 0;

			public void run() {

				try {
					super.run();
					while (wait < welcomeScren) {
						sleep(100);
						wait += 100;
					}
				} catch (Exception e) {
					System.out.println("Exc=" + e);
				} finally {
					startActivity(new Intent(Splash.this, LoginActivity.class));
					finish();
				}
			}
		};
		welcomeThread.start();
	}

}
