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


public class ViewAllJadwal extends Activity implements ListView.OnItemClickListener{
	private ListView listJdw;
	private String JSON_STRINGJ;
	private Button tambahJad;
	String nama;
	private SessionManager session;
	private TextView namaSG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.view_all_jadwal);
		
		listJdw = (ListView)findViewById(R.id.listJadwal);
		listJdw.setOnItemClickListener(this);
		getJSONJDW();
		
		tambahJad = (Button)findViewById(R.id.tambahJad);
		tambahJad.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(ViewAllJadwal.this, TambahJadwal.class);
				startActivity(in);
			}
		});
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		nama = user.get(SessionManager.KEY_NAME);
		namaSG = (TextView)findViewById(R.id.namaSG);
		namaSG.setText(nama);
	}
	
	private void lihatJadwal(){
		JSONObject jsonObjectJ = null;
		ArrayList<HashMap<String,String>> listJ = new ArrayList<HashMap<String, String>>();
		try {
			jsonObjectJ = new JSONObject(JSON_STRINGJ);
			JSONArray result = jsonObjectJ.getJSONArray(Config.TAG_JSON_ARRAY);
			
			for(int i=0; i<result.length(); i++){
				JSONObject jo = result.getJSONObject(i);
				String no = i + 1 + "";
				String id = jo.getString(Config.TAG_ID);
				String kelas = jo.getString(Config.TAG_KELAS);
				String tgl = jo.getString(Config.TAG_TGL);
				String nama_guru = jo.getString(Config.TAG_nmGR);
				String jamke = jo.getString(Config.TAG_JAMKE);
				
				
				HashMap<String,String> Vjadwal = new HashMap<String, String>();
				Vjadwal.put(Config.KEY_NO, no);
				Vjadwal.put(Config.TAG_ID, id);
				Vjadwal.put(Config.TAG_KELAS, kelas);
				Vjadwal.put(Config.TAG_nmGR, nama_guru);
				Vjadwal.put(Config.TAG_TGL, tgl);
				Vjadwal.put(Config.TAG_JAMKE, jamke);
				listJ.add(Vjadwal);
			}
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ListAdapter adapterJ = new SimpleAdapter(ViewAllJadwal.this, 
				listJ, R.layout.list_jadwal, new String[]
						{Config.KEY_NO,Config.TAG_TGL,Config.TAG_KELAS,Config.TAG_nmGR,Config.TAG_JAMKE}, 
						new int[]{R.id.no,R.id.noJ,R.id.kelasJ,R.id.namaGuruJ,R.id.ttlJ});
		listJdw.setAdapter(adapterJ);
	}
	
	private void getJSONJDW(){
		class GetJSONjdw extends AsyncTask<Void,Void,String>{
			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ViewAllJadwal.this,"Fetching Data","Wait...",false,false);
			}


			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                JSON_STRINGJ = s;
                lihatJadwal();
			}


			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequest(Config.URL_APPR_DATA);
				return s;
			}
			
		}
		GetJSONjdw gj = new GetJSONjdw();
		gj.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, DetailJadwal.class);
		HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
		String jdId = map.get(Config.TAG_ID).toString();
		intent.putExtra(Config.JWL_ID, jdId);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,MainActivity.class));
		finish();
	}
}
