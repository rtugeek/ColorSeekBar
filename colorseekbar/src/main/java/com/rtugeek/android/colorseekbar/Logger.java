package com.rtugeek.android.colorseekbar;

import android.util.Log;

/**
 * Created by Leon on 2016/12/5.
 * Email:rtugeek@gmail.com
 */

class Logger {
    private static boolean debug = false;
    private static final String TAG ="ColorSeekBarLib";
    public static void i(String s){
        if(debug) Log.i(TAG,s);
    }
}
