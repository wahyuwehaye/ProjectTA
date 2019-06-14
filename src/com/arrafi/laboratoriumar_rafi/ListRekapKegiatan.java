package com.arrafi.laboratoriumar_rafi;

import java.util.ArrayList;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ListRekapKegiatan extends Activity implements View.OnClickListener{
	private Intent intent;
	private ListView list_kegiatan;
	private String JSON_STRING;
	private String bulan;
	private String tahun;
	private TextView jumlah;
	String namaLBAP;
	private SessionManager session;
	private TextView namaSGLBAP;
	
	private String URL_BULAN = "bulan";
	private String URL_TAHUN = "tahun";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_list_rekap_kegiatan);
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaLBAP = user.get(SessionManager.KEY_NAME);
		namaSGLBAP = (TextView)findViewById(R.id.namaSviewRekap);
		namaSGLBAP.setText(namaLBAP);
		
		intent = getIntent();
		bulan = intent.getStringExtra(URL_BULAN);
		tahun = intent.getStringExtra(URL_TAHUN);

		settingList();
		
		jumlah = (TextView) findViewById(R.id.jm_kegiatan);
		list_kegiatan = (ListView) findViewById(R.id.listRekapJadwal);
	}

	private void settingList() {
		if (bulan.equals("Bulan") && tahun.equals("Tahun")) {
			// semua bulan dan semua tahun
			dataSemua();
		} else if (!bulan.equals("Bulan") && tahun.equals("Tahun")) {
			// bulan apa dan semua tahun
			dataPerBulan();
		} else if (bulan.equals("Bulan") && !tahun.equals("Tahun")) {
			// semua bulan dan tahun berapa
			dataPerTahun();
		} else {
			// bulan apa dan tahun berapa
			dataPerBulanTahun();
		}
	}
	
	private void tampilData(){
		JSONObject jsonObjectL = null;
		ArrayList<HashMap<String,String>> listL = new ArrayList<HashMap<String, String>>();
		try {
			jsonObjectL = new JSONObject(JSON_STRING);
			JSONArray resultL = jsonObjectL.getJSONArray(Config.TAG_JSON_ARRAY);
			
			for(int i=0; i<resultL.length(); i++){
				JSONObject jol = resultL.getJSONObject(i);
				String nom = i + 1 + "";
				String id = jol.getString(Config.TAG_ID);
				String kelas = jol.getString(Config.TAG_KELAS);
				String tgl = jol.getString(Config.TAG_TGL);
				String nama_guru = jol.getString(Config.TAG_nmGR);
				String jamke = jol.getString(Config.TAG_JAMKE);
				jumlah.setText (nom);
				HashMap<String,String> VjadwalL = new HashMap<String, String>();
				VjadwalL.put(Config.KEY_NO, nom);
				VjadwalL.put(Config.TAG_ID, id);
				VjadwalL.put(Config.TAG_KELAS, kelas);
				VjadwalL.put(Config.TAG_nmGR, nama_guru);
				VjadwalL.put(Config.TAG_TGL, tgl);
				VjadwalL.put(Config.TAG_JAMKE, jamke);
				listL.add(VjadwalL);
			}
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ListAdapter adapterL = new SimpleAdapter(ListRekapKegiatan.this, 
				listL, R.layout.list_jdwllab_ap, new String[]
						{Config.KEY_NO,Config.TAG_TGL,Config.TAG_KELAS,Config.TAG_nmGR,Config.TAG_JAMKE}, 
						new int[]{R.id.no,R.id.noLAP,R.id.kelasLAP,R.id.namaGuruLAP,R.id.ttlLAP});
		list_kegiatan.setAdapter(adapterL);
	}
	
	private void dataSemua() {
		class DataSemua extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ListRekapKegiatan.this,
						"Pengambilan Data...", "Mohon Tunggu...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequest(Config.URL_READ_KEGIATAN_ALL);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				tampilData();
			}

		}
		DataSemua ds = new DataSemua();
		ds.execute();
	}
	
	private void dataPerBulan() {
		class DataPerBulan extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ListRekapKegiatan.this,
						"Pengambilan Data", "Wait...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_READ_KEGIATAN_BULAN, bulan);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				tampilData();
			}

		}
		DataPerBulan dpb = new DataPerBulan();
		dpb.execute();
	}
	
	private void dataPerTahun() {
		class DataPerTahun extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ListRekapKegiatan.this,
						"Pengambilan Data", "Wait...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_READ_KEGIATAN_TAHUN, tahun);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				tampilData();
			}

		}
		DataPerTahun dpt = new DataPerTahun();
		dpt.execute();
	}
	
	private void dataPerBulanTahun() {
		class DataPerBulanTahun extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ListRekapKegiatan.this,
						"Pengambilan Data", "Wait...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_READ_KEGIATAN_BT, bulan + tahun);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				tampilData();
			}

		}
		DataPerBulanTahun dpbt = new DataPerBulanTahun();
		dpbt.execute();
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, PilihLaporan.class);
		startActivity(intent);
		this.finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
