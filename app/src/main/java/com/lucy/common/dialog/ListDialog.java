package com.lucy.common.dialog;

import android.content.Context;
import android.util.Log;

import com.lucy.common.R;

import java.util.ArrayList;
import java.util.List;

public class ListDialog extends XDialog {
    private List<String> list = new ArrayList<>();


    public ListDialog(Context context) {
        super(context, R.layout.dialog_loading);
        convert(mViewHolder);
    }

    private void convert(ViewHolder holder) {
        Log.e("TAG", list.size() + "");
    }
}
