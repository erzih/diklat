package com.id.diklatpku;

import com.id.diklatpku.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class ActivityPreEvaluasi extends Activity{

	Button btnevaluasi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre_evaluasi);
		Intent ins = getIntent();
		final String values = ins.getStringExtra("nilailink");
		
		btnevaluasi = (Button) findViewById(R.id.btn_pre_eval);
		btnevaluasi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (values.equals("")) {
					Toast.makeText(ActivityPreEvaluasi.this, "evaluasi linkkosong",
							Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_BROWSABLE);
					intent.setData(Uri.parse(values));
					startActivity(intent);
				}
			}
		});
		
	}
	
}
