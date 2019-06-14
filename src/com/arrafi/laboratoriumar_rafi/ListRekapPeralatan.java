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

public class ListRekapPeralatan extends Activity implements View.OnClickListener{
	private Intent intent;
	private ListView list_peralatan;
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
		setContentView(R.layout.activity_list_rekap_peralatan);
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaLBAP = user.get(SessionManager.KEY_NAME);
		namaSGLBAP = (TextView)findViewById(R.id.namaSviewRekapAlat);
		namaSGLBAP.setText(namaLBAP);
		
		intent = getIntent();
		bulan = intent.getStringExtra(URL_BULAN);
		tahun = intent.getStringExtra(URL_TAHUN);

		settingList();
		jumlah = (TextView) findViewById(R.id.jm_peralatan);
		list_peralatan = (ListView) findViewById(R.id.listRekapAlat);
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
	
	private void lihatPeralatan(){
		JSONObject jsonObjectAL = null;
		ArrayList<HashMap<String,String>> listAL = new ArrayList<HashMap<String, String>>();
		try {
			jsonObjectAL = new JSONObject(JSON_STRING);
			JSONArray resultAL = jsonObjectAL.getJSONArray(Config.TAG_JSON_ARRAY);
			
			for(int i=0; i<resultAL.length(); i++){
				JSONObject jola = resultAL.getJSONObject(i);
				String no = i + 1 + "";
				String id = jola.getString(Config.TAG_ID_ALAT);
				String nama = jola.getString(Config.TAG_NAMA_BARANG);
				String kondisi = jola.getString(Config.TAG_KODISI_BGR);
				String tgl_beli = jola.getString(Config.TAG_TGL_BELI_BRG);
				jumlah.setText (no);
				HashMap<String,String> VjadwalLAL = new HashMap<String, String>();
				VjadwalLAL.put(Config.KEY_NO, no);
				VjadwalLAL.put(Config.TAG_ID_ALAT, id);
				VjadwalLAL.put(Config.TAG_NAMA_BARANG, nama);
				VjadwalLAL.put(Config.TAG_KODISI_BGR, kondisi);
				VjadwalLAL.put(Config.TAG_TGL_BELI_BRG, tgl_beli);
				listAL.add(VjadwalLAL);
			}
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ListAdapter adapterLl = new SimpleAdapter(ListRekapPeralatan.this, 
				listAL, R.layout.list_alat_lab, new String[]
						{Config.KEY_NO,Config.TAG_ID_ALAT,Config.TAG_NAMA_BARANG,Config.TAG_KODISI_BGR,Config.TAG_TGL_BELI_BRG}, 
						new int[]{R.id.no,R.id.noPAl,R.id.namaAlatl,R.id.kondisiAlatl,R.id.ttlBlil});
		list_peralatan.setAdapter(adapterLl);
	}
	
	private void dataSemua() {
		class DataSemua extends AsyncTask<Void, Void, String> {

			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ListRekapPeralatan.this,
						"Pengambilan Data...", "Mohon Tunggu...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequest(Config.URL_READ_ALAT_ALL);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				lihatPeralatan();
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
				loading = ProgressDialog.show(ListRekapPeralatan.this,
						"Pengambilan Data", "Wait...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_READ_ALAT_BULAN, bulan);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				lihatPeralatan();
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
				loading = ProgressDialog.show(ListRekapPeralatan.this,
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
				lihatPeralatan();
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
				loading = ProgressDialog.show(ListRekapPeralatan.this,
						"Pengambilan Data", "Wait...", false, false);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_READ_ALAT_BT, bulan + tahun);
				return s;
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
				JSON_STRING = s;
				lihatPeralatan();
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
