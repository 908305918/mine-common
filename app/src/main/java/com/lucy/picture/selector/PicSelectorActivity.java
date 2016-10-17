package com.lucy.picture.selector;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.lucy.common.R;
import com.lucy.common.adapter.CommonAdapter;
import com.lucy.common.adapter.ViewHolder;
import com.lucy.common.util.Utility;
import com.lucy.picture.selector.ImageLoader.Type;

public class PicSelectorActivity extends Activity {
	private List<ImageFloder> mImageFloderList;
	private List<String> mAllImageList = new ArrayList<String>();
	private GridView mGridView;
	private BaseAdapter mAdapter;
	private ImageLoader mImageLoader;
	private ProgressDialog mProgressDialog;
	// 选中图片的路径集合
	private List<String> mPickedList;
	private Button mSendPictureBtn;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0x111:
				mProgressDialog.dismiss();
				Toast.makeText(PicSelectorActivity.this, "sdcard尚未准备好", Toast.LENGTH_SHORT).show();
				break;
			case 0x110:
				mProgressDialog.dismiss();
				mAdapter.notifyDataSetChanged();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_picture_selector);
		initView();
		getImages();
	}

	private void initView() {
		mImageLoader = ImageLoader.getInstance(3, Type.LIFO);
		mGridView = (GridView) this.findViewById(R.id.gv_picture);
		mSendPictureBtn = (Button) this.findViewById(R.id.pick_picture_send_btn);
		mAdapter = new PictureAdapter(this, mAllImageList);
		mGridView.setAdapter(mAdapter);
	}

	/**
	 * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描
	 */
	private void getImages() {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		// 显示进度条
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
		new Thread(new Runnable() {
			@Override
			public void run() {
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = PicSelectorActivity.this.getContentResolver();
				// 只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?", new String[] {
								"image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED+" DESC");
				if (mCursor == null || mCursor.getCount() == 0) {
					// 通知Handler扫描图片失败
					mHandler.sendEmptyMessage(0x111);
				} else {
					while (mCursor.moveToNext()) {
						// 获取图片的路径
						String path = mCursor.getString(mCursor
								.getColumnIndex(MediaStore.Images.Media.DATA));
						mAllImageList.add(path);
					}
					mCursor.close();
					// 通知Handler扫描图片完成
					mHandler.sendEmptyMessage(0x110);
				}
			}
		}).start();
	}
	
	public class PictureAdapter extends CommonAdapter<String>{
		/**
		 * 用来存储图片的选中情况
		 */
		private SparseBooleanArray mSelectMap = new SparseBooleanArray();

		public PictureAdapter(Context context, List<String> dataList) {
			super(context, dataList, R.layout.item_image);
		}

		@Override
		public void convert(ViewHolder viewHolder, String item) {
			ImageView imageView = viewHolder.getView(R.id.item_image);
			imageView.setImageResource(R.drawable.qiqiu);
			mImageLoader.loadImage(item, imageView);
			final CheckBox mCheckBox = viewHolder.getView(R.id.item_checkbox);
			final int position = viewHolder.getPosition();
			mCheckBox.setClickable(false);
			viewHolder.setOnClickListener(R.id.checkbox_ll, new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!mCheckBox.isChecked()) {
						if (mSelectMap.size() < 9) {
							mCheckBox.setChecked(true);
							mSelectMap.put(position, true);
						} else {
							Utility.showToast(mContext, "你最多只能同时发送9张图片");
						}
					} else {
						mCheckBox.setChecked(false);
						mSelectMap.delete(position);
					}
					if (mSelectMap.size() > 0) {
						mSendPictureBtn.setClickable(true);
						mSendPictureBtn.setText("发送(" + mSelectMap.size() + "/" + "9)");
					} else {
						mSendPictureBtn.setClickable(true);
						mSendPictureBtn.setText("发送");
					}
				}
			});
		}
		
		/**
		 * 获取选中的图片
		 * 
		 * @return 选中的图片路径集合
		 */
		public List<String> getCheckedPictures() {
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < mSelectMap.size(); i++) {
				list.add(mDataList.get(mSelectMap.keyAt(i)));
			}
			return list;
		}
		
	}
}
