package com.id.diklatpku;

import android.R.anim;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import com.id.diklatpku.R;
import com.id.diklatpku.login.AndyConstants;
import com.id.diklatpku.login.AndyUtils;
import com.id.diklatpku.login.HttpRequest;
import com.id.diklatpku.login.ParseContent;
import com.id.diklatpku.login.PreferenceHelper;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;

public class LoginActivity extends Activity {

	private EditText etusername, etpassword;
	private Button btnlogin;
	private ParseContent parseContent;
	private final int LoginTask = 1;
	private PreferenceHelper preferenceHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		ActionBar myActionBar = getActionBar();

		// For hiding android actionbar
		myActionBar.hide();
		parseContent = new ParseContent(this);
		preferenceHelper = new PreferenceHelper(this);

		etusername = (EditText) findViewById(R.id.etus);
		etpassword = (EditText) findViewById(R.id.etpass);

		btnlogin = (Button) findViewById(R.id.btnLogin);

		btnlogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					login();
					LoginActivity.this.overridePendingTransition(
							anim.fade_in, anim.fade_out);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void login() throws IOException, JSONException {

		if (!AndyUtils.isNetworkAvailable(LoginActivity.this)) {
			Toast.makeText(LoginActivity.this, "Internet is required!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		AndyUtils.showSimpleProgressDialog(LoginActivity.this);
		final HashMap<String, String> map = new HashMap<String, String>();
		map.put(AndyConstants.Params.USERNAME, etusername.getText().toString());
		map.put(AndyConstants.Params.PASSWORD, etpassword.getText().toString());
		new AsyncTask<Void, Void, String>() {
			protected String doInBackground(Void[] params) {
				String response = "";
				try {
					HttpRequest req = new HttpRequest(
							AndyConstants.ServiceType.LOGIN);
					response = req.prepare(HttpRequest.Method.POST)
							.withData(map).sendAndReadString();
				} catch (Exception e) {
					response = e.getMessage();
				}
				return response;
			}

			protected void onPostExecute(String result) {
				// do something with response
				Log.d("newwwss", result);
				onTaskCompleted(result, LoginTask);
			}
		}.execute();
	}

	
	
	private void onTaskCompleted(String response, int task) {
		Log.d("responsejson", response.toString());
		AndyUtils.removeSimpleProgressDialog(); // will remove progress dialog
		switch (task) {
		case LoginTask:

			if (parseContent.isSuccess(response)) {

				parseContent.saveInfo(response);
				Toast.makeText(LoginActivity.this, "Login Successfully!",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LoginActivity.this, Menu_utama.class);
				
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				
				this.finish();

			} else {
				Toast.makeText(LoginActivity.this,
						parseContent.getErrorMessage(response),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}