package com.scijoker.popupmanager;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.view.GravityCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by scijoker on 27.03.16.
 */
public class PopupManager {
    public static class ViewHolder {
        private PopupManager panelManager;
        private View popupView;
        private View anchorView;
        private boolean isShowed;
        private Property property;

        public static abstract class Property {
            protected ViewHolder viewHolder;

            public Property() {
            }

            public Property(ViewHolder viewHolder) {
                this.viewHolder = viewHolder;
            }

            public abstract int getPopupViewHeight();

            public abstract int getPopupViewWidth();

            public abstract int getOffsetX();

            public abstract int getOffsetY();
        }

        public ViewHolder(PopupManager manager, View anchorView, View popupView) {
            this(manager, anchorView, popupView, null);
        }

        public ViewHolder(PopupManager manager, View anchorView, View popupView, Property property) {
            this.panelManager = manager;
            this.anchorView = anchorView;
            this.popupView = popupView;
            this.property = property;

            if (property == null) {
                this.property = new DefaultProperty(this);
            }
        }

        public View getAnchorView() {
            return anchorView;
        }

        public View getPopupView() {
            return popupView;
        }

        public boolean isPopupShow() {
            return isShowed;
        }

        public void setShowed(boolean showed) {
            isShowed = showed;
        }

        public void showPopup() {
            panelManager.showPanel(this);
        }

        public void hidePopup() {
            panelManager.hidePanel(this);
        }

        public Property getProperty() {
            return property;
        }

        public void setProperty(Property property) {
            this.property = property;
        }
    }

    private WindowManager windowManager;

    public PopupManager(Context context) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    private void showPanel(ViewHolder viewHolder) {
        viewHolder.setShowed(true);
        IBinder windowToken = viewHolder.getAnchorView().getWindowToken();
        if (windowToken != null) {
            WindowManager.LayoutParams p = createPopupLayout(windowToken);

            p.gravity = Gravity.TOP | GravityCompat.START;
            updateLayoutParamsForPosition(viewHolder, p);
            invokePopup(viewHolder, p);
        }
    }

    private void hidePanel(ViewHolder viewHolder) {
        viewHolder.setShowed(false);
        windowManager.removeViewImmediate(viewHolder.getPopupView());
    }

    private void invokePopup(ViewHolder viewHolder, WindowManager.LayoutParams p) {
        windowManager.addView(viewHolder.getPopupView(), p);
    }

    private WindowManager.LayoutParams createPopupLayout(IBinder token) {
        WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.gravity = Gravity.START | Gravity.TOP;
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.MATCH_PARENT;
        p.format = PixelFormat.TRANSLUCENT;
        p.flags = computeFlags(p.flags);
        p.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        p.token = token;
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
        return p;
    }

    private void updateLayoutParamsForPosition(ViewHolder viewHolder, WindowManager.LayoutParams p) {
        p.x = viewHolder.getProperty().getOffsetX();
        p.y = viewHolder.getProperty().getOffsetY();
        p.width = viewHolder.getProperty().getPopupViewWidth();
        p.height = viewHolder.getProperty().getPopupViewHeight();
    }

    private int computeFlags(int curFlags) {
        curFlags &= ~(
                WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH |
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                        WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        curFlags |= WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES;
        curFlags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        curFlags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        curFlags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        return curFlags;
    }
}
