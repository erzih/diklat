package com.id.diklatpku.koment;


import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import com.id.diklatpku.R;
import com.id.diklatpku.login.PreferenceHelper;
 
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class KomentarActivity extends Activity {
 
	private PreferenceHelper preferenceHelper;
    EditText komen;
    Button submit;
    JSONParserdata jParser = new JSONParserdata();
    ProgressDialog pDialog;
    private static String url = "http://diklat4all.com/android/insertkomentar.php";
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komentar);
        preferenceHelper = new PreferenceHelper(this);
 
        komen = (EditText)findViewById(R.id.text_komentar);
 
        submit = (Button)findViewById(R.id.btnkirimkomentar);
 
        submit.setOnClickListener(new View.OnClickListener()
        {
 
            @Override
            public void onClick(View arg0) {
            	String kom = komen.getText().toString();
            	if (kom.trim().equals("")){
            		Toast.makeText(getApplicationContext(), "komentar masih kosong!", Toast.LENGTH_LONG).show();
            	}else{
            		new input().execute();
            	}
                
            }
        });
    }
 
 
    public class input extends AsyncTask<String, String, String>
    {
 
        String success;
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(KomentarActivity.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.show();
        }
 
        @Override
        protected String doInBackground(String... arg0) {
            String strIdPeserta = preferenceHelper.getHobby();
            String strKomentar = komen.getText().toString();
 
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("kdpeserta", strIdPeserta));
            params.add(new BasicNameValuePair("isipesan", strKomentar));
            
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
 
            if (success.equals("1")) 
            {
                Toast.makeText(getApplicationContext(), "komentar telah terkirim!", Toast.LENGTH_LONG).show();
            } 
            else
            {
                Toast.makeText(getApplicationContext(), "komentar gagal terkirim!", Toast.LENGTH_LONG).show();
 
            }
        }
    }
}