package com.example.uas02_1;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	
	// URL to get contacts JSON
    private static String url = "http://apilearningpayment.totopeto.com/administrators/";
    private static String url2 = "http://apilearningpayment.totopeto.com/members/";
    private static String url3 = "http://apilearningpayment.totopeto.com/tenants";
	

    private Button btnadmin;
    private Button btnmember;
    private Button btntenant;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnadmin = (Button) findViewById(R.id.btnadmin);
        btnmember = (Button) findViewById(R.id.btnmember);
        btntenant = (Button) findViewById(R.id.btntenant);
        
        btnadmin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, admin.class);
				startActivity(intent);
			}
		});
     
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
  
    }
    
}
