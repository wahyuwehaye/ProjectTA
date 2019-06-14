package com.arrafi.laboratoriumar_rafi;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.GpsStatus.NmeaListener;
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

public class ViewDetailKembali extends Activity implements View.OnClickListener{
	//Defining views
			private TextView textID;
			private TextView textNama;
			private TextView textKondisi;
			private TextView textStatus;
			private TextView textNamaGR;
			private TextView textKeluhan;
			private TextView textKetKeluhan;
			private TextView textTGLBeli;
			private TextView namaLBRn, tvTglIn;
			private TextView namaSES;
			private Button btnKembali, btnPinjam;
			String namaJLB;
			private SessionManager session;
			
			private String no;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.view_detail_kembali);
		
		Intent intent = getIntent();
		
		no = intent.getStringExtra(Config.IDNYA_ALAT);
		
		textID = (TextView)findViewById(R.id.detailTmbIDKBL);
		textNama = (TextView)findViewById(R.id.detailTmbNamaKBL);
		textKondisi = (TextView)findViewById(R.id.detailKondisiKBL);
		textStatus = (TextView)findViewById(R.id.detailTmbStsKBL);
		textNamaGR = (TextView)findViewById(R.id.detailTmbNmGRKBL);
		textKeluhan = (TextView)findViewById(R.id.detailTmbKeluhanKBL);
		textKetKeluhan = (TextView)findViewById(R.id.detailTmbKetKeluhanKBL);
		textTGLBeli = (TextView)findViewById(R.id.detailTmbTglBeliKBL);
		namaLBRn = (TextView)findViewById(R.id.detailNMLBRn);
		tvTglIn = (TextView)findViewById(R.id.detailTGLInp);
		namaSES = (TextView)findViewById(R.id.namaSLBTdetailKBL);
		btnKembali = (Button)findViewById(R.id.kembaliAltKBL);
		btnKembali.setOnClickListener(this);
		btnPinjam = (Button)findViewById(R.id.pinjamAlatKBL);
		btnPinjam.setOnClickListener(this);
		
		textID.setText(no);
		
		getAlatLabKBL();
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaJLB = user.get(SessionManager.KEY_NAME);
		namaSES.setText(namaJLB);
	}

	private void getAlatLabKBL(){
		class GetAlatKBL extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ViewDetailKembali.this,"Fetching...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String lb) {
				// TODO Auto-generated method stub
				super.onPostExecute(lb);
				loading.dismiss();
                showAlatLabKBL(lb);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rhl = new RequestHandler();
				String sl = rhl.sendGetRequestParam(Config.URL_GET_DETAIL_ALAT, no);
				return sl;
			}
			
		}
		GetAlatKBL gjl = new GetAlatKBL();
		gjl.execute();
	}
	
	private void showAlatLabKBL(String json){
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
			String tglInp = c.getString(Config.TAG_TGL_INP);
			String nmLBRn = c.getString(Config.TAG_NM_LAB);
			
			textNama.setText(namaBR);
			textKondisi.setText(kondisi);
			textStatus.setText(statusBR);
			textNamaGR.setText(pinjamGR);
			textKeluhan.setText(keluhan);
			textKetKeluhan.setText(ketKeluhan);
			textTGLBeli.setText(tglBeli);
			tvTglIn.setText(tglInp);
			namaLBRn.setText(nmLBRn);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void updateAlatKBL(){
		textStatus.setText("Tersedia");
		textNamaGR.setText("");
		final String namaAl = textNama.getText().toString().trim();
		final String kondisiAl = textKondisi.getText().toString().trim();
		final String stsAl = textStatus.getText().toString().trim();
		final String namaGRAl = textNamaGR.getText().toString().trim();
		final String kelAl = textKeluhan.getText().toString().trim();
		final String ketKelAl = textKetKeluhan.getText().toString().trim();
		final String tglBeliAl = textTGLBeli.getText().toString().trim();
		final String tvTglInp = tvTglIn.getText().toString().trim();
		final String nmBRrn = namaLBRn.getText().toString().trim();
		
		class UpdateAlatPJM extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(ViewDetailKembali.this,"Updating...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                Toast.makeText(ViewDetailKembali.this,s,Toast.LENGTH_LONG).show();
                Intent a = new Intent(ViewDetailKembali.this, PengembalianAlat.class);
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
				hashMap.put(Config.KEY_TGL_INP, tvTglInp);
				hashMap.put(Config.KEY_NM_LAB, nmBRrn);
				
				RequestHandler rh = new RequestHandler();
				String s = rh.sendPostRequest(Config.URL_EDIT_ALAT, hashMap);
				
				return s;
			}
			
		}
		UpdateAlatPJM uj = new UpdateAlatPJM();
		uj.execute();
	}
	
	//Adding
		private void tambahHistoryAlat(){
			final String namaGRAl = namaSES.getText().toString().trim();
			final String namaAl = textNama.getText().toString().trim();
			final String tvTglInp = tvTglIn.getText().toString().trim();
			final String nmBRrn = namaLBRn.getText().toString().trim();
			
			class AddHistoryAL extends AsyncTask<Void,Void,String>{
				
				ProgressDialog loading;

				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					super.onPreExecute();
					loading = ProgressDialog.show(ViewDetailKembali.this,"Adding...","Wait...",false,false);
				}

				@Override
				protected void onPostExecute(String s) {
					// TODO Auto-generated method stub
					super.onPostExecute(s);
					loading.dismiss();
//	                Toast.makeText(ViewDetailKembali.this,s,Toast.LENGTH_LONG).show();
	                Intent vJ = new Intent(ViewDetailKembali.this, PengembalianAlat.class);
	                startActivity(vJ);
	                finish();
				}

				@Override
				protected String doInBackground(Void... v) {
					// TODO Auto-generated method stub
					HashMap<String,String> params = new HashMap<>();
					params.put(Config.KEY_NAMA_PEMINJAM, namaGRAl);
					params.put(Config.KEY_NAMA_BRG_PJM, namaAl);
					params.put(Config.KEY_TGL_PJM, tvTglInp);
					params.put(Config.KEY_NAMA_ORG_LAB, nmBRrn);
					
					RequestHandler rh = new RequestHandler();
					String inJD = rh.sendPostRequest(Config.URL_ADD_HISTORY_ALAT, params);
					return inJD;
				}
				
			}
			AddHistoryAL aj = new AddHistoryAL();
			aj.execute();
		}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btnKembali){
			startActivity(new Intent(this,PengembalianAlat.class));
			finish();
        }
		if(v==btnPinjam){
			tambahHistoryAlat();
			updateAlatKBL();
			
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,PengembalianAlat.class));
		finish();
	}
}
