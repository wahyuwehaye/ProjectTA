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

public class DetailNilaiLab extends Activity implements View.OnClickListener{
	//Defining views
			private TextView textIDNilLAB;
			private TextView textJenisNilLAB;
			private TextView textNilLAB;
			private TextView textKetNilLAB;
			private TextView textNamaGRLAB;
			private TextView textNamaLBLAB;
			private TextView namaDetGRLAB;
			private Button backNilLAB;
			String namaNGRLAB;
			private SessionManager session;
			
			private String no;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.detail_nilai_lab);
		
		Intent intent = getIntent();
		
		no = intent.getStringExtra(Config.NIL_ID);
		
		textIDNilLAB = (TextView)findViewById(R.id.DetailIDNILLAB);
		textJenisNilLAB = (TextView)findViewById(R.id.DetailJenisNilLAB);
		textNilLAB = (TextView)findViewById(R.id.DetailNilaiLAB);
		textKetNilLAB = (TextView)findViewById(R.id.DetailKeterLAB);
		textNamaGRLAB = (TextView)findViewById(R.id.DetailNilGRLAB);
		textNamaLBLAB = (TextView)findViewById(R.id.DetailNilLBLAB);
		namaDetGRLAB = (TextView)findViewById(R.id.namaSDGDTLAB);
		
		backNilLAB = (Button)findViewById(R.id.backDetNilLAB);
		backNilLAB.setOnClickListener(this);
		
		textIDNilLAB.setText(no);
		
		getDetailNilaiLab();
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaNGRLAB = user.get(SessionManager.KEY_NAME);
		namaDetGRLAB.setText(namaNGRLAB);
	}

	private void getDetailNilaiLab(){
		class GetNilaiLab extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(DetailNilaiLab.this,"Fetching...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                showNilaiLab(s);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_GET_DETAIL_NILAI, no);
				return s;
			}
			
		}
		GetNilaiLab gj = new GetNilaiLab();
		gj.execute();
	}
	
	private void showNilaiLab(String json){
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
			
			textJenisNilLAB.setText(jenisNil);
			textNilLAB.setText(nil);
			textKetNilLAB.setText(ket);
			textNamaGRLAB.setText(namaGR);
			textNamaLBLAB.setText(namaLB);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == backNilLAB){
            startActivity(new Intent(this,NilaiLaboran.class));
            finish();
        }
	}
	
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,NilaiLaboran.class));
		finish();
	}
}
