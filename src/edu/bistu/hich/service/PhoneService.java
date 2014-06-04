package edu.bistu.hich.service;

import edu.bistu.hich.content.PhoneListener;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/** 
 * @ClassName: PhoneService 
 * @Description: phone service  
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 17, 2014 5:17:54 PM 
 *  
 */ 
public class PhoneService extends Service{
	
	@Override
	public void onCreate() {
		TelephonyManager manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		manager.listen(new PhoneListener(this), PhoneStateListener.LISTEN_CALL_STATE);
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
}
