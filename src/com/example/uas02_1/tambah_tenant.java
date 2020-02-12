package com.example.uas02_1;

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

public class tambah_tenant extends Activity {
	
	private String TAG = tambah_tenant.class.getSimpleName();
    private ProgressDialog pDialog; 
    private static String url = "http://apilearningpayment.totopeto.com/tenants";
	
    private Button btnsavetenant;
    private EditText etnamaaddtenant, etemailaddtenant, etnumberaddtenant, etpasswordaddtenant ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tambah_tenant);
		
		btnsavetenant = (Button) findViewById(R.id.btnsavetenant);
		etnamaaddtenant = (EditText) findViewById(R.id.etnamaaddtenant);
		etemailaddtenant = (EditText) findViewById(R.id.etemailaddtenant);
		etnumberaddtenant = (EditText) findViewById(R.id.etnumberaddtenant);
		etpasswordaddtenant = (EditText) findViewById(R.id.etpasswordaddtenant);
		
		btnsavetenant.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AddTenant().execute();
				
				
			}
		});
	}
	
	
	private class AddTenant extends AsyncTask<Void, Void, Void> {
		
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(tambah_tenant.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        
        @Override
        protected Void doInBackground(Void... arg0) {
            String post_params = null;
            JSONObject params = new JSONObject();
 
            try {
            	params.put("name", etnamaaddtenant.getText().toString());
            	params.put("email", etemailaddtenant.getText().toString());
            	params.put("number", etnumberaddtenant.getText().toString());
            	//params.post("password", etpasswordaddtenant.getText().toString());
            	post_params = params.toString();
            	
            } catch (JSONException e) {
            	e.printStackTrace();
            }
            
            HttpHandler data = new HttpHandler();
            String jsonStr = data.makePostRequest(url, post_params);
            Log.e(TAG, "Response from url: " + jsonStr);
            
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            
            
            
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            
	            Intent intent = new Intent(tambah_tenant.this, admin.class);
				startActivity(intent);
            
            
            /**
             * Updating parsed JSON data into ListView
             * */
        }
	}
	

}
