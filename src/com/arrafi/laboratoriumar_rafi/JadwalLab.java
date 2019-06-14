package com.arrafi.laboratoriumar_rafi;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;


public class JadwalLab extends Activity {
	String data = "";
	TableLayout tl;
	TableRow tr;
	TextView label;
	Button tambah;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_jadwal_lab);
		
		tambah = (Button)findViewById(R.id.tambah);
		tambah.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent tmb = new Intent(JadwalLab.this, TambahJadwal.class);
				startActivity(tmb);
			}
		});
		
		tl = (TableLayout) findViewById(R.id.maintable);

		final GetDataJadwal getdb = new GetDataJadwal();
		new Thread(new Runnable() {
			public void run() {
				data = getdb.getDataFromDB();
				System.out.println(data);
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						ArrayList<DataJadwal> datajadwal = parseJSON(data);
						addData(datajadwal);						
					}
				});
				
			}
		}).start();
	}

	public ArrayList<DataJadwal> parseJSON(String result) {
		ArrayList<DataJadwal> datajadwal = new ArrayList<DataJadwal>();
		try {
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				DataJadwal jadwal = new DataJadwal();
				jadwal.setNo(json_data.getInt("no"));
				jadwal.setTgl(json_data.getString("tgl"));
				jadwal.setKelas(json_data.getString("kelas"));
				jadwal.setMapel(json_data.getString("mapel"));
				jadwal.setJamKe(json_data.getString("jamKe"));
				jadwal.setKet(json_data.getString("ket"));
				datajadwal.add(jadwal);
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());  
		}
		return datajadwal;
	}

	void addHeader(){
		/** Create a TableRow dynamically **/
		tr = new TableRow(this);

		/** Creating a TextView to add to the row **/
		label = new TextView(this);
		label.setText("No");
		label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		label.setPadding(5, 5, 5, 5);
		label.setBackgroundColor(Color.GREEN);
		LinearLayout Ll = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(label,params);
		tr.addView((View)Ll); // Adding textView to tablerow.

		/** Creating Qty Button **/
		TextView place = new TextView(this);
		place.setText("Tanggal");
		place.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		place.setPadding(5, 5, 5, 5);
		place.setBackgroundColor(Color.GREEN);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(place,params);
		tr.addView((View)Ll); // Adding textview to tablerow.
		
		/** Creating Qty Button **/
		TextView place1 = new TextView(this);
		place1.setText("Kelas");
		place1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		place1.setPadding(5, 5, 5, 5);
		place1.setBackgroundColor(Color.GREEN);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(place1,params);
		tr.addView((View)Ll); // Adding textview to tablerow.
		
		/** Creating Qty Button **/
		TextView place2 = new TextView(this);
		place2.setText("Mata Pelajaran");
		place2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		place2.setPadding(5, 5, 5, 5);
		place2.setBackgroundColor(Color.GREEN);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(place2,params);
		tr.addView((View)Ll); // Adding textview to tablerow.
		
		/** Creating Qty Button **/
		TextView place3 = new TextView(this);
		place3.setText("Jam Ke");
		place3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		place3.setPadding(5, 5, 5, 5);
		place3.setBackgroundColor(Color.GREEN);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(place3,params);
		tr.addView((View)Ll); // Adding textview to tablerow.
		
		/** Creating Qty Button **/
		TextView place4 = new TextView(this);
		place4.setText("Keterangan");
		place4.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		place4.setPadding(5, 5, 5, 5);
		place4.setBackgroundColor(Color.GREEN);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(place4,params);
		tr.addView((View)Ll); // Adding textview to tablerow.
		
		/** Creating Qty Button **/
		TextView place5 = new TextView(this);
		place5.setText("Opsi 1");
		place5.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		place5.setPadding(5, 5, 5, 5);
		place5.setBackgroundColor(Color.GREEN);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(place5,params);
		tr.addView((View)Ll); // Adding textview to tablerow.
		
		/** Creating Qty Button **/
		TextView place6 = new TextView(this);
		place6.setText("Opsi 2");
		place6.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		place6.setPadding(5, 5, 5, 5);
		place6.setBackgroundColor(Color.GREEN);
		Ll = new LinearLayout(this);
		params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(place6,params);
		tr.addView((View)Ll); // Adding textview to tablerow.
		
		

		 // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void addData(ArrayList<DataJadwal> datajadwal) {

		addHeader();
		
		for (Iterator i = datajadwal.iterator(); i.hasNext();) {

			DataJadwal p = (DataJadwal) i.next();

			/** Create a TableRow dynamically **/
			tr = new TableRow(this);

			/** Creating a TextView to add to the row **/
			label = new TextView(this);
			String no = Integer.toString(p.getNo());
			label.setId(p.getNo());
			label.setText(no);
			label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			label.setPadding(5, 5, 5, 5);
			label.setBackgroundColor(Color.GRAY);
			LinearLayout Ll = new LinearLayout(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(label,params);
			tr.addView((View)Ll); // Adding textView to tablerow.

			/** Creating Qty Button **/
			TextView place = new TextView(this);
			place.setText(p.getTgl());
			place.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			place.setPadding(5, 5, 5, 5);
			place.setBackgroundColor(Color.GRAY);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(place,params);
			tr.addView((View)Ll); // Adding textview to tablerow.
			
			/** Creating Qty Button **/
			TextView place1 = new TextView(this);
			place1.setText(p.getKelas());
			place1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			place1.setPadding(5, 5, 5, 5);
			place1.setBackgroundColor(Color.GRAY);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(place1,params);
			tr.addView((View)Ll); // Adding textview to tablerow.
			
			/** Creating Qty Button **/
			TextView place2 = new TextView(this);
			place2.setText(p.getMapel());
			place2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			place2.setPadding(5, 5, 5, 5);
			place2.setBackgroundColor(Color.GRAY);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(place2,params);
			tr.addView((View)Ll); // Adding textview to tablerow.
			
			/** Creating Qty Button **/
			TextView place3 = new TextView(this);
			place3.setText(p.getJamKe());
			place3.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			place3.setPadding(5, 5, 5, 5);
			place3.setBackgroundColor(Color.GRAY);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(place3,params);
			tr.addView((View)Ll); // Adding textview to tablerow.
			
			/** Creating Qty Button **/
			TextView place4 = new TextView(this);
			place4.setText(p.getKet());
			place4.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			place4.setPadding(5, 5, 5, 5);
			place4.setBackgroundColor(Color.GRAY);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(place4,params);
			tr.addView((View)Ll); // Adding textview to tablerow.
			
			/** Creating Qty Button **/
			TextView place5 = new TextView(this);
			place5.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent klik = new Intent(JadwalLab.this, LihatJadwal.class);
					startActivity(klik);
				}
			});
			place5.setText("Edit");
			place5.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			place5.setPadding(5, 5, 5, 5);
			place5.setBackgroundColor(Color.RED);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(place5,params);
			tr.addView((View)Ll); // Adding textview to tablerow.
			
			/** Creating Qty Button **/
			TextView place6 = new TextView(this);
			place6.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent klik = new Intent(JadwalLab.this, TambahJadwal.class);
					startActivity(klik);
				}
			});
			place6.setText("Delete");
			place6.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			place6.setPadding(5, 5, 5, 5);
			place6.setBackgroundColor(Color.RED);
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 2, 2, 2);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(place6,params);
			tr.addView((View)Ll); // Adding textview to tablerow.

			 // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
		}
	}
}
