package com.cms.imagepicker.utils;

import android.content.Context;

/**
 * Created by WD on 2017/7/17.
 */

public class GridDividerItemDecoration extends AbsDividerItemDecoration {
    private int mSpanCount;

    public GridDividerItemDecoration(Context context, int spanCount) {
        super(context, 2, 0x00000000);
        mSpanCount = spanCount;
    }

    public GridDividerItemDecoration(Context context, float dividerSize, int dividerColor, int spanCount) {
        super(context, dividerSize, dividerColor);
        mSpanCount = spanCount;
    }

    @Override
    public boolean[] getItemSidesIsHaveOffsets(int itemPosition) {
        if (itemPosition % mSpanCount != 0) {
            return new boolean[]{false, false, true, true};
        } else {
            return new boolean[]{false, false, false, true};
        }
    }
}
