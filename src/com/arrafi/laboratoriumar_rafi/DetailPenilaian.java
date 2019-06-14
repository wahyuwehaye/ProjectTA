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

public class DetailPenilaian extends Activity implements View.OnClickListener{
	//Defining views
			private TextView textIDPen;
			private TextView textPenjadwalan;
			private TextView textMekanisme;
			private TextView textPelayanan;
			private TextView textSarana;
			private TextView textSuasana;
			private TextView textNMGuru;
			private TextView textNMPegaw;
			private TextView textTanggal;
			private TextView textKelas;
			private TextView textMapel;
			private TextView textJamke;
			private TextView namaDetGR;
			private Button backPenilaian;
			String namaNGR;
			private SessionManager session;
			
			private String no;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_detail_penilaian);
		
		Intent intent = getIntent();
		
		no = intent.getStringExtra(Config.NIL_PENIL);
		
		textIDPen = (TextView)findViewById(R.id.DetailIDPen);
		textPenjadwalan = (TextView)findViewById(R.id.DetailPenjadwalan);
		textMekanisme = (TextView)findViewById(R.id.DetailMekanisme);
		textPelayanan = (TextView)findViewById(R.id.DetailPelayanan);
		textSarana = (TextView)findViewById(R.id.DetailSarana);
		textSuasana = (TextView)findViewById(R.id.DetailSuasana);
		textNMGuru = (TextView)findViewById(R.id.DetailPenilai);
		textNMPegaw = (TextView)findViewById(R.id.DetailDinilai);
		textTanggal = (TextView)findViewById(R.id.DetailTanggalPenilaian);
		textKelas = (TextView)findViewById(R.id.DetailKelasPenilaian);
		textMapel = (TextView)findViewById(R.id.DetailMapelPenilaian);
		textJamke = (TextView)findViewById(R.id.DetailJamKePenilaian);
		namaDetGR = (TextView)findViewById(R.id.namaSESPEN);
		
		backPenilaian = (Button)findViewById(R.id.backDetPenil);
		backPenilaian.setOnClickListener(this);
		
		textIDPen.setText(no);
		
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
				loading = ProgressDialog.show(DetailPenilaian.this,"Fetching...","Wait...",false,false);
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
				String s = rh.sendGetRequestParam(Config.URL_GET_DETAIL_PENILAIAN_LAB, no);
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
			String idPenil = c.getString(Config.TAG_ID_PENILAIAN);
			String penjadwalan = c.getString(Config.TAG_PENJADWALAN);
			String mekanisme = c.getString(Config.TAG_MEKANISME);
			String pelayanan = c.getString(Config.TAG_PELAYANAN);
			String sarana = c.getString(Config.TAG_SARANA);
			String suasana = c.getString(Config.TAG_SUASANA);
			String penilai = c.getString(Config.TAG_NAMA_PENILAI_GR);
			String dinilai = c.getString(Config.TAG_NAMA_PENILAI_LB);
			String tanggalnil = c.getString(Config.TAG_PENILAI_TGL);
			String kelasnil = c.getString(Config.TAG_PENILAI_KELAS);
			String mapelnil = c.getString(Config.TAG_PENILAI_MAPEL);
			String jamkenil = c.getString(Config.TAG_PENILAI_JAMKE);
			
			textIDPen.setText(idPenil);
			textPenjadwalan.setText(penjadwalan);
			textMekanisme.setText(mekanisme);
			textPelayanan.setText(pelayanan);
			textSarana.setText(sarana);
			textSuasana.setText(suasana);
			textNMGuru.setText(penilai);
			textNMPegaw.setText(dinilai);
			textTanggal.setText(tanggalnil);
			textKelas.setText(kelasnil);
			textMapel.setText(mapelnil);
			textJamke.setText(jamkenil);
			
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		if(v == backPenilaian){
//            startActivity(new Intent(this,PenilaianKegiatan.class));
//            finish();
//        }
	}
	
//	@Override
//	public void onBackPressed() {
//		// TODO Auto-generated method stub
//		//super.onBackPressed();
//		startActivity(new Intent(this,PenilaianKegiatan.class));
//		finish();
//	}
}
