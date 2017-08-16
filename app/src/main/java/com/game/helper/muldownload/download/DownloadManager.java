package com.game.helper.muldownload.download;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.game.helper.BaseApplication;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.interfaces.comm.CommValues;
import com.game.helper.muldownload.bean.DownloadInfo;
import com.game.helper.muldownload.utils.AppUtil;
import com.game.helper.muldownload.utils.FileUtil;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.DownloadFinishTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

public class DownloadManager implements CommValues{
	public static final int STATE_NONE = 0;
	/** 等待中 */
	public static final int STATE_WAITING = 1;
	/** 下载中 */
	public static final int STATE_DOWNLOADING = 2;
	/** 暂停 */
	public static final int STATE_PAUSED = 3;
	/** 下载完毕 */
	public static final int STATE_DOWNLOADED = 4;
	/** 下载失败 */
	public static final int STATE_ERROR = 5;

	// public static final int STATE_READ = 6;

	private static DownloadManager instance;

	private DownloadManager() {
	}

	/** 用于记录下载信息，如果是正式项目，需要持久化保存 */
	private Map<Long, DownloadInfo> mDownloadMap = new ConcurrentHashMap<Long, DownloadInfo>();
	/** 用于记录观察者，当信息发送了改变，需要通知他们 */
	private List<DownloadObserver> mObservers = new ArrayList<DownloadObserver>();
	/** 用于记录所有下载的任务，方便在取消下载时，通过id能找到该任务进行删除 */
	private Map<Long, DownloadTask> mTaskMap = new ConcurrentHashMap<Long, DownloadTask>();

	public static synchronized DownloadManager getInstance() {
		if (instance == null) {
			instance = new DownloadManager();
		}
		return instance;
	}

	/** 注册观察者 */
	public void registerObserver(DownloadObserver observer) {
		synchronized (mObservers) {
			if (!mObservers.contains(observer)) {
				mObservers.add(observer);
			}
		}
	}

	/** 反注册观察者 */
	public void unRegisterObserver(DownloadObserver observer) {
		synchronized (mObservers) {
			if (mObservers.contains(observer)) {
				mObservers.remove(observer);
			}
		}
	}

	/** 当下载状态发送改变的时候回调 */
	public void notifyDownloadStateChanged(DownloadInfo info) {
		synchronized (mObservers) {
			for (DownloadObserver observer : mObservers) {
				observer.onDownloadStateChanged(info);
			}
		}
	}

	/** 当下载进度发送改变的时候回调 */
	public void notifyDownloadProgressed(DownloadInfo info) {
		synchronized (mObservers) {
			for (DownloadObserver observer : mObservers) {
				observer.onDownloadProgressed(info);
			}
		}
	}

	/** 下载，需要传入一个appInfo对象 */
	public synchronized void download(AppContent appInfo) {
		// 先判断是否有这个app的下载信息
		System.out.println("DM-------92");
		if(appInfo==null||TextUtils.isEmpty(appInfo.gameId)){
			ToastUtil.showToast(BaseApplication.mInstance.context, "游戏不存在");
			return;
		}
		DownloadInfo info = mDownloadMap.get(Long.parseLong(appInfo.gameId));
		if (info == null) {// 如果没有，则根据appInfo创建一个新的下载信息
			info = DownloadInfo.clone(appInfo);
			mDownloadMap.put(Long.parseLong(appInfo.gameId), info);
			System.out.println("DM-------97");
		}
		System.out.println("DM-------99");
		// 判断状态是否为STATE_NONE、STATE_PAUSED、STATE_ERROR。只有这3种状态才能进行下载，其他状态不予处理
		if (info.getDownloadState() == STATE_NONE
				|| info.getDownloadState() == STATE_PAUSED
				|| info.getDownloadState() == STATE_ERROR|| info.getDownloadState() == STATE_WAITING) {
			System.out.println("DM-------102");
			// 下载之前，把状态设置为STATE_WAITING，因为此时并没有产开始下载，只是把任务放入了线程池中，当任务真正开始执行时，才会改为STATE_DOWNLOADING
			info.setDownloadState(STATE_WAITING);
			notifyDownloadStateChanged(info);// 每次状态发生改变，都需要回调该方法通知所有观察者
			DownloadTask task = new DownloadTask(info);// 创建一个下载任务，放入线程池
			mTaskMap.put(Long.parseLong(appInfo.gameId), task);
			ThreadManager.getDownloadPool().execute(task);
		}
	}

	/** 暂停下载 */
	public synchronized void pause(AppContent appInfo) {
		if(appInfo==null||TextUtils.isEmpty(appInfo.gameId)){
			ToastUtil.showToast(BaseApplication.mInstance.context, "游戏不存在");
			return;
		}
		stopDownload(appInfo);
		DownloadInfo info = mDownloadMap.get(Long.parseLong(appInfo.gameId));// 找出下载信息
		if (info != null) {// 修改下载状态
			info.setDownloadState(STATE_PAUSED);
			notifyDownloadStateChanged(info);
		}
	}

	/** 取消下载，逻辑和暂停类似，只是需要删除已下载的文件 */
	public synchronized void detele(AppContent appInfo) {
		if(appInfo==null||TextUtils.isEmpty(appInfo.gameId)){
			ToastUtil.showToast(BaseApplication.mInstance.context, "游戏不存在");
			return;
		}
		stopDownload(appInfo);
		DownloadInfo info = mDownloadMap.get(Long.parseLong(appInfo.gameId));// 找出下载信息
		if (info != null) {// 修改下载状态并删除文件
			info.setDownloadState(STATE_PAUSED);
			notifyDownloadStateChanged(info);
			info.setCurrentSize(0);
			File file = new File(info.getPath());
			file.delete();
			mDownloadMap.remove(Long.parseLong(appInfo.gameId));
		}
	}

	/** 安装应用 */
	public synchronized void install(AppContent appInfo) {
		stopDownload(appInfo);
		DownloadInfo info = mDownloadMap.get(Long.parseLong(appInfo.gameId));// 找出下载信息
		if (info != null) {// 发送安装的意图
			Intent installIntent = new Intent(Intent.ACTION_VIEW);
			installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			installIntent.setDataAndType(Uri.parse("file://" + info.getPath()),
					"application/vnd.android.package-archive");
			AppUtil.getContext().startActivity(installIntent);
		}
		notifyDownloadStateChanged(info);
	}

	/** 启动应用，启动应用是最后一个 */
	public synchronized void open(AppContent appInfo) {
		try {
			Context context = AppUtil.getContext();
			if(!TextUtils.isEmpty(appInfo.packageName)){
				// 获取启动Intent
				Intent intent = context.getPackageManager()
						.getLaunchIntentForPackage(appInfo.packageName);
				context.startActivity(intent);
			}else{
				PackageManager pm = context.getPackageManager();
				PackageInfo info = pm.getPackageArchiveInfo(FileUtil.getDownloadDir(AppUtil.getContext()) + File.separator + appInfo.gameName + ".apk", PackageManager.GET_ACTIVITIES);
				ApplicationInfo appInfos = null;
				if (info != null) {
					appInfos = info.applicationInfo;
					String packageName = appInfos.packageName;
					Intent intent = context.getPackageManager()
							.getLaunchIntentForPackage(packageName);
					context.startActivity(intent);
				}else{
					ToastUtil.showToast(context, "包名不存在");
				}
			}
			
		} catch (Exception e) {
		}
	}

	/** 如果该下载任务还处于线程池中，且没有执行，先从线程池中移除 */
	private void stopDownload(AppContent appInfo) {
		DownloadTask task = mTaskMap.remove(Long.parseLong(appInfo.gameId));// 先从集合中找出下载任务
		if (task != null) {
			ThreadManager.getDownloadPool().cancel(task);// 然后从线程池中移除
		}
	}

	/** 获取下载信息 */
	public synchronized DownloadInfo getDownloadInfo(long id) {
		return mDownloadMap.get(id);
	}

	public synchronized void setDownloadInfo(long id, DownloadInfo info) {
		mDownloadMap.put(id, info);
	}

	/** 下载任务 */
	public class DownloadTask implements Runnable {
		private DownloadInfo info;

		public DownloadTask(DownloadInfo info) {
			this.info = info;
		}

		@Override
		public void run() {
			startDownload(info.getUrl(), ""+info.getId(), info.getPath(), info);
			/*
			info.setDownloadState(STATE_DOWNLOADING);// 先改变下载状态  
			notifyDownloadStateChanged(info);  
			File file = new File(info.getPath());// 获取下载文件  
			int a=0;
			 *//**********************************************************//*  
			//	          try {  
			try {  
				URL url = new URL(info.getUrl().trim());  
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
				conn.setRequestMethod("GET");  
				conn.setConnectTimeout(30000);  
				conn.setReadTimeout(30000);  
				if (!file.exists()) {  
					info.setCurrentSize(0);  
					file.delete();  
				} else if (file.length() > info.getAppSize()) {  
					info.setCurrentSize(file.length()); 
					info.setHasFinished(true);
					info.setDownloadState(DownloadManager.STATE_DOWNLOADED);
					// notifyDownloadProgressed(info);// 刷新进度
					Log.e("lbb", "---file.length() ->-----");
					return;
					//info.setCurrentSize(0);  
					//file.delete();  
				} else if (file.length() == info.getAppSize()) {  
					info.setCurrentSize(file.length()); 
					info.setHasFinished(true);
					info.setDownloadState(DownloadManager.STATE_DOWNLOADED);
				} else if (file.length() < info.getAppSize()) {  
					info.setCurrentSize(file.length());  
				}  

				if (info.getCurrentSize() == 0 || !file.exists() || file.length() != info.getCurrentSize()) {  
					// 如果文件不存在，或者进度为0，或者进度和文件长度不相符，就需要重新下载  
					info.setCurrentSize(0);  
					file.delete();  
				} else if (file.length() == info.getCurrentSize() && file.length() < info.getAppSize()) {  
					conn.setRequestProperty("Range", "bytes=" + info.getCurrentSize() + "-" + info.getAppSize());  //
					Log.e("lbb",info.getAppSize()+ "---setRequestProperty----"+file.length());
					a=1;
				}  
				int code = conn.getResponseCode();  
				Log.e("lbb", "---getResponseCode-code-----"+code);
				RandomAccessFile raf = new RandomAccessFile(file, "rw");  
				InputStream is = conn.getInputStream();  
				byte[] buffer = new byte[1024 ];  
				int len = -1;  
				int total = 0;// 当前线程下载的总的数据的长度  
				if (code == 200) {  
				} else if (code == 206) {  
					raf.seek(file.length());  
				}  
				while (((len = is.read(buffer)) != -1) && (info.getDownloadState() == STATE_DOWNLOADING)) { // 下载数据的过程。  
					raf.write(buffer, 0, len);  
					total += len;// 需要记录当前的数据。  
					info.setCurrentSize(info.getCurrentSize() + len);  
					notifyDownloadProgressed(info);// 刷新进度  
				}  
				is.close();  
				raf.close();  
			} catch (SocketTimeoutException e){
				e.printStackTrace();  
				info.setDownloadState(STATE_ERROR);  
				info.setCurrentSize(0);  
				file.delete(); 
				AppUtil.post(new Runnable() {
					@Override
					public void run() {
						ToastUtil.showToast(BaseApplication.mInstance.context,"连接超时");
					}
				});
			} catch (MalformedURLException e) {  
				// TODO Auto-generated catch block  
				e.printStackTrace();  
			} catch (ProtocolException e) {  
				// TODO Auto-generated catch block  
				e.printStackTrace();  
			} catch (FileNotFoundException e) {  
				// TODO Auto-generated catch block  
				if(a==1){
					info.setDownloadState(STATE_ERROR);  
					info.setCurrentSize(0);  
					file.delete(); 
					AppUtil.post(new Runnable() {
						@Override
						public void run() {
							ToastUtil.showToast(BaseApplication.mInstance.context,"断点续传失败");
						}
					});

				}
				e.printStackTrace();  
			} catch (IOException e) {  
				// TODO Auto-generated catch block  
				e.printStackTrace();  
			}  
			  *//*************************对于各种情况，需要删除下载任务，从新下载的 请自己改动代码*****************************//*  
			// 判断进度是否和app总长度相等  
			catch (Exception e) {  
				System.out.println(e.toString());  
				info.setDownloadState(STATE_ERROR);  
				//  info.setCurrentSize(0);  
				// file.delete();  
				e.printStackTrace();  
			}  
			if (info.getCurrentSize() >= info.getAppSize()) {  
				info.setDownloadState(STATE_DOWNLOADED); 
				info.setHasFinished(true);
				notifyDownloadStateChanged(info);  
			} else if (info.getDownloadState() == STATE_PAUSED) {// 判断状态  
				notifyDownloadStateChanged(info);  
			} else {  
				info.setDownloadState(STATE_ERROR);  
				notifyDownloadStateChanged(info);  
				// info.setCurrentSize(0);// 错误状态需要删除文件  
				// file.delete();  
			}  
			   *//**********************************************************//*  
			mTaskMap.remove(info.getId());  
			    */}
	}
	private final String HEAD_range = "range";
	public int getBrokenPos(String url,String gameId) {
		return SharedPreUtil.getIntValue(BaseApplication.mInstance.context, SHA_DOWNLOAD_POS + url+"_"+gameId,-1);
	}

	public void putBrokenPos(String url,String gameId,int pos) {
		SharedPreUtil.putIntValue(BaseApplication.mInstance.context, SHA_DOWNLOAD_POS + url+"_"+gameId,pos);
	}

	public void put(String url,String gameId, int curlength) {
		SharedPreUtil.putIntValue(BaseApplication.mInstance.context, SHA_DOWNLOAD_POS + url+"_"+gameId, curlength);
	}
	// 判断本地文件的size 与服务器同名版本size是否相等
	public boolean isExsit(int netApkSize,String mPath,DownloadInfo info) {
		File file = new File(mPath);
		if (file.exists()) {
			if (netApkSize == file.length()) {
				//mHandler.sendEmptyMessage(ACTION_UPGRADE_FINISHED);
				Message msg = mHandler.obtainMessage(ACTION_UPGRADE_FINISHED,netApkSize, netApkSize);
				msg.obj=info;
				mHandler.sendMessage(msg);
				return true;
			}
		}
		return false;
	}
	/** 断点续传 */
	public void downLoadAnd(String url,String gameId,String mPath,DownloadInfo info) {

		RandomAccessFile fos = null;
		BufferedInputStream bis = null;
		int fileSize = 0;
		int borkenLength=0;
		try {
			HttpURLConnection con = null;
			URL uurl = new URL(url);

			URLConnection tmp = uurl.openConnection();
			fileSize = tmp.getContentLength();
			tmp = null;

			int newSize = getBrokenPos(url,gameId);
			if (newSize != -1 && newSize > 0) {
				borkenLength = newSize;
			} 
			if (isExsit(fileSize,mPath,info)) {
				put(url,gameId, fileSize);
				return;
			}else{
				Message msgs = mHandler.obtainMessage(ACTION_DS,borkenLength, fileSize);
				msgs.obj=info;
				mHandler.sendMessage(msgs);
			}

			con = (HttpURLConnection) uurl.openConnection();
			// 设置当前线程下载的起�?
			con.addRequestProperty(HEAD_range, "bytes=" + borkenLength + "-");
			Log.e("lbb","updata service responseCode==="+ con.getResponseCode());
			// 使用java中的RandomAccessFile 对文件进行随机读写操�?
			fos = new RandomAccessFile(mPath, "rwd");
			// 设置�?��写文件的位置
			fos.seek(borkenLength);
			bis = new BufferedInputStream(con.getInputStream());

			byte[] buffer = new byte[1024];

			int len = 0;
			int time = 0;
			int loadedLength = 0;
			while ((len = bis.read(buffer)) != -1) {
				loadedLength += len;
				if (time++ % 100 == 0) {
					Message msg = mHandler.obtainMessage(ACTION_UPGRADE,loadedLength + borkenLength, fileSize);
					msg.obj=info;
					mHandler.sendMessage(msg);
				}
				fos.write(buffer, 0, len);
				put(url,gameId, loadedLength + borkenLength);
			}

			Message msg = mHandler.obtainMessage(ACTION_UPGRADE_FINISHED,fileSize, fileSize);
			msg.obj=info;
			mHandler.sendMessage(msg);

		} catch (Exception e) {
			e.printStackTrace();
			//mHandler.sendEmptyMessage(ACTION_FAIL_CONNECTED);
			Message msg = mHandler.obtainMessage(ACTION_FAIL_CONNECTED,0, fileSize);
			msg.obj=info;
			mHandler.sendMessage(msg);
			putBrokenPos(url,gameId, -1);
			File file = new File(mPath);
			file.delete(); 

			//mHandler.sendEmptyMessage(ACTION_DS);
			Message msgs = mHandler.obtainMessage(ACTION_DS,0, fileSize);
			msgs.obj=info;
			mHandler.sendMessage(msgs);
			downLoadAll(url,gameId,mPath,info);
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					//mHandler.sendEmptyMessage(ACTION_ACTION_UPDATA_ERROR);
					int borkenLengths = getBrokenPos(url,gameId);
					if (borkenLengths != -1 && borkenLengths > 0) {
						Message msg = mHandler.obtainMessage(ACTION_ACTION_UPDATA_ERROR,borkenLengths, fileSize);
						msg.obj=info;
						mHandler.sendMessage(msg);

					}else{
						Message msg = mHandler.obtainMessage(ACTION_ACTION_UPDATA_ERROR,0, fileSize);
						msg.obj=info;
						mHandler.sendMessage(msg);
					}
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
					//mHandler.sendEmptyMessage(ACTION_ACTION_UPDATA_ERROR);
					int borkenLengthss = getBrokenPos(url,gameId);
					if (borkenLengthss != -1 && borkenLengthss > 0) {
						Message msg = mHandler.obtainMessage(ACTION_ACTION_UPDATA_ERROR,borkenLengthss, fileSize);
						msg.obj=info;
						mHandler.sendMessage(msg);
					}else{
						Message msg = mHandler.obtainMessage(ACTION_ACTION_UPDATA_ERROR,0, fileSize);
						msg.obj=info;
						mHandler.sendMessage(msg);
					}
				}
			}
			//put(url,gameId, -1);
			mTaskMap.remove(info.getId()); 
		}
	}
	/** 重新完整的下载一个apk */
	public void downLoadAll(String url,String gameId,String mPath,DownloadInfo info) {
		FileOutputStream fos = null;
		InputStream is = null;
		int fileLength=0;
		File file = new File(mPath);
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(url);
			HttpResponse response = null;
			response = httpClient.execute(getMethod);
			HttpEntity httpEntity = response.getEntity();
			fileLength = (int) httpEntity.getContentLength();
			if (isExsit(fileLength,mPath,info)) {
				put(url,gameId, fileLength);
				return;
			}else{
				Message msgs = mHandler.obtainMessage(ACTION_DS,0, fileLength);
				msgs.obj=info;
				mHandler.sendMessage(msgs);
			}
			if (httpEntity != null) {
				is = httpEntity.getContent();
				int status = response.getStatusLine().getStatusCode();
				if (status != HttpStatus.SC_OK) {
					//mHandler.sendEmptyMessage(ACTION_SERVER_ERROR);
					Message msg = mHandler.obtainMessage(ACTION_FAIL_CONNECTED,0, fileLength);
					msg.obj=info;
					mHandler.sendMessage(msg);
					put(url,gameId, -1);//删除文件
					file.delete(); 
					return;
				}
			}
			file = new File(mPath);
			File file2 = file.getParentFile();
			try {
				if (!file2.exists()) {
					file2.mkdirs();
					file.createNewFile();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				//mHandler.sendEmptyMessage(ACTION_FAIL_CONNECTED);
				Message msg = mHandler.obtainMessage(ACTION_FAIL_CONNECTED,0, fileLength);
				msg.obj=info;
				mHandler.sendMessage(msg);
				put(url,gameId, -1);//删除文件
				file.delete(); 
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
					msg.obj=info;
					mHandler.sendMessage(msg);
				}
				fos.write(buffer, 0, len);
				put(url,gameId, loadedLength);
			}
			Message msg = mHandler.obtainMessage(ACTION_UPGRADE_FINISHED,fileLength, fileLength);
			msg.obj=info;
			mHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
			//mHandler.sendEmptyMessage(ACTION_ACTION_UPDATA_ERROR);
			Message msg = mHandler.obtainMessage(ACTION_FAIL_CONNECTED,0, fileLength);
			msg.obj=info;
			mHandler.sendMessage(msg);
			put(url,gameId, -1);//删除文件
			file.delete(); 
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					//mHandler.sendEmptyMessage(ACTION_ACTION_UPDATA_ERROR);
					int borkenLengthss = getBrokenPos(url,gameId);
					if (borkenLengthss != -1 && borkenLengthss > 0) {
						Message msg = mHandler.obtainMessage(ACTION_ACTION_UPDATA_ERROR,borkenLengthss, fileLength);
						msg.obj=info;
						mHandler.sendMessage(msg);
					}else{
						Message msg = mHandler.obtainMessage(ACTION_ACTION_UPDATA_ERROR,0, fileLength);
						msg.obj=info;
						mHandler.sendMessage(msg);
					}
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					//mHandler.sendEmptyMessage(ACTION_ACTION_UPDATA_ERROR);
					int borkenLengthss = getBrokenPos(url,gameId);
					if (borkenLengthss != -1 && borkenLengthss > 0) {
						Message msg = mHandler.obtainMessage(ACTION_ACTION_UPDATA_ERROR,borkenLengthss, fileLength);
						msg.obj=info;
						mHandler.sendMessage(msg);
					}else{
						Message msg = mHandler.obtainMessage(ACTION_ACTION_UPDATA_ERROR,0, fileLength);
						msg.obj=info;
						mHandler.sendMessage(msg);
					}
				}
			}
			mTaskMap.remove(info.getId()); 
		}
	}
	//开始下载
	public void startDownload(String url,String gameId,String mPath,DownloadInfo info){

		//mHandler.sendEmptyMessage(ACTION_DS);

		//showNofity();
		int borkenLength = getBrokenPos(url,gameId);
		if (borkenLength != -1 && borkenLength > 0) {
			downLoadAnd(url,gameId,mPath,info);
		} else {
			downLoadAll(url,gameId,mPath,info);
		}
	}
	private static final int ACTION_DS = 0;
	//private static final int ACTION_SP = 7;

	private static final int ACTION_UPGRADE = 1;
	private static final int ACTION_UPGRADE_FINISHED = 2;
	private static final int ACTION_ACTION_UPDATA_ERROR = 3;
	private static final int ACTION_FAIL_CONNECTED = 5;
	//private static final int ACTION_SERVER_ERROR = 6;
	public Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ACTION_DS:
				//开始下载
				int downloadProgres =   (int) ((msg.arg1 * 1.0f / msg.arg2) * 100);
				if(downloadProgres>100) downloadProgres = 99;//超过100，则处理为99
				DownloadInfo inf=(DownloadInfo) msg.obj;
				if(inf!=null){
					inf.setDownloadState(STATE_DOWNLOADING);
					inf.setDownloadProgress(downloadProgres);
					inf.setCurrentSize(msg.arg1);  
					notifyDownloadProgressed(inf);// 刷新进度  
				}
				break;
				/*case ACTION_SP:

				break;*/
			case ACTION_UPGRADE:
				int downloadProgress =   (int) ((msg.arg1 * 1.0f / msg.arg2) * 100);
				if(downloadProgress>100) downloadProgress = 99;//超过100，则处理为99
				DownloadInfo info=(DownloadInfo) msg.obj;
				if(info!=null){
					info.setDownloadState(STATE_DOWNLOADING);
					info.setDownloadProgress(downloadProgress);
					info.setCurrentSize(msg.arg1);  
					notifyDownloadProgressed(info);// 刷新进度  
				}

				break;
			case ACTION_UPGRADE_FINISHED:
				int downloadProgresss =   (int) ((msg.arg1 * 1.0f / msg.arg2) * 100);
				if(downloadProgresss>100) downloadProgresss = 99;//超过100，则处理为99
				DownloadInfo infos=(DownloadInfo) msg.obj;
				if(infos!=null){
					infos.setDownloadState(STATE_DOWNLOADED);
					infos.setDownloadProgress(downloadProgresss);
					infos.setCurrentSize(msg.arg1);  
					notifyDownloadProgressed(infos);// 刷新进度  
					LoginData user=DBManager.getInstance(BaseApplication.mInstance.context).getUserMessage();
					new DownloadFinishTask(BaseApplication.mInstance.context, user.userId,""+infos.getId(), new Back() {
						
						@Override
						public void success(Object object, String msg) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void fail(String status, String msg, Object object) {
							// TODO Auto-generated method stub
							
						}
					}).start();
					//put(infos.getUrl(),""+infos.getId(), -1);// 下载完成后把标记断点记为-1
				}

				break;
			case ACTION_ACTION_UPDATA_ERROR:
				int downloadProgressss =   (int) ((msg.arg1 * 1.0f / msg.arg2) * 100);
				if(downloadProgressss>100) downloadProgressss = 99;//超过100，则处理为99
				DownloadInfo infoss=(DownloadInfo) msg.obj;
				if(infoss!=null){
					infoss.setDownloadState(STATE_PAUSED);
					infoss.setDownloadProgress(downloadProgressss);
					infoss.setCurrentSize(msg.arg1);  
					notifyDownloadProgressed(infoss);// 刷新进度  

					//put(infos.getUrl(),""+infos.getId(), -1);// 下载完成后把标记断点记为-1
				}
				break;
			case ACTION_FAIL_CONNECTED:
				int downloadProgresssss =   (int) ((msg.arg1 * 1.0f / msg.arg2) * 100);
				if(downloadProgresssss>100) downloadProgresssss = 99;//超过100，则处理为99
				DownloadInfo infosss=(DownloadInfo) msg.obj;
				if(infosss!=null){
					infosss.setDownloadState(STATE_PAUSED);
					infosss.setDownloadProgress(downloadProgresssss);
					infosss.setCurrentSize(msg.arg1);  
					notifyDownloadProgressed(infosss);// 刷新进度  

					//put(infos.getUrl(),""+infos.getId(), -1);// 下载完成后把标记断点记为-1
				}
				break;
				/*case ACTION_SERVER_ERROR:

				break;*/
			default:
				break;
			}
		}
	};
	public interface DownloadObserver {

		public void onDownloadStateChanged(DownloadInfo info);

		public void onDownloadProgressed(DownloadInfo info);
	}

	/* 重写了Inpustream 中的skip(long n) 方法，将数据流中起始的n 个字节跳过 */
	private long skipBytesFromStream(InputStream inputStream, long n) {
		long remaining = n;
		// SKIP_BUFFER_SIZE is used to determine the size of skipBuffer
		int SKIP_BUFFER_SIZE = 10000;
		// skipBuffer is initialized in skip(long), if needed.
		byte[] skipBuffer = null;
		int nr = 0;
		if (skipBuffer == null) {
			skipBuffer = new byte[SKIP_BUFFER_SIZE];
		}
		byte[] localSkipBuffer = skipBuffer;
		if (n <= 0) {
			return 0;
		}
		while (remaining > 0) {
			try {
				long skip = inputStream.skip(10000);
				System.out.println("skip======="+skip);
				//				inputStream.read(localSkipBuffer, 0, (int)Math.max(SKIP_BUFFER_SIZE-1, remaining));
				nr = inputStream.read(localSkipBuffer, 0,
						(int) Math.min(SKIP_BUFFER_SIZE, remaining));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (nr < 0) {
				break;
			}
			remaining -= nr;
			System.out.println("nr:"+nr+"========remain"+remaining);
		}
		System.out.println("n - remaining"+(n - remaining));
		return n - remaining;
	}
}
