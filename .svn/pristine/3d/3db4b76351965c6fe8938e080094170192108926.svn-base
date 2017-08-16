package com.game.helper.muldownload.bean;


import java.io.File;

import com.game.helper.download.bean.AppContent;
import com.game.helper.muldownload.download.DownloadManager;
import com.game.helper.muldownload.utils.AppUtil;
import com.game.helper.muldownload.utils.FileUtil;

import android.text.TextUtils;
public class DownloadInfo {

	private long id;//app的id，和appInfo中的id对应
	private String appName;//app的软件名称
	private long appSize = 0;//app的size M
	private long currentSize = 0;//当前的size 字节
	private long downloadProgress = 0;
	private int downloadState = 0;//下载的状态
	private String url;//下载地址
	private String path;//保存路径
	private boolean  hasFinished=true;
	/** 从AppInfo中构建出一个DownLoadInfo */
	public static DownloadInfo clone(AppContent info) {
		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.id =TextUtils.isEmpty(info.gameId) ? 0: Long.parseLong(info.gameId);
		downloadInfo.appName = info.gameName;
		downloadInfo.appSize = (TextUtils.isEmpty(info.fileSize) ? 0: ((long) (Double.parseDouble(info.fileSize)*1024*1024)));
		downloadInfo.currentSize = 0;
		downloadInfo.downloadProgress = 0;
		downloadInfo.downloadState = DownloadManager.STATE_NONE;
		downloadInfo.url = info.downloadPath;
		downloadInfo.path = FileUtil.getDownloadDir(AppUtil.getContext()) + File.separator + downloadInfo.appName + ".apk";
		return downloadInfo;
	}
	
	

	public boolean isHasFinished() {
		return hasFinished;
	}



	public void setHasFinished(boolean hasFinished) {
		this.hasFinished = hasFinished;
	}



	public String getPath() {
		return path;
	}

	public float getProgress() {
		if (getAppSize() == 0) {
			return 0;
		}
		return (float) ((getCurrentSize() + 0.0f) / getAppSize());
	}

	public synchronized String getUrl() {
		return url;
	}

	public synchronized void setUrl(String url) {
		this.url = url;
	}

	public synchronized long getId() {
		return id;
	}

	public synchronized void setId(long id) {
		this.id = id;
	}

	public synchronized String getAppName() {
		return appName;
	}

	public synchronized void setAppName(String appName) {
		this.appName = appName;
	}

	public synchronized long getAppSize() {
		
		return appSize;
	}

	public synchronized void setAppSize(long appSize) {
		this.appSize = appSize;
	}

	public synchronized long getCurrentSize() {
		return currentSize;
	}

	public synchronized void setCurrentSize(long currentSize) {
		this.currentSize = currentSize;
	}

	public synchronized int getDownloadState() {
		return downloadState;
	}

	public void setDownloadState(int downloadState) {
		this.downloadState = downloadState;
	}



	public long getDownloadProgress() {
		return downloadProgress;
	}



	public void setDownloadProgress(long downloadProgress) {
		this.downloadProgress = downloadProgress;
	}
	
	
	
}
