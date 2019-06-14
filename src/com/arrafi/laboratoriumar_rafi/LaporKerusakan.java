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
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LaporKerusakan extends Activity implements View.OnClickListener{
	//Defining views
			private TextView textID;
			private TextView textNama;
			private TextView textKondisi;
			private TextView textStatus;
			private TextView textNamaGR;
			private TextView textKeluhan;
			private TextView textKetKeluhan;
			private TextView textTGLBeli;
			private TextView namaSES;
			private Button btnKembali, btnPinjam;
			String namaJLB;
			private SessionManager session;
			
			private String no;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.lapor_kerusakan);
		
Intent intent = getIntent();
		
		no = intent.getStringExtra(Config.IDNYA_ALAT);
		
		textID = (TextView)findViewById(R.id.detailTmbIDLPR);
		textNama = (TextView)findViewById(R.id.detailTmbNamaLPR);
		textKondisi = (TextView)findViewById(R.id.detailKondisiLPR);
		textStatus = (TextView)findViewById(R.id.detailTmbStsLPR);
		textNamaGR = (TextView)findViewById(R.id.detailTmbNmGRLPR);
		textKeluhan = (TextView)findViewById(R.id.detailTmbKeluhanLPR);
		textKetKeluhan = (TextView)findViewById(R.id.detailTmbKetKeluhanLPR);
		textTGLBeli = (TextView)findViewById(R.id.detailTmbTglBeliLPR);
		namaSES = (TextView)findViewById(R.id.namaSLBTdetailLPR);
		btnKembali = (Button)findViewById(R.id.kembaliAltLPR);
		btnKembali.setOnClickListener(this);
		btnPinjam = (Button)findViewById(R.id.LaporAlatLPR);
		btnPinjam.setOnClickListener(this);
		
		textID.setText(no);
		
		getAlatLabLPR();
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaJLB = user.get(SessionManager.KEY_NAME);
		namaSES.setText(namaJLB);
	}

	private void getAlatLabLPR(){
		class GetAlatLPR extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(LaporKerusakan.this,"Fetching...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String lb) {
				// TODO Auto-generated method stub
				super.onPostExecute(lb);
				loading.dismiss();
                showAlatLabLPR(lb);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rhl = new RequestHandler();
				String sl = rhl.sendGetRequestParam(Config.URL_GET_DETAIL_ALAT, no);
				return sl;
			}
			
		}
		GetAlatLPR gjl = new GetAlatLPR();
		gjl.execute();
	}
	
	private void showAlatLabLPR(String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
			JSONObject c = result.getJSONObject(0);
			String namaBR = c.getString(Config.TAG_NAMA_BARANG);
			String kondisi = c.getString(Config.TAG_KODISI_BGR);
			String statusBR = c.getString(Config.TAG_STATUS_BRG);
			String pinjamGR = c.getString(Config.TAG_GURU_PIJNAM);
			String keluhan = c.getString(Config.TAG_KELUHAN_BRG);
			String ketKeluhan = c.getString(Config.TAG_KET_KELUHAN_BRG);
			String tglBeli = c.getString(Config.TAG_TGL_BELI_BRG);
			
			textNama.setText(namaBR);
			textKondisi.setText(kondisi);
			textStatus.setText(statusBR);
			textNamaGR.setText(pinjamGR);
			textKeluhan.setText(keluhan);
			textKetKeluhan.setText(ketKeluhan);
			textTGLBeli.setText(tglBeli);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void updateAlatLPR(){
		textKondisi.setText("Tidak Layak Pakai");
		final String kondisiAl = textKondisi.getText().toString().trim();
		final String kelAl = textKeluhan.getText().toString().trim();
		final String ketKelAl = textKetKeluhan.getText().toString().trim();
		
		class UpdateAlatPJM extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(LaporKerusakan.this,"Updating...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                Toast.makeText(LaporKerusakan.this,s,Toast.LENGTH_LONG).show();
                Intent a = new Intent(LaporKerusakan.this, InputKerusakanAlat.class);
                startActivity(a);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				HashMap<String,String> hashMap = new HashMap<>();
				hashMap.put(Config.KEY_ID_ALAT, no);
				hashMap.put(Config.KEY_KODISI_BGR, kondisiAl);
				hashMap.put(Config.KEY_KELUHAN_BRG, kelAl);
				hashMap.put(Config.KEY_KET_KELUHAN_BRG, ketKelAl);
				
				RequestHandler rh = new RequestHandler();
				String s = rh.sendPostRequest(Config.URL_LAPORKAN_RUSAK, hashMap);
				
				return s;
			}
			
		}
		UpdateAlatPJM uj = new UpdateAlatPJM();
		uj.execute();
	}
	
	public boolean lengkapiData(){
		textKeluhan.getText().toString().trim();
		textKeluhan.setError(null);
		textKetKeluhan.getText().toString().trim();
		textKetKeluhan.setError(null);
		if(textKeluhan.length() == 0){
			textKeluhan.setError(Html
					.fromHtml("<font color='white'>Input tidak boleh kosong</font>"));
			return false;
		}else if(textKetKeluhan.length() == 0){
			textKetKeluhan.setError(Html
					.fromHtml("<font color='white'>Input tidak boleh kosong</font>"));
			return false;
		}else{
			updateAlatLPR();
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btnKembali){
			startActivity(new Intent(this,InputKerusakanAlat.class));
			finish();
        }
		if(v==btnPinjam){
			lengkapiData();
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,InputKerusakanAlat.class));
		finish();
	}
}
