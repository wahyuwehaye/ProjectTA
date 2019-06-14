package com.arrafi.laboratoriumar_rafi;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewJadwalLab extends Activity implements View.OnClickListener{
	//Defining views
		private TextView textID;
		private TextView textTgl;
		private TextView textKelas;
		private TextView textMapel;
		private TextView textJamke;
		private TextView textKet;
		private TextView textStatus;
		private TextView textNamaGR;
		private TextView textNamaLB;
		private Button approveJdwl;
		private Button deleteJdwl;
		String namaJLB;
		private SessionManager session;
		private String JSON_STRINGJ;
		private String already_exists = "";
		
		private String no;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.view_jadwal_lab);
		
		Intent intent = getIntent();
		
		no = intent.getStringExtra(Config.JWL_ID);
		
		textID = (TextView)findViewById(R.id.DetailIDLab);
		textTgl = (TextView)findViewById(R.id.DetailTglLab);
		textKelas = (TextView)findViewById(R.id.DetailKelasLab);
		textMapel = (TextView)findViewById(R.id.DetailMapelLab);
		textJamke = (TextView)findViewById(R.id.DetailJamKeLab);
		textKet = (TextView)findViewById(R.id.DetailKetLab);
		textStatus = (TextView)findViewById(R.id.DetailStatusLab);
		textNamaGR = (TextView)findViewById(R.id.DetailNamaGR);
		textNamaLB = (TextView)findViewById(R.id.namaSDLB);
		approveJdwl = (Button)findViewById(R.id.approveJdwl);
		approveJdwl.setOnClickListener(this);
		deleteJdwl = (Button)findViewById(R.id.deleteJdwl);
		deleteJdwl.setOnClickListener(this);
		
		textID.setText(no);
		
		getJadwalLB();
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaJLB = user.get(SessionManager.KEY_NAME);
		textNamaLB.setText(namaJLB);
	}
	
	private void lihatJadwal(){
		JSONObject jsonObjectJ = null;
		try {
			jsonObjectJ = new JSONObject(JSON_STRINGJ);
			JSONArray result = jsonObjectJ.getJSONArray(Config.TAG_JSON_ARRAY);
			String tgl2 = textTgl.getText().toString().trim();
			String jamKe2 = textJamke.getText().toString().trim();
			
			for(int i=0; i<result.length(); i++){
				JSONObject jo = result.getJSONObject(i);
				String tgl1 = jo.getString(Config.TAG_TGL);
				String jamKe1 = jo.getString(Config.TAG_JAMKE);
				if((tgl1.equals(tgl2)) && (jamKe1.equals(jamKe2))){
					already_exists = "ada";
					Toast.makeText(getApplicationContext(), "Jadwal yang Anda Masukkan Sudah Ada", Toast.LENGTH_SHORT).show();
					break;
				}
				
			}
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	private void getJSONJDW(){
		class GetJSONjdw extends AsyncTask<Void,Void,String>{
			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ViewJadwalLab.this,"Fetching Data","Wait...",false,false);
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

	private void getJadwalLB(){
		class GetJadwalLb extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ViewJadwalLab.this,"Fetching...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String lb) {
				// TODO Auto-generated method stub
				super.onPostExecute(lb);
				loading.dismiss();
                showJadwalLB(lb);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rhl = new RequestHandler();
				String sl = rhl.sendGetRequestParam(Config.URL_GET_DATA, no);
				return sl;
			}
			
		}
		GetJadwalLb gjl = new GetJadwalLb();
		gjl.execute();
	}
	
	private void showJadwalLB(String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
			JSONObject c = result.getJSONObject(0);
			String tgl = c.getString(Config.TAG_TGL);
			String kelas = c.getString(Config.TAG_KELAS);
			String mapel = c.getString(Config.TAG_MAPEL);
			String jamKe = c.getString(Config.TAG_JAMKE);
			String ket = c.getString(Config.TAG_KET);
			String status = c.getString(Config.TAG_STATUS);
			String namaGR = c.getString(Config.TAG_nmGR);
			String namaLB = c.getString(Config.TAG_nmLB);
			
			textTgl.setText(tgl);
			textKelas.setText(kelas);
			textMapel.setText(mapel);
			textJamke.setText(jamKe);
			textKet.setText(ket);
			textStatus.setText(status);
			textNamaGR.setText(namaGR);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void updateJadwal(){
		textStatus.setText("Approve");
		final String tgl = textTgl.getText().toString().trim();
		final String kelas = textKelas.getText().toString().trim();
		final String mapel = textMapel.getText().toString().trim();
		final String jamKe = textJamke.getText().toString().trim();
		final String ket = textKet.getText().toString().trim();
		final String sts = textStatus.getText().toString().trim();
		final String namaGR = textNamaGR.getText().toString().trim();
		final String namaLB = textNamaLB.getText().toString().trim();
		
		class UpdateJadwal extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ViewJadwalLab.this,"Updating...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                Toast.makeText(ViewJadwalLab.this,s,Toast.LENGTH_LONG).show();
                Intent a = new Intent(ViewJadwalLab.this, LBviewAllJdwl.class);
                startActivity(a);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				HashMap<String,String> hashMap = new HashMap<>();
				hashMap.put(Config.KEY_ID, no);
				hashMap.put(Config.KEY_TGL, tgl);
				hashMap.put(Config.KEY_KELAS, kelas);
				hashMap.put(Config.KEY_MAPEL, mapel);
				hashMap.put(Config.KEY_JAMKE, jamKe);
				hashMap.put(Config.KEY_KET, ket);
				hashMap.put(Config.KEY_STATUS, sts);
				hashMap.put(Config.KEY_nmGR, namaGR);
				hashMap.put(Config.KEY_nmLB, namaLB);
				
				RequestHandler rh = new RequestHandler();
				String s = rh.sendPostRequest(Config.URL_UPDATE_JWL, hashMap);
				
				return s;
			}
			
		}
		UpdateJadwal uj = new UpdateJadwal();
		uj.execute();
	}
	
	private void deleteJadwal(){
		class DeleteJadwal extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ViewJadwalLab.this, "Deleting...", "Wait...", false, false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                Toast.makeText(ViewJadwalLab.this, s, Toast.LENGTH_LONG).show();
//                Intent a = new Intent(ViewJadwal.this, ViewAllJadwal.class);
//                startActivity(a);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_DELETE_JWL, no);
				return s;
			}
			
		}
		DeleteJadwal dj = new DeleteJadwal();
		dj.execute();
	}
	
	private void confirmDeleteJadwal(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this jadwal?");
        
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				deleteJadwal();
				startActivity(new Intent(ViewJadwalLab.this, LBviewAllJdwl.class));
			}
		});
        
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
        
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == approveJdwl){
			getJSONJDW();
            if(already_exists.equals("ga")){
            	updateJadwal();
            }
            already_exists = "ga";
        }
 
        if(v == deleteJdwl){
            confirmDeleteJadwal();
        }
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,LBviewAllJdwl.class));
		finish();
	}
}
