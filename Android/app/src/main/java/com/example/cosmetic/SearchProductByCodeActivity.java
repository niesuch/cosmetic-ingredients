package com.example.cosmetic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SearchProductByCodeActivity extends Activity {

	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	JSONParser jParser = new JSONParser();
	private static String url_php = "http://lickn.cba.pl/panel_administracyjny/android-connect/search_product_qr_code.php";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_product_by_code);
	}

	public void scanBar(View v) {
		try {
			Intent intent = new Intent(ACTION_SCAN);
			intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException anfe) {
			showDialog(SearchProductByCodeActivity.this,
					"QR Scanner not found", "Download?",
					"Yes", "No").show();
		}
	}

	private static AlertDialog showDialog(final Activity act,
			CharSequence title, CharSequence msg, CharSequence buttonYes,
			CharSequence buttonNo) {
		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
		downloadDialog.setTitle(title);
		downloadDialog.setMessage(msg);
		downloadDialog.setPositiveButton(buttonYes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
						Uri uri = Uri.parse("market://search?q=pname:"
								+ "com.google.zxing.client.android");
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						try {
							act.startActivity(intent);
						} catch (ActivityNotFoundException anfe) {

						}
					}
				});
		downloadDialog.setNegativeButton(buttonNo,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
					}
				});
		return downloadDialog.show();
	}

	public void viewNewProductDialog() {
		new AlertDialog.Builder(SearchProductByCodeActivity.this)
				.setTitle("Product not found")
				.setMessage("Add to database?")
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
								startActivity(i);
							}
						})
				.setNegativeButton(android.R.string.no,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				// String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				// Toast toast = Toast.makeText(this, "Content:" + contents +
				// " Format:" + format, Toast.LENGTH_LONG);
				// toast.show();

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("qr_code", contents));

				JSONObject json = jParser.makeHttpRequest(url_php, "GET",
						params);
				try {
					if (json.getInt("success") == 1) {
						JSONArray productObj = json.getJSONArray("products");
						JSONObject product = productObj.getJSONObject(0);

						Intent in = new Intent(getApplicationContext(),
								ProductDetailsActivity.class);
						in.putExtra("pid", product.getString("id"));

						startActivityForResult(in, 100);
					} else {
						viewNewProductDialog();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
