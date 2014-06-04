package edu.bistu.hich.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import edu.bistu.hich.entity.MyContact;
import edu.bistu.hich.logs.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/** 
 * @ClassName: MyAdapter 
 * @Description: adapter for logs listview 
 * @author 仇之东  hich.cn@gmail.com 
 * @date May 18, 2014 11:12:04 PM 
 *  
 */ 
public class ContactAdapter extends BaseAdapter {
	private List<HashMap<String, Object>> mData;
	private LayoutInflater mInflater;
	private Resources res;

	public ContactAdapter(Context context, List<HashMap<String, Object>> data) {
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
			convertView = mInflater.inflate(R.layout.list_contact_content, null);
		}
		
		ImageView headIv = (ImageView)convertView.findViewById(R.id.head_left);
		TextView contactTv = (TextView)convertView.findViewById(R.id.contact);
		TextView contactDateTv = (TextView)convertView.findViewById(R.id.latest_contact_date);
		TextView contactCountTv = (TextView)convertView.findViewById(R.id.record_count);
		
		MyContact contact = (MyContact)map.get("contact");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (contact.getName() == null) {
			contact.setName("");
		}
		
		headIv.setImageBitmap((Bitmap)map.get("head"));
		headIv.setTag(contact);
		
		contactTv.setText(contact.getName() + "(" + contact.getNumber() + ")");
		contactDateTv.setText(res.getString(R.string.latest_contact_date) + format.format(new Date(contact.getDate())));
		contactCountTv.setText(res.getString(R.string.latest_contact) + contact.getCount() + res.getString(R.string.count));
		
		return convertView;
	}

}
