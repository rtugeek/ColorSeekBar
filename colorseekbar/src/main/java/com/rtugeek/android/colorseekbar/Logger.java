package com.rtugeek.android.colorseekbar;

import android.util.Log;
import android.view.View;

/**
 * Created by Jack on 2016/12/5.
 * Email:rtugeek@gmail.com
 */

class Logger {
    private static boolean debug = false;
    private static final String TAG ="ColorSeekBarLib";
    public static void i(String s){
        if(debug) Log.i(TAG,s);
    }
    public static void spec(int spec){
        if(debug){
            switch (spec){
                case View.MeasureSpec.AT_MOST:
                    Log.i(TAG,"AT_MOST");
                    break;
                case View.MeasureSpec.EXACTLY:
                    Log.i(TAG,"EXACTLY");
                    break;
                case View.MeasureSpec.UNSPECIFIED:
                    Log.i(TAG,"UNSPECIFIED");
                    break;
                default:
                    Log.i(TAG,String.valueOf(spec));
                    break;
            }
        }
    }
}
