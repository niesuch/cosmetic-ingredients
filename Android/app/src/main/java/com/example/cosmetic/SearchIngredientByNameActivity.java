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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchIngredientByNameActivity extends ListActivity {

    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> ingredientList;

    private static String url_all_ingredient = "http://lickn.cba.pl/panel_administracyjny/android-connect/search_ingredient_name.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_INGREDIENT = "ingredients";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    Button btnSearch;
    String name;
    String statement;
    String phrase;
    String sT;
    EditText searchText;

    JSONArray ingredients = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_ingredient_by_name);

        ingredientList = new ArrayList<HashMap<String, String>>();

        btnSearch = (Button) findViewById(R.id.searchButton);
        searchText = (EditText) findViewById(R.id.searchText);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        statement = i.getStringExtra("statement");
        phrase = i.getStringExtra("phrase");
        sT = searchText.getText().toString();
        new LoadAllIngredient().execute(sT);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String str = searchText.getText().toString();
                new searchIngredient().execute(str);
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
        new AlertDialog.Builder(SearchIngredientByNameActivity.this)
                .setTitle("Ingredient not found")
                .setMessage("Add to database?")
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent in = new Intent(getApplicationContext(),
                                        NewIngredientActivity.class);
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

    class LoadAllIngredient extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            if (!TextUtils.isEmpty(name)) {
                params.add(new BasicNameValuePair("name", name));
            }

            JSONObject json = jParser.makeHttpRequest(url_all_ingredient, "GET",
                    params);

            Log.d("All ingredients: ", json.toString());

            try {
                if (json.getInt(TAG_SUCCESS) == 1) {
                    ingredients = json.getJSONArray(TAG_INGREDIENT);

                    for (int i = 0; i < ingredients.length(); i++) {
                        JSONObject c = ingredients.getJSONObject(i);

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(TAG_ID, c.getString(TAG_ID));
                        map.put(TAG_NAME, c.getString(TAG_NAME));

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
                } else {
                    phrase = args[0];

                    Intent in = new Intent(getApplicationContext(),
                            SearchIngredientByNameActivity.class);
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
                            SearchIngredientByNameActivity.this,
                            ingredientList, R.layout.list_item, new String[]{
                            TAG_ID, TAG_NAME}, new int[]{R.id.pid,
                            R.id.name});
                    setListAdapter(adapter);
                }
            });

        }
    }

    class searchIngredient extends AsyncTask<String, String, String> {

        protected String doInBackground(String... args) {

            Intent in = new Intent(getApplicationContext(),
                    SearchIngredientByNameActivity.class);
            in.putExtra("name", args[0]);

            startActivityForResult(in, 100);

            return null;
        }
    }
}
