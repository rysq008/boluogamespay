
package com.game.helper.net.base;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.game.helper.R;
import com.game.helper.sdk.net.base.BaseNetwork;
import com.game.helper.util.LoadingDialog;
import com.game.helper.util.SystemUtil;


/**
 * @Description
 * @Path 
 * @Author 
 * @Date 
 * @Company 
 */
public abstract class BaseAsyncTask extends AsyncTask<Object, Object, Object> {
	public static int NET_ERROR=408;
	public int DELAY_TIME = 500;
	public static int OUTTIME_TOKEN = 500;
	public abstract void onSuccess(Object object,String msg);

	public abstract void onFailed(String status, String msg,Object object);

	/** 用于区分不同的请求类型 */
	public int type;
	/** 是否现在加载框 */
	private boolean isShow = true;
	protected Context context;
	private LoadingDialog dialog;
	private String msg;
	private boolean isReturnString=false;

	public boolean isLoading = false;
	public boolean isLoadCancel=true;

	public boolean isCancel=false;
	public Handler handler = new Handler(){
		public void handleMessage(Message msg) {  
			switch (msg.what) {      
			case 1:      
				disDialog();
				onFailed("408", "网络不给力，请稍后再试",null);
				break;      
			}      
			super.handleMessage(msg);  
		}  
	};

	private void initDialog() {
		dialog = new LoadingDialog(context,isLoadCancel);
		if (msg == null || TextUtils.isEmpty(msg)) {
			msg = "正在加载...";

		}
		dialog.setTextMsg(msg);
	}

	private void showDialog() {
		try {
			if(!isCancel){
				if (isLoading && dialog != null && !dialog.isShowing() && isShow) {
					isCancel=false;
					dialog.show();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void disDialog() {
		try {
			if (dialog != null) {
				isCancel=true;
				dialog.dismiss();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BaseAsyncTask(Context context, String msg, boolean isShow) {
		this.context = context;
		this.msg = msg;
		this.isShow = isShow;
		if(isShow){
			initDialog();
		}
		//putAsyncTask(this);
	}
	public BaseAsyncTask(Context context,boolean isShow) {
		this.context = context;
		this.isShow = isShow;
		if(isShow){
			initDialog();
		}
		//putAsyncTask(this);
	}

	public BaseAsyncTask(Context context) {
		this.context = context;
		if(isShow){
			initDialog();
		}
		//putAsyncTask(this);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		if(SystemUtil.getNetworkStatus(context)){
			super.onPreExecute();
			isLoading = true;
			if (isShow)
				handler.postDelayed(showDialog, DELAY_TIME);
		}else {
			Message message = new Message();      
			message.what = 1;      
			handler.sendMessage(message);  
		}
	}

	public Runnable showDialog = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			showDialog();
		}
	};

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		if(SystemUtil.getNetworkStatus(context)){
			//msg=context.getResources().getString(R.string.no_network);
			isLoading = false;
			if (result instanceof BaseNetwork) {
				BaseNetwork baseNetwork = (BaseNetwork) result;
				 if (baseNetwork != null && baseNetwork.getSuccess()) {
					if(isReturnString){
						onSuccess(baseNetwork.getString(),baseNetwork.getMsg());
					}
					else{
						onSuccess(baseNetwork.getData(),baseNetwork.getMsg());	
					}
				} else {
					String code=baseNetwork.getCode();
					if(!TextUtils.isEmpty(code)){
						if (code.equals("408") ){
							onFailed(code, "网络不给力，请稍后再试",null);
						}else if (code.equals("500") ){
							onFailed(code, "服务器无反应，请稍后再试",null);
						}else{
							onFailed(code,baseNetwork.getMsg(),baseNetwork.getData());
						}		
					}
				}
			} else {
				onFailed("408", "服务器无反应，请稍后再试",null);
			}
			disDialog();
			super.onPostExecute(result);
		}else{
			//Log.e("lbb","-------disDialog----");
			disDialog();
			onFailed(""+NET_ERROR, "网络不给力，请稍后再试",null);
		}
	}
	/**
	 * 用于区分不同的请求类型
	 */
	protected int getType() {
		return type;
	}

	protected void setType(int type) {
		this.type = type;
	}
	public void setIsReturnString(boolean isReturnString){
		this.isReturnString=isReturnString;
	}
	/**
	 * 设置加载框是否可取消，默认可以
	 */
	public void setLoadCancel(boolean isLoadCancel){
		this.isLoadCancel=isLoadCancel;
	}
	/** @Description 关闭act */
	public void finishAct() {
		if (isAct()) {
			Activity activity = (Activity) context;
			if (!activity.isFinishing()) {
				activity.finish();

			}
		}

	}

	/** @Description 是否为activity */
	public boolean isAct() {
		if (context instanceof Activity) {
			return true;
		}
		return false;
	}
	/*protected static List<AsyncTask<Object, Object, Object>> mAsyncTasks = new ArrayList<AsyncTask<Object, Object, Object>>();
	protected void putAsyncTask(AsyncTask<Object, Object, Object> asyncTask) {
		mAsyncTasks.add(asyncTask);
	}
	public static void clearAsyncTask() {
		try {
			if(mAsyncTasks==null){
				return;
			}
			Iterator<AsyncTask<Object, Object, Object>> iterator = mAsyncTasks
					.iterator();
			while (iterator.hasNext()) {
				AsyncTask<Object, Object, Object> asyncTask = iterator.next();
				if (asyncTask != null && !asyncTask.isCancelled()) {
					asyncTask.cancel(true);
				}
			}
			mAsyncTasks.clear();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}*/
}
