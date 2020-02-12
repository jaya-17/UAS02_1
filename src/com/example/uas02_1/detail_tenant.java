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
import android.widget.TextView;
import android.widget.Toast;

public class detail_tenant extends Activity {
	
	private String TAG = detail_tenant.class.getSimpleName();
	private ProgressDialog pDialog;
	
	private static String url = "http://apilearningpayment.totopeto.com/tenants/";
	ArrayList<HashMap<String, String>> tenanttList;
	

	private TextView tvname, tvemail2, tvphone2;
	String name,email,number;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_tenant);
		
		tvname = (TextView) findViewById(R.id.tvname);
		tvemail2 = (TextView) findViewById(R.id.tvemail2);
		tvphone2 = (TextView) findViewById(R.id.tvphone2);

		new GetTenant().execute();
	}
	
	private class GetTenant extends AsyncTask<Void,Void,Void>
	{
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(detail_tenant.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            Intent intent = getIntent();
            String id = intent.getStringExtra("id");
        	
        	HttpHandler sh = new HttpHandler();
 
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url + id);
 
            Log.e(TAG, "Response from url: " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject c = jsonObj.getJSONObject("tenant");
 
                     
                        name = c.getString("name");
                        email = c.getString("email");
                        number = c.getString("number");   
                        //String created_at = c.getString("created_at");
                        //String update_at = c.getString("updated_at");
                    }
                 catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
 
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
 
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
           
            tvname.setText(name);
            tvemail2.setText(email);
            tvphone2.setText(number);
          
        }
	}

}
