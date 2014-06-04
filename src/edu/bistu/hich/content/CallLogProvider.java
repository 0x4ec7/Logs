package edu.bistu.hich.content;

import java.util.ArrayList;
import java.util.List;

import edu.bistu.hich.entity.MyCallLog;
import edu.bistu.hich.entity.Enum.CallType;
import edu.bistu.hich.util.Constants;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

/** 
 * @ClassName: CallLogProvider 
 * @Description: call log content provider 
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 17, 2014 4:18:09 PM 
 *  
 */ 
public class CallLogProvider {

	/**
	 * @Title: getAllCallLogs 
	 * @Description: get all call logs 
	 * @param context
	 * @return List<MyCallLog> 
	 * @throws
	 */
	@Deprecated
	public static List<MyCallLog> getAllCallLogs(Context context) {
		List<MyCallLog> callLogs = new ArrayList<MyCallLog>();
		ContentResolver resolver = context.getContentResolver();
		//Cursor cursor = resolver.query(Constant.CALLLOG_CONTENT_URI, null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
		Cursor cursor = resolver.query(Constants.CALLLOG_CONTENT_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
			String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
			int duration = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION)));
			long date = Long.parseLong(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE)));
			int type = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)));
			CallType callType = CallType.fromInt(type - 1);
			callLogs.add(new MyCallLog(name, number, duration, date, callType));
		}
		cursor.close();
		return callLogs;
	}
	
	/**
	 * @Title: getLatestCallLog 
	 * @Description: get latest call log 
	 * @param context
	 * @return MyCallLog 
	 * @throws
	 */
	public static MyCallLog getLatestCallLog(Context context){
		MyCallLog callLog = null;
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(Constants.CALLLOG_CONTENT_URI, null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
		while (cursor.moveToFirst()) {
			String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
			String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
			int duration = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION)));
			long date = Long.parseLong(cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE)));
			int type = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)));
			CallType callType = CallType.fromInt(type - 1);
			callLog = new MyCallLog(name, number, duration, date, callType);
			break;
		}
		cursor.close();
		return callLog;
	}

}
