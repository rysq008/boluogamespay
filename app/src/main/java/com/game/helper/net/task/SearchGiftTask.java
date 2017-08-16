package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.SearchGiftBuild;
import com.game.helper.sdk.net.comm.SearchGiftNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.SearchGiftTask.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:52:02
 * @Company 
 */
public class SearchGiftTask extends BaseBBXTask{
	SearchGiftBuild build;
	public SearchGiftTask(Context context,String keyword,Back back) {
		super(context);
		this.back=back;
		build=new SearchGiftBuild(context, API_searchGift_Url, keyword);
	}
	@Override
	public void onSuccess(Object object, String msg) {
		// TODO Auto-generated method stub
		if(back!=null){
			back.success(object,msg);
		}
	}

	@Override
	public void onFailed(String status, String msg, Object result) {
		// TODO Auto-generated method stub
		super.onFailed(status, msg, result);
       if(back!=null){
    	   back.fail(status, msg, result);
		}
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return new SearchGiftNet(context, build.toJson1());
	}
	
}
