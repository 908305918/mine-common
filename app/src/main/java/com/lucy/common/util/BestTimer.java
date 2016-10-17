package com.lucy.common.util;

import android.os.Handler;

public class BestTimer implements Runnable {

	private Handler tickRequestHandler = new Handler();
	private int timeInterval = 1000;
	private BestTimerListener mBestTimerListener;

	public BestTimer(BestTimerListener l) {
		mBestTimerListener = l;
	}

	@Override
	public void run() {

		if (mBestTimerListener != null) {
			mBestTimerListener.timerUp();
		}
		tickRequestHandler.postDelayed(this, timeInterval);
	}

	public void startTimer(int interval) {

		timeInterval = interval;
		tickRequestHandler.postDelayed(this, timeInterval);
	}

	public void cancelTimer() {

		tickRequestHandler.removeCallbacks(this);
	}

	public void setTimerListener(BestTimerListener l) {

		mBestTimerListener = l;
	}

	public interface BestTimerListener {

		public void timerUp();
	}
}
