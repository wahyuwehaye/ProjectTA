package com.arrafi.laboratoriumar_rafi;

import java.util.ArrayList;
import java.util.HashMap;
 
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	Button login;
    Intent a;
    EditText username, password;
    String url, success;
    SessionManager session;
    Spinner spinner1, spinner2;
    String[] namaUser = {"Pilih User","Laboran","Guru"};
    int iconUser[] = { R.drawable.sbg,R.drawable.sbg};
    String[] namaLab = {"Pilih Lab","Multimedia","IPA"};
    int iconLab[] = { R.drawable.lab,R.drawable.lab};
    private Activity activity;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        
        activity = this;
        
        String list1[]={"Pilih User","Laboran","Guru"};
        spinner1 = (Spinner)findViewById(R.id.pengguna);
        ArrayAdapter<String> AdapterList1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,list1);
//        AdapterList1.setDropDownViewResource(R.layout.spinner_item);
		spinner1.setAdapter(AdapterList1);
//		spinner1.setAdapter(new MyAdapter(this, R.layout.raw1, list1));
        
		String list2[]={"Pilih Lab","Multimedia","IPA"};
        spinner2 = (Spinner)findViewById(R.id.lab);
        ArrayAdapter<String> AdapterList2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,list2);
		spinner2.setAdapter(AdapterList2);
//        spinner2.setAdapter(new MyAdapter(this, R.layout.raw2, list2));
 
        session = new SessionManager(getApplicationContext());
//        Toast.makeText(getApplicationContext(),
//                "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG)
//                .show();
 
        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
 
        login.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
            	if(spinner1.getSelectedItem().toString().equals("Pilih User")){
            		Toast.makeText(getApplicationContext(), "User Belum Dipilih!!", Toast.LENGTH_LONG).show();
            	}else
            	if(spinner1.getSelectedItem().toString().equals("Laboran")){
            		url = "http://104.152.168.28/~arrafico/arrafi/login/loginpegawai.php?" + "username="
                            + username.getText().toString() + "&password="
                            + password.getText().toString();
            		
            		if (username.getText().toString().trim().length() > 0
                            && password.getText().toString().trim().length() > 0) 
                    {
                        new Masuk().execute();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Username/Password masih kosong!!", Toast.LENGTH_LONG).show();
                    }
            	}else if(spinner1.getSelectedItem().toString().equals("Guru")){
            		url = "http://104.152.168.28/~arrafico/arrafi/login/loginguru.php?" + "username="
                            + username.getText().toString() + "&password="
                            + password.getText().toString();
            		
            		if (username.getText().toString().trim().length() > 0
                            && password.getText().toString().trim().length() > 0) 
                    {
                        new Masuk().execute();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Username/Password masih kosong!!", Toast.LENGTH_LONG).show();
                    }
            	}
//            	else{
//            		Toast.makeText(getApplicationContext(), "Data Belum Lengkap!!", Toast.LENGTH_LONG).show();
//            	}
                
 
//                if (username.getText().toString().trim().length() > 0
//                        && password.getText().toString().trim().length() > 0) 
//                {
//                    new Masuk().execute();
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(), "Username/Password masih kosong!!", Toast.LENGTH_LONG).show();
//                }
            }
        });
    }
    
    public class MyAdapter extends ArrayAdapter<String>{
    	  
    	  public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
    	      super(context, textViewResourceId, objects);
    	  }

    	  @Override
    	  public View getDropDownView(int position, View convertView,ViewGroup parent) {
    	      return getCustomView(position, convertView, parent);
    	  }

    	  @Override
    	  public View getView(int position, View convertView, ViewGroup parent) {
    	      return getCustomView(position, convertView, parent);
    	  }

    	  public View getCustomView(int position, View convertView, ViewGroup parent) {

    	      LayoutInflater inflater=getLayoutInflater();
    	      View row=inflater.inflate(R.layout.raw1, parent, false);
    	      TextView label=(TextView)row.findViewById(R.id.userLab);
    	      label.setText(namaUser[position]);

    	      ImageView icon=(ImageView)row.findViewById(R.id.image);
    	      icon.setImageResource(iconUser[position]);
    	      return row;
    	      }
    	  }
    
    public class MyAdapter1 extends ArrayAdapter<String>{
  	  
  	  public MyAdapter1(Context context, int textViewResourceId,   String[] objects) {
  	      super(context, textViewResourceId, objects);
  	  }

  	  @Override
  	  public View getDropDownView(int position, View convertView,ViewGroup parent) {
  	      return getCustomView(position, convertView, parent);
  	  }

  	  @Override
  	  public View getView(int position, View convertView, ViewGroup parent) {
  	      return getCustomView(position, convertView, parent);
  	  }

  	  public View getCustomView(int position, View convertView, ViewGroup parent) {

  	      LayoutInflater inflater=getLayoutInflater();
  	      View row=inflater.inflate(R.layout.raw2, parent, false);
  	      TextView label=(TextView)row.findViewById(R.id.jnsLab);
  	      label.setText(namaLab[position]);

  	      ImageView icon=(ImageView)row.findViewById(R.id.image);
  	      icon.setImageResource(iconLab[position]);
  	      return row;
  	      }
  	  }
 
    public class Masuk extends AsyncTask<String, String, String>{
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        ProgressDialog pDialog;
 
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Mohon Tunggu....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        
        @Override
        protected String doInBackground(String... arg0) {
        		JSONParser jParser = new JSONParser();
                JSONObject json = jParser.getJSONFromUrl(url);
                try {
                    success = json.getString("success");
                    Log.e("error", "nilai sukses=" + success);
                    JSONArray hasil = json.getJSONArray("login");
                    if (success.equals("1")) {
                        for (int i = 0; i < hasil.length(); i++) {
                            JSONObject c = hasil.getJSONObject(i);
                            
                            if(spinner1.getSelectedItem().toString().equals("Laboran")){
	                            String nama_pegawai = c.getString("nama_pegawai").trim();
	                            String id_pegawai = c.getString("id_pegawai").trim();
	                            String userLab = c.getString("username").trim();
	                            session.createLoginSession(nama_pegawai, id_pegawai, userLab);
	                            Log.e("ok", " ambil data");
                            }else 
                            if (spinner1.getSelectedItem().toString().equals("Guru")){
                            	String nama_guru = c.getString("nama_guru").trim();
                            	String id_guru = c.getString("id_guru").trim();
                            	String userGuru = c.getString("username").trim();
                                session.createLoginSession(nama_guru, id_guru, userGuru);
                                Log.e("ok", " ambil data");
                            }else{
                            	Toast.makeText(getApplicationContext(), "Data Belum Lengkap!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Log.e("error", "tidak bisa ambil data 0");
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    Log.e("error", "tidak bisa ambil data 1");
                }
        	return null;
        }
        
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
        		super.onPostExecute(result);
                pDialog.dismiss();
                if (success.equals("1")) {
                	
                	if(spinner1.getSelectedItem().toString().equals("Guru")){
                		a = new Intent(LoginActivity.this, MainActivity.class);
                	}else 
                	if(spinner1.getSelectedItem().toString().equals("Laboran")){
                		a = new Intent(LoginActivity.this, LaboranActivity.class);
                	}
                	if(spinner1.getSelectedItem().toString().equals("Pilih User")){
                		Toast.makeText(getApplicationContext(), "User Belum Dipilih!!", Toast.LENGTH_LONG).show();
                	}
//                	else{
//                		Toast.makeText(getApplicationContext(), "Data Belum Lengkap!!", Toast.LENGTH_LONG).show();
//                	}
                    startActivity(a);
//                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Username/Password Salah!!", Toast.LENGTH_LONG).show();
                }
        }
    }
    
    private void showAlertDialog(){
        //Deklarasi variabel dengan tipe AlertDialog
        AlertDialog alertDialog;
 
        //Deklarasi variabel untuk membangun AlertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
 
        //Membangun dialog
        alertDialogBuilder
        //Menentukan judul dialog
            .setTitle("Peringatan")
 
            //Mengatur agar dialog tidak dapat dibatalkan
            .setCancelable(true)
 
            //Menentukan isi pesan pada dialog
            .setMessage("Anda yakin ingin keluar?")
 
            //Menampilkan tombol Positive
            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
 
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
     
                    //menutup dialog
                    dialog.dismiss();
     
                    //menutup aplikasi;
                    activity.finish();
                }
            })
 
            //Menampilkan tombol Negative
            .setNegativeButton("Tidak", new DialogInterface.OnClickListener(){
 
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
     
                    //membatalkan dialog
                    dialog.cancel();
                }
            })
 
        //Tutup pembangunan dialog
        ;
 
        // Mengeset dialog
        alertDialog = alertDialogBuilder.create();
 
        //Menampilkan dialog
        alertDialog.show();
    }
    
    @Override
	public void onBackPressed() {
		showAlertDialog();
	}
}
