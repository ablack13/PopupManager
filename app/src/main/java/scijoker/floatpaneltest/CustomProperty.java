package scijoker.floatpaneltest;

import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

import com.scijoker.popupmanager.PopupManager;

/**
 * Created by scijoker on 27.03.16.
 */
public class CustomProperty extends PopupManager.ViewHolder.Property {
    @Override
    public int getPopupViewHeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int getPopupViewWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int getOffsetX() {
        return 200;
    }

    @Override
    public int getOffsetY() {
        return 400;
    }

    @Override
    public ViewPropertyAnimator showPopupAnimation() {
        return null;
    }

    @Override
    public ViewPropertyAnimator hidePopupAnimation() {
        return null;
    }
}
