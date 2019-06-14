package com.arrafi.laboratoriumar_rafi;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DetailPinjam extends Activity implements View.OnClickListener{
	//Defining views
	private TextView textID;
	private TextView textNama;
	private TextView textKondisi;
	private TextView textStatus;
	private TextView textNamaGR;
	private TextView textKeluhan;
	private TextView textKetKeluhan;
	private TextView textTGLBeli;
	private TextView namaSES, konfirm, nmLab;
	private Button btnKembali, btnAppr;
	String namaJLB;
	private SessionManager session;
	
	private String no;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.detail_pinjam);
		
		Intent intent = getIntent();
		
		no = intent.getStringExtra(Config.IDNYA_ALAT);
		
		textID = (TextView)findViewById(R.id.detailTmbIDPinjam);
		textNama = (TextView)findViewById(R.id.detailTmbNamaPinjam);
		textKondisi = (TextView)findViewById(R.id.detailKondisiPinjam);
//		String listAlat1[]={"Kondisi?","Layak Pakai","Tidak Layak Pakai"};
//		ArrayAdapter<String> AdapterListLb1 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_dropdown_item,listAlat1);
//		textKondisi.setAdapter(AdapterListLb1);
		textStatus = (TextView)findViewById(R.id.detailTmbStsPinjam);
		textNamaGR = (TextView)findViewById(R.id.detailTmbNmGRPinjam);
		textKeluhan = (TextView)findViewById(R.id.detailTmbKeluhanPinjam);
		textKetKeluhan = (TextView)findViewById(R.id.detailTmbKetKeluhanPinjam);
		textTGLBeli = (TextView)findViewById(R.id.detailTmbTglBeliPinjam);
		namaSES = (TextView)findViewById(R.id.namaSLBTdetailPinjam);
		konfirm = (TextView)findViewById(R.id.detailKonfirmPinjam);
		nmLab = (TextView)findViewById(R.id.detailNMLaboran);
		btnKembali = (Button)findViewById(R.id.kembaliAltdetail);
		btnKembali.setOnClickListener(this);
		btnAppr = (Button)findViewById(R.id.approveAltdetail);
		btnAppr.setOnClickListener(this);
		
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
				loading = ProgressDialog.show(DetailPinjam.this,"Fetching...","Wait...",false,false);
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
			String konfri = c.getString(Config.TAG_KONFIRM_BRG);
			
			textNama.setText(namaBR);
			textKondisi.setText(kondisi);
			textStatus.setText(statusBR);
			textNamaGR.setText(pinjamGR);
			textKeluhan.setText(keluhan);
			textKetKeluhan.setText(ketKeluhan);
			textTGLBeli.setText(tglBeli);
			konfirm.setText(konfri);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void updateAlat(){
		konfirm.setText("Approve");
		final String namaAl = textNama.getText().toString().trim();
		final String kondisiAl = textKondisi.getText().toString().trim();
		final String stsAl = textStatus.getText().toString().trim();
		final String namaGRAl = textNamaGR.getText().toString().trim();
		final String kelAl = textKeluhan.getText().toString().trim();
		final String ketKelAl = textKetKeluhan.getText().toString().trim();
		final String tglBeliAl = textTGLBeli.getText().toString().trim();
		final String konf = konfirm.getText().toString().trim();
		final String nmLBRn = namaSES.getText().toString().trim();
		
		class UpdateAlat extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(DetailPinjam.this,"Updating...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                Toast.makeText(DetailPinjam.this,s,Toast.LENGTH_LONG).show();
                Intent a = new Intent(DetailPinjam.this, CekPinjamAlat.class);
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
				hashMap.put(Config.KEY_KONFIRM_BRG, konf);
				hashMap.put(Config.KEY_NM_LAB, nmLBRn);
				
				RequestHandler rh = new RequestHandler();
				String s = rh.sendPostRequest(Config.URL_EDIT_ALAT, hashMap);
				
				return s;
			}
			
		}
		UpdateAlat uj = new UpdateAlat();
		uj.execute();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btnKembali){
			startActivity(new Intent(this,CekPinjamAlat.class));
			finish();
        }
		if(v==btnAppr){
			updateAlat();
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,CekPinjamAlat.class));
		finish();
	}
}
