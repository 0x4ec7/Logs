package edu.bistu.hich.logs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;

import edu.bistu.hich.adapter.RestoreLogAdapter;
import edu.bistu.hich.content.ContactPhotoProvider;
import edu.bistu.hich.db.LogsDAO;
import edu.bistu.hich.entity.Log;

/** 
 * @ClassName: RestoreActivity 
 * @Description: restore activity
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 29, 2014 10:42:02 PM 
 *  
 */ 
public class RestoreActivity extends BaseActivity{

	private ActionBar actionBar;
	private ListView logsLv;
	
	private int lastItem;
	private int count;
	private int curPage = 0;
	private List<HashMap<String, Object>> list;
	private RestoreLogAdapter adapter;
	
	@Override
	protected void initData() {
		list = getData();
		count = list.size();
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_restore);
		
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setLogo(R.drawable.img_common_actionbar_left_back_btn);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setTitle(R.string.restore_actionbar_title);
		
		logsLv = (ListView) findViewById(R.id.listview_restore_logs);
		adapter =  new RestoreLogAdapter(this, list);
		logsLv.setAdapter(adapter);
		logsLv.setOnScrollListener(this);
		logsLv.setOnItemClickListener(this);
	}
	
	private List<HashMap<String, Object>> getData() {
		list = new ArrayList<HashMap<String, Object>>();
		LogsDAO dao = new LogsDAO(this);
		ArrayList<Log> logs = dao.getDeletedLogs(curPage++);

		for (Log log : logs) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("head",
					ContactPhotoProvider.getContactPhoto(this, log.getNumber()));
			map.put("log", log);
			list.add(map);
		}
		return list;
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		System.out.println(scrollState);
		if (lastItem == count
				&& scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			LogsDAO dao = new LogsDAO(this);
			ArrayList<Log> logs = dao.getDeletedLogs(curPage);

			if (logs == null || logs.size() == 0) {
				showShortToast(R.string.no_more_result);
				return;
			} else {
				curPage++;
				for (Log log : logs) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("head",
							ContactPhotoProvider.getContactPhoto(this,
									log.getNumber()));
					map.put("log", log);
					list.add(map);
				}
				count = list.size();
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		lastItem = firstVisibleItem + visibleItemCount;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int resId = item.getItemId();
		switch (resId) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
