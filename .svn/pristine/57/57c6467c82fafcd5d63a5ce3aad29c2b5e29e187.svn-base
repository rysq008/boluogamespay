package com.game.helper.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.RemoteViews;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.WelcomeActivity;
import com.game.helper.activity.mine.MineSetingActivity;
import com.game.helper.interfaces.comm.CommValues;
import com.game.helper.services.ui.MyProgressBar;
import com.game.helper.services.ui.MyUpdateDailog;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.ToastUtil;

@SuppressWarnings("deprecation")
public class UpgradeService extends IntentService implements CommValues {


	private static final int ACTION_DS = 0;
	private static final int ACTION_SP = 7;

	private static final int ACTION_UPGRADE = 1;
	private static final int ACTION_UPGRADE_FINISHED = 2;
	private static final int ACTION_ACTION_UPDATA_ERROR = 3;
	private static final int ACTION_FAIL_CONNECTED = 5;
	private static final int ACTION_SERVER_ERROR = 6;

	private static String ACTION_INSTALL = "application/vnd.android.package-archive";
	private final String SHA_DOWNLOAD_POS = "download";
	private final String SHA_NEW_SIZE = "new_download_size";
	private final String HEAD_range = "range";
	private Notification notify;
	private NotificationManager mNotifyMgr;

	private String url = "";
	private static String version;
	private String newName = "";
	private int borkenLength;
	public Context context;
	// 下载进度条
	private MyProgressBar progressBar;
	private	MyUpdateDailog dialog;

	public UpgradeService() {
		super("upgrade");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		if(SystemUtil.isActRunning(context, WelcomeActivity.class.getName())){
			dialog = new MyUpdateDailog(WelcomeActivity.instance);
			dialog.setCancelable(BaseApplication.mInstance.isCancelable);
			progressBar = dialog.getProgressBar();
			progressBar.setMax(100);
			progressBar.setProgress(0);
		}else if(SystemUtil.isActRunning(context, MineSetingActivity.class.getName())){
			dialog = new MyUpdateDailog(MineSetingActivity.instance);
			dialog.setCancelable(BaseApplication.mInstance.isCancelable);
			progressBar = dialog.getProgressBar();
			progressBar.setMax(100);
			progressBar.setProgress(0);
		}
		mNotifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stop();
		notify = null;
		mNotifyMgr = null;
		version = "";
	}

	public UpgradeService(String name) {
		super(name);
	}

	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ACTION_DS:
				if(dialog!=null){
					dialog.show();
				}
				break;
			case	ACTION_SP:
				if(dialog!=null&&dialog.isShowing()){
					dialog.dismiss();
				}
				break;
			case ACTION_UPGRADE:
				int downloadProgress =   (int) ((msg.arg1 * 1.0f / msg.arg2) * 100);
				if(downloadProgress>100) downloadProgress = 99;//超过100，则处理为99
				if (progressBar != null) {
					progressBar.setMax( msg.arg2);
					progressBar.setProgress(msg.arg1);
					if(downloadProgress>=100){
						progressBar.setVisibility(View.GONE);
						if(dialog!=null&&dialog.isShowing()){
							dialog.dismiss();
						}
					}
				} 
				updateDownloadNotify(msg.arg1, msg.arg2);

				break;
			case ACTION_UPGRADE_FINISHED:
				//				put(SHA_DOWNLOAD_POS, -1);// 下载完成后把标记断点记为-1
				if(WelcomeActivity.instance!=null){
					//	WelcomeActivity.instance.dissRunningDialog();
				}
				if(SystemUtil.isActRunning(context, WelcomeActivity.class.getName())){
					mHandler.sendEmptyMessage(ACTION_SP);
				}
				downloadCompleteNotify();

				break;
			case ACTION_ACTION_UPDATA_ERROR:
				if(WelcomeActivity.instance!=null){
					//WelcomeActivity.instance.dissRunningDialog();
				}
				ToastUtil.showToast(context, R.string.upgrade_fail);
				stop();
				break;
			case ACTION_FAIL_CONNECTED:
				if(WelcomeActivity.instance!=null){
					//WelcomeActivity.instance.dissRunningDialog();
				}
				ToastUtil.showToast(context, R.string.link_fail);
				stop();
				break;
			case ACTION_SERVER_ERROR:
				if(WelcomeActivity.instance!=null){
					//WelcomeActivity.instance.dissRunningDialog();
				}
				ToastUtil.showToast(context, R.string.request_fail);
				stop();
				break;
			default:
				break;
			}
		}
	};

	public String getFielName(String url, String version) {
		return url.substring(url.lastIndexOf("/") + 1) + System.currentTimeMillis() + ".apk";
	}
	//	
	//	public int getBrokenPos(String version) {
	//		return SharedPreUtil.getIntValue(context, SHA_DOWNLOAD_POS + version,-1);
	//	}
	//	
	//	public void putBrokenPos(String version,int pos) {
	//		SharedPreUtil.putIntValue(context, SHA_DOWNLOAD_POS + version,pos);
	//	}
	//	
	//	public void put(String tag, int curlength) {
	//		SharedPreUtil.putIntValue(context, tag + version, curlength);
	//	}



	//开始下载
	public void startDownload(String version){

		mHandler.sendEmptyMessage(ACTION_DS);
		showNofity();
		//		borkenLength = getBrokenPos(version);
		//		if (borkenLength != -1 && borkenLength > 0) {
		//			downLoadAnd(version);
		//		} else {
		downLoadAll(version);
		//		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {

			url = intent.getStringExtra(INTENT_URL);
			String newVersion = intent.getStringExtra(INTENT_VERSION);

			if (newVersion.equals(version)) {//记录当前下载的版本号，如果存在则跳过
				return;
			}
			version = newVersion;
			newName = getFielName(url, newVersion);

			if (TextUtils.isEmpty(url)) {
				mHandler.sendEmptyMessage(ACTION_FAIL_CONNECTED);
				return;
			}else{
				startDownload(newVersion);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//			putBrokenPos(version, -1);
			mHandler.sendEmptyMessage(ACTION_FAIL_CONNECTED);
		}  
		return;
	}



	/** 断点续传 */
	public void downLoadAnd(String version) {

		//		RandomAccessFile fos = null;
		//		BufferedInputStream bis = null;
		//		int fileSize = 0;
		//		try {
		//			HttpURLConnection con = null;
		//			URL uurl = new URL(url);
		//			
		//			int newSize = getBrokenPos(version);
		//			if (newSize != -1 && newSize > 0) {
		//				fileSize = newSize;
		//			} else {
		//				URLConnection tmp = uurl.openConnection();
		//				fileSize = tmp.getContentLength();
		//				tmp = null;
		//			}
		//			if (isExsit(fileSize)) {
		//				return;
		//			}
		//			
		//			con = (HttpURLConnection) uurl.openConnection();
		//			// 设置当前线程下载的起�?
		//			con.addRequestProperty(HEAD_range, "bytes=" + borkenLength + "-");
		//			DebugUtil.printDebugInfo("updata service responseCode==="+ con.getResponseCode());
		//			// 使用java中的RandomAccessFile 对文件进行随机读写操�?
		//			fos = new RandomAccessFile(UPDATA_PATH + newName, "rw");
		//			// 设置�?��写文件的位置
		//			fos.seek(borkenLength);
		//			bis = new BufferedInputStream(con.getInputStream());
		//
		//			byte[] buffer = new byte[1024];
		//
		//			int len = 0;
		//			int time = 0;
		//			int loadedLength = 0;
		//			while ((len = bis.read(buffer)) != -1) {
		//				loadedLength += len;
		//				if (time++ % 100 == 0) {
		//					Message msg = mHandler.obtainMessage(ACTION_UPGRADE,loadedLength + borkenLength, fileSize);
		//					mHandler.sendMessage(msg);
		//				}
		//				fos.write(buffer, 0, len);
		//				put(SHA_DOWNLOAD_POS, loadedLength + borkenLength);
		//			}
		//			
		//			Message msg = mHandler.obtainMessage(ACTION_UPGRADE_FINISHED,fileSize, fileSize);
		//			mHandler.sendMessage(msg);
		//
		//		} catch (Exception e) {
		//			e.printStackTrace();
		////			mHandler.sendEmptyMessage(ACTION_FAIL_CONNECTED);
		//			putBrokenPos(version, -1);
		//			downLoadAll(version);
		//		} finally {
		//			if (null != fos) {
		//				try {
		//					fos.close();
		//				} catch (IOException e) {
		//					e.printStackTrace();
		//					mHandler.sendEmptyMessage(ACTION_ACTION_UPDATA_ERROR);
		//				}
		//			}
		//			if (bis != null) {
		//				try {
		//					bis.close();
		//				} catch (IOException e) {
		//					e.printStackTrace();
		//					mHandler.sendEmptyMessage(ACTION_ACTION_UPDATA_ERROR);
		//				}
		//			}
		//			put(newName, -1);
		//		}
	}

	/** 重新完整的下载一个apk */
	public void downLoadAll(String version) {
		FileOutputStream fos = null;
		InputStream is = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(url);
			HttpResponse response = null;
			response = httpClient.execute(getMethod);
			HttpEntity httpEntity = response.getEntity();
			int fileLength = (int) httpEntity.getContentLength();
			//			put(SHA_NEW_SIZE, fileLength);
			//			
			//			if (isExsit(fileLength)) {
			//				return;
			//			}
			if (httpEntity != null) {
				is = httpEntity.getContent();
				int status = response.getStatusLine().getStatusCode();
				if (status != HttpStatus.SC_OK) {
					mHandler.sendEmptyMessage(ACTION_SERVER_ERROR);
					return;
				}
			}
			File file = new File(UPDATA_PATH + newName);
			File file2 = file.getParentFile();
			try {
				if (!file2.exists()) {
					file2.mkdirs();
					file.createNewFile();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				mHandler.sendEmptyMessage(ACTION_FAIL_CONNECTED);
				return;
			}
			fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int times = 0;
			int len = 0;
			int loadedLength = 0;
			while ((len = is.read(buffer)) != -1) {
				loadedLength += len;
				if (times++ % 100 == 0) {
					Message msg = mHandler.obtainMessage(ACTION_UPGRADE,loadedLength, fileLength);
					mHandler.sendMessage(msg);
				}
				fos.write(buffer, 0, len);
				//				put(SHA_DOWNLOAD_POS, loadedLength);
			}
			Message msg = mHandler.obtainMessage(ACTION_UPGRADE_FINISHED,fileLength, fileLength);
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
			mHandler.sendEmptyMessage(ACTION_ACTION_UPDATA_ERROR);
			//			put(newName, -1);
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					mHandler.sendEmptyMessage(ACTION_ACTION_UPDATA_ERROR);
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					mHandler.sendEmptyMessage(ACTION_ACTION_UPDATA_ERROR);
				}
			}
		}
	}


	/** 下载完成 */
	private void downloadCompleteNotify() {
		try {
			notify = new Notification(R.drawable.logo,getString(R.string.downloaded), System.currentTimeMillis());
			RemoteViews view = new RemoteViews(getPackageName(),R.layout.notify_upgrade);
			//更新view
			view.setTextViewText(R.id.textprogress, getString(R.string.app_name));
			view.setTextColor(R.id.textprogress,  context.getResources().getColor(R.color.light_blue));
			view.setViewVisibility(R.id.progressBar1, View.INVISIBLE);
			view.setTextViewText(R.id.download_complete,getString(R.string.install));
			view.setProgressBar(R.id.progressBar1, 100, 100, false);
			view.setImageViewResource(R.id.image, R.drawable.logo);

			notify.contentView = view;

			Intent intent = new Intent(Intent.ACTION_VIEW);
			Uri data = Uri.fromFile(new File(UPDATA_PATH + newName));
			intent.setDataAndType(data, ACTION_INSTALL);
			PendingIntent ci = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
			notify.flags |= Notification.FLAG_AUTO_CANCEL;
			notify.contentIntent = ci;
			mNotifyMgr.notify(R.drawable.logo, notify);

			intentInstall();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 更新notify */
	private void updateDownloadNotify(int progress, int fileLength) {
		try {
			RemoteViews view;
			if (notify == null) {
				notify = new Notification(R.drawable.logo,getString(R.string.upgrade), System.currentTimeMillis());
				view = new RemoteViews(getPackageName(), R.layout.notify_upgrade);
				view.setViewVisibility(R.id.progressBar1, View.VISIBLE);
				view.setImageViewResource(R.id.image, R.drawable.logo);
				notify.contentView = view;
			} else {
				view = notify.contentView;
			}
			view.setTextViewText(R.id.download_complete, "");
			int downloadProgress =   (int) ((progress * 1.0f / fileLength) * 100);
			if(downloadProgress>100) downloadProgress = 99;//超过100，则处理为99

			view.setTextViewText(R.id.textprogress, getString(R.string.app_name)+ "(" + downloadProgress+ "%)");
			view.setTextColor(R.id.textprogress,  context.getResources().getColor(R.color.light_blue));
			view.setProgressBar(R.id.progressBar1, fileLength, progress, false);

			mNotifyMgr.notify(R.drawable.logo, notify);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 准备升级notify */
	private void showNofity() {
		try {
			notify =getNotify(R.string.upgrade);
			RemoteViews view = new RemoteViews(getPackageName(),R.layout.notify_upgrade);
			view.setTextViewText(R.id.textprogress, getString(R.string.download_ready));
			view.setTextColor(R.id.textprogress,  context.getResources().getColor(R.color.light_blue));
			view.setTextViewText(R.id.download_complete, "");
			view.setProgressBar(R.id.progressBar1, 100, 0, false);
			view.setViewVisibility(R.id.progressBar1, View.VISIBLE);
			view.setImageViewResource(R.id.image, R.drawable.logo);

			notify.contentView = view;
			//Intent intent = new Intent(Intent.ACTION_MAIN);
			//intent.addCategory(Intent.CATEGORY_LAUNCHER);
			//intent.setClass(this, WelcomeActivity.class);
			//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

			PendingIntent ci = PendingIntent.getActivity(this, 0, null,PendingIntent.FLAG_UPDATE_CURRENT);
			notify.flags |= Notification.FLAG_AUTO_CANCEL;
			notify.contentIntent = ci;
			mNotifyMgr.notify(R.drawable.logo, notify);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Notification getNotify(int title) {
		return new Notification(R.drawable.logo, getString(title),System.currentTimeMillis());
	}

	public void intentInstall(){
		
		// 下载完成后弹出安装窗
		File file = new File(UPDATA_PATH + newName);
		Intent intentInstall = new Intent();
		intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intentInstall.setAction(Intent.ACTION_VIEW);
		intentInstall.setDataAndType(Uri.fromFile(file), ACTION_INSTALL);
		startActivity(intentInstall);

		if(BaseApplication.mInstance.isCancelable==false){
			if(BaseActivity.activityList != null){
				for(Activity activity :BaseActivity.activityList){
					activity.finish();
				}
			}
			android.os.Process.killProcess(android.os.Process.myPid());
		}else{
		}
		//SharedPreUtil.putIntValue(this, "isFirstGuide", 0);
		
	}

	// 判断本地文件的size 与服务器同名版本size是否相等
	public boolean isExsit(int netApkSize) {
		File file = new File(UPDATA_PATH + newName);
		if (file.exists()) {
			if (netApkSize == file.length()) {
				mHandler.sendEmptyMessage(ACTION_UPGRADE_FINISHED);
				return true;
			}
		}
		return false;
	}
	public void stop() {
		mHandler.sendEmptyMessage(ACTION_SP);
		mNotifyMgr.cancelAll();
		UpgradeService.this.stopSelf();
	}

}
