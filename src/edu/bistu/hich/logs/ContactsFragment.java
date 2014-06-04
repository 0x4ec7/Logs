package edu.bistu.hich.logs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.bistu.hich.adapter.ContactAdapter;
import edu.bistu.hich.content.ContactPhotoProvider;
import edu.bistu.hich.db.LogsDAO;
import edu.bistu.hich.entity.MyContact;
import edu.bistu.hich.entity.Enum.RecordState;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

/** 
 * @ClassName: ContactsFragment 
 * @Description: view of contacts fragment
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 17, 2014 5:15:23 PM 
 *  
 */ 
public class ContactsFragment extends BaseFragment{
	private ListView contactsLv;
	
	private int lastItem;
	private int count;
	private int curPage = 0;
	private List<HashMap<String, Object>> list;
	public static ContactAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		curPage = 0;
		initData();
		
		View view = inflater.inflate(R.layout.fragment_contacts, container, false);
		contactsLv = (ListView) view.findViewById(R.id.listview_contacts);

		adapter = new ContactAdapter(mContext, list);
		contactsLv.setAdapter(adapter);
		contactsLv.setOnScrollListener(this);
		contactsLv.setOnItemClickListener(this);
		contactsLv.setOnItemLongClickListener(this);
		
		return view;
	}
	
	private void initData() {
		list = getData();
		count = list.size();
	}
	
	private List<HashMap<String, Object>> getData() {
		list = new ArrayList<HashMap<String, Object>>();
		LogsDAO dao = new LogsDAO(mContext);
		ArrayList<MyContact> contacts = dao.getContacts(curPage++);
		
		for (MyContact contact : contacts) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("head", ContactPhotoProvider.getContactPhoto(mContext, contact.getNumber()));
			map.put("contact", contact);
			list.add(map);
		}
		return list;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		System.out.println(scrollState);
		if(lastItem == count  && scrollState == OnScrollListener.SCROLL_STATE_IDLE){
			LogsDAO dao = new LogsDAO(mContext);
			ArrayList<MyContact> contacts = dao.getContacts(curPage);
			
			if (contacts == null || contacts.size() == 0) {
				showShortToast(R.string.no_more_result);
				return;
			} else {
				curPage++;
				for (MyContact contact : contacts) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("head", ContactPhotoProvider.getContactPhoto(mContext, contact.getNumber()));
					map.put("contact", contact);
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
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MyContact contact = (MyContact)list.get(position).get("contact");
		Intent intent = new Intent(mContext, IndividualActivity.class);
		intent.putExtra("number", contact.getNumber());
		startActivity(intent);
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		final HashMap<String, Object> map = list.get(position);
		final MyContact contact = (MyContact)map.get("contact");
		
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setMessage(R.string.confirm_delete_individual);
		builder.setPositiveButton(R.string.confirm, new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LogsDAO logsDAO = new LogsDAO(mContext);
				logsDAO.updateRecordStateIndividually(contact.getNumber(), RecordState.DELETED);
				list.remove(list.indexOf(map));
				adapter.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton(R.string.cancel, new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
		
		return super.onItemLongClick(parent, view, position, id);
	}
}
