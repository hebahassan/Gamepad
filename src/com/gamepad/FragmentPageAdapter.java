package com.gamepad;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentPageAdapter extends FragmentPagerAdapter {
	// el class dh hwa elly haye2dar net7akem mn 5lalo anhy page elly hatet3ered
	// Implementation of PagerAdapter that represents each page as a Fragment
	// that is persistently kept in the fragment manager as long as the user can
	// return to the page.
	public FragmentPageAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		// method to supply instances of ScreenSlidePageFragment as new pages
		switch (arg0) {
		case 0:
			return new Home();
		case 1:
			return new Check();
		case 2:
			return new about();

		default:
			break;
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		// returns the amount of pages the adapter will create
		return 3;
	}

}
