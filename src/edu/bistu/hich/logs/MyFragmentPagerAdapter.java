package edu.bistu.hich.logs;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/** 
 * @ClassName: MyFragmentPagerAdapter 
 * @Description: adapter for fragments 
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 17, 2014 5:17:19 PM 
 *  
 */ 
public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
	private List<Fragment> fragments;

	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
