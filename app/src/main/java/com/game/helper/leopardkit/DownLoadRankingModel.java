package com.game.helper.leopardkit;

import com.game.helper.download.bean.AppContent;
import com.yuan.leopardkit.download.model.DownloadInfo;

/**
 * 
 * Detail 下载适配器实体类
 */
public class DownLoadRankingModel {

    private DownloadInfo info;
    private AppContent mAppContent;

    public DownLoadRankingModel() {
    }

    public DownloadInfo getInfo() {
        return info;
    }

    public void setInfo(DownloadInfo info) {
        this.info = info;
    }

	public AppContent getmAppContent() {
		return mAppContent;
	}

	public void setmAppContent(AppContent mAppContent) {
		this.mAppContent = mAppContent;
	}
    
}
