package com.game.helper.net.task;

import android.content.Context;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.CgameplatlistBuild;
import com.game.helper.sdk.net.comm.CgameplatlistNet;

public class CgameplatlistTask extends BaseBBXTask{
	CgameplatlistBuild build;
	public CgameplatlistTask(Context context,Back back) {
		super(context);
		this.back=back;
		build=new CgameplatlistBuild(context, API_cgameplatlist_Url);
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
		return new CgameplatlistNet(context, build.toJson1());
	}
	
}
