package com.arrafi.laboratoriumar_rafi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TambahJdwlLab extends Activity implements View.OnClickListener{
	private EditText editTgl, editKet;
	private TextView editStatus, editMapel, namaGuruT;
	private Button cancel, ok;
	private String JSON_STRINGJ;
	private String already_exists = "";
	private Spinner editKelasLab, editJamke;
	private DatePickerDialog fromDatePickerDialog;
	private SimpleDateFormat dateFormatter;
	private ProgressDialog pDialog;
	private String namaLBL, confirm, url, hasilNama, namaPIN;
	private SessionManager session;
	private TextView namaLBT, kelas;
	private String s, nisTmp, mapelTmp;
	
	ArrayList<String> listItems3=new ArrayList<>();
    ArrayAdapter<String> adapter3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.tambah_jdwl_lab);
		
//		String listLb1[]={"Pilih Kelas","Kelas 1 Joy","Kelas 1 Love","Kelas 1 Peace","Kelas 2 Faith","Kelas 2 Joy","Kelas 2 Love","Kelas 2 Peace","Kelas 3 Faith","Kelas 3 Joy","Kelas 3 Love","Kelas 3 Peace","Kelas 4 Faith","Kelas 4 Joy","Kelas 4 Love","Kelas 4 Peace","Kelas 5 Love","Kelas 6 Joy","Kelas 6 Love","Kelas 6 Peace"};
//		editKelas = (Spinner)findViewById(R.id.editKelasLab);
//		ArrayAdapter<String> AdapterListLb1 = new ArrayAdapter<String>(this,
//                R.layout.spinner_text,listLb1);
//		AdapterListLb1.setDropDownViewResource(R.layout.spinner_item);
//		editKelas.setAdapter(AdapterListLb1);
//		
//		String listLb2[]={"Pilih Mata Pelajaran","Matematika","B.Inggris","B.Indonesia","IPA","IPS"};
//		editMapel = (Spinner)findViewById(R.id.editMapelLab);
//		ArrayAdapter<String> AdapterListLb2 = new ArrayAdapter<String>(this,
//				R.layout.spinner_text,listLb2);
//		AdapterListLb2.setDropDownViewResource(R.layout.spinner_item);
//		editMapel.setAdapter(AdapterListLb2);
		
		String listLb3[]={"Pilih Jam","Ke 1 - Ke 2 (Pukul 07.30 - 08.30)","Ke 3 - Ke 4 (Pukul 08.30 - 09.30)","Ke 5 - Ke 6 (Pukul 10.00 - 11.00)","Ke 7 - Ke 8 (Pukul 11.00 - 12.00)","Ke 9 - Ke 10 (Pukul 13.00 - 14.00)"};
		editJamke = (Spinner)findViewById(R.id.editJamkeLab);
		ArrayAdapter<String> AdapterListLb3 = new ArrayAdapter<String>(this,
				R.layout.spinner_text,listLb3);
		AdapterListLb3.setDropDownViewResource(R.layout.spinner_item);
		editJamke.setAdapter(AdapterListLb3);
		
		editKet = (EditText)findViewById(R.id.editKetLab);
		cancel = (Button)findViewById(R.id.cancelJadwLab);
		cancel.setOnClickListener(this);
		ok = (Button)findViewById(R.id.inputJadwLab);
		ok.setOnClickListener(this);
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	 	editTgl = (EditText) findViewById(R.id.editTglLab);
	 	editTgl.setOnClickListener(this);
	 	editTgl.setInputType(InputType.TYPE_NULL);
	 	editTgl.requestFocus();
        setDataTimeField();
        
        editStatus = (TextView)findViewById(R.id.editStatusLab);
        editStatus.setText("Approve");
        
        session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaLBL = user.get(SessionManager.KEY_NAME);
		namaLBT = (TextView)findViewById(R.id.namaSLBT);
		namaLBT.setText(namaLBL);
		kelas = (TextView)findViewById(R.id.txt_nampung_dataJD);
		editMapel = (TextView)findViewById(R.id.editMapelLab);
		namaGuruT=(TextView)findViewById(R.id.editNamaGuru);
		
		url = "http://104.152.168.28/~arrafico/arrafi/laboratorium/spinnerKelasLab.php";
		
		editKelasLab=(Spinner)findViewById(R.id.editKelasLab);
	    adapter3=new ArrayAdapter<String>(this,R.layout.spinner_guru,R.id.txt,listItems3);
	    editKelasLab.setAdapter(adapter3);
	    editKelasLab.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				s = (String) (editKelasLab.getSelectedItem());
	 	    	   kelas.setText(s);
	 	    	   nisTmp = kelas.getText().toString().trim();
	 	    	   
	 	    	   	String[] tempNama = nisTmp.split(" ");
		 	         ArrayList<String> teks_lengkap= new ArrayList<String>();
		 	        mapelTmp="";
		 	   
		 	         for(int i=0; i < tempNama.length ; i++)
		 	             teks_lengkap.add(tempNama[i].trim());
		 	   
		 	         for(int i=0; i < tempNama.length ; i++)
		 	        	mapelTmp = mapelTmp + teks_lengkap.get(i)+"%20";
	 	    	   getKelas();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    
	    Intent intent = getIntent();
	    
	    confirm = intent.getStringExtra(Config.GETJDWL);
	}
	
	private void getKelas(){
		class GetKelas extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(TambahJdwlLab.this,"Pengambilan Data...","Mohon Tunggu...",false,false);
			}

			@Override
			protected void onPostExecute(String sp) {
				// TODO Auto-generated method stub
				super.onPostExecute(sp);
				loading.dismiss();
				showKelas(sp);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				RequestHandler rhp = new RequestHandler();
				String sp = rhp.sendGetRequestParam(Config.URL_GET_MP_GR, mapelTmp);
				return sp;
			}
			
		}
		GetKelas gjg = new GetKelas();
		gjg.execute();
	}
    
	private void showKelas(String json){
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
			JSONObject c = result.getJSONObject(0);
			
			String mapel = c.getString(Config.TAG_MAPEL_JG1).toString();
			String manaGR = c.getString(Config.TAG_NAMAGR_JG).toString();
			
			editMapel.setText(mapel);
			namaGuruT.setText(manaGR);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void lihatJadwal(){
		JSONObject jsonObjectJ = null;
		try {
			jsonObjectJ = new JSONObject(JSON_STRINGJ);
			JSONArray result = jsonObjectJ.getJSONArray(Config.TAG_JSON_ARRAY);
			String tgl2 = editTgl.getText().toString().trim();
			String jamKe2 = editJamke.getSelectedItem().toString().trim();
			
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
				loading = ProgressDialog.show(TambahJdwlLab.this,"Fetching Data","Wait...",false,false);
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
	
	public void onStart(){
	      super.onStart();
	      BackTask bt1=new BackTask();
	      bt1.execute();
	}
	
	private class BackTask extends AsyncTask<Void,Void,Void> {
	      ArrayList<String> list;
	      protected void onPreExecute(){
	        super.onPreExecute();
	        list=new ArrayList<>();
	      }
	      protected Void doInBackground(Void...params){
	        InputStream is=null;
	        String result="";
	        try{
	           HttpClient httpclient=new DefaultHttpClient();
	           HttpPost httppost= new HttpPost(url);
	           HttpResponse response=httpclient.execute(httppost);
	           HttpEntity entity = response.getEntity();
	           // Get our response as a String.
	           is = entity.getContent();
	        }catch(IOException e){
	           e.printStackTrace();
	        }

	        //convert response to string
	        try{
	           BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));           
	           String line = null;
	           while ((line = reader.readLine()) != null) {
	             result+=line;
	           }
	           is.close();
	           //result=sb.toString();
	        }catch(Exception e){
	           e.printStackTrace();
	        }
	        // parse json data
	        try{
	           JSONArray jArray =new JSONArray(result);
	           list.add("Pilih Kelas");
	           for(int i=0;i<jArray.length();i++){
	              JSONObject jsonObject=jArray.getJSONObject(i);
	              // add interviewee name to arraylist
	              list.add(jsonObject.getString("kelas"));
	           }
	        }
	        catch(JSONException e){
	           e.printStackTrace();
	        }
	        return null;
	      }
	      protected void onPostExecute(Void result){
	        listItems3.addAll(list);
	        adapter3.notifyDataSetChanged();
	      }
	   }

	//Adding
			private void tambahJadwalLabor(){
				final String tgl = editTgl.getText().toString().trim();
				final String kelas = editKelasLab.getSelectedItem().toString().trim();
				final String mapel = editMapel.getText().toString().trim();
				final String jamKe = editJamke.getSelectedItem().toString().trim();
				final String ket = editKet.getText().toString().trim();
				final String sts = editStatus.getText().toString().trim();
				final String namaGR = namaGuruT.getText().toString().trim();
				final String namaLB = namaLBT.getText().toString().trim();
				
				class AddJadwalLabor extends AsyncTask<Void,Void,String>{
					
					ProgressDialog loading;

					@Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						loading = ProgressDialog.show(TambahJdwlLab.this,"Adding...","Wait...",false,false);
					}

					@Override
					protected void onPostExecute(String s) {
						// TODO Auto-generated method stub
						super.onPostExecute(s);
						loading.dismiss();
		                Toast.makeText(TambahJdwlLab.this,s,Toast.LENGTH_LONG).show();
		                if(confirm.equals("APP")){
		                	Intent vJ = new Intent(TambahJdwlLab.this, LBviewAPPJdwl.class);
		                	startActivity(vJ);
		                }else if(confirm.equals("ALL")){
		                	Intent vJ = new Intent(TambahJdwlLab.this, LBviewAllJdwl.class);
		                	startActivity(vJ);
		                }
		                finish();
					}

					@Override
					protected String doInBackground(Void... v) {
						// TODO Auto-generated method stub
						HashMap<String,String> params = new HashMap<>();
						params.put(Config.KEY_TGL, tgl);
						params.put(Config.KEY_KELAS, kelas);
						params.put(Config.KEY_MAPEL, mapel);
						params.put(Config.KEY_JAMKE, jamKe);
						params.put(Config.KEY_KET, ket);
						params.put(Config.KEY_STATUS, sts);
						params.put(Config.KEY_nmGR, namaGR);
						params.put(Config.KEY_nmLB, namaLB);
						
						RequestHandler rh = new RequestHandler();
						String inJD = rh.sendPostRequest(Config.URL_ADD, params);
						return inJD;
					}
					
				}
				AddJadwalLabor aj = new AddJadwalLabor();
				aj.execute();
			}
			
			private void setDataTimeField(){
				editTgl.setOnClickListener((OnClickListener) this);
				Calendar newCalender = Calendar.getInstance();
				
				fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						Calendar newDate = Calendar.getInstance();
		                newDate.set(year, monthOfYear, dayOfMonth);
		                editTgl.setText(dateFormatter.format(newDate.getTime()));
					}
		        },newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
				fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-10000);
			}
			
			public boolean lengkapiData(){
				editTgl.getText().toString().trim();
				editTgl.setError(null);
				editKelasLab.getSelectedItem().toString().trim();
				editMapel.getText().toString().trim();
				editJamke.getSelectedItem().toString().trim();
				editKet.getText().toString().trim();
				editKet.setError(null);
				namaGuruT.getText().toString().trim();
				if(editTgl.length() == 0){
					editTgl.setError(Html
							.fromHtml("<font color='white'>Input tidak boleh kosong</font>"));
					return false;
				}else if (editKelasLab.getSelectedItem().toString().equals("Pilih Kelas")){
					Toast.makeText(getApplicationContext(), "Kelas Belum Dipilih!!", Toast.LENGTH_LONG).show();
				}else if (editMapel.length() == 0){
					Toast.makeText(getApplicationContext(), "Mata Pelajaran Belum Dipilih!!", Toast.LENGTH_LONG).show();
				}else if (editJamke.getSelectedItem().toString().equals("Pilih Jam")){
					Toast.makeText(getApplicationContext(), "Jam Belum Dipilih!!", Toast.LENGTH_LONG).show();
				}else if(editKet.length() == 0){
					editKet.setError(Html
							.fromHtml("<font color='white'>Input tidak boleh kosong</font>"));
					return false;
				}else if(namaGuruT.length() == 0){
					Toast.makeText(getApplicationContext(), "Guru Belum Dipilih!!", Toast.LENGTH_LONG).show();
				}else{
					getJSONJDW();
		            if(already_exists.equals("ga")){
		            	tambahJadwalLabor();
		            }
		            already_exists = "ga";
				}
				
				return true;
				
			}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == editTgl){
			fromDatePickerDialog.show();
		}
		
		if(v == cancel){
			if(confirm.equals("APP")){
				startActivity(new Intent(this,LBviewAPPJdwl.class));
			}else if(confirm.equals("APP")){
				startActivity(new Intent(this,LBviewAllJdwl.class));
			}
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
		if(confirm.equals("APP")){
			startActivity(new Intent(this,LBviewAPPJdwl.class));
		}else if(confirm.equals("APP")){
			startActivity(new Intent(this,LBviewAllJdwl.class));
		}
		finish();
	}
}
