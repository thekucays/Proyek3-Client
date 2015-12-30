package com.example.kloneapp;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Config;
import utils.RequestHandler;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BengkelDetail extends ActionBarActivity {

	private Button btnRating, btnPeta;
	private EditText txtRating, txtJenis, txtNama;
	private String idBengkel, JSON_STRING;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bengkel_detail);
		
		btnRating = (Button)findViewById(R.id.btnRating);
		btnPeta = (Button)findViewById(R.id.btnPeta);
		txtRating = (EditText)findViewById(R.id.txtRating);
		txtJenis = (EditText)findViewById(R.id.txtJenis);
		txtNama = (EditText)findViewById(R.id.txtNama);
		
		// ambil id yang dilempar dari list
		Intent intent = getIntent();
		idBengkel = intent.getStringExtra(Config.tag_id_bengkel);
		
		getBengkelDetail();
	}

	// button listeners.. defined in xml too
	public void beriRating(View v){
		Toast.makeText(getApplicationContext(), "this feature is in development mode", Toast.LENGTH_SHORT).show();
	}
	public void tampilPeta(View v){
		Toast.makeText(getApplicationContext(), "this feature is in development mode", Toast.LENGTH_SHORT).show();
	}
	
	private void getBengkelDetail(){
		class GetBengkel extends AsyncTask<Void, Void, String>{
			ProgressDialog loading;
			
			@Override
			protected void onPreExecute(){
				super.onPreExecute();
				loading = ProgressDialog.show(BengkelDetail.this, "Fetching", "Fetching Data..", false, false);
			}
			@Override
			protected void onPostExecute(String s){
				super.onPostExecute(s);
				loading.dismiss();
				
				JSON_STRING = s;
				showBengkel(s);
			}
			@Override
			protected String doInBackground(Void... params) {
				RequestHandler rh = new RequestHandler();
				String s = rh.sendGetRequest(Config.BASE_URL + "bengkel/getBengkelDetail/" + idBengkel);
				
				return s;
			}
		}
		
		GetBengkel gb = new GetBengkel();
		gb.execute();
	}
	
	private void showBengkel(String s){
		JSONObject jsonObject = null;
		Toast.makeText(getApplicationContext(), "JSON String : " + s, Toast.LENGTH_SHORT).show();
		
		try{
			jsonObject = new JSONObject(s).getJSONObject(Config.tag_json_array);
			//JSONArray result = jsonObject.getJSONArray(Config.tag_json_array);
			
			//JSONObject jo = jsonObject.getJSONObject(0);
				
			//String idBengkel = Integer.toString(jsonObject.getInt(Config.tag_id_bengkel));
			String nama = jsonObject.getString(Config.tag_nama);
			String jenis = jsonObject.getString(Config.tag_jenis);
			String rating = String.valueOf(jsonObject.getInt(Config.tag_rating));
			
			//Toast.makeText(getApplicationContext(), "data : " + nama + " " + jenis + " " + rating + " ", Toast.LENGTH_SHORT).show();
			txtNama.setText(nama);
			txtJenis.setText(jenis);
			txtRating.setText(rating);
		}
		catch(JSONException je){
			je.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bengkel_detail, menu);
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
