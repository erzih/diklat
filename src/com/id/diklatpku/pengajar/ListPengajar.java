package com.id.diklatpku.pengajar;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.id.diklatpku.R;
import com.id.diklatpku.JSONParser;
import com.id.diklatpku.login.PreferenceHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListPengajar extends Activity {

	private PreferenceHelper preferenceHelper;
	ListView lv;
	ProgressDialog pDialog;
	JSONArray contacts = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		preferenceHelper = new PreferenceHelper(this);
		setContentView(R.layout.list);
		lv = (ListView) findViewById(R.id.list);
		new AmbilDataDokter().execute();
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private class AmbilDataDokter extends AsyncTask<String, String, String> {

		String id = preferenceHelper.getHobby();

		String url = "http://diklat4all.com/android/getdatapengajar.php?id="
				+ id;

		ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ListPengajar.this);
			pDialog.setMessage("Loading Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {

			JSONParser jParser = new JSONParser();
			JSONObject json = jParser.getJSONFromUrl(url);

			try {
				contacts = json.getJSONArray("pengajar");
				for (int i = 0; i < contacts.length(); i++) {

					JSONObject c = contacts.getJSONObject(i);
					HashMap<String, String> map = new HashMap<String, String>();

					String id = c.getString("id").trim();
					String nama = c.getString("nama").trim();
					String nip = c.getString("nip").trim();
					String link = c.getString("link").trim();
					String gambar = c.getString("gambar").trim();
					map.put("id", id);
					map.put("nama", nama);
					map.put("nip", nip);
					map.put("link", link);
					map.put("gambar", gambar);

					contactList.add(map);
				}

			} catch (JSONException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			ListAdapter adapter2 = new SimpleAdapter(getApplicationContext(),
					contactList, R.layout.list_info, new String[] { "nama",
							"nip" }, new int[] { R.id.id, R.id.tipe });
			lv.setAdapter(adapter2);

			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					HashMap<String, String> o = (HashMap<String, String>) lv
							.getItemAtPosition(position);

					Toast.makeText(ListPengajar.this,
							"review " + o.get("nama"), Toast.LENGTH_SHORT)
							.show();

					String lklink = o.get("link");
					if (lklink.equals("")) {
						Toast.makeText(ListPengajar.this,
								"link pengajar " + o.get("nama") + " kosong",
								Toast.LENGTH_SHORT).show();
					} else {

						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_VIEW);
						intent.addCategory(Intent.CATEGORY_BROWSABLE);
						intent.setData(Uri.parse(lklink));
						startActivity(intent);
					}
				}

			});
		}
	}
}
