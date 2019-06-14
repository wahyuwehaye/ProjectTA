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

public class DetailJadwal extends Activity implements View.OnClickListener{
	
	//Defining views
	private TextView textID;
	private TextView textTgl;
	private TextView textKelas;
	private TextView textMapel;
	private TextView textJamke;
	private TextView textKet;
	private TextView textStatus;
	private TextView textNamaGR;
	private TextView textNamaGRU;
	private TextView textNamaLB;
	private Button kemViewJ;
	String namaJGR;
	private SessionManager session;
	
	private String no;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.detail_jadwal);
		
		Intent intent = getIntent();
		
		no = intent.getStringExtra(Config.JWL_ID);
		
		textID = (TextView)findViewById(R.id.DetailID);
		textTgl = (TextView)findViewById(R.id.DetailTgl);
		textKelas = (TextView)findViewById(R.id.DetailKelas);
		textMapel = (TextView)findViewById(R.id.DetailMapel);
		textJamke = (TextView)findViewById(R.id.DetailJamKe);
		textKet = (TextView)findViewById(R.id.DetailKet);
		textStatus = (TextView)findViewById(R.id.DetailStatus);
		textNamaGR = (TextView)findViewById(R.id.namaSDG);
		textNamaGRU = (TextView)findViewById(R.id.DetailNamaGRU);
		textNamaLB = (TextView)findViewById(R.id.detSts);
		kemViewJ = (Button)findViewById(R.id.kemVJD);
		kemViewJ.setOnClickListener(this);
		
		textID.setText(no);
		
		getJadwal();
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaJGR = user.get(SessionManager.KEY_NAME);
		textNamaGR.setText(namaJGR);
	}

	private void getJadwal(){
		class GetJadwal extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(DetailJadwal.this,"Fetching...","Wait...",false,false);
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
				String s = rh.sendGetRequestParam(Config.URL_GET_DATA, no);
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
			textNamaGRU.setText(namaGR);
			textNamaLB.setText(namaLB);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == kemViewJ){
            startActivity(new Intent(this,ViewAllJadwal.class));
            finish();
        }
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,ViewAllJadwal.class));
		finish();
	}
	
}
