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

public class NilaiLaboran extends Activity implements ListView.OnItemClickListener{
	private ListView listNilaiLB;
	private String JSON_STRINGLB;
	private String nama, hasilNama, namaPIN;
	private SessionManager session;
	private TextView namaSG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.nilai_laboran);
		
		listNilaiLB = (ListView)findViewById(R.id.listNilaiLaboran);
		listNilaiLB.setOnItemClickListener(this);
		getJSONNILAI();
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		nama = user.get(SessionManager.KEY_NAME);
		namaSG = (TextView)findViewById(R.id.namaSGNlLB);
		namaSG.setText(nama);
		namaPIN = namaSG.getText().toString();
		
		String[] tempNama = namaPIN.split(" ");
        ArrayList<String> teks_lengkap= new ArrayList<String>();
        hasilNama="";
  
        for(int i=0; i < tempNama.length ; i++)
            teks_lengkap.add(tempNama[i].trim());
  
        for(int i=0; i < tempNama.length ; i++)
        	hasilNama = hasilNama + teks_lengkap.get(i)+"%20";
	}

	private void lihatNilai(){
		JSONObject jsonObjectJn = null;
		ArrayList<HashMap<String,String>> listJn = new ArrayList<HashMap<String, String>>();
		try {
			jsonObjectJn = new JSONObject(JSON_STRINGLB);
			JSONArray result = jsonObjectJn.getJSONArray(Config.TAG_JSON_ARRAY);
			
			for(int i=0; i<result.length(); i++){
				JSONObject jo = result.getJSONObject(i);
				String nomr = i + 1 + "";
				String id = jo.getString(Config.TAG_ID_NILAI);
				String jenisNilai = jo.getString(Config.TAG_JENIS_NILAI);
				String nilai = jo.getString(Config.TAG_NILAI);
				String nama_pegawai = jo.getString(Config.TAG_NILAI_LB);
				String nama_guru = jo.getString(Config.TAG_NILAI_GR);
				
				
				HashMap<String,String> VjadwalN = new HashMap<String, String>();
				VjadwalN.put(Config.KEY_NO, nomr);
				VjadwalN.put(Config.TAG_ID_NILAI, id);
				VjadwalN.put(Config.TAG_JENIS_NILAI, jenisNilai);
				VjadwalN.put(Config.TAG_NILAI, nilai);
				VjadwalN.put(Config.TAG_NILAI_LB, nama_pegawai);
				VjadwalN.put(Config.TAG_NILAI_GR, nama_guru);
				listJn.add(VjadwalN);
			}
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ListAdapter adapterJN = new SimpleAdapter(NilaiLaboran.this, 
				listJn, R.layout.list_nilai_laboran, new String[]
						{Config.KEY_NO,Config.TAG_NILAI_LB,Config.TAG_JENIS_NILAI,Config.TAG_NILAI,Config.TAG_NILAI_GR}, 
						new int[]{R.id.no,R.id.noNLB,R.id.jenisNlLB,R.id.nilaiGRLB,R.id.namaPegLB});
		listNilaiLB.setAdapter(adapterJN);
	}
	
	private void getJSONNILAI(){
		class GetJSONNilai extends AsyncTask<Void,Void,String>{
			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(NilaiLaboran.this,"Fetching Data","Wait...",false,false);
			}


			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                JSON_STRINGLB = s;
                lihatNilai();
			}


			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_GET_NILAI_LAB_ORG, hasilNama);
				return s;
			}
			
		}
		GetJSONNilai gj = new GetJSONNilai();
		gj.execute();
	}
	
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,LaboranActivity.class));
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, DetailNilaiLab.class);
		HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
		String jdId = map.get(Config.TAG_ID_NILAI).toString();
		intent.putExtra(Config.NIL_ID, jdId);
		startActivity(intent);
	}
}
