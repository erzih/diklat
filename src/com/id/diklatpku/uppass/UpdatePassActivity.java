package com.id.diklatpku.uppass;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import com.id.diklatpku.R;
import com.id.diklatpku.koment.JSONParserdata;
import com.id.diklatpku.koment.KomentarActivity;
import com.id.diklatpku.koment.KomentarActivity.input;
import com.id.diklatpku.login.PreferenceHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class UpdatePassActivity extends Activity {

	private PreferenceHelper preferenceHelper;

	EditText pass_one;
	EditText pas_two;
	TextView error;
	TextView successlah;
	Button btnSubmit;

	JSONParserdata jParser = new JSONParserdata();
	ProgressDialog pDialog;
	private static String url = "http://diklat4all.com/android/updatepassuser.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatepasswrod);

		preferenceHelper = new PreferenceHelper(this);

		pass_one = (EditText) findViewById(R.id.txt_first_pass);
		pas_two = (EditText) findViewById(R.id.txt_second_pass);

		error = (TextView) findViewById(R.id.txt_message_error);
		successlah = (TextView) findViewById(R.id.txt_message_success);

		error.setVisibility(View.GONE);
		successlah.setVisibility(View.GONE);

		btnSubmit = (Button) findViewById(R.id.btn_update_password);
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String valone = pass_one.getText().toString();
				String valtwo = pas_two.getText().toString();
				if (valone.trim().equals("") || valtwo.trim().equals("")) {
					Toast.makeText(getApplicationContext(),
							"pasword tidak boleh kosong!", Toast.LENGTH_LONG)
							.show();
				} else if (!valone.equals(valtwo)) {
					error.setVisibility(View.VISIBLE);
					successlah.setVisibility(View.GONE);
				} else {
					new input().execute();
				}
			}
		});

	}

	public class input extends AsyncTask<String, String, String> {

		String success;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(UpdatePassActivity.this);
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			String strIdPeserta = preferenceHelper.getHobby();
			String strPassOne = pass_one.getText().toString();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("kdpeserta", strIdPeserta));
			params.add(new BasicNameValuePair("passone", strPassOne));

			JSONObject json = jParser.makeHttpRequest(url, "POST", params);

			try {
				success = json.getString("success");

			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Error",
						Toast.LENGTH_LONG).show();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();

			if (success.equals("1")) {
				// Toast.makeText(getApplicationContext(),
				// "update password berhasil!", Toast.LENGTH_LONG).show();
				successlah.setVisibility(View.VISIBLE);
				error.setVisibility(View.GONE);

			} else {
				Toast.makeText(getApplicationContext(),
						"komentar gagal terkirim!", Toast.LENGTH_LONG).show();

			}
		}
	}

}
