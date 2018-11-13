package com.lucy.common.dialog;

import com.lucy.common.R;

public class Loading extends XDialogFragment {

    public Loading() {
        super();
        mDialogParams.cancelable = false;
        mDialogParams.canceledOnTouchOutside = false;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.dialog_loading;
    }

}
