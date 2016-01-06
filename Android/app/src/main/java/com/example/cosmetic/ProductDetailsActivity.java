package com.example.cosmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.ListActivity;


public class ProductDetailsActivity extends ListActivity {

    TextView txtProductName;
    TextView txtQRCode;
    Button btnReportErrorProduct;
    String pid;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> ingredientList;
    private static final String url_php = "http://lickn.cba.pl/panel_administracyjny/android-connect/product_details.php";
    JSONArray ingredients = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        btnReportErrorProduct = (Button) findViewById(R.id.btnReportErrorProduct);
        Intent i = getIntent();
        pid = i.getStringExtra("pid");
        ingredientList = new ArrayList<HashMap<String, String>>();
        new LoadAllIngredient().execute();
        new DownloadProductDetails().execute();

        btnReportErrorProduct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), ReportErrorProductActivity.class);
                in.putExtra("pid", pid);
                startActivityForResult(in, 100);
            }
        });
    }

    class DownloadProductDetails extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {

            runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("id", pid));

                        JSONObject json = jsonParser.makeHttpRequest(
                                url_php, "GET", params);

                        if (json.getInt("success") == 1) {
                            JSONArray productObj = json
                                    .getJSONArray("products");

                            JSONObject product = productObj.getJSONObject(0);

                            txtProductName = (TextView) findViewById(R.id.txtProductName);
                            txtQRCode = (TextView) findViewById(R.id.txtQRCode);

                            txtProductName.setText(product.getString("name"));
                            txtQRCode.setText(product.getString("qr_code"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }
    }

    class LoadAllIngredient extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", pid));
            JSONObject json = jsonParser.makeHttpRequest(url_php, "GET",
                    params);

            try {
                if (json.getInt("success") == 1) {
                    ingredients = json.getJSONArray("products");

                    for (int i = 0; i < ingredients.length(); i++) {
                        JSONObject c = ingredients.getJSONObject(i);

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put("pid", c.getString("id_ingredient"));
                        map.put("name", c.getString("ingredient_name"));

                        ingredientList.add(map);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                ListView lv = getListView();
                                lv.setOnItemClickListener(new OnItemClickListener() {

                                    public void onItemClick(AdapterView<?> parent,
                                                            View view, int position, long id) {

                                        String pid = ((TextView) view
                                                .findViewById(R.id.pid)).getText()
                                                .toString();

                                        Intent in = new Intent(getApplicationContext(),
                                                IngredientDetailsActivity.class);
                                        in.putExtra("pid", pid);

                                        startActivityForResult(in, 100);
                                    }
                                });
                            }
                        });
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            runOnUiThread(new Runnable() {
                public void run() {
                    ListAdapter adapter = new SimpleAdapter(
                            ProductDetailsActivity.this,
                            ingredientList, R.layout.list_item, new String[]{
                            "pid", "name"}, new int[]{R.id.pid,
                            R.id.name});
                    setListAdapter(adapter);
                }
            });

        }
    }
}
