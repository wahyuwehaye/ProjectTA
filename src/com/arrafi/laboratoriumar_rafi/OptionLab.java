package com.arrafi.laboratoriumar_rafi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class OptionLab extends Activity {
	Button ipa, mulmed;
    Intent a;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.option_lab);
		
		ipa = (Button)findViewById(R.id.ipa);
		mulmed = (Button)findViewById(R.id.mulmed);
		
		mulmed.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent masukmul = new Intent(OptionLab.this, OptionLogin.class);
				startActivity(masukmul);
			}
		});
		
		ipa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Anda Menekan Tombol Lab IPA", Toast.LENGTH_SHORT).show();
			}
		});

	}
	
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,LaboranActivity.class));
		finish();
	}

	
}
