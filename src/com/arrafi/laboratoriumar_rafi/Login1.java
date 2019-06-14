package com.arrafi.laboratoriumar_rafi;

import java.util.ArrayList;
import java.util.HashMap;
 
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login1 extends Activity {

	Button login;
    Intent a;
    EditText username, password;
    String urlGuru, urlLaboran, success;
    SessionManager session;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login1);
 
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
            	urlGuru = "http://104.152.168.28/~arrafico/arrafi/login/loginguru.php?" + "username="
                        + username.getText().toString() + "&password="
                        + password.getText().toString();
            	
            	urlLaboran = "http://104.152.168.28/~arrafico/arrafi/login/loginpegawai.php?" + "username="
                        + username.getText().toString() + "&password="
                        + password.getText().toString();
 
                if (username.getText().toString().trim().length() > 0
                        && password.getText().toString().trim().length() > 0) 
                {
                    new MasukGuru().execute();
                } 
                else
                {
//                    Toast.makeText(getApplicationContext(), "Username/password belum terisi.!!", Toast.LENGTH_LONG).show();
                	harusDiisi();
                }
            }
        });
    }
 
    public class MasukGuru extends AsyncTask<String, String, String> 
    {
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        ProgressDialog pDialog;
 
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
 
            pDialog = new ProgressDialog(Login1.this);
            pDialog.setMessage("Mohon Tunggu....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            JSONParser jParser = new JSONParser();
 
            JSONObject jsonGuru = jParser.getJSONFromUrl(urlGuru);
 
            try {
            	success = jsonGuru.getString("success");
 
                Log.e("error", "nilai sukses=" + success);
 
                JSONArray hasilGuru = jsonGuru.getJSONArray("login");
 
                if (success.equals("1")) {
 
                    for (int i = 0; i < hasilGuru.length(); i++) {
 
                        JSONObject c = hasilGuru.getJSONObject(i);
 
                        String nama_guru = c.getString("nama_guru").trim();
                        String id_guru = c.getString("id_guru").trim();
                        String username = c.getString("username").trim();
                        session.createLoginSession(nama_guru, id_guru, username);
                        Log.e("ok", " ambil data");
 
                    }
                }else {
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
                a = new Intent(Login1.this, MainActivity.class);
                startActivity(a);
                finish();
            }else if ( success != "1"){
            	new MasukLaboran().execute();
                
            } else {
            	Toast.makeText(getApplicationContext(), "Username/password salah!!", Toast.LENGTH_LONG).show();
            }
 
        }
 
    }
    
    public class MasukLaboran extends AsyncTask<String, String, String> 
    {
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        ProgressDialog pDialog;
 
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
 
            pDialog = new ProgressDialog(Login1.this);
            pDialog.setMessage("Mohon Tunggu....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            JSONParser jParser = new JSONParser();
 
            JSONObject jsonLab = jParser.getJSONFromUrl(urlLaboran);
 
            try {
            	success = jsonLab.getString("success");
 
                Log.e("error", "nilai sukses=" + success);
 
                JSONArray hasilLab = jsonLab.getJSONArray("login");
 
                if (success.equals("1")) {
 
                    for (int i = 0; i < hasilLab.length(); i++) {
 
                        JSONObject c = hasilLab.getJSONObject(i);
 
                        String nama_pegawai = c.getString("nama_pegawai").trim();
                        String id_pegawai = c.getString("id_pegawai").trim();
                        String userLab = c.getString("username").trim();
                        session.createLoginSession(nama_pegawai, id_pegawai, userLab);
                        Log.e("ok", " ambil data");
 
                    }
                }else {
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
                a = new Intent(Login1.this, LaboranActivity.class);
                startActivity(a);
                finish();
            } else {
            	Toast.makeText(getApplicationContext(), "Username/password salah!!", Toast.LENGTH_LONG).show();
            }
 
        }
 
    }
    
    public boolean harusDiisi() {

		username.getText().toString().trim();
		username.setError(null);
		password.getText().toString().trim();
		password.setError(null);

		// length 0 means there is no text
		if (username.length() == 0 ) {
			username.setError(Html
					.fromHtml("<font color='white'>Username tidak boleh kosong</font>"));
			
			return false;
		}else if (password.length() == 0){
			password.setError(Html
					.fromHtml("<font color='white'>Password tidak boleh kosong</font>"));
			return false;
		}

		return true;
	}
    
    private void showAlertDialog(){
        //Deklarasi variabel dengan tipe AlertDialog
        AlertDialog alertDialog;
 
        //Deklarasi variabel untuk membangun AlertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login1.this);
 
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
                    Login1.this.finish();
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
