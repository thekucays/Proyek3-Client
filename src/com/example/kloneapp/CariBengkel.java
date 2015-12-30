package com.example.kloneapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Config;
import utils.RequestHandler;
import android.support.v7.app.ActionBarActivity;
import android.R.integer;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CariBengkel extends ActionBarActivity implements ListView.OnItemClickListener{
	
	private ImageButton imgCari;
	private ListView listBengkel;
	
	private String JSON_STRING;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cari_bengkel);
		
		imgCari = (ImageButton)findViewById(R.id.imgCari);
		listBengkel = (ListView)findViewById(R.id.listBengkel);
		listBengkel.setOnItemClickListener(this);
		
		getJSON(); //showBengkel();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(getApplicationContext(), BengkelDetail.class);
		
		HashMap<String, String> map = (HashMap)parent.getItemAtPosition(position);
		String idBengkel = map.get(Config.tag_id_bengkel).toString();
		
		//Toast.makeText(getApplicationContext(), "id bengkel : "+idBengkel, Toast.LENGTH_SHORT).show();
		intent.putExtra(Config.tag_id_bengkel, idBengkel);
		startActivity(intent);
	}

	private void showBengkel(){
		JSONObject jsonObject = null;
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		Toast.makeText(getApplicationContext(), "JSON String : " + JSON_STRING, Toast.LENGTH_SHORT).show();
		
		try{
			jsonObject = new JSONObject(JSON_STRING);
			JSONArray result = jsonObject.getJSONArray(Config.tag_json_array);
			
			for(int i=0; i<result.length(); i++){
				JSONObject jo = result.getJSONObject(i);
				
				String idBengkel = Integer.toString(jo.getInt(Config.tag_id_bengkel));
				String nama = jo.getString(Config.tag_nama);
				String jenis = jo.getString(Config.tag_jenis);
				
				HashMap<String, String> bengkels = new HashMap<String, String>();
				
				bengkels.put(Config.tag_id_bengkel, idBengkel);
				bengkels.put(Config.tag_nama, nama);
				bengkels.put(Config.tag_jenis, jenis);
				list.add(bengkels);
			}
		}
		catch(JSONException je){
			je.printStackTrace();
		}
		
		ListAdapter listAdapter = new SimpleAdapter(CariBengkel.this, 
				list,  												// resource data 
				R.layout.list_bengkel, 								// xml layout for list
				new String[]{Config.tag_nama, Config.tag_jenis}, 	// from
				new int[]{R.id.nama_bengkel, R.id.jenis});  		// to
		listBengkel.setAdapter(listAdapter);
	}
	
	private void getJSON(){
		class JSONget extends AsyncTask<Void, Void, String>{
			ProgressDialog loading;
			
			@Override
			protected void onPreExecute(){
				super.onPreExecute();
				loading = ProgressDialog.show(CariBengkel.this, "Fetching Data", "Wait...", false,false);
			}
			
			@Override
			protected void onPostExecute(String s){
				super.onPostExecute(s);
				loading.dismiss();
				
				JSON_STRING = s;
				showBengkel();
			}
			
			@Override
			protected String doInBackground(Void... params) {
				RequestHandler rh = new RequestHandler();
				String sa = rh.sendGetRequest(Config.BASE_URL+"bengkel/list_data_ajax/1");
				/*String s = null;
				try {
					s = rh.tesReturn("http://192.168.1.100/Proyek3/bengkel/tes");
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				return sa;
			}
		}
		JSONget gj = new JSONget();
		gj.execute();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cari_bengkel, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
