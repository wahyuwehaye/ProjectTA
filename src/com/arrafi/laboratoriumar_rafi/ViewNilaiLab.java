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

public class ViewNilaiLab extends Activity implements ListView.OnItemClickListener{
	private ListView listNilai;
	private String JSON_STRINGN;
	private Button tambahNilai;
	private String nama, hasilNama, namaPIN;
	private SessionManager session;
	private TextView namaSG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.view_nilai_lab);
		
		listNilai = (ListView)findViewById(R.id.listNilaiG);
		listNilai.setOnItemClickListener(this);
		getJSONNILAI();
		
		tambahNilai = (Button)findViewById(R.id.tambahNilai);
		tambahNilai.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(ViewNilaiLab.this, TambahNilai.class);
				startActivity(in);
			}
		});
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		nama = user.get(SessionManager.KEY_NAME);
		namaSG = (TextView)findViewById(R.id.namaSGNl);
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
		JSONObject jsonObjectJ = null;
		ArrayList<HashMap<String,String>> listJ = new ArrayList<HashMap<String, String>>();
		try {
			jsonObjectJ = new JSONObject(JSON_STRINGN);
			JSONArray result = jsonObjectJ.getJSONArray(Config.TAG_JSON_ARRAY);
			
			for(int i=0; i<result.length(); i++){
				JSONObject jo = result.getJSONObject(i);
				String no = i + 1 + "";
				String id = jo.getString(Config.TAG_ID_NILAI);
				String jenisNilai = jo.getString(Config.TAG_JENIS_NILAI);
				String nilai = jo.getString(Config.TAG_NILAI);
				String nama_pegawai = jo.getString(Config.TAG_NILAI_LB);
				String nama_guru = jo.getString(Config.TAG_NILAI_GR);
				
				
				HashMap<String,String> Vjadwal = new HashMap<String, String>();
				Vjadwal.put(Config.KEY_NO, no);
				Vjadwal.put(Config.TAG_ID_NILAI, id);
				Vjadwal.put(Config.TAG_JENIS_NILAI, jenisNilai);
				Vjadwal.put(Config.TAG_NILAI, nilai);
				Vjadwal.put(Config.TAG_NILAI_LB, nama_pegawai);
				Vjadwal.put(Config.TAG_NILAI_GR, nama_guru);
				listJ.add(Vjadwal);
			}
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ListAdapter adapterJ = new SimpleAdapter(ViewNilaiLab.this, 
				listJ, R.layout.list_nilai_guru, new String[]
						{Config.KEY_NO,Config.TAG_NILAI_LB,Config.TAG_JENIS_NILAI,Config.TAG_NILAI,Config.TAG_NILAI_GR}, 
						new int[]{R.id.no,R.id.noN,R.id.jenisNl,R.id.nilaiGR,R.id.namaPeg});
		listNilai.setAdapter(adapterJ);
	}
	
	private void getJSONNILAI(){
		class GetJSONNilai extends AsyncTask<Void,Void,String>{
			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ViewNilaiLab.this,"Fetching Data","Wait...",false,false);
			}


			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                JSON_STRINGN = s;
                lihatNilai();
			}


			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_GET_NILAI_drGR, hasilNama);
				return s;
			}
			
		}
		GetJSONNilai gj = new GetJSONNilai();
		gj.execute();
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, DetailNilai.class);
		HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
		String jdId = map.get(Config.TAG_ID_NILAI).toString();
		intent.putExtra(Config.NIL_ID, jdId);
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
