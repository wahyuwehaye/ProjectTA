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

public class PenilaianKegiatan extends Activity implements ListView.OnItemClickListener{
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
		setContentView(R.layout.activity_penilaian_kegiatan);
		
		listNilai = (ListView)findViewById(R.id.listPEnilaian);
		listNilai.setOnItemClickListener(this);
		getJSONNILAI();
		
		tambahNilai = (Button)findViewById(R.id.tambahPenilaian);
		tambahNilai.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(PenilaianKegiatan.this, TambahPenilaian.class);
				startActivity(in);
			}
		});
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		nama = user.get(SessionManager.KEY_NAME);
		namaSG = (TextView)findViewById(R.id.namaPenilai);
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
				String id = jo.getString(Config.TAG_ID_PENILAIAN);
				String nama_pegawai = jo.getString(Config.TAG_NAMA_PENILAI_LB);
				String tanggal = jo.getString(Config.TAG_PENILAI_TGL);
				String jamkeber = jo.getString(Config.TAG_PENILAI_JAMKE);
				String nama_guru = jo.getString(Config.TAG_NAMA_PENILAI_GR);
				
				
				HashMap<String,String> Vjadwal = new HashMap<String, String>();
				Vjadwal.put(Config.KEY_NO, no);
				Vjadwal.put(Config.TAG_ID_PENILAIAN, id);
				Vjadwal.put(Config.TAG_NAMA_PENILAI_LB, nama_pegawai);
				Vjadwal.put(Config.TAG_PENILAI_TGL, tanggal);
				Vjadwal.put(Config.TAG_PENILAI_JAMKE, jamkeber);
				Vjadwal.put(Config.TAG_NAMA_PENILAI_GR, nama_guru);
				listJ.add(Vjadwal);
			}
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ListAdapter adapterJ = new SimpleAdapter(PenilaianKegiatan.this, 
				listJ, R.layout.list_penilaian, new String[]
						{Config.KEY_NO,Config.TAG_NAMA_PENILAI_LB,Config.TAG_PENILAI_TGL,Config.TAG_PENILAI_JAMKE,Config.TAG_NAMA_PENILAI_GR}, 
						new int[]{R.id.noPen,R.id.namaPen,R.id.tglPen,R.id.jamKePen,R.id.namaPenil});
		listNilai.setAdapter(adapterJ);
	}
	
	private void getJSONNILAI(){
		class GetJSONNilai extends AsyncTask<Void,Void,String>{
			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(PenilaianKegiatan.this,"Fetching Data","Wait...",false,false);
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
				String s = rh.sendGetRequestParam(Config.URL_GET_DETAIL_PENILAIAN_GR, hasilNama);
//				String s = rh.sendGetRequest(Config.URL_GET_ALL_PENILAIAN_LAB);
				return s;
			}
			
		}
		GetJSONNilai gj = new GetJSONNilai();
		gj.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, DetailPenilaian.class);
		HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
		String jdId = map.get(Config.TAG_ID_PENILAIAN).toString();
		intent.putExtra(Config.NIL_PENIL, jdId);
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
