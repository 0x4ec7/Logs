package edu.bistu.hich.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import edu.bistu.hich.content.RecordPlayer;
import edu.bistu.hich.db.LogsDAO;
import edu.bistu.hich.entity.Enum.RecordState;
import edu.bistu.hich.entity.Log;
import edu.bistu.hich.entity.Enum.CallType;
import edu.bistu.hich.logs.IndividualActivity;
import edu.bistu.hich.logs.R;
import edu.bistu.hich.util.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @ClassName: MyAdapter
 * @Description: adapter for logs listview
 * @author 仇之东 hich.cn@gmail.com
 * @date May 18, 2014 11:12:04 PM
 * 
 */
public class LogAdapter extends BaseAdapter implements OnClickListener, OnLongClickListener {
	private Context mContext;
	private List<HashMap<String, Object>> mData;
	private LayoutInflater mInflater;

	private Resources res;
	private static String currentAudioPath = "";
	
	public static boolean isPlaying = false;
	public static RecordPlayer player = RecordPlayer.getInstance();

	public LogAdapter(Context context, List<HashMap<String, Object>> data) {
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
			convertView = mInflater.inflate(R.layout.list_log_content, null);
		}

		ImageView leftHeadIv = (ImageView) convertView
				.findViewById(R.id.head_left);
		ImageView rightHeadIv = (ImageView) convertView
				.findViewById(R.id.head_right);
		LinearLayout contentLl = (LinearLayout) convertView
				.findViewById(R.id.record_content);
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

		if (CallType.INCOMING_CALL != log.getCallType()) {
			leftHeadIv.setVisibility(View.VISIBLE);
			leftHeadIv.setOnClickListener(this);
			leftHeadIv.setImageBitmap((Bitmap) map.get("head"));
			leftHeadIv.setTag(map);

			rightHeadIv.setVisibility(View.INVISIBLE);

			contentLl.setBackgroundResource(R.drawable.list_content_bg_left);
			contentLl.setPadding(15, 5, 5, 5);
			contentLl.setGravity(Gravity.LEFT);
		} else {
			rightHeadIv.setVisibility(View.VISIBLE);
			rightHeadIv.setOnClickListener(this);
			rightHeadIv.setImageBitmap((Bitmap) map.get("head"));
			rightHeadIv.setTag(map);

			leftHeadIv.setVisibility(View.INVISIBLE);

			contentLl.setBackgroundResource(R.drawable.list_content_bg_right);
			contentLl.setPadding(5, 5, 15, 5);
			contentLl.setGravity(Gravity.RIGHT);
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
		HashMap<String, Object> map = (HashMap<String, Object>) v.getTag();
		Log log = (Log) map.get("log");
		switch (resId) {
		case R.id.head_left:
			if (mContext instanceof IndividualActivity) {
				return;
			}
			Intent intent = new Intent(mContext, IndividualActivity.class);
			intent.putExtra("number", log.getNumber());
			mContext.startActivity(intent);
			break;
		case R.id.head_right:
			if (mContext instanceof IndividualActivity) {
				return;
			}
			Intent intent2 = new Intent(mContext, IndividualActivity.class);
			intent2.putExtra("number", log.getNumber());
			mContext.startActivity(intent2);
			break;
		case R.id.record_content:
			if (isPlaying) {
				isPlaying = false;
				player.stop();
				String path = Utils.generateAbsoluteFilePath(log.getUid(), log.getDate());
				if (!currentAudioPath.equals(path)) {
					currentAudioPath = path;
					isPlaying = true;
					Utils.showShortToast(mContext, R.string.start_playing);
					player.play(path);
				} else {
					Utils.showShortToast(mContext, R.string.stop_playing);
				}
			} else {
				String path = Utils.generateAbsoluteFilePath(log.getUid(), log.getDate());
				currentAudioPath = path;
				isPlaying = true;
				Utils.showShortToast(mContext, R.string.start_playing);
				player.play(path);
			}
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
		case R.id.record_content:
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setMessage(R.string.confirm_delete);
			builder.setPositiveButton(R.string.confirm, new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					LogsDAO logsDAO = new LogsDAO(mContext);
					logsDAO.updateRecordState(log.getUid(), RecordState.DELETED);
					mData.remove(mData.indexOf(map));
					LogAdapter.this.notifyDataSetChanged();
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
