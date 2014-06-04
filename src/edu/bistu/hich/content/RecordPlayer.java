package edu.bistu.hich.content;

import java.io.File;
import java.io.FileInputStream;

import edu.bistu.hich.adapter.LogAdapter;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

/** 
 * @ClassName: RecordPlayer 
 * @Description: TODO 
 * @author 仇之东   hich.cn@gmail.com 
 * @date May 29, 2014 10:43:03 AM 
 *  
 */ 
public class RecordPlayer implements OnCompletionListener{
	private static RecordPlayer mRecordPlayer;
	private static MediaPlayer mMediaPlayer;
	
	private RecordPlayer(){
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setOnCompletionListener(this);
	}
	
	public static RecordPlayer getInstance(){
		return mRecordPlayer == null ? new RecordPlayer() : mRecordPlayer;
	}
	
	public static MediaPlayer getMediaPlayer(){
		return mMediaPlayer;
	}
	
	public void play(String path){
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.stop();
		}
		try {
			mMediaPlayer.reset();
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			mMediaPlayer.setDataSource(fis.getFD());
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.stop();
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		LogAdapter.isPlaying = false;
	}

}
