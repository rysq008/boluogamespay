package com.game.helper.net.task;

import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.sdk.model.comm.LoginBuild;
import com.game.helper.sdk.model.comm.LoginUseAuthBuild;
import com.game.helper.sdk.net.comm.LoginNet;

import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.net.task.LoginTask.java
 * @Author lbb
 * @Date 2016年9月13日 下午5:42:05
 * @Company
 */
public class LoginTask extends BaseBBXTask {
    LoginBuild build;
    public static long time_start, time_end;

    public LoginTask(Context context, boolean isShow, String phone, String pwd,Back back) {
        super(context, isShow);
        this.back = back;
        time_start = System.currentTimeMillis();
        build = new LoginBuild(context, API_login_Url, phone, pwd);
    }

    @Override
    public void onSuccess(Object object, String msg) {
        time_end = System.currentTimeMillis();
        if (back != null) {
            back.success(object, msg);
        }
    }

    @Override
    public void onFailed(String status, String msg, Object result) {
        // TODO Auto-generated method stub
        super.onFailed(status, msg, result);
        time_end = System.currentTimeMillis();
        if (back != null) {
            back.fail(status, msg, result);
        }
    }

    @Override
    protected Object doInBackground(Object... params) {
        // TODO Auto-generated method stub
        return new LoginNet(context, build.toJson1());
    }


}
