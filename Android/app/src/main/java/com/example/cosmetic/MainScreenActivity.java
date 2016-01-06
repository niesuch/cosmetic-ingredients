package com.example.cosmetic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreenActivity extends Activity{
	
	Button btnSearchIngredient;
	Button btnSearchProduct;
	Button btnEndApp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);

		btnSearchIngredient = (Button) findViewById(R.id.btnSearchIngredient);
		btnSearchProduct = (Button) findViewById(R.id.btnSearchProduct);
		btnEndApp = (Button) findViewById(R.id.btnEndApp);
		
		btnSearchIngredient.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), SearchIngredientByNameActivity.class);
				startActivity(i);
			}
		});
		
		btnSearchProduct.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), SearchProductActivity.class);
				startActivity(i);				
			}
		});
		
		btnEndApp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				finish();
	            System.exit(0);				
			}
		});
	}
}
