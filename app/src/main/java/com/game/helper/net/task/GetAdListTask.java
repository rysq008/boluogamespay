package com.game.helper.net.task;

import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.GetAdListBuild;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.comm.GetAdListNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.GetAdListTask.java
 * @Author lbb
 * @Date 2016年9月12日 下午7:51:28
 * @Company
 */
public class GetAdListTask extends BaseBBXTask {
    GetAdListBuild build;

    public GetAdListTask(Context context, String type, Back back) {
        super(context, false);
        LoginData user = DBManager.getInstance(context).getUserMessage();
        String userId = user.userId;
        if (type .equals("4")) {
            build = new GetAdListBuild(context, API_getBunnerList_Url, type, userId);
        } else {
            build = new GetAdListBuild(context, API_getAdList_Url, type, userId);

        }
        this.back = back;
    }

    @Override
    public void onSuccess(Object object, String msg) {
        // TODO Auto-generated method stub
        if (back != null) {
            back.success(object, msg);
        }
    }

    @Override
    public void onFailed(String status, String msg, Object result) {
        // TODO Auto-generated method stub
        super.onFailed(status, msg, result);
        if (back != null) {
            back.fail(status, msg, result);
        }
    }

    @Override
    protected Object doInBackground(Object... params) {
        // TODO Auto-generated method stub
        return new GetAdListNet(context, build.toJson1());
    }

}
