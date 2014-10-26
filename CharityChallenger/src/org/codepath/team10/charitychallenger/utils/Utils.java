package org.codepath.team10.charitychallenger.utils;

import org.codepath.team10.charitychallenger.R;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by kemo on 10/21/14.
 */
public class Utils {
    private static int sTheme;

    public final static int THEME_HOLO_LIGHT = 0;
    public final static int THEME_CC_THEME1 = 1;
    public final static int THEME_CC_THEME2 = 2;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME_CC_THEME1:
                activity.setTheme(R.style.cctheme1);
                break;
            case THEME_CC_THEME2:
                activity.setTheme(R.style.cctheme2);
                break;
        }
    }
}