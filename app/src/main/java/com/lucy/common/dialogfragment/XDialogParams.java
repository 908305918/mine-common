package com.lucy.common.dialogfragment;

public class XDialogParams {

    String fragmentTag = "XDialogFragment";
    boolean cancelable = true;
    boolean canceledOnTouchOutside = true;

    public XDialogParams setCancelable(boolean cancel) {
        this.cancelable = cancel;
        return this;
    }

    public XDialogParams setCanceledOnTouchOutside(boolean cancel) {
        this.canceledOnTouchOutside = cancel;
        return this;
    }

    public XDialogParams setFragmentTag(String tag) {
        this.fragmentTag = tag;
        return this;
    }
}
