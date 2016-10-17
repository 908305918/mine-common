package com.lucy.common.util;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * SharedPreferences 工具类
 * @author Administrator
 *
 */
public class SharedPreUtil {
	private SharedPreferences sp;

	private SharedPreUtil(Context context, String fileName) {
		sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
	}

	public String getString(String key, final String defaultValue) {
		return sp.getString(key, defaultValue);
	}

	public void putString(final String key, final String value) {
		sp.edit().putString(key, value).commit();
	}

	public boolean getBoolean(final String key, final boolean defaultValue) {
		return sp.getBoolean(key, defaultValue);
	}

	public void putBoolean(final String key, final boolean value) {
		sp.edit().putBoolean(key, value).commit();
	}

	public void putInt(final String key, final int value) {
		sp.edit().putInt(key, value).commit();
	}

	public int getInt(final String key, final int defaultValue) {
		return sp.getInt(key, defaultValue);
	}

	public void putFloat(final String key, final float value) {
		sp.edit().putFloat(key, value).commit();
	}

	public float getFloat(final String key, final float defaultValue) {
		return sp.getFloat(key, defaultValue);
	}

	public void putLong(final String key, final long value) {
		sp.edit().putLong(key, value).commit();
	}

	public long getLong(final String key, final long defaultValue) {
		return sp.getLong(key, defaultValue);
	}

}
