package com.id.diklatpku;

import com.id.diklatpku.R;

import android.R.anim;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.view.View.OnClickListener;

public class AboutActivity extends Activity {

	Button buttonOke;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		buttonOke = (Button) findViewById(R.id.btn_about_oke);
		buttonOke.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				AboutActivity.this.overridePendingTransition(
						anim.fade_in, anim.fade_out);
			}
		});
	}
}
