package com.arrafi.laboratoriumar_rafi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TambahPenilaian extends Activity implements View.OnClickListener{
	private Button cancel, ok;
	private Spinner penjadwalan, mekanisme, pelayanan, sarana, suasana, getKelas;
	private ProgressDialog pDialog;
	private String JSON_STRINGJ;
	private String namaLB, url, namaPIN, hasilNama, namaPIN1, hasilNama1;
	private SessionManager session;
	private TextView namaLBTNI, namaGRNil, namaPGNil, mapelNil, jamKeNil, kelas, hashNil;
	String tmpKelas;
	private String s,n, nisTmp, mapelTmp;;
	
	ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_tambah_penilaian);
		
		cancel = (Button)findViewById(R.id.cancelPenilaian);
		cancel.setOnClickListener(this);
		ok = (Button)findViewById(R.id.inputPenilaian);
		ok.setOnClickListener(this);
		namaLBTNI = (TextView)findViewById(R.id.namaSESPENIL);
		namaGRNil = (TextView)findViewById(R.id.editPenilaiLab);
		namaPGNil = (TextView)findViewById(R.id.editDinilaiLab);
		mapelNil = (TextView)findViewById(R.id.editMapelNil);
		jamKeNil = (TextView)findViewById(R.id.editJamKeNil);
		kelas = (TextView)findViewById(R.id.txt_nampung_dataNILAI);
		hashNil = (TextView)findViewById(R.id.txt_hasilNilai);
		
//		tmpKelas = "Kelas 4 Love";
		
		
		String list1[]={"Pilih","Sangat Baik","Baik","Kurang","Sangat Kurang"};
		penjadwalan = (Spinner)findViewById(R.id.editPenjadwalan);
		ArrayAdapter<String> AdapterList1 = new ArrayAdapter<String>(this,
				R.layout.spinner_text,list1);
		AdapterList1.setDropDownViewResource(R.layout.spinner_item);
		penjadwalan.setAdapter(AdapterList1);
		
		mekanisme = (Spinner)findViewById(R.id.editMekanisme);
		ArrayAdapter<String> AdapterList2 = new ArrayAdapter<String>(this,
				R.layout.spinner_text,list1);
		AdapterList2.setDropDownViewResource(R.layout.spinner_item);
		mekanisme.setAdapter(AdapterList2);
		
		pelayanan = (Spinner)findViewById(R.id.editPelayanan);
		ArrayAdapter<String> AdapterList3 = new ArrayAdapter<String>(this,
				R.layout.spinner_text,list1);
		AdapterList3.setDropDownViewResource(R.layout.spinner_item);
		pelayanan.setAdapter(AdapterList3);
		
		sarana = (Spinner)findViewById(R.id.editSarana);
		ArrayAdapter<String> AdapterList4 = new ArrayAdapter<String>(this,
				R.layout.spinner_text,list1);
		AdapterList4.setDropDownViewResource(R.layout.spinner_item);
		sarana.setAdapter(AdapterList4);
		
		suasana = (Spinner)findViewById(R.id.editSuasana);
		ArrayAdapter<String> AdapterList5 = new ArrayAdapter<String>(this,
				R.layout.spinner_text,list1);
		AdapterList5.setDropDownViewResource(R.layout.spinner_item);
		suasana.setAdapter(AdapterList5);
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaLB = user.get(SessionManager.KEY_NAME);
		namaLBTNI.setText(namaLB);
		
		namaPIN = namaLBTNI.getText().toString();
		
		String[] tempNama = namaPIN.split(" ");
        ArrayList<String> teks_lengkap= new ArrayList<String>();
        hasilNama="";
  
        for(int i=0; i < tempNama.length ; i++)
            teks_lengkap.add(tempNama[i].trim());
  
        for(int i=0; i < tempNama.length ; i++)
        	hasilNama = hasilNama + teks_lengkap.get(i)+"%20";
        
        url = "http://104.152.168.28/~arrafico/arrafi/laboratorium/spinnerNilaiLab.php?nama_guru="+hasilNama;
		
		getKelas = (Spinner)findViewById(R.id.editKelasDinilai);
		adapter=new ArrayAdapter<String>(this,R.layout.spinner_guru,R.id.txt,listItems);
		getKelas.setAdapter(adapter);
		getKelas.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				s = (String) (getKelas.getSelectedItem());
	 	    	   kelas.setText(s);
	 	    	   nisTmp = kelas.getText().toString().trim();
	 	    	  tmpKelas = kelas.getText().toString().trim();
	 	    	   
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
	}
	
	private void getKelas(){
		class GetKelas extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(TambahPenilaian.this,"Pengambilan Data...","Mohon Tunggu...",false,false);
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
				String sp = rhp.sendGetRequestParam(Config.URL_GET_MAPEL_NILAI, mapelTmp);
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
			
			String namaGRUN = c.getString(Config.TAG_nmGR).toString();
			String namaLBN = c.getString(Config.TAG_nmLB).toString();
			String mapel = c.getString(Config.TAG_MAPEL).toString();
			String jamkeN = c.getString(Config.TAG_JAMKE).toString();
			
			namaGRNil.setText(namaGRUN);
			namaPGNil.setText(namaLBN);
			mapelNil.setText(mapel);
			jamKeNil.setText(jamkeN);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void onStart(){
	      super.onStart();
	      BackTask bt=new BackTask();
	      bt.execute();
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
//	              list.add(jsonObject.getString("no"));
	              list.add((jsonObject.getString("kelas")));
	           }
	        }
	        catch(JSONException e){
	           e.printStackTrace();
	        }
	        return null;
	      }
	      protected void onPostExecute(Void result){
	        listItems.addAll(list);
	        adapter.notifyDataSetChanged();
	      }
	   }
	
	//Adding
	private void tambahNilai(){
		final String getPenjadwalan = penjadwalan.getSelectedItem().toString().trim();
		final String getMekanisme = mekanisme.getSelectedItem().toString().trim();
		final String getPelayanan = pelayanan.getSelectedItem().toString().trim();
		final String getSarana = sarana.getSelectedItem().toString().trim();
		final String getSuasana = suasana.getSelectedItem().toString().trim();
		final String getNilKelas = getKelas.getSelectedItem().toString().trim();
		final String getNMGRNil = namaGRNil.getText().toString().trim();
		final String getNMPGNil = namaPGNil.getText().toString().trim();
		final String getMapelNil = mapelNil.getText().toString().trim();
		final String getJamKeNil = jamKeNil.getText().toString().trim();
		
		class AddNilaiLab extends AsyncTask<Void,Void,String>{
			
			ProgressDialog loading;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(TambahPenilaian.this,"Adding...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
                Toast.makeText(TambahPenilaian.this,s,Toast.LENGTH_LONG).show();
                Intent vJ = new Intent(TambahPenilaian.this, PenilaianKegiatan.class);
                startActivity(vJ);
                finish();
			}

			@Override
			protected String doInBackground(Void... v) {
				// TODO Auto-generated method stub
				HashMap<String,String> params = new HashMap<>();
				params.put(Config.KEY_PENJADWALAN, getPenjadwalan);
				params.put(Config.KEY_MEKANISME, getMekanisme);
				params.put(Config.KEY_PELAYANAN, getPelayanan);
				params.put(Config.KEY_SARANA, getSarana);
				params.put(Config.KEY_SUASANA, getSuasana);
				params.put(Config.KEY_NAMA_PENILAI_GR, getNMGRNil);
				params.put(Config.KEY_NAMA_PENILAI_LB, getNMPGNil);
				params.put(Config.KEY_PENILAI_KELAS, getNilKelas);
				params.put(Config.KEY_PENILAI_MAPEL, getMapelNil);
				params.put(Config.KEY_PENILAI_JAMKE, getJamKeNil);
				
				RequestHandler rh = new RequestHandler();
				String inJD = rh.sendPostRequest(Config.URL_ADD_PENILAIAN_LAB, params);
				return inJD;
			}
			
		}
		AddNilaiLab aj = new AddNilaiLab();
		aj.execute();
	}
	
	private void updateNilaiJadwal(){
		hashNil.setText("sudah");
		final String hasNilai = hashNil.getText().toString().trim();
		
		class UpdateNiliJad extends AsyncTask<Void,Void,String>{

			ProgressDialog loading;
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading = ProgressDialog.show(TambahPenilaian.this,"Updating...","Wait...",false,false);
			}

			@Override
			protected void onPostExecute(String s) {
				// TODO Auto-generated method stub
				super.onPostExecute(s);
				loading.dismiss();
//                Toast.makeText(TambahPenilaian.this,s,Toast.LENGTH_LONG).show();
//                Intent a = new Intent(ViewJadwal.this, ViewAllJadwal.class);
//                startActivity(a);
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				HashMap<String,String> hashMap = new HashMap<>();
				hashMap.put(Config.KEY_KELAS, tmpKelas);
				
				RequestHandler rh = new RequestHandler();
				String s = rh.sendPostRequest(Config.URL_UPDATE_NILAI_JADWAL, hashMap);
				
				return s;
			}
		}
		UpdateNiliJad uj = new UpdateNiliJad();
		uj.execute();
	}
	
	public boolean lengkapiData(){
		penjadwalan.getSelectedItem().toString().trim();
		mekanisme.getSelectedItem().toString().trim();
		pelayanan.getSelectedItem().toString().trim();
		sarana.getSelectedItem().toString().trim();
		suasana.getSelectedItem().toString().trim();
		getKelas.getSelectedItem().toString().trim();
		if(penjadwalan.getSelectedItem().toString().equals("Pilih")){
			Toast.makeText(getApplicationContext(), "Data Belum Lengkap!!", Toast.LENGTH_LONG).show();
		}else if(mekanisme.getSelectedItem().toString().equals("Pilih")){
			Toast.makeText(getApplicationContext(), "Data Belum Lengkap!!", Toast.LENGTH_LONG).show();
		}else if(pelayanan.getSelectedItem().toString().equals("Pilih")){
			Toast.makeText(getApplicationContext(), "Data Belum Lengkap!!", Toast.LENGTH_LONG).show();
		}else if(sarana.getSelectedItem().toString().equals("Pilih")){
			Toast.makeText(getApplicationContext(), "Data Belum Lengkap!!", Toast.LENGTH_LONG).show();
		}else if(suasana.getSelectedItem().toString().equals("Pilih")){
			Toast.makeText(getApplicationContext(), "Data Belum Lengkap!!", Toast.LENGTH_LONG).show();
		}else if(getKelas.getSelectedItem().toString().equals("Pilih Kelas")){
			Toast.makeText(getApplicationContext(), "Data Belum Lengkap!!", Toast.LENGTH_LONG).show();
		}else{
			tambahNilai();
			updateNilaiJadwal();
		}
		return false;
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == cancel){
            startActivity(new Intent(this,PenilaianKegiatan.class));
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
		startActivity(new Intent(this,PenilaianKegiatan.class));
		finish();
	}
}
