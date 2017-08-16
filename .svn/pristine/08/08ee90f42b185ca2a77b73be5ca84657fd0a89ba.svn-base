package com.game.helper.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.UpdNickTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 昵称
 * @Path com.game.helper.activity.mine.MineSetingNameActivity.java
 * @Author lbb
 * @Date 2016年8月18日 下午6:02:06
 * @Company
 */
public class MineSetingNameActivity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_setingname);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("昵称");
        topLeftBack.setVisibility(View.VISIBLE);
        topRight.setVisibility(View.VISIBLE);
        topRight.setText("确认");
    }

    LoginData user;

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();
        if (user != null) {
            if (!TextUtils.isEmpty(user.nickName)) {
                et_username.setText("" + user.nickName);
                et_username.setSelection(et_username.getText().length());
            }
        }
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;

            case R.id.top_right:
                if (topRight.isEnabled()) {
                    topRight.setEnabled(false);
                    if (user != null) {
                        final String nickName = et_username.getText().toString();
                        if (!TextUtils.isEmpty(nickName)) {
                            new UpdNickTask(mContext, user.userId, nickName, new Back() {

                                @Override
                                public void success(Object object, String msg) {
                                    // TODO Auto-generated method stub
                                    user.jsonData = null;
                                    user.nickName = nickName;
                                    user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                                    DBManager.getInstance(mContext).insert(user);
                                    ToastUtil.showToast(mContext, "昵称修改成功");
                                    topRight.setEnabled(true);
                                    finish1();
                                }

                                @Override
                                public void fail(String status, String msg, Object object) {
                                    // TODO Auto-generated method stub
                                    topRight.setEnabled(true);
                                    ToastUtil.showToast(mContext, msg);
                                }
                            }).start();
                        } else {
                            topRight.setEnabled(true);
                            ToastUtil.showToast(mContext, "请输入昵称");
                        }

                    } else {
                        topRight.setEnabled(true);
                        ToastUtil.showToast(mContext, "用户不存在");
                    }
                }


                break;
            default:
                super.onClick(v);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish1();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("MineSetingNameActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineSetingNameActivity");
        super.onResume();
    }

}
