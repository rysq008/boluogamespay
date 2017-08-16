
package com.game.helper.net.base;

import java.util.concurrent.Executors;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.interfaces.comm.CommValues;
import com.game.helper.sdk.net.base.HttpComm;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

/**
 * @Description
 * @Path 
 * @Author 
 * @Date 
 * @Company 
 */
public abstract class BaseBBXTask extends BaseAsyncTask implements CommValues, HttpComm {

//	public String uid, token;
	protected ProgressDialog progressDialog;

	public interface Back {

		public void success(Object object, String msg);

		public void fail(String status, String msg, Object object);
		
	}

	protected Back back;
	
	public BaseBBXTask(Context context,String msg, boolean isDialog) {
		super(context,msg, isDialog);
	}
	
	public BaseBBXTask(Context context,String msg, boolean isDialog, Back back) {
		super(context,msg, isDialog);
		this.back = back;
	}
	
	public BaseBBXTask(Context context, boolean isDialog) {
		super(context, isDialog);
	}

	public BaseBBXTask(Context context, boolean isDialog, Back back) {
		super(context, isDialog);
		this.back = back;
	}

	public BaseBBXTask(Context context, Back back) {
		super(context);
		this.back = back;
	}

	public BaseBBXTask(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		//initProgress();
	}

	/*public String getUid() {
		return SharedPreUtil.getStringValue(context, SHA_UID, "");
	}

	public String getToken() {
		return SharedPreUtil.getStringValue(context, SHA_TOKEN, "");
	}*/

	public void start() {
		execute(Executors.newCachedThreadPool());
	}

	public void initProgress() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setCanceledOnTouchOutside(true);
	}

	/** @Description 显示对话框 */
	public void showDialog(String msg, boolean isCancelable,
			final boolean isFinish) {
		if (context != null) {
			if (context instanceof BaseActivity) {
				Activity activity = (Activity) context;
				if (activity != null && activity.isFinishing()) {
					return;
				}
			}
		}
		Builder builder = new Builder(context);
		builder.setMessage(msg).setCancelable(isCancelable);
		builder.setNegativeButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int pos) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						if (isFinish) {
							finishAct();
						}
					}
				});
		builder.create().show();
	}

	@Override
	protected void onPostExecute(Object result) {
		if(progressDialog!=null){
			progressDialog.dismiss();
		}
		super.onPostExecute(result);
	}

	@Override
	public void onFailed(String status, String msg,Object result) {
		// TODO Auto-generated method stub
		/*if(status == OUTTIME_TOKEN||status == -11){
			boolean isLogin = SystemUtil.isActRunning(context,GuidePageActivity.class.getName());
			if(!isLogin){
				boolean isLogin1 = SystemUtil.isActRunning(context,LoginActivity.class.getName());
				if(!isLogin1){
					ToastUtil.showToast(context, "您的账号已过期，请重新登陆");
					LoginUtil.reLogin(context);
				}
				
			}
			return;
		}*/
	}
}
