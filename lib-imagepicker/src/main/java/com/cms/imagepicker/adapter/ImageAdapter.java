package com.cms.imagepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cms.imagepicker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WD on 2017/7/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mDataList;
    private SparseBooleanArray mCheckedMap = new SparseBooleanArray(); //用来存储图片的选中情况
    private Button mSendImageBut;


    public ImageAdapter(Context context, List<String> dataList, Button sendImageBtn) {
        mContext = context;
        mDataList = dataList;
        mSendImageBut = sendImageBtn;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(
                R.layout.item_image, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ImageAdapter.ViewHolder holder, final int position) {
        String url = mDataList.get(position);
        Glide.with(mContext).load(url).into(holder.imageView);
        final CheckBox checkBox = holder.checkBox;
        checkBox.setChecked(mCheckedMap.get(position));
        holder.checkBoxLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkBox.isChecked()) {
                    if (mCheckedMap.size() < 9) {
                        checkBox.setChecked(true);
                        mCheckedMap.put(position, true);
                    } else {
                        Toast.makeText(mContext, "你最多只能同时发送9张图片", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    checkBox.setChecked(false);
                    mCheckedMap.delete(position);
                }
                if (mCheckedMap.size() > 0) {
                    mSendImageBut.setClickable(true);
                    mSendImageBut.setText("发送(" + mCheckedMap.size() + "/" + "9)");
                } else {
                    mSendImageBut.setClickable(true);
                    mSendImageBut.setText("发送");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }


    /**
     * 获取选中的图片
     *
     * @return 选中的图片路径集合
     */
    public List<String> getCheckedImages() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < mCheckedMap.size(); i++) {
            list.add(mDataList.get(mCheckedMap.keyAt(i)));
        }
        return list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        CheckBox checkBox;
        LinearLayout checkBoxLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_picture);
            checkBox = (CheckBox) itemView.findViewById(R.id.item_checkbox);
            checkBoxLayout = (LinearLayout) itemView.findViewById(R.id.ll_checkbox);
        }
    }
}
