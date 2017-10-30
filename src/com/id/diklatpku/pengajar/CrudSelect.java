package com.id.diklatpku.pengajar;

import com.id.diklatpku.login.PreferenceHelper;
import com.id.diklatpku.pengajar.CustomHttpClient;
import com.id.diklatpku.pengajar.ListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class CrudSelect extends ListActivity {

	// Array untuk menampung data yang di ambil dari MySQL
	private String[] kdSelect;
	private String[] Nama;
	private String[] subNama;
	private String[] Gambar;
	private String[] Email;
	private String[] HP;
	private String[] Link;

	private PreferenceHelper preferenceHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Mengeksekusi kelas GetData untuk mengirim permintaan ke MySQL
		preferenceHelper = new PreferenceHelper(this);
		new GetData()
				.execute("http://192.168.56.1/IcaksamaCrud/android/getdata.php");
	}

	// Method untuk mengeluarkan event saat list di click
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		String lklink = Link[position];
		if (lklink.equals("")) {
			Toast.makeText(CrudSelect.this,
					"link pengajar " + lklink + " kosong", Toast.LENGTH_SHORT)
					.show();
		} else {

			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);
			intent.setData(Uri.parse(lklink));
			startActivity(intent);
		}
	}

	// Class GetData yang menuruni kelas AsyncTask untuk melakukan requset data
	// dari internet
	private class GetData extends AsyncTask<String, Void, String> {

		// kiriman dari list kategori data no kategori
		// Intent send = getIntent();
		// String no = send.getExtras().getString("idkategori");
		String id= preferenceHelper.getHobby();
		String url = "http://diklat4all.com/android/getdatapengajar.php?id="+id;
		// --akhir dari kiriman data --//

		// Instansiasi class dialog
		ProgressDialog dialog = new ProgressDialog(CrudSelect.this);
		String Content;
		String Error = null;
		// membuat object class JSONObject yang digunakan untuk menangkap data
		// dengan format json
		JSONObject jObject;
		// instansiasi class ArrayList
		ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();

		@Override
		protected String doInBackground(String... params) {
			try {
				Content = CustomHttpClient.executeHttpPost(url, data);
			} catch (ClientProtocolException e) {
				Error = e.getMessage();
				cancel(true);
			} catch (IOException e) {
				Error = e.getMessage();
				cancel(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Content;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// menampilkan dialog pada saat proses pengambilan data dari
			// internet
			this.dialog.setMessage("Loading Data..");
			this.dialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			// menutup dialog saat pengambilan data selesai
			this.dialog.dismiss();
			if (Error != null) {
				Toast.makeText(getBaseContext(), "Error Connection Internet",
						Toast.LENGTH_LONG).show();
			} else {
				try {
					// instansiasi kelas JSONObject
					jObject = new JSONObject(Content);
					// mengubah json dalam bentuk array
					JSONArray menuitemArray = jObject.getJSONArray("pengajar");

					// mendeskripsikan jumlah array yang bisa di tampung
					kdSelect = new String[menuitemArray.length()];
					Nama = new String[menuitemArray.length()];
					subNama = new String[menuitemArray.length()];
					Gambar = new String[menuitemArray.length()];
					Email = new String[menuitemArray.length()];
					HP = new String[menuitemArray.length()];
					Link = new String[menuitemArray.length()];

					// mengisi variable array dengan data yang di ambil dari
					// internet yang telah dibuah menjadi Array
					for (int i = 0; i < menuitemArray.length(); i++) {
						kdSelect[i] = menuitemArray.getJSONObject(i)
								.getString("id").toString();
						Nama[i] = menuitemArray.getJSONObject(i)
								.getString("nama").toString();
						subNama[i] = menuitemArray.getJSONObject(i)
								.getString("nip").toString();
						Email[i] = menuitemArray.getJSONObject(i)
								.getString("email").toString();

						HP[i] = menuitemArray.getJSONObject(i).getString("hp")
								.toString();
						Link[i] = menuitemArray.getJSONObject(i)
								.getString("link").toString();
						Gambar[i] = "http://diklat4all.com/upload/"
								+ menuitemArray.getJSONObject(i)
										.getString("gambar").toString();

					}
					// instansiasi class ListAdapter (Buka class ListAdapter)
					ListAdapter adapter = new ListAdapter(getBaseContext(),
							Nama, subNama, Email, HP, Gambar, Link);
					setListAdapter(adapter);
				} catch (JSONException ex) {
					Logger.getLogger(CrudSelect.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}
	}

}
