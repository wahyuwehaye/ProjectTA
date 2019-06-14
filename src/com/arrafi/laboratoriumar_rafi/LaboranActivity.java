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

public class LaboranActivity extends Activity implements View.OnClickListener{
	private Button logout;
	private Button btnJadwal,btnPeralatan,btnNilai,btnBooking,btnKerusakan,btnLaporan;
	private SessionManager session;
	private TextView status;
	private JSONArray contacts = null;
	private String nama, id, usern;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_laboran);
		
		session = new SessionManager(getApplicationContext());
//      Toast.makeText(getApplicationContext(),
//              "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG)
//              .show();

      session.checkLogin();

      HashMap<String, String> user = session.getUserDetails();

      nama = user.get(SessionManager.KEY_NAME).toString();
      id = user.get(SessionManager.KEY_IDL);
      usern = user.get(SessionManager.KEY_USER).toString();

      status = (TextView) findViewById(R.id.statusLBR);
      status.setText(Html.fromHtml("Welcome,<b>" + nama + "</b>  "));
      status.setOnClickListener(this);

      logout = (Button) findViewById(R.id.logoutLB);

      logout.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View arg0) {
              // TODO Auto-generated method stub
              confirmLogout();
              
          }
      });
      
      
    //Get ID tombol & Event Tombol
		btnJadwal= (Button) findViewById(R.id.btn_jadwalP);
		btnJadwal.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				 Toast.makeText(getApplicationContext(), "Anda Menekan Tombol Jadwal", Toast.LENGTH_SHORT).show();
				Intent jdJD = new Intent(LaboranActivity.this, LBviewAPPJdwl.class);
				startActivity(jdJD);

		} });
		
		btnPeralatan= (Button) findViewById(R.id.btn_peralatanP);
		btnPeralatan.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				 Toast.makeText(getApplicationContext(), "Anda Menekan Tombol Peralatan", Toast.LENGTH_SHORT).show();
				Intent keAlat = new Intent(LaboranActivity.this, PeralatanLab.class);
				startActivity(keAlat);

		} });

		
		
		btnNilai= (Button) findViewById(R.id.btn_nilaiP);
		btnNilai.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				 Toast.makeText(getApplicationContext(), "Anda Menekan Tombol Nilai", Toast.LENGTH_SHORT).show();
				Intent nilab = new Intent(LaboranActivity.this, PenilaianKegiatanLab.class);
				startActivity(nilab);

		} });
		
		btnBooking= (Button) findViewById(R.id.btn_bookingP);
		btnBooking.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				 Toast.makeText(getApplicationContext(), "Anda Menekan Tombol Booking", Toast.LENGTH_SHORT).show();
				Intent jdP = new Intent(LaboranActivity.this, LBviewAllJdwl.class);
				startActivity(jdP);

		} });
		
		
		
		btnKerusakan= (Button) findViewById(R.id.btn_kerusakanP);
		btnKerusakan.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				 Toast.makeText(getApplicationContext(), "Anda Menekan Tombol Kerusakan", Toast.LENGTH_SHORT).show();
				Intent rusakL = new Intent(LaboranActivity.this, KerusakanPerangkatLab.class);
				startActivity(rusakL);

		} });
		
		btnLaporan= (Button) findViewById(R.id.btn_laporanP);
		btnLaporan.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				 Toast.makeText(getApplicationContext(), "Anda Menekan Tombol Laporan", Toast.LENGTH_SHORT).show();
				Intent laporP = new Intent(LaboranActivity.this, PilihLaporan.class);
				startActivity(laporP);

		} });
	}
	
	private void confirmLogout(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Keluar dari Aplikasi Ini?");
        
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				startActivity(new Intent(LaboranActivity.this, LoginActivity.class));
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
		Intent intentLB = new Intent(this, UpdateProfLab.class);
		intentLB.putExtra(Config.NAMAPEG, nama);
		startActivity(intentLB);
		this.finish();
	}
}
