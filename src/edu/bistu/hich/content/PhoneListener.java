package edu.bistu.hich.content;

import edu.bistu.hich.util.Utils;
import android.content.Context;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/** 
 * @ClassName: PhoneListener 
 * @Description: phone state listener 
 * @author 仇之东  hich.cn@gmail.com 
 * @date May 17, 2014 4:22:32 PM 
 *  
 */ 
public class PhoneListener extends PhoneStateListener{
	private static final String TAG = "PhoneListener";
	Context context = null;
	PhoneRecorder recorder = null;
	
	public PhoneListener(Context context){
		this.context = context;
		this.recorder = new PhoneRecorder(context, new Handler());
	}
	
	@Override
	/**
	 * <p>Title: onCallStateChanged</p> 
	 * <p>Description: </p> 
	 * @param state 
	 * @param incomingNumber 
	 * @see android.telephony.PhoneStateListener#onCallStateChanged(int, java.lang.String)
	 */
	public void onCallStateChanged(int state, String incomingNumber) {
		switch (state) {
		case TelephonyManager.CALL_STATE_IDLE:
			Utils.D(TAG, "call state idle...");
			recorder.stopRecording(context);
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			Utils.D(TAG, "call state offhook...");
			recorder.startRecording();
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			Utils.D(TAG, "call state ringing...");
			//do nothing
			break;
		default:
			break;
		}
	}
}
