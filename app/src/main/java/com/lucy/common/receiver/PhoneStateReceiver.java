package com.lucy.common.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 监听来去电
 * 
 * @author Administrator
 * 
 */
public class PhoneStateReceiver extends BroadcastReceiver {
	private final String TAG = "PhoneStateReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("action" + intent.getAction());
		// 去电
		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			System.out.println("去电==" + phoneNumber);
		} else {// 来电
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Service.TELEPHONY_SERVICE);
			if (tm == null) {
				return;
			}
			switch (tm.getCallState()) {
			case TelephonyManager.CALL_STATE_IDLE:
				System.out.println("挂断");
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				System.out.println("接听");
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				System.out.println("响铃:来电号码"+intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER));
				break;
			}
			// 设置一个监听器
		}
	}

	PhoneStateListener listener = new PhoneStateListener() {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// 注意，方法必须写在super方法后面，否则incomingNumber无法获取到值。
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				System.out.println("挂断");
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				System.out.println("接听");
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				System.out.println("响铃:来电号码" + incomingNumber);
				break;
			}
		}
	};
}
