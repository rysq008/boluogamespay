package com.game.helper.net.task;

import java.io.UnsupportedEncodingException;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.returns.FileUpDoald;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.TestFileUpload;

import android.content.Context;
import android.text.TextUtils;

/**
 * @Description
 * @Path com.game.helper.net.task.FileUpDoaldTask.java
 * @Author lbb
 * @Date 2016年9月22日 下午1:38:53
 * @Company 
 */
public class FileUpDoaldTask extends BaseBBXTask{
    String picPath;
    String phone,needThumb;
	public FileUpDoaldTask(Context context,String phone,String picPath,String needThumb,Back back) {
		super(context,false,back);
		this.picPath=picPath;
		this.phone=phone;
		this.needThumb=needThumb;
	}

	@Override
	public void onSuccess(Object object, String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailed(String status, String msg, Object result) {
		
		//super.onFailed(status, msg, result);
	}

	@Override
	protected Object doInBackground(Object... params) {
		
		try {
			String resp =TestFileUpload.upLoad(phone, picPath, needThumb);
			
			if(!TextUtils.isEmpty(resp)){
				Object mObject=new JsonBuild().getData(FileUpDoald.class, resp);
				if(mObject!=null && mObject instanceof FileUpDoald){
					FileUpDoald mFileUpDoald=(FileUpDoald) mObject;
					if(!TextUtils.isEmpty(mFileUpDoald.code)&&mFileUpDoald.code.equals("100")){
						if(back!=null){
							back.success(mFileUpDoald,mFileUpDoald.msg);
						}
					}else{
						if(back!=null){
							back.fail(mFileUpDoald.code, mFileUpDoald.msg, mFileUpDoald);
						}
					}
				}else{
					if(back!=null){
						back.fail("200", "", resp);
					}
				}
			}else{
				if(back!=null){
					back.fail("200", "", resp);
				}
			}
			return resp;
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			if(back!=null){
				back.fail("200", "", "");
			}
		}
		return null;
	}
	   
   }
