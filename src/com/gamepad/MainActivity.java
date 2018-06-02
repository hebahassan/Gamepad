package com.gamepad;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	public static ActionBar actionbar;
	public static Activity main_con;

	ViewPager viewpager; 
	FragmentPageAdapter ft;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_main);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C1C1C")));
		viewpager = (ViewPager) findViewById(R.id.pager);
		ft = new FragmentPageAdapter(getSupportFragmentManager());
		actionbar = getActionBar();
		actionbar.setIcon(R.drawable.ic_logo);
		main_con = (Activity)this;

		actionbar.setCustomView(R.layout.custom_title);
		 actionbar.setDisplayShowCustomEnabled(true);
		
	 viewpager.setAdapter(ft); 
		viewpager.setPageTransformer(true, new zoomout_transfer());

		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab tab = actionbar.newTab();
		tab.setIcon(R.drawable.ic_home);
		tab.setText("" + "Home");
		tab.setTabListener(this);
		actionbar.addTab(tab);

		Tab tab2 = actionbar.newTab();
		tab2.setIcon(R.drawable.ic_check);
		tab2.setText("" + "Check");
		tab2.setTabListener(this);
		actionbar.addTab(tab2);

		Tab tab3 = actionbar.newTab();
		tab3.setIcon(R.drawable.ic_about);
		tab3.setText("" + "About");
		tab3.setTabListener(this);
		actionbar.addTab(tab3);
		
		viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				actionbar.setSelectedNavigationItem(arg0);
				// lama at7arak bel touchscreen el actionbar ye7arak el tabs
				// bta3to m3aaya
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}
	

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	  // TODO Auto-generated method stub
	  switch (item.getItemId()) {
	        
	        case android.R.id.home:  

	                Intent parentActivityIntent = new Intent(this, MainActivity.class);
	                parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
	                startActivity(parentActivityIntent);
	                return true;
	                
	  
	        default:
	            return super.onOptionsItemSelected(item);   
	  }
	 }



	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewpager.setCurrentItem(tab.getPosition());
		// lama adoos 3la el tab fe el actionbar yroo7 lelsaf7a bta3to f3lan

	}	

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}


/*	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		if(gamepad_Global.connectionType==gamepad_Global.BLEUTOOTH_CONNECTION){
			setRequestedOrientation(orient);
			Log.e("hena", "d5l hena");
		}
		super.onResume();
	}*/
	
}
