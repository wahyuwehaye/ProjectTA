package com.arrafi.laboratoriumar_rafi;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class DetailNilai extends Activity implements View.OnClickListener{
	
	//Defining views
		private TextView textIDNil;
		private TextView textJenisNil;
		private TextView textNil;
		private TextView textKetNil;
		private TextView textNamaGR;
		private TextView textNamaLB;
		private TextView namaDetGR;
		private Button backNil;
		String namaNGR;
		private SessionManager session;
		
		private String no;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.detail_nilai);
		
		Intent intent = getIntent();
		
		no = intent.getStringExtra(Config.NIL_ID);
		
		textIDNil = (TextView)findViewById(R.id.DetailIDNIL);
		textJenisNil = (TextView)findViewById(R.id.DetailJenisNil);
		textNil = (TextView)findViewById(R.id.DetailNilai);
		textKetNil = (TextView)findViewById(R.id.DetailKeter);
		textNamaGR = (TextView)findViewById(R.id.DetailNilGR);
		textNamaLB = (TextView)findViewById(R.id.DetailNilLB);
		namaDetGR = (TextView)findViewById(R.id.namaSDGDT);
		
		backNil = (Button)findViewById(R.id.backDetNil);
		backNil.setOnClickListener(this);
		
		textIDNil.setText(no);
		
		getDetailNilai();
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaNGR = user.get(SessionManager.KEY_NAME);
		namaDetGR.setText(namaNGR);
	}

	private void getDetailNilai(){
		class GetJadwal extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(DetailNilai.this,"Fetching...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                showJadwal(s);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_GET_DETAIL_NILAI, no);
				return s;
			}
			
		}
		GetJadwal gj = new GetJadwal();
		gj.execute();
	}
	
	private void showJadwal(String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
			JSONObject c = result.getJSONObject(0);
			String idNil = c.getString(Config.TAG_ID_NILAI);
			String jenisNil = c.getString(Config.TAG_JENIS_NILAI);
			String nil = c.getString(Config.TAG_NILAI);
			String ket = c.getString(Config.TAG_KET_NILAI);
			String namaGR = c.getString(Config.TAG_NILAI_GR);
			String namaLB = c.getString(Config.TAG_NILAI_LB);
			
			textJenisNil.setText(jenisNil);
			textNil.setText(nil);
			textKetNil.setText(ket);
			textNamaGR.setText(namaGR);
			textNamaLB.setText(namaLB);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == backNil){
            startActivity(new Intent(this,ViewNilaiLab.class));
            finish();
        }
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,ViewNilaiLab.class));
		finish();
	}
}
