package com.game.helper.download.services;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.game.helper.db.DbBtHelper;
import com.game.helper.download.DownLoaderManger;
import com.game.helper.download.bean.FileInfo;
import com.game.helper.util.ToastUtil;
import com.yuan.leopardkit.download.DownLoadManager;

import java.io.File;

/**
 * @Class:
 * @Description:
 * @author: 
 * @Date: 
 */
public class DownloadService extends Service{
	private static final String TAG="TestTag";
	private Long mTaskId;
	private DownloadManager downloadManager;
	private String versionUrl;
	private String versionName;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.i(TAG, "onStart");
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}

}
