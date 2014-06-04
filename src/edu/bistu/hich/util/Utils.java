package edu.bistu.hich.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import edu.bistu.hich.entity.MyCallLog;

/** 
 * @ClassName: Utils 
 * @Description: utilities for the project 
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 17, 2014 5:22:34 PM 
 *  
 */ 
public class Utils {

	/**
	 * @Title: containsUid 
	 * @Description: check if the list contains a calllog, which uid is the given uid 
	 * @param uid the given unique id of the call log 
	 * @param callLogs calllog list 
	 * @return boolean is exists 
	 * @throws
	 */
	public static boolean containsUid(String uid, List<MyCallLog> callLogs){
		for (MyCallLog callLog : callLogs) {
			if (uid != null && uid.equals(Check.getCRC32(callLog))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @Title: isFileExists 
	 * @Description: check the existence of the record 
	 * @param uid the unique id of the record 
	 * @param date date called 
	 * @return boolean is exists 
	 * @throws
	 */
	public static boolean isFileExists(String uid, long date){
		return new File(generateAbsoluteFilePath(uid, date)).exists();
	}
	
	/**
	 * @Title: generateFilePathAndName 
	 * @Description: generate file path and name by given calllog instance 
	 * @param latestCallLog the given calllog 
	 * @return String file path and name 
	 * @throws
	 */
	@SuppressLint("SimpleDateFormat")
	public static String generateFilePathAndName(MyCallLog latestCallLog) {
		String folder = new SimpleDateFormat("yyyyMM").format(new Date(latestCallLog.getDate()));
		String name = Check.getCRC32(latestCallLog) + ".wav";
		return folder + "/" + name;
	}
	
	/**
	 * @Title: generateAbsoluteFilePath 
	 * @Description: generate absolute file path by given uid and date 
	 * @param uid the unique id of the call 
	 * @param date date called
	 * @return String absolute path of the record 
	 * @throws
	 */
	@SuppressLint("SimpleDateFormat")
	public static String generateAbsoluteFilePath(String uid, long date){
		String folder = new SimpleDateFormat("yyyyMM").format(new Date(date));
		String name = uid + ".wav";
		return Constants.TEMP_FILE_PATH + "/" + folder + "/" + name;
	}
	
	/**
	 * @Title: D 
	 * @Description: debug log 
	 * @param tag tag
	 * @param msg msg to print
	 * @throws
	 */
	public static void D(String tag, String msg){
		Log.d(tag, msg);
	}
	
	public static void showShortToast(Context context, int resId) {
		showToast(context, resId, Toast.LENGTH_SHORT);
	}

	public static void showShortToast(Context context, String msg) {
		showToast(context, msg, Toast.LENGTH_SHORT);
	}
	
	public static void showLongToast(Context context, int resId) {
		showToast(context, resId, Toast.LENGTH_LONG);
	}

	public static void showLongToast(Context context, String msg) {
		showToast(context, msg, Toast.LENGTH_LONG);
	}

	private static void showToast(Context context, int resId, int duration) {
		Toast.makeText(context, resId, duration).show();
	}

	private static void showToast(Context context, String str, int duration) {
		Toast.makeText(context, str, duration).show();
	}
}
