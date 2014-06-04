package edu.bistu.hich.logs;

import edu.bistu.hich.util.Utils;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

/** 
 * @ClassName: BaseFragment 
 * @Description: adapter for fragment 
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 19, 2014 10:02:02 PM 
 *  
 */ 
public abstract class BaseFragment extends Fragment implements OnScrollListener, OnItemClickListener, OnItemLongClickListener{
	
	protected Resources res;
	protected Context mContext;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.mContext = this.getActivity();
		res = mContext.getResources();
	}

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
		Toast.makeText(mContext, resId, duration).show();
	}

	private void showToast(String str, int duration) {
		Toast.makeText(mContext, str, duration).show();
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
		Intent intent = new Intent(mContext, clz);
		intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
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
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
