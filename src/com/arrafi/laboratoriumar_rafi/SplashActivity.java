package com.arrafi.laboratoriumar_rafi;

import java.util.HashMap;

import com.arrafi.laboratoriumar_rafi.Loading.LoadingTaskFinishedListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashActivity extends Activity implements LoadingTaskFinishedListener{
	private SessionManager session;
	private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar_Horizontal);
		new Loading(progressBar, this).execute("");
		session = new SessionManager(getApplicationContext());
		HashMap<String, String> user = session.getUserDetails();
		username = user.get(SessionManager.KEY_USER);
	}

	@Override
	public void onTaskFinished() {
		// TODO Auto-generated method stub
		completeSplash();
	}
	
	private void completeSplash(){
        startApp();
        finish(); // Don't forget to finish this Splash Activity so the user can't return to it!
    }
	
	private void startApp() {
		if(!session.isLoggedIn()){
			Intent intent = new Intent(SplashActivity.this, Login1.class);
	        startActivity(intent);
		}else{
			if((username.equals("ina")) || (username.equals("ayudewi")) || (username.equals("catherien")) || (username.equals("joko")) || (username.equals("wahyu"))){
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		        startActivity(intent);
			}else if((username.equals("tisna")) || (username.equals("rachmat")) || (username.equals("sari")) || (username.equals("tatang")) || (username.equals("w"))){
				Intent intent = new Intent(SplashActivity.this, LaboranActivity.class);
		        startActivity(intent);
			}
			
		}
        
    }
}
