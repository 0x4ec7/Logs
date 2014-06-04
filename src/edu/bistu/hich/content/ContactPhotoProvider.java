package edu.bistu.hich.content;

import java.io.InputStream;

import edu.bistu.hich.logs.R;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

/** 
 * @ClassName: ContactPhotoProvider 
 * @Description: get photo of specific contact by number
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 18, 2014 10:15:19 PM 
 *  
 */ 
public class ContactPhotoProvider {

	/**
	 * @Title: getContactPhoto 
	 * @Description: get photo of specific contact 
	 * @param context context 
	 * @param number contact number 
	 * @return Bitmap 
	 * @throws
	 */
	public static Bitmap getContactPhoto(Context context, String number) {
		Uri contactUri = Uri.parse("content://com.android.contacts/data/phones/filter/" + number);
		Cursor cursor = context.getContentResolver().query(contactUri, null, null, null, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			Long contactID = cursor.getLong(cursor.getColumnIndex("contact_id"));
			Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactID);
			InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), uri);
			return BitmapFactory.decodeStream(input);
		} else {
			return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		}
	}

}
