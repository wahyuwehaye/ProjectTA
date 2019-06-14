package com.arrafi.laboratoriumar_rafi;

import java.util.ArrayList;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateProfLab extends Activity implements View.OnClickListener{
	//Defining views
		private EditText editIDLB;
		private EditText editNamaLB;
		private EditText editJKLB;
		private EditText editNipLB;
		private EditText editTmtLB;
		private EditText editJabatanLB;
		private EditText editUserLB;
		private EditText editPassLB;
		private Button btnUpdLB;
		private Button btnCnclLB;
		
		private String nmLB, username, namaPeg, hasilNama;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.update_prof_lab);
		
		Intent intentGR = getIntent();
		
		nmLB = intentGR.getStringExtra(Config.ID_PROFILLB);
		username = intentGR.getStringExtra(Config.USERNAME);
		namaPeg = intentGR.getStringExtra(Config.NAMAPEG);
		
		String[] tempNama = namaPeg.split(" ");
        ArrayList<String> teks_lengkap= new ArrayList<String>();
        hasilNama="";
  
        for(int i=0; i < tempNama.length ; i++)
            teks_lengkap.add(tempNama[i].trim());
  
        for(int i=0; i < tempNama.length ; i++)
        	hasilNama = hasilNama + teks_lengkap.get(i)+"%20";
		
		editIDLB = (EditText)findViewById(R.id.editIDLB);
		editNamaLB = (EditText)findViewById(R.id.editNamaLB);
		editJKLB = (EditText)findViewById(R.id.editJenisLB);
		editNipLB = (EditText)findViewById(R.id.editNipLB);
		editTmtLB = (EditText)findViewById(R.id.editTMTLB);
		editJabatanLB = (EditText)findViewById(R.id.editJabatanLB);
		editUserLB = (EditText)findViewById(R.id.editUserLB);
		editPassLB = (EditText)findViewById(R.id.editPassLB);
		
		btnCnclLB = (Button)findViewById(R.id.btnBackLB);
		btnCnclLB.setOnClickListener(this);
		btnUpdLB = (Button)findViewById(R.id.btnupdateLB);
		btnUpdLB.setOnClickListener(this);
		
//		editIDLB.setText(nmLB);
		
		getProfilLB();
	}
	

	private void getProfilLB(){
		class GetPRofLB extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(UpdateProfLab.this,"Fetching...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String sp) {
				// TODO Auto-generated method stub
				super.onPostExecute(sp);
				loading.dismiss();
                showPRofilLB(sp);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rhp = new RequestHandler();
				String sp = rhp.sendGetRequestParam(Config.URL_GET_PROFLB, hasilNama);
				return sp;
			}
			
		}
		GetPRofLB gjg = new GetPRofLB();
		gjg.execute();
	}
	
	private void showPRofilLB(String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
			JSONObject c = result.getJSONObject(0);
			String idLB = c.getString(Config.KEY_ID_PEG);
			String namaLB = c.getString(Config.TAG_NAMA_PEG);
			String JKLB = c.getString(Config.TAG_JK_PEG);
			String nipLB = c.getString(Config.TAG_NIP_PEG);
			String tmtLB = c.getString(Config.TAG_TMT_PEG);
			String jabatanLB = c.getString(Config.TAG_JBTN_PEG);
			String userLB = c.getString(Config.TAG_USER_PEG);
			String pasLB = c.getString(Config.TAG_PASS_PEG);
			
			editIDLB.setText(idLB);
			editNamaLB.setText(namaLB);
			editJKLB.setText(JKLB);
			editNipLB.setText(nipLB);
			editTmtLB.setText(tmtLB);
			editJabatanLB.setText(jabatanLB);
			editUserLB.setText(userLB);
			editPassLB.setText(pasLB);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void updateProfilLB(){
		final String idLB = editIDLB.getText().toString().trim();
		final String namaLB = editNamaLB.getText().toString().trim();
		final String jkLB = editJKLB.getText().toString().trim();
		final String nipLB = editNipLB.getText().toString().trim();
		final String tmtLB = editTmtLB.getText().toString().trim();
		final String jabatanLB = editJabatanLB.getText().toString().trim();
		final String userLB = editUserLB.getText().toString().trim();
		final String passLB = editPassLB.getText().toString().trim();
		
		class UpdateProfLB extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(UpdateProfLab.this,"Updating...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                Toast.makeText(UpdateProfLab.this,s,Toast.LENGTH_LONG).show();
//                Intent a = new Intent(ViewJadwal.this, ViewAllJadwal.class);
//                startActivity(a);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				HashMap<String,String> hashMap = new HashMap<>();
				hashMap.put(Config.KEY_ID_PEG, idLB);
				hashMap.put(Config.KEY_NAMA_PEG, namaLB);
				hashMap.put(Config.KEY_JK_PEG, jkLB);
				hashMap.put(Config.KEY_NIP_PEG, nipLB);
				hashMap.put(Config.KEY_TMT_PEG, tmtLB);
				hashMap.put(Config.KEY_JBTN_PEG, jabatanLB);
				hashMap.put(Config.KEY_USER_PEG, userLB);
				hashMap.put(Config.KEY_PASS_PEG, passLB);
				
				RequestHandler rh = new RequestHandler();
				String s = rh.sendPostRequest(Config.URL_UPDATE_PROFLB, hashMap);
				
				return s;
			}
			
		}
		UpdateProfLB uj = new UpdateProfLB();
		uj.execute();
	}
	
	private void deleteProfilLB(){
		class DeleteGR extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(UpdateProfLab.this, "Deleting...", "Wait...", false, false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                Toast.makeText(UpdateProfLab.this, s, Toast.LENGTH_LONG).show();
//                Intent a = new Intent(ViewJadwal.this, ViewAllJadwal.class);
//                startActivity(a);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_DELETE_JWL, nmLB);
				return s;
			}
			
		}
		DeleteGR dj = new DeleteGR();
		dj.execute();
	}
	
	private void confirmDeleteLB(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this Account?");
        
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				deleteProfilLB();
				startActivity(new Intent(UpdateProfLab.this, LaboranActivity.class));
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
		if(v == btnCnclLB){
            startActivity(new Intent(this,LaboranActivity.class));
            finish();
        }
		
		if(v == btnUpdLB){
//            updateProfilLB();
        }
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,LaboranActivity.class));
		finish();
	}
}
