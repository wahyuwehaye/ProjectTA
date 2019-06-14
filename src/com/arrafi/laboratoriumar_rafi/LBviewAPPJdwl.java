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

public class LBviewAPPJdwl extends Activity implements ListView.OnItemClickListener{
	private ListView listJdwLbAP;
	private String JSON_STRINGAP;
	private Button tambahJadLbAP;
	String namaLBAP;
	private SessionManager session;
	private TextView namaSGLBAP;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.lbview_appjdwl);
		
		listJdwLbAP = (ListView)findViewById(R.id.listJadwalLaboranAPP);
		listJdwLbAP.setOnItemClickListener(this);
		getJdwlLBAP();
		tambahJadLbAP = (Button)findViewById(R.id.tambahJadLabAPP);
		tambahJadLbAP.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent tbA = new Intent(LBviewAPPJdwl.this, TambahJdwlLab.class);
				String APPJDWL = "APP";
				tbA.putExtra(Config.GETJDWL, APPJDWL);
				startActivity(tbA);
			}
		});
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaLBAP = user.get(SessionManager.KEY_NAME);
		namaSGLBAP = (TextView)findViewById(R.id.namaSviewGAPP);
		namaSGLBAP.setText(namaLBAP);
		
	}

	private void lihatJadwalLabAp(){
		JSONObject jsonObjectL = null;
		ArrayList<HashMap<String,String>> listL = new ArrayList<HashMap<String, String>>();
		try {
			jsonObjectL = new JSONObject(JSON_STRINGAP);
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
		ListAdapter adapterL = new SimpleAdapter(LBviewAPPJdwl.this, 
				listL, R.layout.list_jdwllab_ap, new String[]
						{Config.KEY_NO,Config.TAG_TGL,Config.TAG_KELAS,Config.TAG_nmGR,Config.TAG_JAMKE}, 
						new int[]{R.id.no,R.id.noLAP,R.id.kelasLAP,R.id.namaGuruLAP,R.id.ttlLAP});
		listJdwLbAP.setAdapter(adapterL);
	}
	
	private void getJdwlLBAP(){
		class GetJdwlLBAP extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(LBviewAPPJdwl.this,"Fetching Data","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String l) {
				// TODO Auto-generated method stub
				super.onPostExecute(l);
				super.onPostExecute(l);
				loading.dismiss();
                JSON_STRINGAP = l;
                lihatJadwalLabAp();
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rhl = new RequestHandler();
				String l = rhl.sendGetRequest(Config.URL_APPR_DATA);
				return l;
			}
			
		}
		GetJdwlLBAP gjl = new GetJdwlLBAP();
		gjl.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intentLAP = new Intent(this, ViewJadwalLabAPP.class);
		HashMap<String,String> mapla =(HashMap)parent.getItemAtPosition(position);
		String jdIdL = mapla.get(Config.TAG_ID).toString();
		intentLAP.putExtra(Config.JWL_ID, jdIdL);
		startActivity(intentLAP);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,LaboranActivity.class));
		finish();
	}
}
