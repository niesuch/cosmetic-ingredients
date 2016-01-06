package com.example.cosmetic;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class SearchProductActivity extends Activity {
	
	Button btnSearchProductByName;
	Button btnSearchProductByCode;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_product);
		
		btnSearchProductByName = (Button) findViewById(R.id.btnSearchProductByName);
		btnSearchProductByCode = (Button) findViewById(R.id.btnSearchProductByCode);
		
		btnSearchProductByName.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), SearchProductByNameActivity.class);
				startActivity(i);
			}
		});
		
		btnSearchProductByCode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), SearchProductByCodeActivity.class);
				startActivity(i);				
			}
		});
	}
}