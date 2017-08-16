package com.example.Util;

import android.util.Log;

public class ClickFilter {
	public static final long INTERVAL = 300L; // 防止连续点击的时间间隔
	private static long lastClickTime = 0L; // 上一次点击的时间

	public static boolean filter() {
		long time = System.currentTimeMillis();
		if ((time - lastClickTime) > INTERVAL) {
			lastClickTime = time;
			return true;
		} else {
			lastClickTime = time;
			return false;
		}
	}
}