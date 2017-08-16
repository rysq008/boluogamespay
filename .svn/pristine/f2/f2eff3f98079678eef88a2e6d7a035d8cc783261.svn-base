package com.game.helper.activity.mine;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.mine.MineRechargeRebateAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetTradeListTask;
import com.game.helper.sdk.model.returns.GetTradeList;
import com.game.helper.sdk.model.returns.GetTradeList.Trade;
import com.game.helper.sdk.model.returns.LoginData;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 充值返利
 * @Path com.game.helper.activity.mine.MineRechargeRebateActivty.java
 * @Author lbb
 * @Date 2016年8月26日 下午6:56:59
 * @Company
 */
public class MineRechargeRebateActivty extends BaseActivity {


    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    private List<Trade> mList = new ArrayList<Trade>();
    MineRechargeRebateAdapter mMineRechargeRebateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activtiy_mine_recharge_rebate);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("充值返利");
        topLeftBack.setVisibility(View.VISIBLE);
        mMineRechargeRebateAdapter = new MineRechargeRebateAdapter(mContext, mList);
        listView.setAdapter(mMineRechargeRebateAdapter);
    }

    @Override
    protected void initData() {

        LoginData user = DBManager.getInstance(mContext).getUserMessage();
        //-收入
        new GetTradeListTask(mContext, user.userId, "1", "充值返利",
                new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        if (object != null && object instanceof GetTradeList) {
                            GetTradeList mGetTradeList = (GetTradeList) object;
                            if (mGetTradeList.data != null) {
                                mList.clear();
                                mList.addAll(mGetTradeList.data);
                                double ptb = 0;
                                for (Trade mTrade : mGetTradeList.data) {
                                    ptb += mTrade.ptb;
                                }
                                BigDecimal bd = new BigDecimal(ptb);
                                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                money.setText("" + bd.toString());
                                mMineRechargeRebateAdapter.setmList(mList);
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
    @OnClick({R.id.top_left_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
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
        MobclickAgent.onPageEnd("MineRechargeRebateActivty");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineRechargeRebateActivty");
        super.onResume();
    }
}