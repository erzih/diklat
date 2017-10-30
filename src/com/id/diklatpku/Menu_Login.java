package com.id.diklatpku;
import com.id.diklatpku.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.view.View;
import android.view.View.OnClickListener;

public class Menu_Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ActionBar myActionBar = getActionBar();

		// For hiding android actionbar
		myActionBar.hide();

		Button btnLogin;
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Menu_Login.this, Menu_utama.class);
				startActivity(intent);
			}
		});
	}

}