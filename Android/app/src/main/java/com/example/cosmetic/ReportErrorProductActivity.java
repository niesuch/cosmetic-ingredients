package com.example.cosmetic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ReportErrorProductActivity extends Activity {
	String pid;
	JSONParser jsonParser = new JSONParser();
	EditText errorDesc;

	private static String url_php = "http://lickn.cba.pl/panel_administracyjny/android-connect/request_modification_product.php";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_error_product);

		errorDesc = (EditText) findViewById(R.id.errorDesc);

		Intent i = getIntent();
		pid = i.getStringExtra("pid");

		Button btnSaveRequest = (Button) findViewById(R.id.btnSaveRequest);

		btnSaveRequest.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String desc = errorDesc.getText().toString();
				new SendRequest().execute(desc);
			}
		});
	}

	class SendRequest extends AsyncTask<String, String, String> {

		protected String doInBackground(String... args) {


			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_desc", args[0]));
			params.add(new BasicNameValuePair("id_product", pid));

			JSONObject json = jsonParser.makeHttpRequest(url_php,
					"POST", params);

			try {
				if (json.getInt("success") == 1) {
					Intent i = new Intent(getApplicationContext(),
							SearchProductByNameActivity.class);
					startActivity(i);

					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
	}
}
