package com.lucy.common.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public abstract class XDialogFragment extends DialogFragment {
    protected XDialogParams mDialogParams;

    public XDialogFragment() {
        mDialogParams = new XDialogParams();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        if (getLayoutRes() > 0) {
            view = inflater.inflate(getLayoutRes(), container, false);
            convert(ViewHolder.get(view));
        }
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.setCancelable(mDialogParams.cancelable);
        Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(mDialogParams.canceledOnTouchOutside);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public void show(FragmentManager manager) {
        super.show(manager, mDialogParams.fragmentTag);
    }

    public XDialogParams getDialogParams() {
        return mDialogParams;
    }

    protected void convert(ViewHolder holder) {

    }

    protected abstract int getLayoutRes();

}
