package com.id.diklatpku;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.id.diklatpku.R;
import com.id.diklatpku.login.PreferenceHelper;

import android.R.anim;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class Activity_DetilUser extends Activity {

	private PreferenceHelper preferenceHelper;
	JSONObject jsonobject;
	JSONArray jsonarray;
	ProgressDialog pdialog;
	Button btnOke;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detiluser);
		preferenceHelper = new PreferenceHelper(this);
		
		new DownloadJSON().execute();
		btnOke = (Button) findViewById(R.id.buttonoke);
		btnOke.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Activity_DetilUser.this.overridePendingTransition(
						anim.fade_in, anim.fade_out);
			}
		});
	}

	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		String nama = "";
		String nip = "";
		String jenkel = "";
		String instansi = "";
		String jabatan = "";
		String notlp = "";
		String email = "";
		String diklat = "";
		String lokasi = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog = new ProgressDialog(Activity_DetilUser.this);
			pdialog.setTitle("Load data pengguna");
			pdialog.setMessage("Loading ...");
			pdialog.setIndeterminate(false);
			pdialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String id= preferenceHelper.getHobby();
			String url = "http://diklat4all.com/android/getdatauser.php?id="+id;
			jsonobject = JSONFunctions.getJSONfromURL(url);

			try {
				jsonarray = jsonobject.getJSONArray("member");
				jsonobject = jsonarray.getJSONObject(0);

				nama = jsonobject.getString("nama").toString().trim();
				nip = jsonobject.getString("nip").toString().trim();
				jenkel = jsonobject.getString("jenkel").toString().trim();
				instansi = jsonobject.getString("instansi").toString().trim();
				jabatan = jsonobject.getString("jabatan").toString().trim();
				notlp = jsonobject.getString("hp").toString().trim();
				email = jsonobject.getString("email").toString().trim();
				diklat = jsonobject.getString("diklat").toString().trim();
				lokasi = jsonobject.getString("lokasi").toString().trim();
				
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {
			// TODO Auto-generated method stub
			TextView txtnama = (TextView) findViewById(R.id.text_nama_title);
			txtnama.setText(nama);

			TextView txtnip = (TextView) findViewById(R.id.text_nip);
			txtnip.setText(nip);

			TextView txtjenkel = (TextView) findViewById(R.id.text_jenkel);
			if (jenkel.equals("1")){
				txtjenkel.setText("Laki-Laki");
			}else{
				txtjenkel.setText("Perempuan");
			}
			
			TextView txtinstansi = (TextView) findViewById(R.id.text_instansi);
			txtinstansi.setText(instansi);

			TextView txtjabatan = (TextView) findViewById(R.id.text_jabatan);
			txtjabatan.setText(jabatan);

			TextView txtnotelepon = (TextView) findViewById(R.id.text_hp);
			txtnotelepon.setText(notlp);

			TextView txtemail = (TextView) findViewById(R.id.text_email);
			txtemail.setText(email);

			TextView txtdiklat = (TextView) findViewById(R.id.text_diklat);
			txtdiklat.setText(diklat);

			TextView txtlokasi = (TextView) findViewById(R.id.text_lokasi);
			txtlokasi.setText(lokasi);
			
			

			pdialog.dismiss();
		}
	}
}
