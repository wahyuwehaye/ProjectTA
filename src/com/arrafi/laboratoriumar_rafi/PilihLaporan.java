package com.arrafi.laboratoriumar_rafi;


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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class PilihLaporan extends Activity implements View.OnClickListener{
	private Button seleksi;
	private Button lihat_kegiatan;
	private Button lihat_perangkat;

	private Intent intent;
	
	private TextView tv_bulan;
	private TextView tv_tahun;

	private String JSON_STRING;
	private String teks_bulan;
	private String teks_tahun;
	
	private String KEY_TOTAL_KEGIATAN = "total_kegiatan";
	private String KEY_TOTAL_PERANGKAT = "total_perangkat";
	private String URL_BULAN = "bulan";
	private String URL_TAHUN = "tahun";
	
	private Spinner bulan;
	private Spinner tahun;

	private String[] list_bulan = { "Bulan", "January", "February", "March",
			"April", "May", "June", "July", "August", "September", "October",
			"November", "December" };
	private String[] list_tahun = { "Tahun", "2010", "2011", "2012", "2013",
			"2014", "2015", "2016", "2017", "2018", "2019", "2020" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_pilih_laporan);
		
		tv_bulan = (TextView) findViewById(R.id.tv_bulan);
		tv_tahun = (TextView) findViewById(R.id.tv_tahun);
		
		bulan = (Spinner) findViewById(R.id.bulan);
		tahun = (Spinner) findViewById(R.id.tahun);
		settingSpinner();
		
		lihat_kegiatan = (Button)findViewById(R.id.lihat_kegiatan);
		lihat_kegiatan.setOnClickListener(this);
		lihat_perangkat = (Button)findViewById(R.id.lihat_peralatan);
		lihat_perangkat.setOnClickListener(this);
		
		seleksi = (Button) findViewById(R.id.cari_data);
		seleksi.setOnClickListener(this);
	}

	private void settingSpinner() {
		ArrayAdapter<String> adapterBulan = new ArrayAdapter<String>(this,
				R.layout.spinner_text, list_bulan);
		adapterBulan
				.setDropDownViewResource(R.layout.spinner_item);
		bulan.setAdapter(adapterBulan);

		ArrayAdapter<String> adapterTahun = new ArrayAdapter<String>(this,
				R.layout.spinner_text, list_tahun);
		adapterTahun
				.setDropDownViewResource(R.layout.spinner_item);
		tahun.setAdapter(adapterTahun);

	}
	
	private void aksiSeleksi() {

		teks_bulan = bulan.getSelectedItem().toString();
		teks_tahun = tahun.getSelectedItem().toString();

		if (teks_bulan.equals("Bulan") && teks_tahun.equals("Tahun")) {
			// semua bulan dan semua tahun

			hitungTotalKegiatan();
			hitungTotalPerangkat();
			tulisHasil(teks_bulan, teks_tahun);
		} else if (!teks_bulan.equals("Bulan") && teks_tahun.equals("Tahun")) {
			// bulan apa dan semua tahun

			hitungBulanKegiatan(teks_bulan);
			hitungBulanPerangkat(teks_bulan);
			tulisHasil(teks_bulan, teks_tahun);
		} else if (teks_bulan.equals("Bulan") && !teks_tahun.equals("Tahun")) {
			// semua bulan dan tahun berapa

			hitungTahunKegiatan(teks_tahun);
			hitungTahunPerangkat(teks_tahun);
			tulisHasil(teks_bulan, teks_tahun);
		} else {
			// bulan apa dan tahun berapa

			hitungBTKegiatan(teks_bulan, teks_tahun);
			hitungBTPerangkat(teks_bulan, teks_tahun);
			tulisHasil(teks_bulan, teks_tahun);
		}

	}
	
	private void tulisHasil(String bulan, String tahun) {
		tv_bulan.setText(bulan);
		tv_tahun.setText(tahun);
	}
	
	private void hitungTotalKegiatan(){
		class DataReward extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(PilihLaporan.this,
						"Pengambilan Data", "Wait...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequest(Config.URL_REKAP_KEGIATAN_ALL);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				tampilDataKegiatan();
			}

		}
		DataReward dk = new DataReward();
		dk.execute();
	}
	
	private void hitungBulanKegiatan(final String bulan){
		class DataReward extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(PilihLaporan.this,
						"Pengambilan Data", "Wait...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_REKAP_KEGIATAN_BULAN,
						bulan);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				tampilDataKegiatan();
			}

		}
		DataReward dk = new DataReward();
		dk.execute();
	}
	
	private void hitungTahunKegiatan(final String tahun){
		class DataReward extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(PilihLaporan.this,
						"Pengambilan Data", "Wait...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_REKAP_KEGIATAN_TAHUN,
						tahun);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				tampilDataKegiatan();
			}

		}
		DataReward dk = new DataReward();
		dk.execute();
	}
	
	private void hitungBTKegiatan(final String bulan, final String tahun){
		class DataReward extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(PilihLaporan.this,
						"Pengambilan Data", "Wait...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(
						Config.URL_REKAP_KEGIATAN_BT, bulan + tahun);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				tampilDataKegiatan();
			}

		}
		DataReward dk = new DataReward();
		dk.execute();
	}
	
	private void tampilDataKegiatan() {
		JSONObject jsonObject = null;

		try {
			jsonObject = new JSONObject(JSON_STRING);
			JSONArray result = jsonObject.getJSONArray("result");

			for (int i = 0; i < result.length(); i++) {
				JSONObject jo = result.getJSONObject(i);
				lihat_kegiatan
						.setText(jo.getString(KEY_TOTAL_KEGIATAN).toString());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	//======================================
	private void hitungTotalPerangkat(){
		class DataReward extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(PilihLaporan.this,
						"Pengambilan Data", "Wait...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequest(Config.URL_REKAP_ALAT_ALL);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				tampilDataPerangkat();
			}

		}
		DataReward dk = new DataReward();
		dk.execute();
	}
	
	private void hitungBulanPerangkat(final String bulan){
		class DataReward extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(PilihLaporan.this,
						"Pengambilan Data", "Wait...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_REKAP_ALAT_BULAN,
						bulan);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				tampilDataPerangkat();
			}

		}
		DataReward dk = new DataReward();
		dk.execute();
	}
	
	private void hitungTahunPerangkat(final String tahun){
		class DataReward extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(PilihLaporan.this,
						"Pengambilan Data", "Wait...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_REKAP_ALAT_TAHUN,
						tahun);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				tampilDataPerangkat();
			}

		}
		DataReward dk = new DataReward();
		dk.execute();
	}
	
	private void hitungBTPerangkat(final String bulan, final String tahun){
		class DataReward extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(PilihLaporan.this,
						"Pengambilan Data", "Wait...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(
						Config.URL_REKAP_ALAT_BT, bulan + tahun);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				tampilDataPerangkat();
			}

		}
		DataReward dk = new DataReward();
		dk.execute();
	}
	
	private void tampilDataPerangkat() {
		JSONObject jsonObject = null;

		try {
			jsonObject = new JSONObject(JSON_STRING);
			JSONArray result = jsonObject.getJSONArray("result");

			for (int i = 0; i < result.length(); i++) {
				JSONObject jo = result.getJSONObject(i);
				lihat_perangkat
						.setText(jo.getString(KEY_TOTAL_PERANGKAT).toString());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
//	@Override
//	public void onBackPressed() {
//		// TODO Auto-generated method stub
//		//super.onBackPressed();
//		startActivity(new Intent(this,LaboranActivity.class));
//		finish();
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String teks_bulan = tv_bulan.getText().toString();
		String teks_tahun = tv_tahun.getText().toString();
		
		if (v == seleksi) {
			aksiSeleksi();
		} 
		if (v == lihat_kegiatan) {
			intent = new Intent(this, ListRekapKegiatan.class);
			intent.putExtra(URL_BULAN, teks_bulan);
			intent.putExtra(URL_TAHUN, teks_tahun);
			startActivity(intent);
			this.finish();
		}
		if (v == lihat_perangkat) {
			intent = new Intent(this, ListRekapPeralatan.class);
			intent.putExtra(URL_BULAN, teks_bulan);
			intent.putExtra(URL_TAHUN, teks_tahun);
			startActivity(intent);
			this.finish();
		}
	}
}
