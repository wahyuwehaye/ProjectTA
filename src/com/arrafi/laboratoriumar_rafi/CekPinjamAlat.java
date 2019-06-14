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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CekPinjamAlat extends Activity implements ListView.OnItemClickListener{
	private ListView listPrAlt;
	private String JSON_STRINGAL;
	String namadiAl;
	private SessionManager session;
	private TextView namaSGLT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.cek_pinjam_alat);
		
		listPrAlt = (ListView)findViewById(R.id.listPinjamAlatLab);
		listPrAlt.setOnItemClickListener(this);
		getPerangkatLab();
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namadiAl = user.get(SessionManager.KEY_NAME);
		namaSGLT = (TextView)findViewById(R.id.namaAlAbPinjam);
		namaSGLT.setText(namadiAl);
	}

	private void lihatPeralatanLab(){
		JSONObject jsonObjectAL = null;
		ArrayList<HashMap<String,String>> listAL = new ArrayList<HashMap<String, String>>();
		try {
			jsonObjectAL = new JSONObject(JSON_STRINGAL);
			JSONArray resultAL = jsonObjectAL.getJSONArray(Config.TAG_JSON_ARRAY);
			
			for(int i=0; i<resultAL.length(); i++){
				JSONObject jola = resultAL.getJSONObject(i);
				String no = i + 1 + "";
				String id = jola.getString(Config.TAG_ID_ALAT);
				String nama = jola.getString(Config.TAG_NAMA_BARANG);
				String kondisi = jola.getString(Config.TAG_KODISI_BGR);
				String tgl_beli = jola.getString(Config.TAG_TGL_BELI_BRG);
				
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
		ListAdapter adapterLl = new SimpleAdapter(CekPinjamAlat.this, 
				listAL, R.layout.list_alat_lab, new String[]
						{Config.KEY_NO,Config.TAG_ID_ALAT,Config.TAG_NAMA_BARANG,Config.TAG_KODISI_BGR,Config.TAG_TGL_BELI_BRG}, 
						new int[]{R.id.no,R.id.noPAl,R.id.namaAlatl,R.id.kondisiAlatl,R.id.ttlBlil});
		listPrAlt.setAdapter(adapterLl);
	}
	
	private void getPerangkatLab(){
		class GetPerLab extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(CekPinjamAlat.this,"Fetching Data","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String al) {
				// TODO Auto-generated method stub
				super.onPostExecute(al);
				super.onPostExecute(al);
				loading.dismiss();
                JSON_STRINGAL = al;
                lihatPeralatanLab();
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rhal = new RequestHandler();
				String al = rhal.sendGetRequest(Config.URL_GET_PINJAM);
				return al;
			}
			
		}
		GetPerLab gjl = new GetPerLab();
		gjl.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intentL = new Intent(this, DetailPinjam.class);
		HashMap<String,String> mapl =(HashMap)parent.getItemAtPosition(position);
		String jdIdL = mapl.get(Config.TAG_ID_ALAT).toString();
		intentL.putExtra(Config.IDNYA_ALAT, jdIdL);
		startActivity(intentL);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,PeralatanLab.class));
		finish();
	}
}
