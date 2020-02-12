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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class tambah_member extends Activity {
	
	private String TAG = tambah_member.class.getSimpleName();
    private ProgressDialog pDialog; 
    private static String url = "http://apilearningpayment.totopeto.com/members";
	
	Button btnsavemember;
	EditText etnamaaddmember, etemailaddmember, ethpaddmember, etpasswordaddtenant ;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tambah_member);
		
		btnsavemember = (Button) findViewById(R.id.btnsavemember);
		etnamaaddmember = (EditText) findViewById(R.id.etnamaaddmember);
		etemailaddmember = (EditText) findViewById(R.id.etemailaddmember);
		ethpaddmember = (EditText) findViewById(R.id.ethpaddmember);
		
		btnsavemember.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AddMember().execute();
				
				
			}
		});
		
		
	}
	
	
	
	private class AddMember extends AsyncTask<Void, Void, Void> {
		
		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(tambah_member.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        
        @Override
        protected Void doInBackground(Void... arg0) {
            String post_params = null;
            JSONObject params = new JSONObject();
 
            try {
            	params.put("name", etnamaaddmember.getText().toString());
            	params.put("email", etemailaddmember.getText().toString());
            	params.put("phone_number", ethpaddmember.getText().toString());
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
            
	            Intent intent = new Intent(tambah_member.this, admin.class);
				startActivity(intent);
            
            
            /**
             * Updating parsed JSON data into ListView
             * */
        }
	}
}
