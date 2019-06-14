package com.arrafi.laboratoriumar_rafi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
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
import android.app.DatePickerDialog;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TambahNilai extends Activity implements View.OnClickListener{
	private EditText editKet;
	private Button cancel, ok;
	private Spinner jenisNilai, nilai, namaLabor, jadwalGR;
	private ProgressDialog pDialog;
	String namaLB, url, namaPIN, hasilNama;
	private SessionManager session;
	private TextView namaLBTNI;
	
	ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> adapter;
    
    ArrayList<String> listItems1=new ArrayList<>();
    ArrayAdapter<String> adapter1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.tambah_nilai);
		
		String list1[]={"Pilih Jenis Nilai","Nilai Kegiatan Laboran","Nilai Laboran"};
		jenisNilai = (Spinner)findViewById(R.id.editJenisNilai);
		ArrayAdapter<String> AdapterList1 = new ArrayAdapter<String>(this,
				R.layout.spinner_text,list1);
		AdapterList1.setDropDownViewResource(R.layout.spinner_item);
		jenisNilai.setAdapter(AdapterList1);
		
		String list2[]={"Pilih Nilai","10","20","30","40","50","60","70","80","90","100"};
		nilai = (Spinner)findViewById(R.id.editNilai);
		ArrayAdapter<String> AdapterList2 = new ArrayAdapter<String>(this,
				R.layout.spinner_text,list2);
		AdapterList2.setDropDownViewResource(R.layout.spinner_item);
		nilai.setAdapter(AdapterList2);
		
		namaLabor=(Spinner)findViewById(R.id.editNamaLabor);
	    adapter=new ArrayAdapter<String>(this,R.layout.spinner_laboran,R.id.txt,listItems);
	    namaLabor.setAdapter(adapter);
	    
	    editKet = (EditText)findViewById(R.id.editKetNil);
	    cancel = (Button)findViewById(R.id.cancelNilai);
		cancel.setOnClickListener(this);
		ok = (Button)findViewById(R.id.inputNilai);
		ok.setOnClickListener(this);
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		HashMap<String, String> user = session.getUserDetails();
		namaLB = user.get(SessionManager.KEY_NAME);
		namaLBTNI = (TextView)findViewById(R.id.namaSGTNI);
		namaLBTNI.setText(namaLB);
		
		namaPIN = namaLBTNI.getText().toString();
		
		String[] tempNama = namaPIN.split(" ");
        ArrayList<String> teks_lengkap= new ArrayList<String>();
        hasilNama="";
  
        for(int i=0; i < tempNama.length ; i++)
            teks_lengkap.add(tempNama[i].trim());
  
        for(int i=0; i < tempNama.length ; i++)
        	hasilNama = hasilNama + teks_lengkap.get(i)+"%20";
		
		url = "http://104.152.168.28/~arrafico/arrafi/laboratorium/spinnerJadwalNilai.php?nama_guru="+hasilNama;
		
		jadwalGR = (Spinner)findViewById(R.id.editJadwal);
		adapter1=new ArrayAdapter<String>(this,R.layout.spinner_guru,R.id.txt,listItems1);
		jadwalGR.setAdapter(adapter1);
	}

	public void onStart(){
	      super.onStart();
	      BackTask bt=new BackTask();
	      bt.execute();
	      BackTask1 bt2=new BackTask1();
	      bt2.execute();
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
	           HttpPost httppost= new HttpPost(Config.URL_SPINNER_LABORAN);
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
	           list.add("Nama Pegawai");
	           for(int i=0;i<jArray.length();i++){
	              JSONObject jsonObject=jArray.getJSONObject(i);
	              // add interviewee name to arraylist
	              list.add(jsonObject.getString("nama_pegawai"));
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
	
	private class BackTask1 extends AsyncTask<Void,Void,Void> {
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
	           list.add("Jam Ke -");
	           for(int i=0;i<jArray.length();i++){
	              JSONObject jsonObject=jArray.getJSONObject(i);
	              // add interviewee name to arraylist
	              list.add(jsonObject.getString("jamKe"));
	           }
	        }
	        catch(JSONException e){
	           e.printStackTrace();
	        }
	        return null;
	      }
	      protected void onPostExecute(Void result){
	        listItems1.addAll(list);
	        adapter1.notifyDataSetChanged();
	      }
	   }
	
	//Adding
			private void tambahNilai(){
				final String jenisNil = jenisNilai.getSelectedItem().toString().trim();
				final String nil = nilai.getSelectedItem().toString().trim();
				final String namaLb = namaLabor.getSelectedItem().toString().trim();
				final String ket = editKet.getText().toString().trim();
				final String namaGR = namaLBTNI.getText().toString().trim();
				
				class AddNilaiLab extends AsyncTask<Void,Void,String>{
					
					ProgressDialog loading;

					@Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						super.onPreExecute();
						loading = ProgressDialog.show(TambahNilai.this,"Adding...","Wait...",false,false);
					}

					@Override
					protected void onPostExecute(String s) {
						// TODO Auto-generated method stub
						super.onPostExecute(s);
						loading.dismiss();
		                Toast.makeText(TambahNilai.this,s,Toast.LENGTH_LONG).show();
		                Intent vJ = new Intent(TambahNilai.this, ViewNilaiLab.class);
		                startActivity(vJ);
		                finish();
					}

					@Override
					protected String doInBackground(Void... v) {
						// TODO Auto-generated method stub
						HashMap<String,String> params = new HashMap<>();
						params.put(Config.KEY_JENIS_NILAI, jenisNil);
						params.put(Config.KEY_NILAI, nil);
						params.put(Config.KEY_NILAI_LB, namaLb);
						params.put(Config.KEY_KET_NILAI, ket);
						params.put(Config.KEY_NILAI_GR, namaGR);
						
						RequestHandler rh = new RequestHandler();
						String inJD = rh.sendPostRequest(Config.URL_ADD_NILAI_LAB, params);
						return inJD;
					}
					
				}
				AddNilaiLab aj = new AddNilaiLab();
				aj.execute();
			}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == cancel){
            startActivity(new Intent(this,ViewNilaiLab.class));
            finish();
        }
		
		if(v == ok){
            tambahNilai();
        }
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		startActivity(new Intent(this,ViewNilaiLab.class));
		finish();
	}
}
