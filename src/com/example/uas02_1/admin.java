package com.example.uas02_1;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

public class admin extends Activity {
	
	ActionBar.Tab TabTenant, TabMember;
	Fragment tenantdiadmin = new tenantdiadmin();
	Fragment memberdiadmin = new memberdiadmin();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
       
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        TabTenant = actionBar.newTab().setText("Tenant");
        TabMember = actionBar.newTab().setText("Member");
        
        TabTenant.setTabListener(new TabListener(tenantdiadmin));
        TabMember.setTabListener(new TabListener(memberdiadmin));
        
		actionBar.addTab(TabTenant);
		actionBar.addTab(TabMember);
    }
	

}
