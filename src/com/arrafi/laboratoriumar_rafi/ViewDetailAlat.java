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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ViewDetailAlat extends Activity implements View.OnClickListener{
	//Defining views
			private TextView textID;
			private TextView textNama;
			private Spinner textKondisi;
			private TextView textStatus;
			private TextView textNamaGR;
			private TextView textKeluhan;
			private TextView textKetKeluhan;
			private TextView textTGLBeli;
			private TextView namaSES;
			private Button btnHapus, btnUpdate;
			String namaJLB;
			private SessionManager session;
			
			private String no;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.view_detail_alat);
		
		Intent intent = getIntent();
		
		no = intent.getStringExtra(Config.IDNYA_ALAT);
		
		textID = (TextView)findViewById(R.id.detailTmbID);
		textNama = (TextView)findViewById(R.id.detailTmbNama);
		textKondisi = (Spinner)findViewById(R.id.detailKondisi);
		String listAlat1[]={"Kondisi?","Layak Pakai","Tidak Layak Pakai"};
		ArrayAdapter<String> AdapterListLb1 = new ArrayAdapter<String>(this,
				R.layout.spinner_text,listAlat1);
		AdapterListLb1.setDropDownViewResource(R.layout.spinner_item);
		textKondisi.setAdapter(AdapterListLb1);
		textStatus = (TextView)findViewById(R.id.detailTmbSts);
		textNamaGR = (TextView)findViewById(R.id.detailTmbNmGR);
		textKeluhan = (TextView)findViewById(R.id.detailTmbKeluhan);
		textKetKeluhan = (TextView)findViewById(R.id.detailTmbKetKeluhan);
		textTGLBeli = (TextView)findViewById(R.id.detailTmbTglBeli);
		namaSES = (TextView)findViewById(R.id.namaSLBTdetail);
		btnHapus = (Button)findViewById(R.id.hapusAltdetail);
		btnHapus.setOnClickListener(this);
		btnUpdate = (Button)findViewById(R.id.updateAltdetail);
		btnUpdate.setOnClickListener(this);
		
		textID.setText(no);
		
		getAlatLab();
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaJLB = user.get(SessionManager.KEY_NAME);
		namaSES.setText(namaJLB);
	}

	private void getAlatLab(){
		class GetAlat extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ViewDetailAlat.this,"Fetching...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String lb) {
				// TODO Auto-generated method stub
				super.onPostExecute(lb);
				loading.dismiss();
                showAlatLab(lb);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rhl = new RequestHandler();
				String sl = rhl.sendGetRequestParam(Config.URL_GET_DETAIL_ALAT, no);
				return sl;
			}
			
		}
		GetAlat gjl = new GetAlat();
		gjl.execute();
	}
	
	private void showAlatLab(String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
			JSONObject c = result.getJSONObject(0);
			String namaBR = c.getString(Config.TAG_NAMA_BARANG);
			String kondisi = c.getString(Config.TAG_KODISI_BGR);
			String statusBR = c.getString(Config.TAG_STATUS_BRG);
			String pinjamGR = c.getString(Config.TAG_GURU_PIJNAM);
			String keluhan = c.getString(Config.TAG_KELUHAN_BRG);
			String ketKeluhan = c.getString(Config.TAG_KET_KELUHAN_BRG);
			String tglBeli = c.getString(Config.TAG_TGL_BELI_BRG);
			
			textNama.setText(namaBR);
//			textKondisi.setText(kondisi);
			textStatus.setText(statusBR);
			textNamaGR.setText(pinjamGR);
			textKeluhan.setText(keluhan);
			textKetKeluhan.setText(ketKeluhan);
			textTGLBeli.setText(tglBeli);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void updateAlat(){
		final String namaAl = textNama.getText().toString().trim();
		final String kondisiAl = textKondisi.getSelectedItem().toString().trim();
		final String stsAl = textStatus.getText().toString().trim();
		final String namaGRAl = textNamaGR.getText().toString().trim();
		final String kelAl = textKeluhan.getText().toString().trim();
		final String ketKelAl = textKetKeluhan.getText().toString().trim();
		final String tglBeliAl = textTGLBeli.getText().toString().trim();
		
		class UpdateAlat extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ViewDetailAlat.this,"Updating...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                Toast.makeText(ViewDetailAlat.this,s,Toast.LENGTH_LONG).show();
                Intent a = new Intent(ViewDetailAlat.this, PeralatanLab.class);
                startActivity(a);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				HashMap<String,String> hashMap = new HashMap<>();
				hashMap.put(Config.KEY_ID_ALAT, no);
				hashMap.put(Config.KEY_NAMA_BARANG, namaAl);
				hashMap.put(Config.KEY_KODISI_BGR, kondisiAl);
				hashMap.put(Config.KEY_STATUS_BRG, stsAl);
				hashMap.put(Config.KEY_GURU_PIJNAM, namaGRAl);
				hashMap.put(Config.KEY_KELUHAN_BRG, kelAl);
				hashMap.put(Config.KEY_KET_KELUHAN_BRG, ketKelAl);
				hashMap.put(Config.KEY_TGL_BELI_BRG, tglBeliAl);
				
				RequestHandler rh = new RequestHandler();
				String s = rh.sendPostRequest(Config.URL_EDIT_ALAT, hashMap);
				
				return s;
			}
			
		}
		UpdateAlat uj = new UpdateAlat();
		uj.execute();
	}
	
	private void deleteAlat(){
		class DeleteAlat extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ViewDetailAlat.this, "Deleting...", "Wait...", false, false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                Toast.makeText(ViewDetailAlat.this, s, Toast.LENGTH_LONG).show();
//                Intent a = new Intent(ViewJadwal.this, ViewAllJadwal.class);
//                startActivity(a);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequestParam(Config.URL_DELETE_ALAT, no);
				return s;
			}
			
		}
		DeleteAlat dj = new DeleteAlat();
		dj.execute();
	}
	
	private void confirmDeleteAlat(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to delete this Alat?");
        
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				deleteAlat();
				startActivity(new Intent(ViewDetailAlat.this, PeralatanLab.class));
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
		if(v == btnHapus){
			confirmDeleteAlat();
        }
		if(v==btnUpdate){
			updateAlat();
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,PeralatanLab.class));
		finish();
	}
}
