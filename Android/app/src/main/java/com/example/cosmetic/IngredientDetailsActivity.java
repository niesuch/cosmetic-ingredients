package com.example.cosmetic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IngredientDetailsActivity extends Activity {
	TextView txtIngredientName;
	TextView txtIngredientDesc;
	TextView txtIngredients;
	Button btnReportErrorIngredient;
	String pid;
	JSONParser jsonParser = new JSONParser();
	private static final String url_php = "http://lickn.cba.pl/panel_administracyjny/android-connect/ingredient_details.php";
	JSONArray ingredients = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ingredient_details);
		btnReportErrorIngredient = (Button) findViewById(R.id.btnReportErrorIngredient);

		Intent i = getIntent();
		pid = i.getStringExtra("pid");
		new downloadIngredientDetails().execute();
		
		btnReportErrorIngredient.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent in = new Intent(getApplicationContext(), ReportErrorIngredientActivity.class);
				in.putExtra("pid", pid);
				startActivityForResult(in, 100);			
			}
		});
	}

	class downloadIngredientDetails extends AsyncTask<String, String, String> {

		protected String doInBackground(String... params) {

			runOnUiThread(new Runnable() {
				public void run() {
					try {
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("id", pid));

						JSONObject json = jsonParser.makeHttpRequest(url_php,
								"GET", params);

						if (json.getInt("success") == 1) {
							JSONArray ingredientObj = json
									.getJSONArray("ingredients");

							JSONObject ingredient = ingredientObj.getJSONObject(0);

							txtIngredientName = (TextView) findViewById(R.id.txtIngredientName);
							txtIngredientDesc = (TextView) findViewById(R.id.txtIngredientDesc);

							txtIngredientName.setText(ingredient
									.getString("name"));
							txtIngredientDesc
									.setText(ingredient.getString("desc"));

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}
	}
}
