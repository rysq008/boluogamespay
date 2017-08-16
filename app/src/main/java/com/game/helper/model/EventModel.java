package com.game.helper.model;

import com.game.helper.download.bean.AppContent;
import com.yuan.leopardkit.download.model.DownloadInfo;

/**
 * @Description
 * @Path com.game.helper.model.EventModel.java
 * @Author lbb
 * @Date 2016年11月1日 下午7:24:01
 * @Company 
 */
public class EventModel {

	public long progress; 
	public long total;
	public boolean done;
	public DownloadInfo infos;
	public AppContent mAppContent;
}
