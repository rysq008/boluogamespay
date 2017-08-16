package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.CommentDynamicBuild;
import com.game.helper.sdk.net.comm.CommentDynamicNet;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.commentDynamicTask.java
 * @Author lbb
 * @Date 2016年9月16日 下午1:10:05
 * @Company 
 */
public class CommentDynamicTask  extends BaseBBXTask{
	CommentDynamicBuild build;
	public CommentDynamicTask(Context context,String userId,String dynamicId,String content,Back back) {
		super(context);
		this.back=back;
		build=new CommentDynamicBuild(context, API_commentDynamic_Url,userId, dynamicId, content);
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
		return new CommentDynamicNet(context, build.toJson1());
	}
	
}