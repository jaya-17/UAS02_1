package com.example.uas02_1;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.util.ArrayList;
import java.util.HashMap;


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
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class tenantdiadmin extends Fragment {
	
	private String id;
	private String TAG = tenantdiadmin.class.getSimpleName();
    private ProgressDialog pDialog;
	private Button btnaddtenant;
	private ListView lvtenantinadmin;
	private static String url = "http://apilearningpayment.totopeto.com/tenants";
	private ArrayList<HashMap<String, String>> tenantList;
	private int tenantLength;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tenantdiadmin, container, false);
              
        tenantList = new ArrayList<HashMap<String, String>>();
        lvtenantinadmin = (ListView) rootView.findViewById(R.id.lvtenantinadmin);
        btnaddtenant = (Button) rootView.findViewById(R.id.btnaddtenant);
        
        new GetTenant().execute();
        
        lvtenantinadmin.setOnItemClickListener(new OnItemClickListener() {
			
			
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				HashMap<String, String> hm = tenantList.get(position);
				Intent intent = new Intent(getActivity(), detail_tenant.class);
				intent.putExtra("id", hm.get("id"));
				startActivity(intent);
				
			}
			});
        btnaddtenant.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(getActivity(), tambah_tenant.class);
				startActivity(intent);
				
			}
		});
        

        return rootView;
	}
	
	private class GetTenant extends AsyncTask<Void, Void, Void> {
	   	 
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
                    JSONArray data = jsonObj.getJSONArray("tenants");
                    tenantLength = data.length();
                    
                    for (int i = 0; i < tenantLength; i++) {
                        JSONObject c = data.getJSONObject(i);
                        
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String number = c.getString("number");
                        
                        HashMap<String, String> message = new HashMap<String, String>();
 
                        message.put("id", id);
                        message.put("name", name);
                        message.put("email", email);
                        message.put("number", number);
                        
                        tenantList.add(message);
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
                        getActivity(), tenantList,
                        R.layout.list_tenant, new String[]{"name", "email" , "number"}, 
                        new int[]{R.id.name, R.id.email , R.id.number})
                {
                	@Override
                	public View getView (int position, View convertView, ViewGroup parent) {
                		View v = super.getView(position, convertView, parent);
                		
                		final int pos = position;
                		
                		Button btnedittenant = (Button) v.findViewById(R.id.btnedittenant);
                		//Button bdelete = (Button) v.findViewById(R.id.delete);
                		
                		btnedittenant.setOnClickListener(new View.OnClickListener() {
    						
    						@Override
    						public void onClick(View v) {
    							HashMap<String, String> hm = tenantList.get(pos);
                				id = hm.get("id");
                				//Toast.makeText(MainActivity.this,"pressed share id " + id,Toast.LENGTH_SHORT).show();
                				
                				Intent intent = new Intent(getActivity(), edit_tenant.class);
                				intent.putExtra("id", id);
                				startActivity(intent);
    						}
    					});
                		
                		/*bdelete.setOnClickListener(new View.OnClickListener() {
                			
                			@Override
                			public void onClick(View arg0) {
                				HashMap<String, String> hm = noteList.get(pos);
                				id = hm.get("id");
                				Toast.makeText(MainActivity.this,"pressed note id " + id, Toast.LENGTH_SHORT).show();
                				new DeleteNote().execute();
                				}
                			});*/
          		
                		return v;
                     }
       	
                	
                };
               
                lvtenantinadmin.setAdapter(adapter);
            }
     
        }    
}
