package edu.bistu.hich.content;

import java.io.File;

import edu.bistu.hich.db.LogsDAO;
import edu.bistu.hich.entity.MyCallLog;
import edu.bistu.hich.util.Constants;
import edu.bistu.hich.util.Utils;
import android.content.Context;
import android.database.ContentObserver;
import android.media.MediaRecorder;
import android.os.Handler;

/** 
 * @ClassName: PhoneRecorder 
 * @Description: call recorder 
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 17, 2014 4:25:00 PM 
 *  
 */ 
public class PhoneRecorder extends ContentObserver {
	private static final String TAG = "PhoneRecorder";
	
	private Context context = null;
	private MediaRecorder recorder;
	public static boolean isRecording = false;
	private static boolean dumpFlag = true;

	/**
	 * Create a new instance of PhoneRecorder. 
	 * <p>Title: PhoneRecorder </p> 
	 * <p>Description: </p> 
	 * @param context 
	 * @param handler 
	 */
	public PhoneRecorder(Context context, Handler handler) {
		super(handler);
		this.context = context;
		context.getContentResolver().registerContentObserver(
				Constants.CALLLOG_CONTENT_URI, false, this);
	}

	/**
	 * @Title: startRecording 
	 * @Description: start recording
	 * @throws
	 */
	public void startRecording() {
		Utils.D(TAG, "start recording...");
		try {
			if (!isRecording) {
				recorder = new MediaRecorder();
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
				File tempFile = new File(Constants.TEMP_FILE_NAME);
				if (!tempFile.getParentFile().exists()) {
					tempFile.getParentFile().mkdirs();
				}
				recorder.setOutputFile(Constants.TEMP_FILE_NAME);
				recorder.prepare();
				recorder.start();
				isRecording = true;
			}
		} catch (Exception e) {
			isRecording = false;
			e.printStackTrace();
		}
	}

	/**
	 * @Title: stopRecording 
	 * @Description: stop recording 
	 * @param context
	 * @throws
	 */
	public void stopRecording(Context context) {
		Utils.D(TAG, "stop recording...");
		if (isRecording) {
			recorder.stop();
			recorder.release();
			recorder = null;
			isRecording = false;
			dumpFlag = false;
		}
	}

	@Override
	/**
	 * <p>Title: onChange</p> 
	 * <p>Description: call when content is changed. </p> 
	 * @param selfChange 
	 * @see android.database.ContentObserver#onChange(boolean)
	 */
	public void onChange(boolean selfChange) {
		Utils.D(TAG, "saving recording...");
		MyCallLog lastestCallLog = CallLogProvider.getLatestCallLog(context);
		if (!dumpFlag) {
			File oldFile = new File(Constants.TEMP_FILE_NAME); 
			File newFile = new File(Constants.TEMP_FILE_PATH + "/" + Utils.generateFilePathAndName(lastestCallLog));
			if(!newFile.getParentFile().exists()) {
				newFile.getParentFile().mkdirs(); 
			}
			Utils.D(TAG, "file path ---> " + Constants.TEMP_FILE_PATH + "/" + Utils.generateFilePathAndName(lastestCallLog));
			oldFile.renameTo(newFile);
			dumpFlag = true;
			
			LogsDAO dao = new LogsDAO(context);
			dao.insertNewLog(lastestCallLog);
			dao.refreshRecords();
		}
	}
}
