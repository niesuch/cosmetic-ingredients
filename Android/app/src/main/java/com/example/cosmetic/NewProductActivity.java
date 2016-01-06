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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewProductActivity extends Activity {
	JSONParser jsonParser = new JSONParser();
	EditText productName;
	String name;

	private static String url_php = "http://lickn.cba.pl/panel_administracyjny/android-connect/request_add_product.php";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_new_product);

		productName = (EditText) findViewById(R.id.productName);
		Intent i = getIntent();
		name = i.getStringExtra("name");
		if (!TextUtils.isEmpty(name)) {
			productName.setText(name);
		}
		
		Button btnSaveRequest = (Button) findViewById(R.id.btnSaveRequest);

		btnSaveRequest.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String name = productName.getText().toString();
				new SendRequest().execute(name);
			}
		});
	}

	class SendRequest extends AsyncTask<String, String, String> {

		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", args[0]));

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
