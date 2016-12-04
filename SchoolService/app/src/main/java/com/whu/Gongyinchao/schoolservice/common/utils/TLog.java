package com.whu.Gongyinchao.schoolservice.common.utils;

import android.util.Log;

/**
 * Created by panfei on 16/4/5.
 */
public class TLog {

    private static final int CHUNK_SIZE = 2000;
    private static boolean isUsed = true;

    public static void e(String tag, String s){
        if (!isUsed) return;

        if (s.length() <= CHUNK_SIZE) {
            Log.e(tag, s);
        } else {
            for (int i = 0; i < s.length(); i += CHUNK_SIZE) {
                int count = Math.min(s.length() - i, CHUNK_SIZE);
                //create a new String with system's default charset (which is UTF-8 for Android)
                Log.e(tag, s.substring(i, i + count));
            }
        }
    }

    public static void i(String tag, String s) {
        if (!isUsed) return;

        if (s.length() <= CHUNK_SIZE) {
            Log.i(tag, s);
        } else {
            for (int i = 0; i < s.length(); i += CHUNK_SIZE) {
                int count = Math.min(s.length() - i, CHUNK_SIZE);
                //create a new String with system's default charset (which is UTF-8 for Android)
                Log.i(tag, s.substring(i, i + count));
            }
        }
    }

}
