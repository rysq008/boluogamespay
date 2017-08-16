package com.game.helper.activity.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.home.MineCollarDiscountNumberAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.QueryMyAccountTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryMyAccount;
import com.game.helper.sdk.model.returns.QueryMyAccount.Account;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineCollarDiscountNumberActivity extends BaseActivity {

    @BindView(R.id.minediscountnumber_listView)
    ListView minediscountnumber_listView;
    MineCollarDiscountNumberAdapter mMineCollarDiscountNumberAdapter;
    protected List<Account> data = new ArrayList<Account>();
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_mine_collar_discount_number);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("我的折扣号");
        topLeftBack.setVisibility(View.VISIBLE);
        topRight.setText("添加折扣号");
        topRight.setVisibility(View.VISIBLE);
        mMineCollarDiscountNumberAdapter = new MineCollarDiscountNumberAdapter(mContext, data);
        minediscountnumber_listView.setAdapter(mMineCollarDiscountNumberAdapter);
    }

    LoginData user;

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
                        mMineCollarDiscountNumberAdapter.setData(data);
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
    }

    int num = 0;

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_right:
                num = 1;
                startActivity(AddCollarDiscountNumberActivity.class);
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
        MobclickAgent.onPageEnd("MineCollarDiscountNumberActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineCollarDiscountNumberActivity");
        super.onResume();
        if (num == 1) {
            new QueryMyAccountTask(mContext, user.userId, new Back() {

                @Override
                public void success(Object object, String msg) {
                    if (object != null && object instanceof QueryMyAccount) {
                        QueryMyAccount mQueryMyAccount = (QueryMyAccount) object;
                        if (mQueryMyAccount.data != null) {
                            data.clear();
                            data.addAll(mQueryMyAccount.data);
                            mMineCollarDiscountNumberAdapter.setData(data);
                        }
                    }

                }

                @Override
                public void fail(String status, String msg, Object object) {
                    // TODO Auto-generated method stub

                }
            }).start();
            num = 0;
        }
    }

}