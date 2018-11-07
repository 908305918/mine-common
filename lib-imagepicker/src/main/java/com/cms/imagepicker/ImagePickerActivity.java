package com.cms.imagepicker;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.cms.imagepicker.adapter.ImageAdapter;
import com.cms.imagepicker.utils.GridDividerItemDecoration;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by WD on 2017/7/17.
 */

public class ImagePickerActivity extends AppCompatActivity {
    private List<String> mAllImageList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private Button mSendImageBut;
    private ImageAdapter mImageAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_image_picker);
        initViews();
        final RxPermissions rxPermissions = new RxPermissions(this);
        boolean isGranted = rxPermissions.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (isGranted) {
            scanImages();
        } else {
            Disposable d = rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) throws Exception {
                            if (granted) {
                                scanImages();
                            } else {
                                Toast.makeText(ImagePickerActivity.this, "请打开读取SD权限", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

    private void initViews() {
        mRecyclerView = this.findViewById(R.id.rv_picture);
        mSendImageBut = this.findViewById(R.id.btn_send_image);
        mImageAdapter = new ImageAdapter(this, mAllImageList, mSendImageBut);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(this, 3));
        mRecyclerView.setAdapter(mImageAdapter);
    }


    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描。
     */
    private void scanImages() {
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
                ContentResolver mContentResolver = ImagePickerActivity.this.getContentResolver();
                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{
                                "image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED + " DESC");
                if (mCursor == null || mCursor.getCount() == 0) {
                    // 扫描图片失败
                    mProgressDialog.dismiss();
                    Toast.makeText(ImagePickerActivity.this, "sdcard尚未准备好", Toast.LENGTH_SHORT).show();
                } else {
                    while (mCursor.moveToNext()) {
                        // 获取图片的路径
                        String path = mCursor.getString(mCursor
                                .getColumnIndex(MediaStore.Images.Media.DATA));
                        mAllImageList.add(path);
                    }
                    mCursor.close();
                    // 扫描图片完成
                    mProgressDialog.dismiss();
                    mImageAdapter.notifyDataSetChanged();
                }
            }
        }).start();
    }
}
