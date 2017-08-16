package com.game.helper.activity.mine;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.mine.MineProceedsAdapter;
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
 * @Description 我的收益
 * @Path com.game.helper.activity.mine.MineProceedsActivity.java
 * @Author lbb
 * @Date 2016年8月26日 下午6:39:39
 * @Company
 */
public class MineProceedsActivity extends BaseActivity {

    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_right)
    TextView topRight;
    //@BindView(R.id.tv_Proceed) TextView tv_Proceed;//充值返利
    private List<Trade> mList = new ArrayList<Trade>();
    @BindView(R.id.listView)
    ListView listView;
    MineProceedsAdapter mMineProceedsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_proceeds);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("我的收益");
        topLeftBack.setVisibility(View.VISIBLE);

        topRight.setText("充值返利");
        topRight.setVisibility(View.VISIBLE);
        mMineProceedsAdapter = new MineProceedsAdapter(mContext, mList);
        listView.setAdapter(mMineProceedsAdapter);

    }

    @Override
    protected void initData() {
        LoginData user = DBManager.getInstance(mContext).getUserMessage();
        //-收入
        new GetTradeListTask(mContext, user.userId, "1", null,
                new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        if (object != null && object instanceof GetTradeList) {
                            GetTradeList mGetTradeList = (GetTradeList) object;
                            if (mGetTradeList.data != null) {
                                mList.clear();
                                mList.addAll(mGetTradeList.data);
                                mMineProceedsAdapter.setmList(mList);
                                double ptb = 0;
                                for (Trade mTrade : mList) {
                                    ptb += mTrade.ptb;
                                }
                                BigDecimal bd = new BigDecimal(ptb);
                                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                money.setText("" + bd.toString());
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
    @OnClick({R.id.top_left_layout, R.id.top_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_right:
                startActivity(MineRechargeRebateActivty.class);
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
        MobclickAgent.onPageEnd("MineProceedsActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineProceedsActivity");
        super.onResume();
    }
}
