package com.game.helper.download.downloador;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.download.bean.DownloadInfo;
import com.game.helper.download.utils.DownloadUtils;
import com.game.helper.muldownload.utils.AppUtil;
import com.game.helper.muldownload.utils.FileUtil;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.DownloadFinishTask;
import com.game.helper.sdk.model.returns.LoginData;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Class: Downloador
 * @Description: 任务下载器
 * @author: 
 * @Date: 
 */
public class Downloador {
	public static final String TAG = "Downloador";
	private static final int THREAD_POOL_SIZE = 9;  //线程池大小为9
	private static final int THREAD_NUM = 3;  //每个文件3个线程下载
	private static final int GET_LENGTH_SUCCESS = 1;
	public static final Executor THREAD_POOL_EXECUTOR = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

	private List<DownloadTask>  tasks = new ArrayList<DownloadTask>();
	private InnerHandler handler = new InnerHandler();

	private AppContent appContent; //待下载的应用
	private long downloadLength; //下载过程中记录已下载大小
	private long fileLength;
	private Context context;
	private String downloadPath;

	public Downloador(Context context, AppContent appContent) {
		this.context = context;
		this.appContent = appContent;
		this.downloadPath = FileUtil.getDownloadDir(AppUtil.getContext());
	}

	/**
	 * 开始下载
	 */
	public void download() {
		if(TextUtils.isEmpty(downloadPath)) {
			Toast.makeText(context, "未找到SD卡", Toast.LENGTH_SHORT).show();
			return;
		}
		if(appContent == null||TextUtils.isEmpty(appContent.downloadPath)) {
			Toast.makeText(context, "游戏下载地址不存在", Toast.LENGTH_SHORT).show();
			return;
			// throw new IllegalArgumentException("download content can not be null");
		}
		new Thread() {
			@Override
			public void run() {
				URL url;
				URI uri=null;
				try {
					url = new URL(appContent.downloadPath.trim()); // 把你需要的地址放在这里，此段代码会帮你编码
					try {
						uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
						//获取文件大小
						HttpClient client = new DefaultHttpClient();
						HttpGet request = new HttpGet(appContent.downloadPath.trim());
						HttpResponse response = null;
						try {
							response = client.execute(request);
							fileLength = response.getEntity().getContentLength();
						} catch (Exception e) {
							e.printStackTrace();
							Log.e(TAG, e.getMessage());
						} finally {
							if (request != null) {
								request.abort();
							}
						}
						//计算出该文件已经下载的总长度
						List<DownloadInfo> lists = DBManager.getInstance(context.getApplicationContext())
								.getDownloadInfosByUrl(appContent.downloadPath.trim());
						for (DownloadInfo info : lists) {
							downloadLength += info.getDownloadLength();
						}
						
						//插入文件下载记录到数据库
						DBManager.getInstance(context.getApplicationContext()).insertDownloadFile(appContent);
						Message.obtain(handler, GET_LENGTH_SUCCESS).sendToTarget();
					
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 开始创建AsyncTask下载
	 */
	private void beginDownload() {
		Log.e(TAG, "beginDownload" + appContent.downloadPath.trim()+"beginDownload");
		appContent.status=(AppContent.Status.DOWNLOADING);
		long blockLength = fileLength / THREAD_NUM;
		for (int i = 0; i < THREAD_NUM; i++) {
			long beginPosition = i * blockLength;//每条线程下载的开始位置
			long endPosition = (i + 1) * blockLength;//每条线程下载的结束位置
			if (i == (THREAD_NUM - 1)) {
				endPosition = fileLength;//如果整个文件的大小不为线程个数的整数倍，则最后一个线程的结束位置即为文件的总长度
			}
			DownloadTask task = new DownloadTask(i, beginPosition, endPosition, this, context);
			task.executeOnExecutor(THREAD_POOL_EXECUTOR, appContent.downloadPath.trim(), appContent.packageName);
			if(tasks == null) {
				tasks = new ArrayList<DownloadTask>();
			}
			tasks.add(task);
		}
	}

	/**
	 * 暂停下载
	 */
	public void pause() {
		for (DownloadTask task : tasks) {
			if (task != null && (task.getStatus() == AsyncTask.Status.RUNNING || !task.isCancelled())) {
				task.cancel(true);
			}
		}
		tasks.clear();
		appContent.status=(AppContent.Status.PAUSED);
		DBManager.getInstance(context.getApplicationContext()).updateDownloadFile(appContent);
	}
	/**
	 * 安装
	 */
	public void Installed() {
		for (DownloadTask task : tasks) {
			if (task != null && (task.getStatus() == AsyncTask.Status.RUNNING || !task.isCancelled())) {
				task.cancel(true);
			}
		}
		tasks.clear();
		intentInstall();
		/*appContent.status=(AppContent.Status.AlreadyInstalled);
		DBManager.getInstance(context.getApplicationContext()).updateDownloadFile(appContent);*/
	}
	private static String ACTION_INSTALL = "application/vnd.android.package-archive";
	public void intentInstall(){
		// 下载完成后弹出安装窗
		File file = new File(downloadPath + File.separator + appContent.gameName+ ".apk");
//		Intent intentInstall = new Intent();
//		intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		intentInstall.setAction(Intent.ACTION_VIEW);
//		intentInstall.setDataAndType(Uri.fromFile(file), ACTION_INSTALL);
//		context.startActivity(intentInstall);
        com.game.helper.util.FileUtil.apkInstall(file,context);
	}
	/**
	 * 将已下载大小归零
	 */
	protected synchronized void resetDownloadLength() {
		this.downloadLength = 0;
	}

	/**
	 * 添加已下载大小
	 * 多线程访问需加锁
	 * @param size
	 */
	protected synchronized void updateDownloadLength(long size){
		this.downloadLength += size;
		//通知更新界面
		int percent = (int)((float)downloadLength * 100 / (float)fileLength);
		appContent.downloadPercent=(percent);
		if((percent>0&&percent<100)&&appContent.status == AppContent.Status.PENDING) {
			appContent.status=(AppContent.Status.DOWNLOADING);
		}else  if((percent>0&&percent<100)&&appContent.status == AppContent.Status.PAUSED) {
			appContent.status=(AppContent.Status.DOWNLOADING);
		}
		if(percent == 100 || downloadLength == fileLength) {
			appContent.downloadPercent=(100); //上面计算有时候会有点误差，算到percent=99
			appContent.status=(AppContent.Status.FINISHED);
			LoginData user=DBManager.getInstance(context).getUserMessage();
			new DownloadFinishTask(context, user.userId, appContent.gameId, new Back() {
				
				@Override
				public void success(Object object, String msg) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void fail(String status, String msg, Object object) {
					// TODO Auto-generated method stub
					
				}
			}).start();
		}
		DBManager.getInstance(context.getApplicationContext()).updateDownloadFile(appContent);
		Intent intent = new Intent(Constants.DOWNLOAD_MSG);
		Bundle bundle = new Bundle();
		bundle.putParcelable("appContent", appContent);
		intent.putExtras(bundle);
		context.sendBroadcast(intent);
	}

	protected String getDownloadPath() {
		return downloadPath;
	}

	private class InnerHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_LENGTH_SUCCESS :
				beginDownload();
				break;
			}
			super.handleMessage(msg);
		}
	}
}
