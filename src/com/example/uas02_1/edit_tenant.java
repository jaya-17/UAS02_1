package com.example.uas02_1;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class edit_tenant extends Activity {
	
	private String TAG = edit_tenant.class.getSimpleName();
	private ProgressDialog pDialog;
	private static String url = "http://apilearningpayment.totopeto.com/tenants/";
	ArrayList<HashMap<String, String>> tenanttList;
	Button btnsave,btndelete;
	EditText etname, etemail, etnumber;
	String name,email,number,id;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_tenant);
		
		btnsave = (Button) findViewById(R.id.btnsave);
		btndelete = (Button) findViewById(R.id.btndelete);
		etname = (EditText) findViewById(R.id.etname);
		etemail = (EditText) findViewById(R.id.etemail);
		etnumber = (EditText) findViewById(R.id.etnumber);
		
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		

		btnsave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new PutTenant().execute();	
			}
		});	
		
		btndelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//new DeleteTenant().execute();	
			}
		});	
	}

	private class PutTenant extends AsyncTask<Void, Void, Void> {
		
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(edit_tenant.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        
        @Override
        protected Void doInBackground(Void... arg0) {
            String put_params = null;
            JSONObject params = new JSONObject();
 
            try {
            	params.put("name", etname.getText().toString());
            	params.put("email", etemail.getText().toString());
            	params.put("number", etnumber.getText().toString());
            	//params.post("password", etpasswordaddtenant.getText().toString());
            	put_params = params.toString();
            	
            } catch (JSONException e) {
            	e.printStackTrace();
            }
            
            HttpHandler data = new HttpHandler();
            String jsonStr = data.makePutRequest(url + id, put_params);
            Log.e(TAG, "Response from url: " + jsonStr);
            
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            
            
            
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            
	            Intent intent = new Intent(edit_tenant.this, admin.class);
				startActivity(intent);
            
            
            /**
             * Updating parsed JSON data into ListView
             * */
        }
	}
	
	private class DeleteTenant extends AsyncTask<Void, Void, Void> {
		
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(edit_tenant.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            //String jsonStr = sh.makeDeleteRequest(url + "/" + id);
            //Log.e(TAG, "Response from url: " + jsonStr);
            
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
    
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            
	            Intent intent = new Intent(edit_tenant.this, admin.class);
				startActivity(intent);
              
            /**
             * Updating parsed JSON data into ListView
             * */
        }
	}
	
	
}
