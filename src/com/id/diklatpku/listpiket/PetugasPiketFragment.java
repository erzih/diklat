package com.id.diklatpku.listpiket;

import com.id.diklatpku.R;
import com.id.diklatpku.listpiket.ListAdapterPiket;
import com.id.diklatpku.login.PreferenceHelper;
import com.id.diklatpku.pengajar.CrudSelect;
import com.id.diklatpku.pengajar.CustomHttpClient;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class PetugasPiketFragment extends ListActivity {

	private PreferenceHelper preferenceHelper;
	private String[] nama;
	private String[] hape;
	private String[] gambar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preferenceHelper = new PreferenceHelper(this);
		new GetData().execute();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// String nohp = hape[position];
		//
		// Intent callIntent = new Intent(Intent.ACTION_CALL);
		// callIntent.setData(Uri.parse("tel:"+nohp));
		// startActivity(callIntent);

	}

	private class GetData extends AsyncTask<String, Void, String> {

		String id = preferenceHelper.getHobby();
		String url = "http://www.diklat4all.com/android/getdataPetugasPiket.php?id="
				+ id;

		ProgressDialog dialog = new ProgressDialog(PetugasPiketFragment.this);
		String Content;
		String Error = null;
		JSONObject jObject;
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
			// internet
			this.dialog.setMessage("Loading Data..");
			this.dialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			this.dialog.dismiss();
			if (Error != null) {
				Toast.makeText(getBaseContext(), "Error Connection Internet",
						Toast.LENGTH_LONG).show();
			} else {
				try {
					// instansiasi kelas JSONObject
					jObject = new JSONObject(Content);
					// mengubah json dalam bentuk array
					JSONArray menuitemArray = jObject.getJSONArray("datapiket");

					// mendeskripsikan jumlah array yang bisa di tampung
					nama = new String[menuitemArray.length()];
					hape = new String[menuitemArray.length()];
					gambar = new String[menuitemArray.length()];

					// mengisi variable array dengan data yang di ambil dari
					// internet yang telah dibuah menjadi Array
					for (int i = 0; i < menuitemArray.length(); i++) {
						nama[i] = menuitemArray.getJSONObject(i)
								.getString("nama").toString();
						hape[i] = menuitemArray.getJSONObject(i)
								.getString("hp").toString();
						gambar[i] = "http://diklat4all.com/upload/"
								+ menuitemArray.getJSONObject(i)
										.getString("foto").toString();

					}
					// instansiasi class ListAdapter (Buka class ListAdapter)
					ListAdapterPiket adapter = new ListAdapterPiket(
							getBaseContext(), nama, hape, gambar);

					setListAdapter(adapter);

				} catch (JSONException ex) {
					Logger.getLogger(PetugasPiketFragment.class.getName()).log(
							Level.SEVERE, null, ex);
				}

			}

		}
	}

}
