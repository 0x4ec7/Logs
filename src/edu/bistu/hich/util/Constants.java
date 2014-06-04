package edu.bistu.hich.util;

import android.net.Uri;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract;

/** 
 * @ClassName: Constants 
 * @Description: constants 
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 17, 2014 5:21:49 PM 
 *  
 */ 
public class Constants {
	public static final String ZHIDONG = "I WILL ALWAYS LOVE YOU, QIUZHIDONG!";
	public static final Uri CALLLOG_CONTENT_URI = CallLog.Calls.CONTENT_URI;
	public static final Uri CONTACTS_CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
	public static final String TEMP_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/records";
	public static final String TEMP_FILE_NAME = TEMP_FILE_PATH + "/temp_file.wav";
//	public static final long SYNC_DURATION = 1 * 60 * 1000;
	
}
