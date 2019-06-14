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
import android.os.Handler;
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

public class LBviewAllJdwl extends Activity implements ListView.OnItemClickListener{
	private ListView listJdwLb;
	private String JSON_STRINGL;
	private Button tambahJadLb;
	String nama;
	private SessionManager session;
	private TextView namaSGLB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.lbview_all_jdwl);
		
		listJdwLb = (ListView)findViewById(R.id.listJadwalLaboran);
		listJdwLb.setOnItemClickListener(this);
		getJdwlLB();
		
		tambahJadLb = (Button)findViewById(R.id.tambahJadLab);
		tambahJadLb.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inL = new Intent(LBviewAllJdwl.this, TambahJdwlLab.class);
				String APPJDWL = "ALL";
				inL.putExtra(Config.GETJDWL, APPJDWL);
				startActivity(inL);
				
			}
		});
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		nama = user.get(SessionManager.KEY_NAME);
		namaSGLB = (TextView)findViewById(R.id.namaSviewG);
		namaSGLB.setText(nama);
	}

	private void lihatJadwalLab(){
		JSONObject jsonObjectL = null;
		ArrayList<HashMap<String,String>> listL = new ArrayList<HashMap<String, String>>();
		try {
			jsonObjectL = new JSONObject(JSON_STRINGL);
			JSONArray resultL = jsonObjectL.getJSONArray(Config.TAG_JSON_ARRAY);
			
			for(int i=0; i<resultL.length(); i++){
				JSONObject jol = resultL.getJSONObject(i);
				String nom = i + 1 + "";
				String id = jol.getString(Config.TAG_ID);
				String kelas = jol.getString(Config.TAG_KELAS);
				String tgl = jol.getString(Config.TAG_TGL);
				String nama_guru = jol.getString(Config.TAG_nmGR);
				String jamke = jol.getString(Config.TAG_JAMKE);
				
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
		ListAdapter adapterL = new SimpleAdapter(LBviewAllJdwl.this, 
				listL, R.layout.list_jdwl_lb, new String[]
						{Config.KEY_NO,Config.TAG_TGL,Config.TAG_KELAS,Config.TAG_nmGR,Config.TAG_JAMKE}, 
						new int[]{R.id.no,R.id.noL,R.id.kelasL,R.id.namaGuruL,R.id.ttlL});
		listJdwLb.setAdapter(adapterL);
	}
	
	private void getJdwlLB(){
		class GetJdwlLB extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(LBviewAllJdwl.this,"Fetching Data","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String l) {
				// TODO Auto-generated method stub
				super.onPostExecute(l);
				super.onPostExecute(l);
				loading.dismiss();
                JSON_STRINGL = l;
                lihatJadwalLab();
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rhl = new RequestHandler();
				String l = rhl.sendGetRequest(Config.URL_BLM_APPR_DT);
				return l;
			}
			
		}
		GetJdwlLB gjl = new GetJdwlLB();
		gjl.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intentL = new Intent(this, ViewJadwalLab.class);
		HashMap<String,String> mapl =(HashMap)parent.getItemAtPosition(position);
		String jdIdL = mapl.get(Config.TAG_ID).toString();
		intentL.putExtra(Config.JWL_ID, jdIdL);
		startActivity(intentL);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,LaboranActivity.class));
		finish();
	}
}
