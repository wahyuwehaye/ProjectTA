package com.arrafi.laboratoriumar_rafi;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
//import android.widget.Toast;

public class OptionLogin extends Activity {

	Button laboran, guru;
    Intent a;
    String url, success;
    SessionManager session;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_option_login);
 
        session = new SessionManager(getApplicationContext());
//        Toast.makeText(getApplicationContext(),
//                "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG)
//                .show();
        laboran = (Button) findViewById(R.id.laboran);
        guru = (Button) findViewById(R.id.mulmed);
 
        laboran.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0) {
                Intent daftar = new Intent(OptionLogin.this, LoginActivity.class);
                startActivity(daftar);
            }
        });
        
        guru.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0) {
                Intent daftar = new Intent(OptionLogin.this, Login1.class);
                startActivity(daftar);
            }
        });
        
    }
}
