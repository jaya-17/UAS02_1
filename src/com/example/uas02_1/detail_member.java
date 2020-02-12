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
import android.widget.TextView;
import android.widget.Toast;

public class detail_member extends Activity {
	
	private String TAG = detail_member.class.getSimpleName();
	private ProgressDialog pDialog;
	
	private static String url = "http://apilearningpayment.totopeto.com/members/";
	private static String urltopup = "http://apilearningpayment.totopeto.com/transactions/top_up";
	ArrayList<HashMap<String, String>> membertList;
	
	Button btntopup;
	EditText etamount;
	TextView tvname, tvemail2, tvphone2, tvbalance2;
	String name,email,phone_number,current_balance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_member);
		
		etamount = (EditText) findViewById(R.id.etamount);
		btntopup = (Button) findViewById(R.id.btntopup);
		tvname = (TextView) findViewById(R.id.tvname);
		tvemail2 = (TextView) findViewById(R.id.tvemail2);
		tvphone2 = (TextView) findViewById(R.id.tvphone2);
		tvbalance2 = (TextView) findViewById(R.id.tvbalance2);
		
		btntopup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				new Amount() .execute();
				
				
				
			}
		});
		
		
		
		
		new GetMember().execute();
	}
	
	private class GetMember extends AsyncTask<Void,Void,Void>
	{
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(detail_member.this);
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
                    JSONObject c = jsonObj.getJSONObject("member");
 
                     
                        name = c.getString("name");
                        email = c.getString("email");
                        phone_number = c.getString("phone_number"); 
                        current_balance = c.getString("current_balance");
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
            tvphone2.setText(phone_number);
            tvbalance2.setText(current_balance);
          
        }
	}
	
	
	
	
	private class Amount extends AsyncTask<Void,Void,Void>
	{
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(detail_member.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            Intent intent = getIntent();
            String id = intent.getStringExtra("id");
        	
            String post_params = null;
            JSONObject params = new JSONObject();
 
            try {
            	params.put("member_id", id);
            	params.put("amount", etamount.getText().toString());
            	
            	post_params = params.toString();
            	
            } catch (JSONException e) {
            	e.printStackTrace();
            }
            
            HttpHandler data = new HttpHandler();
            String jsonStr = data.makePostRequest(urltopup, post_params);
            Log.e(TAG, "Response from url: " + jsonStr);
            
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            
            Intent intent = new Intent(detail_member.this, admin.class);
			startActivity(intent);
          
        }
	}

}
