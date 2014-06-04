package edu.bistu.hich.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.bistu.hich.entity.Log;
import edu.bistu.hich.entity.MyCallLog;
import edu.bistu.hich.entity.MyContact;
import edu.bistu.hich.entity.Enum.CallType;
import edu.bistu.hich.entity.Enum.LogState;
import edu.bistu.hich.entity.Enum.RecordFlagState;
import edu.bistu.hich.entity.Enum.RecordState;
import edu.bistu.hich.util.Check;
import edu.bistu.hich.util.Utils;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/** 
 * @ClassName: LogsDAO 
 * @Description: logs table data access object. 
 * @author 仇之东  hich.cn@gmail.com 
 * @date May 17, 2014 4:16:35 PM 
 *  
 */ 
@SuppressWarnings("deprecation")
public class LogsDAO {
	private DbOpenHelper dbOpenHelper;
	
	public LogsDAO(Context context){
		this.dbOpenHelper = new DbOpenHelper(context);
	}
	
	/**
	 * @Title: updateLogs 
	 * @Description: update logs 
	 * @param callLogs call logs to update 
	 * @return boolean the result of the operation 
	 * @throws 
	 */
	@Deprecated 
	public boolean updateLogs(List<MyCallLog> callLogs){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		String sql = "SELECT uid, date FROM logs ORDER BY date";
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sql, null);
			
			List<String> uids = new ArrayList<String>();
			//update log_state
			while (cursor.moveToNext()) {
				String uid = cursor.getString(cursor.getColumnIndex("uid"));
				uids.add(uid);
				if (!Utils.containsUid(uid, callLogs)) {
					updateLogState(uid, LogState.ONLY_LOCAL);
				}
			}
			
			//insert new logs into table
			for (MyCallLog callLog : callLogs) {
				String tmp = Check.getCRC32(callLog);
				if (!uids.contains(tmp)) {
					insertNewLog(callLog);
				}
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return true;
	}
	
	/**
	 * @Title: refreshRecords 
	 * @Description: refresh records in database 
	 * @return boolean operation result 
	 * @throws
	 */
	public boolean refreshRecords(){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		String sql = "SELECT uid, date FROM logs ORDER BY date";
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sql, null);
			
			while (cursor.moveToNext()) {
				String uid = cursor.getString(cursor.getColumnIndex("uid"));
				long date = Long.parseLong(cursor.getString(cursor.getColumnIndex("date")));
				if (!Utils.isFileExists(uid, date)) {
//					updateRecordState(uid, RecordState.DELETED);
					deleteLogByUid(uid);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return true;
	}
	
	/**
	 * @Title: insertNewLog 
	 * @Description: insert new log to database 
	 * @param callLog MyCallLog instance
	 * @param recordFlagState is record exists
	 * @param recordState record storage state
	 * @return boolean operation result
	 * @throws
	 */
	public boolean insertNewLog(MyCallLog callLog, RecordFlagState recordFlagState, RecordState recordState){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		String sql = "INSERT INTO logs (name, number, duration, date, type, uid, record_flag, log_state_flag, record_state_flag) VALUES(?, ?, ?, ?, ?, ?, ?, 0, ?)";
		try {
			db.execSQL(sql, new Object[]{callLog.getName(), callLog.getNumber(), callLog.getDuration(), callLog.getDate(), callLog.getCallType().ordinal(), Check.getCRC32(callLog), recordFlagState.ordinal(), recordState.ordinal()});
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return true;
	}
	
	/**
	 * @Title: insertNewLog 
	 * @Description: insert new log to database, set RecordFlagState to EXIST, and RecordState to LOCAL
	 * @param callLog an instance of MyCallLog to insert 
	 * @return boolean operation result
	 * @throws 
	 */
	public boolean insertNewLog(MyCallLog callLog){
		return insertNewLog(callLog, RecordFlagState.EXIST, RecordState.LOCAL);
	}
	
	/**
	 * @Title: getLogs 
	 * @Description: get logs by page 
	 * @param page page (10 items per page) 
	 * @return ArrayList<Log> 
	 * @throws
	 */
	public ArrayList<Log> getLogs(int page){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		String sql = "SELECT * FROM logs WHERE record_state_flag = " + RecordState.LOCAL.ordinal() + " ORDER BY date DESC limit " + (page * 10) + ", 10";
		Cursor cursor = null;
		
		ArrayList<Log> list = new ArrayList<Log>();
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String number = cursor.getString(cursor.getColumnIndex("number"));
				int duration = cursor.getInt(cursor.getColumnIndex("duration"));
				long date = Long.parseLong(cursor.getString(cursor.getColumnIndex("date")));
				CallType callType = CallType.fromInt(cursor.getInt(cursor.getColumnIndex("type")) - 1);
				String uid = cursor.getString(cursor.getColumnIndex("uid"));
				RecordFlagState recordFlag = RecordFlagState.fromInt(cursor.getInt(cursor.getColumnIndex("record_flag")));
				LogState logState = LogState.NOT_DELETED;
				RecordState recordState = RecordState.fromInt(cursor.getInt(cursor.getColumnIndex("record_state_flag")));
				Timestamp addDate = Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("update_date")));
				
				list.add(new Log(id, name, number, duration, date, callType, uid, recordFlag, logState, recordState, addDate));
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
	}
	
	/**
	 * @Title: getDeletedLogs 
	 * @Description: get deleted logs 
	 * @param page 
	 * @return ArrayList<Log> 
	 * @throws
	 */
	public ArrayList<Log> getDeletedLogs(int page){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		String sql = "SELECT * FROM logs WHERE record_state_flag = " + RecordState.DELETED.ordinal() + " ORDER BY date DESC limit " + (page * 10) + ", 10";
		Cursor cursor = null;
		
		ArrayList<Log> list = new ArrayList<Log>();
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String number = cursor.getString(cursor.getColumnIndex("number"));
				int duration = cursor.getInt(cursor.getColumnIndex("duration"));
				long date = Long.parseLong(cursor.getString(cursor.getColumnIndex("date")));
				CallType callType = CallType.fromInt(cursor.getInt(cursor.getColumnIndex("type")) - 1);
				String uid = cursor.getString(cursor.getColumnIndex("uid"));
				RecordFlagState recordFlag = RecordFlagState.fromInt(cursor.getInt(cursor.getColumnIndex("record_flag")));
				LogState logState = LogState.NOT_DELETED;
				RecordState recordState = RecordState.fromInt(cursor.getInt(cursor.getColumnIndex("record_state_flag")));
				Timestamp addDate = Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("update_date")));
				
				list.add(new Log(id, name, number, duration, date, callType, uid, recordFlag, logState, recordState, addDate));
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
	}
	
	/**
	 * @Title: getIndividualLogs 
	 * @Description: get individual logs by page
	 * @param page 
	 * @return ArrayList<Log> 
	 * @throws 
	 */
	public ArrayList<Log> getIndividualLogs(String number, int page){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		String sql = "SELECT * FROM logs WHERE number like '%" + number + "%' AND record_state_flag = " + RecordState.LOCAL.ordinal() + " ORDER BY date DESC limit " + (page * 10) + ", 10";
//		System.out.println(sql);
		Cursor cursor = null;
		
		ArrayList<Log> list = new ArrayList<Log>();
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				int duration = cursor.getInt(cursor.getColumnIndex("duration"));
				long date = Long.parseLong(cursor.getString(cursor.getColumnIndex("date")));
				CallType callType = CallType.fromInt(cursor.getInt(cursor.getColumnIndex("type")) - 1);
				String uid = cursor.getString(cursor.getColumnIndex("uid"));
				RecordFlagState recordFlag = RecordFlagState.fromInt(cursor.getInt(cursor.getColumnIndex("record_flag")));
				LogState logState = LogState.NOT_DELETED;
				RecordState recordState = RecordState.fromInt(cursor.getInt(cursor.getColumnIndex("record_state_flag")));
				Timestamp addDate = Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("update_date")));
				
				list.add(new Log(id, name, number, duration, date, callType, uid, recordFlag, logState, recordState, addDate));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
	}
	
	/**
	 * @Title: getContacts 
	 * @Description: get contacts 
	 * @param page page (10 items per page) 
	 * @return ArrayList<MyContact> 
	 * @throws
	 */
	public ArrayList<MyContact> getContacts(int page){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		String sql = "SELECT DISTINCT name, number FROM logs WHERE record_state_flag = " + RecordState.LOCAL.ordinal() + " ORDER BY date DESC limit " + (page * 10) + ", 10";
		Cursor cursor = null;
		
		ArrayList<MyContact> contacts = new ArrayList<MyContact>();
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String number = cursor.getString(cursor.getColumnIndex("number"));
				long date = getLatestContactDate(number);
				int count = getContactCount(number);
				
				contacts.add(new MyContact(name, number, date, count));
			}
			
			return contacts;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
	}
	
	/**
	 * @Title: getContactCount 
	 * @Description: get count 
	 * @param number number of the contact 
	 * @return int count 
	 * @throws
	 */
	public int getContactCount(String number){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		String sql = "";
		if (number.length() < 5) {
			sql = "SELECT count(*) as count FROM logs WHERE number = '" + number + "' AND record_state_flag = " + RecordState.LOCAL.ordinal();
		} else {
			sql = "SELECT count(*) as count FROM logs WHERE number like '%" + number + "%' AND record_state_flag = " + RecordState.LOCAL.ordinal();
		}
		Cursor cursor = null;
		
//		ArrayList<MyContact> contacts = new ArrayList<MyContact>();
		try {
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				return cursor.getInt(cursor.getColumnIndex("count"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return 0;
	}
	
	/**
	 * @Title: getLatestContactDate 
	 * @Description: get latest contact date
	 * @param number contact number
	 * @return long date
	 * @throws
	 */
	public long getLatestContactDate(String number){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		String sql = "SELECT date FROM logs WHERE number = '" + number + "' ORDER BY date DESC limit 1";
		Cursor cursor = null;
		
		try {
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				return Long.parseLong(cursor.getString(cursor.getColumnIndex("date")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return 0;
	}
	
	/**
	 * @Title: updateLogState 
	 * @Description: udpate call log state 
	 * @param uid the unique id of the record 
	 * @param state the state of the record 
	 * @return boolean the result of the operation 
	 * @throws 
	 */
	@Deprecated
	private boolean updateLogState(String uid, LogState state){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		String sql = "UPDATE logs SET log_state_flag = " + state.ordinal() + " WHERE uid = \"" + uid + "\"";
		try {
			db.execSQL(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return true;
	}
	
	/**
	 * @Title: updateRecordFlags 
	 * @Description: check existence of those records, and update record flags in database. 
	 * @return boolean operation result 
	 * @throws
	 */
	@Deprecated
	public boolean updateRecordFlags(){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		String sql = "SELECT uid, date, record_flag FROM logs ORDER BY date DESC";
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				String uid = cursor.getString(cursor.getColumnIndex("uid"));
				long date = Long.parseLong(cursor.getString(cursor.getColumnIndex("date")));
				int recordFlag = cursor.getInt(cursor.getColumnIndex("record_flag"));
				
				boolean fileExists = Utils.isFileExists(uid, date);
				
				if (recordFlag == 0 && fileExists) {
					updateRecordFlag(uid, RecordFlagState.EXIST);
					updateRecordState(uid, RecordState.LOCAL);
				} else if (recordFlag == 1 && !fileExists) {
					updateRecordFlag(uid, RecordFlagState.NOT_EXIST);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return true;
	}
	
	/**
	 * @Title: updateRecordFlag 
	 * @Description: upadte record flag 
	 * @param uid the unique id of the record 
	 * @param state the state of the record 
	 * @return boolean operation result 
	 * @throws
	 */
	@Deprecated
	private boolean updateRecordFlag(String uid, RecordFlagState state){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		String sql = "UPDATE logs SET record_flag = " + state.ordinal() + " WHERE uid = \"" + uid + "\"";
		try {
			db.execSQL(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return true;
	}
	
	/**
	 * @Title: updateRecordState 
	 * @Description: update record state 
	 * @param uid the unique id of the record 
	 * @param state the state to set 
	 * @return boolean operation result 
	 * @throws 
	 */
	public boolean updateRecordState(String uid, RecordState state){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		String sql = "UPDATE logs SET record_state_flag = " + state.ordinal() + " WHERE uid = \"" + uid + "\"";
		try {
			db.execSQL(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return true;
	}
	
	/**
	 * @Title: updateRecordStateIndividually 
	 * @Description: update record state individually 
	 * @param number phone number of the person
	 * @param state 
	 * @return boolean 
	 * @throws
	 */
	public boolean updateRecordStateIndividually(String number, RecordState state){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		String sql = "UPDATE logs SET record_state_flag = " + state.ordinal() + " WHERE number = \"" + number + "\"";
		try {
			db.execSQL(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return true;
	}
	
	/**
	 * @Title: deleteLogByUid 
	 * @Description: delete log by uid 
	 * @param uid unique id of the log to be deleted
	 * @return boolean operation result
	 * @throws
	 */
	public boolean deleteLogByUid(String uid){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		String sql = "DELETE FROM logs WHERE uid = \"" + uid + "\"";
		try {
			db.execSQL(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
		return true;
	}

}