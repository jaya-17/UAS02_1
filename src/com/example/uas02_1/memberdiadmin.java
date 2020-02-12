package com.example.uas02_1;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Fragment;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class memberdiadmin extends Fragment {
	
	private String TAG = memberdiadmin.class.getSimpleName();
    private ProgressDialog pDialog;
	private Button btnaddmember;
	private ListView lvmemberinadmin;
	private static String url = "http://apilearningpayment.totopeto.com/members";
	private ArrayList<HashMap<String, String>> memberList;
	private int memberLength;
	
	
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.memberdiadmin, container, false);
        
        memberList = new ArrayList<HashMap<String, String>>();
        
        lvmemberinadmin = (ListView) rootView.findViewById(R.id.lvmemberinadmin);
        btnaddmember = (Button) rootView.findViewById(R.id.btnaddmember);
        
        new GetMember().execute();
        
        lvmemberinadmin.setOnItemClickListener(new OnItemClickListener() {
			
			
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				HashMap<String, String> hm = memberList.get(position);
				Intent intent = new Intent(getActivity(), detail_member.class);
				intent.putExtra("id", hm.get("id"));
				startActivity(intent);
				
			}
			});
        
        btnaddmember.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(getActivity(), tambah_member.class);
				startActivity(intent);
				
			}
		});
            
        return rootView;
	}
	
	
	private class GetMember extends AsyncTask<Void, Void, Void> {
	   	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
    		//String linkId = getArguments().getString("id");
        	
            HttpHandler sh = new HttpHandler();
 
            // String jsonStr = sh.makeServiceCall(url + "1");
            String jsonStr = sh.makeServiceCall(url);
 
            Log.e(TAG, "Response from url: " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray data = jsonObj.getJSONArray("members");
                    memberLength = data.length();
                    
                    for (int i = 0; i < memberLength; i++) {
                        JSONObject c = data.getJSONObject(i);
                        
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String phone_number = c.getString("phone_number");
                        
                        HashMap<String, String> message = new HashMap<String, String>();
 
                        message.put("id", id);
                        message.put("name", name);
                        message.put("email", email);
                        message.put("phone_number", phone_number);
                        
                        memberList.add(message);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } 
            else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() 
                {
                   
                    public void run() 
                    {
                        Toast.makeText(getActivity(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            
            }
 
            return null;
        }
       
            
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                
                if (pDialog.isShowing())
                    pDialog.dismiss();
                
                //tinbox.setText(String.valueOf(tenantLength));
                
                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), memberList,
                        R.layout.list_member, new String[]{"name", "email", "phone_number" 
                        }, new int[]{R.id.name, R.id.email , R.id.phone_number });
     
                lvmemberinadmin.setAdapter(adapter);
            }
     
        }
	
	
	
	
	
}
