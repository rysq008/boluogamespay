package com.game.helper.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.adapter.home.SelectCollarDiscountNumberAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.QueryMyAccountTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryMyAccount;
import com.game.helper.sdk.model.returns.QueryMyAccount.Account;
import com.game.helper.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description
 * @Path com.game.helper.activity.home.SelectGameNoActivity.java
 * @Author lbb
 * @Date 2016年11月21日 下午1:29:14
 * @Company
 */
public class SelectGameNoActivity extends BaseActivity {
    @BindView(R.id.lv_selectGameNo)
    ListView lv_selectGameNo;
    SelectCollarDiscountNumberAdapter mSelectCollarDiscountNumberAdapter;

    protected List<Account> data = new ArrayList<Account>();
    LoginData user;
    int type = 0;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.selectgame_sure_tv)
    TextView selectgameSureTv;
    @BindView(R.id.selectgame_account_et)
    EditText selectgameAccountEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_select_game_no);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 0);
        topTitle.setText("选择充值游戏账号");
        topLeftBack.setVisibility(View.VISIBLE);

        mSelectCollarDiscountNumberAdapter = new SelectCollarDiscountNumberAdapter(mContext, data);
        lv_selectGameNo.setAdapter(mSelectCollarDiscountNumberAdapter);
        lv_selectGameNo.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //返回到充值页面
                if (type == 0) {
                    BaseApplication.mInstance.put(KEY_SelectGameNNo, mSelectCollarDiscountNumberAdapter.getItem(position));
                    finish();
                    sendBroadcast(new Intent(ACTION_SelectGameNNo));
                } else if (type == 1) {
                    BaseApplication.mInstance.put(KEY_SelectGameNNo1, mSelectCollarDiscountNumberAdapter.getItem(position));
                    finish();
                    sendBroadcast(new Intent(ACTION_SelectGameNNo1));
                }

            }
        });
    }

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();
        new QueryMyAccountTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryMyAccount) {
                    QueryMyAccount mQueryMyAccount = (QueryMyAccount) object;
                    if (mQueryMyAccount.data != null) {
                        data.clear();
                        data.addAll(mQueryMyAccount.data);
                        mSelectCollarDiscountNumberAdapter.setData(data);
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.selectgame_sure_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;

            case R.id.selectgame_sure_tv:
                String game_account = selectgameAccountEt.getText().toString().trim();
                if (!TextUtils.isEmpty(game_account)){
                    Account account=new Account();
                    account.gameAccount=game_account;
                    BaseApplication.mInstance.put(KEY_SelectGameNNo, account);
                    finish();
                    sendBroadcast(new Intent(ACTION_SelectGameNNo));
                }else{
                    ToastUtil.showToast(this,"账号不能为空");
                }
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageStart("SelectGameNoActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageEnd("SelectGameNoActivity");
        super.onResume();
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

}
