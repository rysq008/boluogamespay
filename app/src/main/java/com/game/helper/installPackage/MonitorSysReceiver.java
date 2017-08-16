package com.game.helper.installPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.download.downloador.Constants;
import com.game.helper.download.utils.DownloadUtils;
import com.game.helper.leopardkit.DownLoadModel;
import com.game.helper.muldownload.utils.AppUtil;
import com.game.helper.muldownload.utils.FileUtil;
import com.yuan.leopardkit.db.HttpDbUtil;
import com.yuan.leopardkit.download.DownLoadManager;
import com.yuan.leopardkit.download.model.DownloadInfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import de.greenrobot.event.EventBus;

/**
 * @Description
 * @Path com.game.helper.installPackage.MonitorSysReceiver.java
 * @Author lbb
 * @Date 2016年10月20日 下午12:39:35
 * @Company 
 */
public class MonitorSysReceiver extends BroadcastReceiver{
	@Override  
	public void onReceive(Context context, Intent intent){
		//接收安装广播 
		//-------MonitorSysReceiver----------package:com.tencent.qt.qtl
		Log.e("lbb", "-------MonitorSysReceiver-1---------"+intent.getPackage());
		Log.e("lbb", "-------MonitorSysReceiver----------"+intent.getDataString());
		String page=intent.getDataString();
		if(!TextUtils.isEmpty(page)){
			String []a=page.split(":");
			if(a.length==2){
				String pageNameD=a[1];
				if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {   
					//TODO    
					List<DownLoadModel> mList=getDataList(context);
					for(DownLoadModel mD:mList){
						if(!TextUtils.isEmpty(mD.getmAppContent().packageName)){
							if(mD.getmAppContent().packageName.equals(pageNameD)){
								if(mD.getInfo()!=null){
									if(mD.getInfo().getState()== DownLoadManager.STATE_FINISH){
										if(((int) ((float) mD.getInfo().getProgress() / mD.getInfo().getFileLength() * 100))==100){
											if(mD.getInfo().getDownLoadTask()!=null){
												mD.getInfo().getDownLoadTask().setState(DownLoadManager.AlreadyInstalled);
												
												DownloadInfo infos = mD.getInfo().getDownLoadTask().getDownloadInfo();
												DownLoadModel mDown=new DownLoadModel();
												mDown.setmAppContent(mD.getmAppContent());
												mDown.setInfo(infos);
												EventBus.getDefault().post(mDown);
											}else{
												 mD.getInfo().setState(DownLoadManager.AlreadyInstalled);
												 HttpDbUtil.initHttpDB(context).update(mD.getInfo());
												 
												 DownLoadModel mDown=new DownLoadModel();
												 mDown.setmAppContent(mD.getmAppContent());
												 mDown.setInfo(mD.getInfo());
												 EventBus.getDefault().post(mDown);
											}
											
											
											Intent intents = new Intent(Constants.DOWNLOAD_MSG);
											Bundle bundle = new Bundle();
											bundle.putParcelable("appContent", mD.getmAppContent());
											intents.putExtras(bundle);
											context.sendBroadcast(intents);//该怎么处理好呢
											break;
										}
									}
								}
							}
						}
					}
				}   
				//接收卸载广播  
				if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {   
					//TODO
					/*List<AppContent> list = DBManager.getInstance(context).getAll();
					for (AppContent appContent : list) {}*/
				}
				if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
					System.out.println("监听到系统广播替换");

				}
			}
		}
		
	}
	public static List<DownLoadModel> getDataList(Context context){
		List<DownLoadModel> mList=new ArrayList<DownLoadModel>();
		List<DownloadInfo> downloadInfoList =   HttpDbUtil.initHttpDB(context).queryFileInfo(0);
		List<AppContent> mAppContentList=DBManager.getInstance(context).getAll();
		if(downloadInfoList!=null){
			DownLoadModel model=null;
			for(DownloadInfo info:downloadInfoList){
				String utr="";
				for(AppContent mAppContent:mAppContentList){
					utr="IRecordApp_" + mAppContent.gameId+ ".apk";
					if(utr.equals(info.getFileName())){
						model = new DownLoadModel();
						model.setInfo(info);
						model.setmAppContent(mAppContent);
						mList.add(model);
						break;
					}
				}

			}
		}
		return mList;
	}
	public static boolean isInstall(Context context,String packageName, String gameId){
		if(TextUtils.isEmpty(packageName)){
			PackageManager pm = context.getPackageManager();
			PackageInfo info = pm.getPackageArchiveInfo(
					DownLoadManager.getManager().deFaultDir+"IRecordApp_" + gameId+ ".apk", PackageManager.GET_ACTIVITIES);
			ApplicationInfo appInfo = null;
			if (info != null) {
				appInfo = info.applicationInfo;
				packageName = appInfo.packageName;
				
			}
		}
		if(checkApkExist(context,packageName,gameId)){
			return true;
		}
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);

		} catch (NameNotFoundException e) {
			packageInfo = null;
			e.printStackTrace();
		}catch (Exception e) {
			packageInfo = null;
			e.printStackTrace();
		}
		if(packageInfo == null) {
			//System.out.println("not installed");
			return false;
		} else {
			//System.out.println("is installed");
			return true;
		}

	}
	public static boolean checkApkExist(Context context, String packageName, String gameId) {
		try {
			if (TextUtils.isEmpty(packageName)){
				PackageManager pm = context.getPackageManager();
				PackageInfo info = pm.getPackageArchiveInfo(
						DownLoadManager.getManager().deFaultDir+"IRecordApp_" + gameId+ ".apk",
						  PackageManager.GET_ACTIVITIES);
				ApplicationInfo appInfo = null;
				if (info != null) {
					appInfo = info.applicationInfo;
					packageName = appInfo.packageName;
					
				}
			}
			ApplicationInfo info = context.getPackageManager().
					getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES); 
			if(info!=null){
				return true;
			}else{
				return false;
			}
			
		} catch (NameNotFoundException e) { 
			e.printStackTrace();
			return false; 
		}
	} 
	
	private static String ACTION_INSTALL = "application/vnd.android.package-archive";
	public static void intentInstall(Context context,AppContent appContent){
		// 下载完成后弹出安装窗
		File file = new File(DownLoadManager.getManager().deFaultDir+"IRecordApp_" + appContent.gameId+ ".apk");
		Intent intentInstall = new Intent();
		intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intentInstall.setAction(Intent.ACTION_VIEW);
		intentInstall.setDataAndType(Uri.fromFile(file), ACTION_INSTALL);
		context.startActivity(intentInstall);
	}
}
