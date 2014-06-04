package edu.bistu.hich.logs;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.widget.SearchView;

import edu.bistu.hich.util.Utils;

/**
 * @ClassName: BaseActivity
 * @Description: adapter for MainActivity
 * @author 仇之东 hich.cn@gmail.com
 * @date May 17, 2014 5:13:06 PM
 * 
 */
public abstract class BaseActivity extends SherlockFragmentActivity implements
		ActionBar.TabListener, SearchView.OnQueryTextListener,
		SearchView.OnSuggestionListener, OnPageChangeListener,
		OnScrollListener, OnItemClickListener, OnClickListener {
	
	protected Resources res;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		res = this.getResources();
		initData();
		initView();
	}

	protected abstract void initData();

	protected abstract void initView();

	protected void showShortToast(int resId) {
		showToast(resId, Toast.LENGTH_SHORT);
	}

	protected void showShortToast(String msg) {
		showToast(msg, Toast.LENGTH_SHORT);
	}

	protected void showLongToast(int resId) {
		showToast(resId, Toast.LENGTH_LONG);
	}

	protected void showLongToast(String msg) {
		showToast(msg, Toast.LENGTH_LONG);
	}

	private void showToast(int resId, int duration) {
		Toast.makeText(this, resId, duration).show();
	}

	private void showToast(String str, int duration) {
		Toast.makeText(this, str, duration).show();
	}

	protected void logD(String msg) {
		Utils.D(getClassName(), msg);
	}

	protected String getClassName() {
		return this.getClass().getSimpleName();
	}

	protected void openActivity(Class<?> clz) {
		openActivity(clz, null);
	}

	protected void openActivity(Class<?> clz, Bundle bundle) {
		Intent intent = new Intent(this, clz);
		intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	@Override
	public boolean onSuggestionSelect(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSuggestionClick(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
