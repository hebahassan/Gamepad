package com.gamepad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import bluetooth_Connection.Bluetooth_activity;


public class about extends Fragment implements View.OnClickListener {
	//Button butGoHome;

	
    /** Called when the activity is first created. */
    public TextView mTitle;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.about_layout, container, false);
        //Button butGoHome = (Button) view.findViewById(R.id.button1);
        //butGoHome.setOnClickListener(this);
        
  //      mTitle = (TextView)view.findViewById(R.id.mtile);
       Bluetooth_activity.mTitl = mTitle;
        
        
        
        return view;
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		/*switch (v.getId()) {
		case R.id.button1:
			Intent intent1 = new Intent(getActivity(), Gamepad.class);
			startActivity(intent1);
			
			break;

		default:
			break;
		}*/

	}

}

