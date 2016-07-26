package com.mz.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.Toast;

import com.hdj.downapp_market.R;

public class TTSUtil {
	static TTSUtil instance;

	public static TTSUtil getInstance(Context context) {
		if (instance == null) {
			instance = new TTSUtil(context);
		}
		return instance;
	}

	private TTSUtil(Context context) {
		this.context = context;
		textToSpeech = new TextToSpeech(context, new TTSListener());
	}

	TextToSpeech textToSpeech = null;
	Context context;

	public void ringReboot() {
		int result = textToSpeech.setLanguage(Locale.CHINESE);
		Logger.i(Build.VERSION.SDK_INT+"=====result==========="+result);
		if (result == TextToSpeech.LANG_MISSING_DATA
				|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
			playRadioReboot();
		} else {
			if (textToSpeech == null) {
				textToSpeech = new TextToSpeech(context, new TTSListener());
			} else {
				textToSpeech.speak(context.getString(R.string.tts_reboot),
						TextToSpeech.QUEUE_FLUSH, null);
			}
		}
	}
	public void ringGoogle() {
		int result = textToSpeech.setLanguage(Locale.CHINESE);
		Logger.i(Build.VERSION.SDK_INT+"====TTS==========="+result);
		if (result == TextToSpeech.LANG_MISSING_DATA
				|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
			playRadioGoogle();

		} else {
			if (textToSpeech == null) {
				textToSpeech = new TextToSpeech(context, new TTSListener());
			} else {
				textToSpeech.speak(context.getString(R.string.google_market_no_more_account),
						TextToSpeech.QUEUE_FLUSH, null);
//				 textToSpeech.synthesizeToFile(context.getString(R.string.google_market_no_more_account), new HashMap<String, String>(), "/sdcard/speak_googel.wav");
			}
		}
	}

	private void playRadioGoogle() {
		playRadio(GlobalConstants.SOUND_GOOGLE_NAME);
	}

	private void playRadioReboot() {
		playRadio(GlobalConstants.SOUND_ROOT_NAME);
	}
	private void playRadio(String soundName){
		MediaPlayer player = new MediaPlayer();
		File soundFile=new File(GlobalConstants.EXTRA_PATH + File.separator
				+soundName);
		if(!soundFile.exists()){
			MyFileUtil.copySoundFile(context,soundName,
					soundName);
		}
		try {
			player.setDataSource(GlobalConstants.EXTRA_PATH + File.separator
					+soundName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			player.prepare();
			player.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class TTSListener implements OnInitListener {

		@Override
		public void onInit(int status) {
			// TODO Auto-generated method stub
			if (status == TextToSpeech.SUCCESS) {
				// int result = mSpeech.setLanguage(Locale.ENGLISH);
				int result = textToSpeech.setLanguage(Locale.CHINESE);
				// int result = textToSpeech.setLanguage(Locale.ENGLISH);
				// 如果打印为-2，说明不支持这种语言
//				Toast.makeText(context, "" + result, Toast.LENGTH_LONG).show();
				if (result == TextToSpeech.LANG_MISSING_DATA
						|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
					Toast.makeText(context,
							context.getString(R.string.tts_error) + result,
							Toast.LENGTH_LONG).show();
				} else {
					textToSpeech.speak(context.getString(R.string.tts_ok),
							TextToSpeech.QUEUE_FLUSH, null);
				}
			}
		}
	}
	public  void save(String tips){
		 textToSpeech.synthesizeToFile(tips, new HashMap<String, String>(), "/sdcard/speak.wav");
	}
	
}
