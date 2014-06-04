package edu.bistu.hich.logs;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import edu.bistu.hich.adapter.LogAdapter;
import edu.bistu.hich.db.LogsDAO;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

/** 
 * @ClassName: MainActivity 
 * @Description: MainActivity
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 17, 2014 5:16:39 PM 
 *  
 */ 
public class MainActivity extends BaseActivity {
	// views
	private ActionBar actionBar;
	private ViewPager viewPager;
	private List<Fragment> fragments;

	@Override
	protected void initData() {
		LogsDAO dao = new LogsDAO(this);
		dao.refreshRecords();
	}
	
	protected void initView() {
		//content view
		setContentView(R.layout.activity_main);
		
		// init fragments
		LogsFragment logs = new LogsFragment();
		ContactsFragment contacts = new ContactsFragment();
		fragments = new ArrayList<Fragment>();
		fragments.add(logs);
		fragments.add(contacts);

		// get views
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments));
		viewPager.setOnPageChangeListener(this);
		viewPager.setOffscreenPageLimit(0);

		// config actionbar
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setLogo(R.drawable.ic_launcher);
		actionBar.setTitle(R.string.actionbar_title);

		// add tabs
		String[] tabNames = res.getStringArray(R.array.tab_names);
		for (int i = 0; i < tabNames.length; i++) {
			Tab tab = actionBar.newTab();
			tab.setText(tabNames[i]);
			tab.setTabListener(this);
			actionBar.addTab(tab);
		}
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onPageSelected(int arg0) {
		actionBar.setSelectedNavigationItem(arg0);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int resId = item.getItemId();
		if (resId == R.id.menu_restore) {
			openActivity(RestoreActivity.class);
		} else {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if (LogAdapter.isPlaying) {
			LogAdapter.player.stop();
			LogAdapter.isPlaying = false;
		}
	}
	
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - exitTime > 2000) {
				showShortToast(R.string.quit_toast);
				exitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
