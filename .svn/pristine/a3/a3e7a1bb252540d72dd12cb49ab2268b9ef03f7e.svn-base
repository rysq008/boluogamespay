package com.yuan.leopardkit.download;

import android.os.Environment;

import com.yuan.leopardkit.db.HttpDbUtil;
import com.yuan.leopardkit.download.model.DownloadInfo;
import com.yuan.leopardkit.download.task.DownLoadTask;
import com.yuan.leopardkit.interfaces.FileRespondResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by Yuan on 2016/8/30.
 * Detail 下载辅助类,管理下载任务 与UI进行回馈
 */
public class DownLoadManager {

    private static DownLoadManager manager = new DownLoadManager();

    //定义下载状态常量
    public static final int STATE_WAITING = 1;      //等待    --> 下载，暂停
    public static final int STATE_DOWNLOADING = 2;  //下载中  --> 暂停，完成，错误
    public static final int STATE_PAUSE = 3;        //暂停    --> 等待，下载
    public static final int STATE_FINISH = 4;       //完成    --> 重新下载
    public static final int STATE_ERROR = 5;        //错误    --> 等待
    public static final int AlreadyInstalled = 6;        //安装    --> 打开应用
    
    public String deFaultDir = Environment.getExternalStorageDirectory() + "/YuanDwonloadLbb/";

    private FileRespondResult callback;
    private List<DownloadInfo> downloadInfosList;
    private List<DownLoadTask> downLoadTaskList;
    public static DownLoadManager getManager(){
        if (manager == null){
            manager = new DownLoadManager();
        }
        return manager;
    }

    public DownLoadManager() {
        //初始化一系列
        deFaultDir = Environment.getExternalStorageDirectory() + "/YuanDwonloadLbb/";
        downloadInfosList = new ArrayList<DownloadInfo>();
        downLoadTaskList = new ArrayList<DownLoadTask>();
        if (!new File(deFaultDir).exists()) new File(deFaultDir).mkdirs();
    }

    public void addTask(DownloadInfo downloadInfo, FileRespondResult listener){
        if (downloadInfo.getFileSavePath() == null || downloadInfo.getFileSavePath().equals("")){
            downloadInfo.setFileSavePath(deFaultDir);
        }
        if (!isHaved(downloadInfo)) {
        	DownLoadTask task = new DownLoadTask(downloadInfo,listener);
        	downLoadTaskList.add(task);
        	downloadInfo.setDownLoadTask(task);
        	downloadInfosList.add(downloadInfo);
        }
        // TODO: 2016/8/31 添加一条记录
        long key =  HttpDbUtil.instance.insert(downloadInfo);
        downloadInfo.setKey(key);
    }
    public boolean isHaved(DownloadInfo downloadInfo){
    	boolean isS=false;
    	for (DownloadInfo down : downloadInfosList){
    		if(down.getFileName().equals(downloadInfo.getFileName())){
    			isS=true;
    			break;
    		}
    	}
    	return isS;
    }

    /**
     * 是不是有游戏在下载中
     * @param downloadInfo
     * @return
     */
    public boolean isDowLoading(DownloadInfo downloadInfo){
          boolean   isDowLoad=false;
        for (DownloadInfo down : downloadInfosList){
            if (down.getState()==STATE_DOWNLOADING){//有下载中的任务
                if ((!downloadInfo.getFileName().equals(down.getFileName())) && downloadInfo.getState()!=AlreadyInstalled){//并且点击的不是原来的那个下载中的，并且状态不是已经下载结束的
                    isDowLoad=true;
                }
                break;
            }else{
                isDowLoad=false;
            }
        }
        return isDowLoad;
    }

    public DownloadInfo getDownloadInfo(DownloadInfo downloadInfo){
    	DownloadInfo d=null;
    	for (DownloadInfo down : downloadInfosList){
    		if(down.getFileName().equals(downloadInfo.getFileName())){
    			 d=down;
    			break;
    		}
    	}
		return d;
    }
    public void removeTask(DownloadInfo downloadInfo){
        if (downloadInfosList.contains(downloadInfo)) {
            downloadInfosList.remove(downloadInfo);
            File downFile = new File(downloadInfo.getFileSavePath() + downloadInfo.getFileName());
            if (downFile.exists()){
                downFile.delete();
            }
        }
        HttpDbUtil.instance.delete(downloadInfo);
    }

    public void removeAllTask(){
        for (DownloadInfo downloadInfo : downloadInfosList){
            downloadInfo.getDownLoadTask().remove();
        }
        downloadInfosList.clear();
    }

    public void pauseAllTask(){
        for (DownloadInfo downloadInfo : downloadInfosList){
            downloadInfo.getDownLoadTask().pause();
        }
    }

    public void restartAllTask(){
        for (DownloadInfo downloadInfo : downloadInfosList){
            downloadInfo.getDownLoadTask().download(true);
        }
    }

    public void startAllTask(){
        for (DownloadInfo downloadInfo : downloadInfosList){
            downloadInfo.getDownLoadTask().download(false);
        }
    }

    public void stopAllTask(){
        for (DownloadInfo downloadInfo : downloadInfosList){
            downloadInfo.getDownLoadTask().stop();
        }
    }

    public void restartTask(DownloadInfo downloadInfo){
        downloadInfo.getDownLoadTask().download(true);
    }

    public void startTask(DownloadInfo downloadInfo){
        downloadInfo.getDownLoadTask().download(false);
    }

    public void stopTask(DownloadInfo downloadInfo){
        downloadInfo.getDownLoadTask().stop();
    }
	public List<DownloadInfo> getDownloadInfosList() {
		return downloadInfosList;
	}

	public void setDownloadInfosList(List<DownloadInfo> downloadInfosList) {
		this.downloadInfosList = downloadInfosList;
	}
}
