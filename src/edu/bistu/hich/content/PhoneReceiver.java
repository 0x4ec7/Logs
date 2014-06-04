package edu.bistu.hich.content;

import edu.bistu.hich.util.Utils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @ClassName: PhoneReceiver 
 * @Description: phone broadcast receiver 
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 17, 2014 4:23:47 PM 
 * 
 */
public class PhoneReceiver extends BroadcastReceiver{
	private static final String TAG = "PhoneReceiver";

	@Override
	/**
	 * <p>Title: onReceive</p> 
	 * <p>Description: </p> 
	 * @param context
	 * @param intent 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	public void onReceive(Context context, Intent intent) {
		Utils.D(TAG, "phone state changed...");
		Intent service = new Intent("edu.bistu.hich.phoneService");
		context.startService(service);
	}
}
