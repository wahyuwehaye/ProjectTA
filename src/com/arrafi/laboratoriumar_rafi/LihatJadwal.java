package com.arrafi.laboratoriumar_rafi;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.widget.ExpandableListView;

public class LihatJadwal extends Activity {
	SparseArray<Group> groups = new SparseArray<Group>();
	String data = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lihat_jadwal);
		
		final GetDataJadwal getdb = new GetDataJadwal();
		new Thread(new Runnable() {
			public void run() {
				data = getdb.getDataFromDB();
				System.out.println(data);
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						ArrayList<DataJadwal> datajadwal = parseJSON(data);
						createData(datajadwal);						
					}
				});
				
			}
		}).start();
		
//		createData();
	    ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
	    MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
	        groups);
	    listView.setAdapter(adapter);
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

	@SuppressWarnings({ "rawtypes" })
	public void createData(ArrayList<DataJadwal> datajadwal) {
		for (Iterator i = datajadwal.iterator(); i.hasNext();) {
//	    for (int j = 0; j < 5; j++) {
			DataJadwal p = (DataJadwal) i.next();
			
	      Group group = new Group(p.getMapel());
//	      for (int i = 0; i < 5; i++) {
	      	String no = Integer.toString(p.getNo());
	        group.children.add(no);
	        group.children.add(p.getTgl());
	        group.children.add(p.getKelas());
	        group.children.add(p.getMapel());
	        group.children.add(p.getJamKe());
	        group.children.add(p.getKet());
//	      }
	      groups.append( 6, group);
	    }
	  }
}
