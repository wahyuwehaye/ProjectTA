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

public class ViewJadwalLabAPP extends Activity implements View.OnClickListener{
	//Defining views
			private TextView textID;
			private TextView textTgl;
			private TextView textKelas;
			private TextView textMapel;
			private TextView textJamke;
			private TextView textKet;
			private TextView textStatus;
			private TextView textNamaGR;
			private TextView textNamaLB;
			private Button kembaliAPP;
			String namaJLB;
			private SessionManager session;
			
			private String no;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.view_jadwal_lab_app);
		
		Intent intent = getIntent();
		
		no = intent.getStringExtra(Config.JWL_ID);
		
		textID = (TextView)findViewById(R.id.DetailIDLabAPP);
		textTgl = (TextView)findViewById(R.id.DetailTglLabAPP);
		textKelas = (TextView)findViewById(R.id.DetailKelasLabAPP);
		textMapel = (TextView)findViewById(R.id.DetailMapelLabAPP);
		textJamke = (TextView)findViewById(R.id.DetailJamKeLabAPP);
		textKet = (TextView)findViewById(R.id.DetailKetLabAPP);
		textStatus = (TextView)findViewById(R.id.DetailStatusLabAPP);
		textNamaGR = (TextView)findViewById(R.id.DetailNamaGRAPP);
		textNamaLB = (TextView)findViewById(R.id.namaSDLBAPP);
		kembaliAPP = (Button)findViewById(R.id.KembaliAPP);
		kembaliAPP.setOnClickListener(this);
		
		textID.setText(no);
		
		getJadwalLB();
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaJLB = user.get(SessionManager.KEY_NAME);
		textNamaLB.setText(namaJLB);
	}

	private void getJadwalLB(){
		class GetJadwalLb extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ViewJadwalLabAPP.this,"Fetching...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String lb) {
				// TODO Auto-generated method stub
				super.onPostExecute(lb);
				loading.dismiss();
                showJadwalLB(lb);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rhl = new RequestHandler();
				String sl = rhl.sendGetRequestParam(Config.URL_GET_DATA, no);
				return sl;
			}
			
		}
		GetJadwalLb gjl = new GetJadwalLb();
		gjl.execute();
	}
	
	private void showJadwalLB(String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
			JSONObject c = result.getJSONObject(0);
			String tgl = c.getString(Config.TAG_TGL);
			String kelas = c.getString(Config.TAG_KELAS);
			String mapel = c.getString(Config.TAG_MAPEL);
			String jamKe = c.getString(Config.TAG_JAMKE);
			String ket = c.getString(Config.TAG_KET);
			String status = c.getString(Config.TAG_STATUS);
			String namaGR = c.getString(Config.TAG_nmGR);
			String namaLB = c.getString(Config.TAG_nmLB);
			
			textTgl.setText(tgl);
			textKelas.setText(kelas);
			textMapel.setText(mapel);
			textJamke.setText(jamKe);
			textKet.setText(ket);
			textStatus.setText(status);
			textNamaGR.setText(namaGR);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		startActivity(new Intent(this,LBviewAPPJdwl.class));
		finish();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,LBviewAPPJdwl.class));
		finish();
	}
}
