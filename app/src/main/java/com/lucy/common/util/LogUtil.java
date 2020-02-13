package com.lucy.common.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.util.Log;

public class LogUtil {
	private static final boolean DEBUG = true;
	private static final String TAG = "LogUtil";
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss",
			Locale.CHINA);

	// 获取LOG
	private static String getFormatTag() {
		StackTraceElement trace = Thread.currentThread().getStackTrace()[4];
		String className = trace.getClassName();
		className = className.substring(className.lastIndexOf(".") + 1);
		return String.format("%s.%s:%d", className, trace.getMethodName(), trace.getLineNumber());
	}

	public static void i(String msg) {
		if (DEBUG) {
			String tag = getFormatTag();
			Log.i(tag, msg);
		}
	}

}
