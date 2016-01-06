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

public class NewIngredientActivity extends Activity {
	JSONParser jsonParser = new JSONParser();
	EditText desc;
	EditText name;

	private static String url_php = "http://lickn.cba.pl/panel_administracyjny/android-connect/request_add_ingredient.php";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_new_ingredient);

		desc = (EditText) findViewById(R.id.desc);
		name = (EditText) findViewById(R.id.name);

		Button btnSaveRequest = (Button) findViewById(R.id.btnSaveRequest);

		btnSaveRequest.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String user_desc = desc.getText().toString();
				String user_name = name.getText().toString();
				new SendRequest().execute(user_desc, user_name);
			}
		});
	}

	class SendRequest extends AsyncTask<String, String, String> {

		protected String doInBackground(String... args) {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("desc", args[0]));
			params.add(new BasicNameValuePair("name", args[1]));

			JSONObject json = jsonParser.makeHttpRequest(url_php,
					"POST", params);

			try {
				if (json.getInt("success") == 1) {
					Intent i = new Intent(getApplicationContext(),
							SearchIngredientByNameActivity.class);
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
