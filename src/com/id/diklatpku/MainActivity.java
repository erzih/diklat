package com.id.diklatpku;

import com.id.diklatpku.R;
import com.id.diklatpku.listpiket.PetugasPiketFragment;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stu
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TabHost tabhost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(this, Activity_DetilUser.class);
		spec = tabhost.newTabSpec("akunpengguna")
				.setIndicator("AKUN PENGGUNA", null).setContent(intent);
		tabhost.addTab(spec);

		intent = new Intent().setClass(this, PetugasPiketFragment.class);
		spec = tabhost.newTabSpec("petugaspiket")
				.setIndicator("PETUGAS PIKET", null).setContent(intent);
		tabhost.addTab(spec);

	}
}
