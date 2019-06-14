package com.arrafi.laboratoriumar_rafi;

import java.util.HashMap;

import org.json.JSONArray;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;
//import android.widget.Toast;



public class MainActivity extends Activity implements View.OnClickListener{

	private Button logout;
	private Button btnJadwal,btnPeralatan,btnNilai,btnPengembalian,btnInput,btnLaporan;
	private SessionManager session;
	private TextView status;
	private JSONArray contacts = null;
	private String nama, id;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
 
        session = new SessionManager(getApplicationContext());
//        Toast.makeText(getApplicationContext(),
//                "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG)
//                .show();
 
        session.checkLogin();
 
        HashMap<String, String> user = session.getUserDetails();
 
        nama = user.get(SessionManager.KEY_NAME);
        id = user.get(SessionManager.KEY_IDL);
 
        status = (TextView) findViewById(R.id.statusGR);
        status.setText(Html.fromHtml("Welcome,<b>" + nama + "</b>  "));
        status.setOnClickListener(this);
 
        logout = (Button) findViewById(R.id.logout);
 
        logout.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
            	confirmLogout();
            }
        });
        
        
      //Get ID tombol & Event Tombol
		btnJadwal= (Button) findViewById(R.id.btn_jadwalG);
		btnJadwal.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				 //Toast.makeText(getApplicationContext(), "Anda Menekan Tombol Jadwal", Toast.LENGTH_SHORT).show();
				Intent jadwal = new Intent(MainActivity.this, ViewAllJadwal.class);
                startActivity(jadwal);

		} });
		
		btnPeralatan= (Button) findViewById(R.id.btn_peralatanG);
		btnPeralatan.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				 Toast.makeText(getApplicationContext(), "Anda Menekan Tombol Peralatan", Toast.LENGTH_SHORT).show();
				Intent alatGuru = new Intent(MainActivity.this, PeralatanDiGuru.class);
                startActivity(alatGuru);

		} });

		
		
		btnNilai= (Button) findViewById(R.id.btn_nilaiG);
		btnNilai.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				 Toast.makeText(getApplicationContext(), "Anda Menekan Tombol Nilai", Toast.LENGTH_SHORT).show();
				Intent lihNil = new Intent(MainActivity.this, PenilaianKegiatan.class);
				startActivity(lihNil);

		} });
		
		btnPengembalian= (Button) findViewById(R.id.btn_pengembalianG);
		btnPengembalian.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				 Toast.makeText(getApplicationContext(), "Anda Menekan Tombol Pengambilan", Toast.LENGTH_SHORT).show();
				Intent kembaliAlat = new Intent(MainActivity.this, PengembalianAlat.class);
                startActivity(kembaliAlat);

		} });
		
		
		
		btnInput= (Button) findViewById(R.id.btn_inputG);
		btnInput.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				 Toast.makeText(getApplicationContext(), "Anda Menekan Tombol Input", Toast.LENGTH_SHORT).show();
				Intent inKer = new Intent(MainActivity.this, InputKerusakanAlat.class);
                startActivity(inKer);

		} });
		
		btnLaporan= (Button) findViewById(R.id.btn_laporanG);
		btnLaporan.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				 Toast.makeText(getApplicationContext(), "Anda Menekan Tombol Laporan", Toast.LENGTH_SHORT).show();
				Intent pilLap = new Intent(MainActivity.this, PilihLaporan.class);
                startActivity(pilLap);

		} });
		
		
    }
    
    private void confirmLogout(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Keluar dari Aplikasi Ini?");
        
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
                session.logoutUser();
                finish();
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		confirmLogout();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intentGR = new Intent(this, UpdateProfil.class);
		intentGR.putExtra(Config.ID_PROFILGR, id);
		startActivity(intentGR);
	}
}
