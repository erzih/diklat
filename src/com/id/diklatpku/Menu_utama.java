package com.id.diklatpku;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.id.diklatpku.R;
import com.id.diklatpku.koment.KomentarActivity;
import com.id.diklatpku.login.PreferenceHelper;
import com.id.diklatpku.pengajar.CrudSelect;
import com.id.diklatpku.pengajar.ListPengajar;
import com.id.diklatpku.uppass.UpdatePassActivity;

import android.R.anim;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class Menu_utama extends Activity implements OnItemSelectedListener {
	private PreferenceHelper preferenceHelper;

	JSONObject jsonobject;
	JSONArray jsonarray;
	ProgressDialog pdialog;

	TextView textnama;
	TextView textNamaTitle;
	Button btn_materi;
	Button btn_petugas;

	static TextView mDotsText[];
	private int mDotsCount;
	private LinearLayout mDotsLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		preferenceHelper = new PreferenceHelper(this);

		Gallery gallery = (Gallery) findViewById(R.id.gallery);
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setOnItemSelectedListener(this);

		mDotsLayout = (LinearLayout) findViewById(R.id.image_count);
		// here we count the number of images we have to know how many dots we
		// need
		mDotsCount = gallery.getAdapter().getCount();

		// here we create the dots
		// as you can see the dots are nothing but "." of large size
		mDotsText = new TextView[mDotsCount];

		// here we set the dots
		for (int i = 0; i < mDotsCount; i++) {
			mDotsText[i] = new TextView(this);
			mDotsText[i].setText(".");
			mDotsText[i].setTextSize(45);
			mDotsText[i].setTypeface(null, Typeface.BOLD);
			mDotsText[i].setTextColor(android.graphics.Color.BLUE);
			mDotsLayout.addView(mDotsText[i]);
		}

		btn_materi = (Button) findViewById(R.id.btn_materi);
		btn_petugas = (Button) findViewById(R.id.btn_petugas);
		textnama = (TextView) findViewById(R.id.textNama);
		textnama.setText("Welcome " + preferenceHelper.getName()
				+ preferenceHelper.getHobby());

		new DownloadJSON().execute();

		Button btn_panitia;
		btn_panitia = (Button) findViewById(R.id.btn_panitia);
		btn_panitia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Menu_utama.this, MainActivity.class);
				startActivity(intent);
				Menu_utama.this.overridePendingTransition(anim.fade_in,
						anim.fade_out);

			}
		});

		Button btn_pengajar;
		btn_pengajar = (Button) findViewById(R.id.btn_pengajar);
		btn_pengajar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Menu_utama.this, CrudSelect.class);
				startActivity(intent);
				Menu_utama.this.overridePendingTransition(anim.fade_in,
						anim.fade_out);
			}
		});

		Button komentar;
		komentar = (Button) findViewById(R.id.btn_komentar);
		komentar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Menu_utama.this,
						KomentarActivity.class);
				startActivity(intent);
				Menu_utama.this.overridePendingTransition(anim.fade_in,
						anim.fade_out);
			}
		});

		Button about;
		about = (Button) findViewById(R.id.btn_about);
		about.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Menu_utama.this, AboutActivity.class);
				startActivity(intent);
				Menu_utama.this.overridePendingTransition(anim.fade_in,
						anim.fade_out);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_settings:
			preferenceHelper.putIsLogin(false);
			Intent intent = new Intent(Menu_utama.this, LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			Menu_utama.this.finish();
			Menu_utama.this.overridePendingTransition(anim.fade_in,
					anim.fade_out);
			break;
		case R.id.set_password:
			Intent in = new Intent(Menu_utama.this, UpdatePassActivity.class);
			startActivity(in);
			Menu_utama.this.overridePendingTransition(anim.fade_in,
					anim.fade_out);
			break;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);

	}

	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		String title = "";
		String materi = "";
		String penyelenggara = "";
		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> map = new HashMap<String, String>();

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog = new ProgressDialog(Menu_utama.this);
			pdialog.setMessage("Loading ...");
			pdialog.setIndeterminate(false);
			pdialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String id = preferenceHelper.getHobby();
			String url = "http://diklat4all.com/android/getdatahome.php?id="
					+ id;

			jsonobject = JSONFunctions.getJSONfromURL(url);

			try {
				jsonarray = jsonobject.getJSONArray("datahome");
				jsonobject = jsonarray.getJSONObject(0);

				title = jsonobject.getString("diklat").toString().trim();
				materi = jsonobject.getString("link").toString().trim();
				penyelenggara = jsonobject.getString("linkevaluasi").toString()
						.trim();

				map.put("dikla", title);
				map.put("mater", materi);
				map.put("evaluasi", penyelenggara);
				contactList.add(map);

			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			TextView tvTitle = (TextView) findViewById(R.id.text_nama_title);
			tvTitle.setText(title);

			pdialog.dismiss();

			btn_materi.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String value = (String) map.get("mater");
					if (value.equals("")) {
						Toast.makeText(Menu_utama.this, "diklat linkkosong",
								Toast.LENGTH_SHORT).show();
					} else {

						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_VIEW);
						intent.addCategory(Intent.CATEGORY_BROWSABLE);
						intent.setData(Uri.parse(value));
						startActivity(intent);
					}

				}
			});

			btn_petugas.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String values = map.get("evaluasi");

					Intent in = new Intent(Menu_utama.this,
							ActivityPreEvaluasi.class);
					in.putExtra("nilailink", values);
					startActivity(in);
					Menu_utama.this.overridePendingTransition(anim.fade_in,
							anim.fade_out);

				}
			});

		}
	}

	@Override
	public void onItemSelected(AdapterView arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		for (int i = 0; i < mDotsCount; i++) {
			Menu_utama.mDotsText[i].setTextColor(Color.GRAY);
		}

		Menu_utama.mDotsText[arg2].setTextColor(Color.GREEN);

	}

	@Override
	public void onNothingSelected(AdapterView arg0) {
		// TODO Auto-generated method stub

	}

}
