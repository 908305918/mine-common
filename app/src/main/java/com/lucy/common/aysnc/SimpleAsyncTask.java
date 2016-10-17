package com.lucy.common.aysnc;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class SimpleAsyncTask extends AsyncTask<Object, Integer, String> {
	private Context mContext;
	private ProgressDialog mDialog;

	private DataLoader mDataLoader;

	public SimpleAsyncTask(Context context, DataLoader dataLoader) {
		mContext = context;
		mDataLoader = dataLoader;
	}

	public SimpleAsyncTask(Context context, DataLoader dataLoader, String msg) {
		mContext = context;
		mDialog = new ProgressDialog(mContext);
		mDialog.setMessage(msg);
		mDataLoader = dataLoader;
	}

	@Override
	protected void onPreExecute() {
		if (mDialog != null) {
			mDialog.show();
		}
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(Object... params) {
		return mDataLoader.loadData(params);
	}

	@Override
	protected void onPostExecute(String result) {
		if (mDialog != null) {
			mDialog.dismiss();
		}
		mDataLoader.handleData(result);
		super.onPostExecute(result);
	}

}
