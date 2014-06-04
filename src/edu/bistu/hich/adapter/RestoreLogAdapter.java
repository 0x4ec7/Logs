package edu.bistu.hich.adapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import edu.bistu.hich.db.LogsDAO;
import edu.bistu.hich.entity.Enum.RecordState;
import edu.bistu.hich.entity.Log;
import edu.bistu.hich.logs.R;
import edu.bistu.hich.util.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/** 
 * @ClassName: RestoreLogAdapter 
 * @Description: adater for restore listview
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 29, 2014 3:41:08 PM 
 *  
 */ 
public class RestoreLogAdapter extends BaseAdapter implements OnClickListener, OnLongClickListener {
	private Context mContext;
	private List<HashMap<String, Object>> mData;
	private LayoutInflater mInflater;

	private Resources res;
	
	public RestoreLogAdapter(Context context, List<HashMap<String, Object>> data) {
		mContext = context;
		mData = data;
		mInflater = LayoutInflater.from(context);

		res = context.getResources();
	}

	@Override
	public int getCount() {
		return mData != null ? mData.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HashMap<String, Object> map = mData.get(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_restore_log_content, null);
		}

		LinearLayout contentLl = (LinearLayout) convertView
				.findViewById(R.id.restore_log_content);
		TextView contactTv = (TextView) convertView.findViewById(R.id.contact);
		TextView contactDateTv = (TextView) convertView
				.findViewById(R.id.contact_date);
		TextView contactDurationTv = (TextView) convertView
				.findViewById(R.id.contact_duration);

		Log log = (Log) map.get("log");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (log.getName() == null) {
			log.setName("");
		}

		contentLl.setOnClickListener(this);
		contentLl.setOnLongClickListener(this);
		contentLl.setTag(map);

		contactTv.setText(log.getName() + "(" + log.getNumber() + ")");
		contactDateTv.setText(format.format(new Date(log.getDate())));
		contactDurationTv.setText(res.getString(R.string.contact_duration)
				+ log.getDuration() + "s");

		return convertView;
	}

	@Override
	public void onClick(View v) {
		int resId = v.getId();
		@SuppressWarnings("unchecked")
		final HashMap<String, Object> map = (HashMap<String, Object>) v.getTag();
		final Log log = (Log) map.get("log");
		switch (resId) {
		case R.id.restore_log_content:
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage(R.string.confirm_restore);
			builder.setPositiveButton(R.string.confirm, new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					LogsDAO logsDAO = new LogsDAO(mContext);
					logsDAO.updateRecordState(log.getUid(), RecordState.LOCAL);
					mData.remove(mData.indexOf(map));
					RestoreLogAdapter.this.notifyDataSetChanged();
				}
			});
			builder.setNegativeButton(R.string.cancel, new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onLongClick(View v) {
		int resId = v.getId();
		@SuppressWarnings("unchecked")
		final HashMap<String, Object> map = (HashMap<String, Object>) v.getTag();
		final Log log = (Log) map.get("log");
		switch (resId) {
		case R.id.restore_log_content:
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage(R.string.confirm_delete_thoroughly);
			builder.setPositiveButton(R.string.confirm, new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					LogsDAO logsDAO = new LogsDAO(mContext);
					logsDAO.deleteLogByUid(log.getUid());
					File file = new File(Utils.generateAbsoluteFilePath(log.getUid(), log.getDate()));
					if(file.exists()){
						file.delete();
						Utils.showShortToast(mContext, R.string.delete_success);
					}
					mData.remove(mData.indexOf(map));
					RestoreLogAdapter.this.notifyDataSetChanged();
				}
			});
			builder.setNegativeButton(R.string.cancel, new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
			
			break;
		default:
			break;
		}
		return false;
	}
	
}
