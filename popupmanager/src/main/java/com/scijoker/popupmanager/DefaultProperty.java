package com.scijoker.popupmanager;

import android.view.ViewGroup;

/**
 * Created by scijoker on 27.03.16.
 */
public class DefaultProperty extends PopupManager.ViewHolder.Property {
    public DefaultProperty(PopupManager.ViewHolder viewHolder) {
        super(viewHolder);
    }

    public int getPopupViewHeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    public int getPopupViewWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    public int getOffsetX() {
        return viewHolder.getAnchorView().getLeft();
    }

    public int getOffsetY() {
        int[] mDrawingLocation = new int[2];
        viewHolder.getAnchorView().getLocationOnScreen(mDrawingLocation);
        return mDrawingLocation[1] + (getPopupViewHeight())+viewHolder.getAnchorView().getMeasuredHeight();
    }
}
