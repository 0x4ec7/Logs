package edu.bistu.hich.logs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.bistu.hich.adapter.LogAdapter;
import edu.bistu.hich.content.ContactPhotoProvider;
import edu.bistu.hich.db.LogsDAO;
import edu.bistu.hich.entity.Log;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * @ClassName: LogsFragment
 * @Description: view of logs fragment
 * @author 仇之东 hich.cn@gmail.com
 * @date May 17, 2014 5:16:18 PM
 * 
 */
public class LogsFragment extends BaseFragment {
	private ListView logsLv;

	private int lastItem;
	private int count;
	private int curPage = 0;
	private List<HashMap<String, Object>> list;
	private LogAdapter adapter;
	private View view;

	private void initData() {
		list = getData();
		count = list.size();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		curPage = 0;
		initData();
		
		view = inflater.inflate(R.layout.fragment_logs, container, false);
		adapter = new LogAdapter(mContext, list);
		logsLv = (ListView) view.findViewById(R.id.listview_logs);

		logsLv.setAdapter(adapter);
		logsLv.setOnScrollListener(this);
		logsLv.setOnItemClickListener(this);

		return view;
	}

	private List<HashMap<String, Object>> getData() {
		list = new ArrayList<HashMap<String, Object>>();
		LogsDAO dao = new LogsDAO(mContext);
		ArrayList<Log> logs = dao.getLogs(curPage++);

		for (Log log : logs) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("head",
					ContactPhotoProvider.getContactPhoto(mContext,
							log.getNumber()));
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
			LogsDAO dao = new LogsDAO(mContext);
			ArrayList<Log> logs = dao.getLogs(curPage);

			if (logs == null || logs.size() == 0) {
				showShortToast(R.string.no_more_result);
				return;
			} else {
				curPage++;
				for (Log log : logs) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("head",
							ContactPhotoProvider.getContactPhoto(mContext,
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

}
