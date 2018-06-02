package com.gamepad;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import bluetooth_Connection.Bluetooth_activity;

public class Prefs extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	/** Called when the activity is first created. */
	ActionBar actionbar;
	public TextView mTitle;
	Switch SwP;
	SeekBarPreference seek;
	public static int check = 0;
	public static final String KEY_LIST_PREFERENCE = "listPref";
    public static String speed ="def";
	private ListPreference mListPreference;
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
     //   mTitle = (TextView)findViewById(R.id.mtile);
       Bluetooth_activity.mTitl = mTitle;
       getListView().setCacheColorHint(Color.TRANSPARENT);
	   getListView().setBackgroundColor(Color.rgb(0, 0, 0));
	//   super.onBindView(view); ;
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C1C1C")));
		
		actionbar = getActionBar();	 
		actionbar.setIcon(R.drawable.ic_logo);
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		
		
		final PreferenceManager prefranceM = getPreferenceManager();
		SwitchPreference swP = (SwitchPreference) findPreference("Vibrationswitch");
		swP.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if (prefranceM.getSharedPreferences().getBoolean("Vibrationswitch", true))
				{
					Toast.makeText(getApplicationContext(), "abdooooo", Toast.LENGTH_LONG).show();
				       check =0;
				}
				    
				else 
				{
				    	Toast.makeText(getApplicationContext(), "abdo", Toast.LENGTH_LONG).show();
				    	check =1; 
				}
				
				return true;
		
			}
		});
		
        mListPreference = (ListPreference)getPreferenceScreen().findPreference(KEY_LIST_PREFERENCE);

		
		
	/*	SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
		 editor.putInt("time", SeekBarPreference.mCurrentValue );
		 editor.putInt("enble", check);
		 editor.commit();*/
		
		
		
		//SeekBarPreference x = new SeekBarPreference(cond);
		/*seek = (SeekBarPreference) findPreference("Vibration");
		seek.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				// TODO Auto-generated method stub
				return false;
			}
		});*/
		
		//addPreferencesFromResource(R.xml.prefs);
	/*	PreferenceManager preferenceManager = getPreferenceManager();
	    if (preferenceManager.getSharedPreferences().getBoolean("Vibrationswitch", true)){
	       check =1;
	    } else {
	        // Your switch is off
	    }*/
	    
	    	
	    
	   //mStatusText = (TextView) findViewById(R.id.seekBarPrefValue);
	  //  SeekBarPreference x = new SeekBarPreference(getApplicationContext(), attrs);
	   // ProgressBar;
	    //c.getProgress();
	    //String x = mStatusText.getText().toString();
	    //long  c = Long.parseLong(string)
	    
	   // Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
		//v.vibrate(Long.parseLong(mStatusText.getText().toString()));
		
		//seek = (SeekBar) findViewById(R.id.seekbr)
		//SwP = (Switch) findViewById();
		
		// Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
		
	//	v.vibrate();
	}
	
	 @SuppressWarnings("deprecation")
	@Override
	    protected void onResume() {
	        super.onResume();

	        // Setup the initial values
	        mListPreference.setSummary("Current value is " + mListPreference.getEntry().toString());

	        // Set up a listener whenever a key changes            
	        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	    }

	    @SuppressWarnings("deprecation")
		@Override
	    protected void onPause() {
	        super.onPause();

	        // Unregister the listener whenever a key changes            
	        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);    
	    }

	    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	        // Set new summary, when a preference value changes
	        if (key.equals(KEY_LIST_PREFERENCE)) {
	            mListPreference.setSummary("Current value is " + mListPreference.getEntry().toString()); 
				Toast.makeText(getApplicationContext(), mListPreference.getEntry().toString(), Toast.LENGTH_LONG).show();
speed= mListPreference.getValue();
	        }
	    }
	
    public void abdo() {
    	
    }
	
	@Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	  //return super.onOptionsItemSelected(item);
	  switch (item.getItemId()) {
	  case android.R.id.home:
			Intent parentActivityIntent = new Intent(this, MainActivity.class);
          parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(parentActivityIntent);
          overridePendingTransition(R.animator.enterance_shrink_rotate,R.animator.exit_shrink_rotate);
          return true;  
	  }
	  return super.onOptionsItemSelected(item);
	 }
}

