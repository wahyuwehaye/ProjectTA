package com.arrafi.laboratoriumar_rafi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TambahAlatLab extends Activity implements View.OnClickListener{
	private EditText editNamaBarang, editTglBeli;
	private TextView editStatus, editTmbNmGR, editKeluhan, editKetKeluhan,editKondisi, editKonfirm;
	private Button cancel, ok;
//	private Spinner editKondisi;
	private String JSON_STRINGJ;
	private String already_exists = "";
	private DatePickerDialog fromDatePickerDialog;
	private SimpleDateFormat dateFormatter;
	private ProgressDialog pDialog;
	String namaTMB;
	private SessionManager session;
	private TextView namaTMBL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.tambah_alat_lab);
		
//		String listAlat1[]={"Kondisi?","Layak Pakai","Tidak Layak Pakai"};
		editKondisi = (TextView)findViewById(R.id.editTmbKondisiAl);
//		ArrayAdapter<String> AdapterListLb1 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_dropdown_item,listAlat1);
//		editKondisi.setAdapter(AdapterListLb1);
		editNamaBarang = (EditText)findViewById(R.id.editTmbNama);
		editTglBeli = (EditText)findViewById(R.id.editTmbTglBeli);
		editStatus = (TextView)findViewById(R.id.editTmbSts);
		editTmbNmGR = (TextView)findViewById(R.id.editTmbNmGR);
		editKeluhan = (TextView)findViewById(R.id.editTmbKeluhan);
		editKetKeluhan = (TextView)findViewById(R.id.editTmbKetKeluhan);
		editKonfirm = (TextView)findViewById(R.id.editKonfirmKeluhan);
		cancel = (Button)findViewById(R.id.cancelTmbAlt);
		ok = (Button)findViewById(R.id.inputTmbAlt);
		namaTMBL = (TextView)findViewById(R.id.namaSLBT);
		cancel.setOnClickListener(this);
		ok.setOnClickListener(this);
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		editTglBeli.setOnClickListener(this);
		editTglBeli.setInputType(InputType.TYPE_NULL);
		editTglBeli.requestFocus();
        setDataTimeField();
        
        session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaTMB = user.get(SessionManager.KEY_NAME);
		namaTMBL.setText(namaTMB);
		editKondisi.setText("Layak Pakai");
		editStatus.setText("Tersedia");
		editTmbNmGR.setText("");
		editKeluhan.setText("");
		editKetKeluhan.setText("");
		editKonfirm.setText("");
	}
	
	private void lihatJadwal(){
		JSONObject jsonObjectJ = null;
		try {
			jsonObjectJ = new JSONObject(JSON_STRINGJ);
			JSONArray result = jsonObjectJ.getJSONArray(Config.TAG_JSON_ARRAY);
			String nmBRG = editNamaBarang.getText().toString().trim();
			
			for(int i=0; i<result.length(); i++){
				JSONObject jo = result.getJSONObject(i);
				String nmBRGP = jo.getString(Config.TAG_NAMA_BARANG);
				if((nmBRGP.equals(nmBRG))){
					already_exists = "ada";
					Toast.makeText(getApplicationContext(), "Nama Barang yang Anda Masukkan Sudah Ada", Toast.LENGTH_SHORT).show();
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
				loading = ProgressDialog.show(TambahAlatLab.this,"Fetching Data","Wait...",false,false);
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
				String s = rh.sendGetRequest(Config.URL_GET_NAMA_BARANG);
				return s;
			}
			
		}
		GetJSONjdw gj = new GetJSONjdw();
		gj.execute();
	}

	//Adding
	private void tambahAlatLabor(){
		final String namaBarang = editNamaBarang.getText().toString().trim();
		final String kondisi = editKondisi.getText().toString().trim();
		final String status = editStatus.getText().toString().trim();
		final String namaGR = editTmbNmGR.getText().toString().trim();
		final String keluhan = editKeluhan.getText().toString().trim();
		final String ketKeluhan = editKetKeluhan.getText().toString().trim();
//		final String tglBeli = editTglBeli.getText().toString().trim();
		final String konfrm = editKonfirm.getText().toString().trim();
		
		class AddALAT extends AsyncTask<Void,Void,String>{
			
			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(TambahAlatLab.this,"Adding...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                Toast.makeText(TambahAlatLab.this,s,Toast.LENGTH_LONG).show();
                Intent vAl = new Intent(TambahAlatLab.this, PeralatanLab.class);
                startActivity(vAl);
                finish();
			}

			@Override
			protected String doInBackground(Void... v) {
				// TODO Auto-generated method stub
				HashMap<String,String> params = new HashMap<>();
				params.put(Config.KEY_NAMA_BARANG, namaBarang);
				params.put(Config.KEY_KODISI_BGR, kondisi);
				params.put(Config.KEY_STATUS_BRG, status);
				params.put(Config.KEY_GURU_PIJNAM, namaGR);
				params.put(Config.KEY_KELUHAN_BRG, keluhan);
				params.put(Config.KEY_KET_KELUHAN_BRG, ketKeluhan);
//				params.put(Config.KEY_TGL_BELI_BRG, tglBeli);
				params.put(Config.KEY_KONFIRM_BRG, konfrm);
				
				RequestHandler rhAl = new RequestHandler();
				String inJDAl = rhAl.sendPostRequest(Config.URL_ADD_ALAT, params);
				return inJDAl;
			}
			
		}
		AddALAT adl = new AddALAT();
		adl.execute();
	}
	
	private void setDataTimeField(){
		editTglBeli.setOnClickListener((OnClickListener) this);
		Calendar newCalender = Calendar.getInstance();
		
		fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editTglBeli.setText(dateFormatter.format(newDate.getTime()));
			}
        },newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
	}
	
	public boolean lengkapiData(){
		editNamaBarang.getText().toString().trim();
		editNamaBarang.setError(null);
		if(editNamaBarang.length() == 0){
			editNamaBarang.setError(Html
					.fromHtml("<font color='white'>Input tidak boleh kosong</font>"));
			return false;
		}else{
			getJSONJDW();
            if(already_exists.equals("ga")){
            	tambahAlatLabor();
            }
            already_exists = "ga";
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == editTglBeli){
			fromDatePickerDialog.show();
		}
		
		if(v == cancel){
            startActivity(new Intent(this,PeralatanLab.class));
            finish();
        }
		
		if(v == ok){
            lengkapiData();
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
