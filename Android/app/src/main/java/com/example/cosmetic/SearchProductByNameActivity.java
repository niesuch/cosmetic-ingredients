package com.example.cosmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchProductByNameActivity extends ListActivity {

    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> productList;
    private static String url_php = "http://lickn.cba.pl/panel_administracyjny/android-connect/search_product_name.php";
    Button btnSearch;
    String name;
    String statement;
    String phrase;
    String sT;
    EditText searchText;
    JSONArray products = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_product_by_name);

        productList = new ArrayList<HashMap<String, String>>();
        btnSearch = (Button) findViewById(R.id.searchButton);
        searchText = (EditText) findViewById(R.id.searchText);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        statement = i.getStringExtra("statement");
        phrase = i.getStringExtra("phrase");
        sT = searchText.getText().toString();
        new LoadAllProducts().execute(sT);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String str = searchText.getText().toString();
                new searchProduct().execute(str);
            }
        });

        if (!TextUtils.isEmpty(statement)) {
            viewDialog();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    public void viewDialog() {
        new AlertDialog.Builder(SearchProductByNameActivity.this)
                .setTitle("Product not found")
                .setMessage("Add to database?")
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent in = new Intent(getApplicationContext(),
                                        NewProductActivity.class);
                                in.putExtra("name", phrase);
                                startActivityForResult(in, 100);
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    class LoadAllProducts extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            if (!TextUtils.isEmpty(name)) {
                params.add(new BasicNameValuePair("name", name));
            }

            JSONObject json = jParser.makeHttpRequest(url_php, "GET", params);

            try {
                if (json.getInt("success") == 1) {
                    products = json.getJSONArray("products");

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("pid", c.getString("pid"));
                        map.put("name", c.getString("name"));

                        productList.add(map);
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
                                                ProductDetailsActivity.class);
                                        in.putExtra("pid", pid);

                                        startActivityForResult(in, 100);
                                    }
                                });
                            }
                        });
                    }
                } else {
                    phrase = args[0];

                    Intent in = new Intent(getApplicationContext(),
                            SearchProductByNameActivity.class);
                    in.putExtra("statement", "dialog");
                    in.putExtra("name", args[0]);
                    in.putExtra("phrase", phrase);
                    startActivityForResult(in, 100);
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
                            SearchProductByNameActivity.this,
                            productList, R.layout.list_item, new String[]{
                            "pid", "name"}, new int[]{R.id.pid,
                            R.id.name});
                    setListAdapter(adapter);
                }
            });

        }
    }

    class searchProduct extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {
            Intent in = new Intent(getApplicationContext(),
                    SearchProductByNameActivity.class);
            in.putExtra("name", args[0]);
            startActivityForResult(in, 100);

            return null;
        }
    }
}
