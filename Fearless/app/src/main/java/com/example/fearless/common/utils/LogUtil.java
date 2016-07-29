package com.example.fearless.common.utils;

import android.util.Log;




public class LogUtil {
	
	public static void Logi(String tag,String msg){
		if(BuildConfig.DEBUG){
			Log.i(tag, msg);
		}
	}

	public static void Loge(String tag,String msg){
		if(BuildConfig.DEBUG){
			Log.e(tag, msg);
		}
	}
	public static final class BuildConfig {
		public static final boolean DEBUG = Boolean.parseBoolean("true");
		public static final String APPLICATION_ID = "com.msb.masterorg";
		public static final String BUILD_TYPE = "debug";
		public static final String FLAVOR = "";
		public static final int VERSION_CODE = 1;
		public static final String VERSION_NAME = "1.0";
	}

}
