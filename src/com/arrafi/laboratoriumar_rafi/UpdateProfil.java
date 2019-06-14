package com.arrafi.laboratoriumar_rafi;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateProfil extends Activity implements View.OnClickListener{
	
	//Defining views
	private EditText editIDGR;
	private EditText editNamaGR;
	private EditText editJKGR;
	private EditText editNipGR;
	private EditText editTmtGR;
	private EditText editWewenangGR;
	private EditText editKelGR;
	private EditText editUserGR;
	private EditText editPassGR;
	
	private Button btnUpdGR;
	private Button btnCnclGR;
	
	private String nmGR;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.update_profil);
		
		Intent intentGR = getIntent();
		
		nmGR = intentGR.getStringExtra(Config.ID_PROFILGR);
		
		editIDGR = (EditText)findViewById(R.id.editIDGR);
		editNamaGR = (EditText)findViewById(R.id.editNamaGR);
		editJKGR = (EditText)findViewById(R.id.editJenisGR);
		editNipGR = (EditText)findViewById(R.id.editNipGR);
		editTmtGR = (EditText)findViewById(R.id.editTMTGR);
		editWewenangGR = (EditText)findViewById(R.id.editWewenangGR);
		editKelGR = (EditText)findViewById(R.id.editKelGR);
		editUserGR = (EditText)findViewById(R.id.editUserGR);
		editPassGR = (EditText)findViewById(R.id.editPassGR);
		
		btnCnclGR = (Button)findViewById(R.id.btnBackGR);
		btnCnclGR.setOnClickListener(this);
		btnUpdGR = (Button)findViewById(R.id.btnupdateGR);
		btnUpdGR.setOnClickListener(this);
		
		editIDGR.setText(nmGR);
		
		getProfilGR();
	}

	private void getProfilGR(){
		class GetPRofGR extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(UpdateProfil.this,"Fetching...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String sp) {
				// TODO Auto-generated method stub
				super.onPostExecute(sp);
				loading.dismiss();
                showPRofilGR(sp);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rhp = new RequestHandler();
				String sp = rhp.sendGetRequestParam(Config.URL_GET_PROFILGR, nmGR);
				return sp;
			}
			
		}
		GetPRofGR gjg = new GetPRofGR();
		gjg.execute();
	}
	
	private void showPRofilGR(String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
			JSONObject c = result.getJSONObject(0);
			String namaGR = c.getString(Config.TAG_NAMA_GURU);
			String JKGR = c.getString(Config.TAG_JK_GURU);
			String nipGR = c.getString(Config.TAG_NIP_GURU);
			String tmtGR = c.getString(Config.TAG_TMT_GURU);
			String wewenangGR = c.getString(Config.TAG_WWNG_GURU);
			String kelGR = c.getString(Config.TAG_KELAS_GURU);
			String userGR = c.getString(Config.TAG_USER_GURU);
			String pasGR = c.getString(Config.TAG_PASS_GURU);
			
			editNamaGR.setText(namaGR);
			editJKGR.setText(JKGR);
			editNipGR.setText(nipGR);
			editTmtGR.setText(tmtGR);
			editWewenangGR.setText(wewenangGR);
			editKelGR.setText(kelGR);
			editUserGR.setText(userGR);
			editPassGR.setText(pasGR);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void updateProfilGR(){
		final String namaGR = editNamaGR.getText().toString().trim();
		final String jkGR = editJKGR.getText().toString().trim();
		final String nipGR = editNipGR.getText().toString().trim();
		final String tmtGR = editTmtGR.getText().toString().trim();
		final String wewenangGR = editWewenangGR.getText().toString().trim();
		final String klsGR = editKelGR.getText().toString().trim();
		final String userGR = editUserGR.getText().toString().trim();
		final String passGR = editPassGR.getText().toString().trim();
		
		class UpdateProfGR extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(UpdateProfil.this,"Updating...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                Toast.makeText(UpdateProfil.this,s,Toast.LENGTH_LONG).show();
//                Intent a = new Intent(ViewJadwal.this, ViewAllJadwal.class);
//                startActivity(a);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				HashMap<String,String> hashMap = new HashMap<>();
				hashMap.put(Config.KEY_ID_GURU, nmGR);
				hashMap.put(Config.KEY_NAMA_GURU, namaGR);
				hashMap.put(Config.KEY_JK_GURU, jkGR);
				hashMap.put(Config.KEY_NIP_GURU, nipGR);
				hashMap.put(Config.KEY_TMT_GURU, tmtGR);
				hashMap.put(Config.KEY_WWNG_GURU, wewenangGR);
				hashMap.put(Config.KEY_KELAS_GURU, klsGR);
				hashMap.put(Config.KEY_USER_GURU, userGR);
				hashMap.put(Config.KEY_PASS_GURU, passGR);
				
				RequestHandler rh = new RequestHandler();
				String s = rh.sendPostRequest(Config.URL_UPDATE_PROFILGR, hashMap);
				
				return s;
			}
			
		}
		UpdateProfGR uj = new UpdateProfGR();
		uj.execute();
	}
	
	private void deleteProfilGR(){
		class DeleteGR extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(UpdateProfil.this, "Deleting...", "Wait...", false, false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                Toast.makeText(UpdateProfil.this, s, Toast.LENGTH_LONG).show();
//                Intent a = new Intent(ViewJadwal.this, ViewAllJadwal.class);
//                startActivity(a);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_DELETE_JWL, nmGR);
				return s;
			}
			
		}
		DeleteGR dj = new DeleteGR();
		dj.execute();
	}
	
	private void confirmDeleteGR(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this Account?");
        
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				deleteProfilGR();
				startActivity(new Intent(UpdateProfil.this, MainActivity.class));
			}
		});
        
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
        
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btnCnclGR){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
		
		if(v == btnUpdGR){
            updateProfilGR();
        }
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,MainActivity.class));
		finish();
	}
}
