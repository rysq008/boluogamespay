package com.game.helper.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.activity.home.RechargeActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetTradeListTask;
import com.game.helper.sdk.model.returns.GetTradeList;
import com.game.helper.sdk.model.returns.GetTradeList.Trade;
import com.game.helper.sdk.model.returns.LoginData;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 我的金币
 * @Path com.game.helper.activity.mine.PlatformCurrencyActivity.java
 * @Author lbb
 * @Date 2016年8月18日 下午7:57:46
 * @Company
 */
public class MinePlatformCurrencyActivity extends BaseActivity {

    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.relat_shouru)
    RelativeLayout relat_shouru;
    @BindView(R.id.relat_zhichu)
    RelativeLayout relat_zhichu;
    @BindView(R.id.relat_guize)
    RelativeLayout relat_guize;
    @BindView(R.id.cz)
    LinearLayout cz;
    @BindView(R.id.tx)
    LinearLayout tx;
    @BindView(R.id.shouruMoney)
    TextView shouruMoney;
    @BindView(R.id.zhichuMoney)
    TextView zhichuMoney;

    @BindView(R.id.relat_tixian)
    RelativeLayout relat_tixian;

    LoginData user;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_platform_currency);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("我的金币");
        topLeftBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        user = DBManager.getInstance(MinePlatformCurrencyActivity.this).getUserMessage();
        if (user != null) {
            if (!TextUtils.isEmpty(user.ptb)) {
                money.setText(user.ptb);
            }
        }
        //支出
        new GetTradeListTask(mContext, user.userId, "0", null,
                new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        if (object != null && object instanceof GetTradeList) {
                            GetTradeList mGetTradeList = (GetTradeList) object;
                            if (mGetTradeList.data != null) {
                                double total = 0;
                                for (Trade mTrade : mGetTradeList.data) {
                                    total += mTrade.ptb;
                                }
                                BigDecimal bd = new BigDecimal(total);
                                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                zhichuMoney.setText("" + bd.toString() + "金币");
                            }
                        }

                    }

                    @Override
                    public void fail(String status, String msg, Object object) {
                        // TODO Auto-generated method stub

                    }
                }).start();
        //-收入
        new GetTradeListTask(mContext, user.userId, "1", null,
                new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        if (object != null && object instanceof GetTradeList) {
                            GetTradeList mGetTradeList = (GetTradeList) object;
                            if (mGetTradeList.data != null) {
                                double total = 0;
                                for (Trade mTrade : mGetTradeList.data) {
                                    total += mTrade.ptb;
                                }
                                BigDecimal bd = new BigDecimal(total);
                                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                shouruMoney.setText("" + bd.toString() + "金币");
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
    @OnClick({R.id.top_left_layout, R.id.relat_shouru, R.id.relat_zhichu,
            R.id.relat_guize, R.id.cz, R.id.tx, R.id.relat_tixian})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.relat_shouru:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("currIndex", 0);
                startActivity(MineMingXiActivity.class, bundle1);
                break;
            case R.id.relat_zhichu:
                Bundle bundle2 = new Bundle();
                bundle2.putInt("currIndex", 1);
                startActivity(MineMingXiActivity.class, bundle2);
                break;
            case R.id.relat_tixian:
                Bundle bundle03 = new Bundle();
                bundle03.putInt("currIndex", 2);
                startActivity(MineMingXiActivity.class, bundle03);
                break;
            case R.id.relat_guize:
                startActivity(MinePlatformCurrencyGZActivity.class);
                break;
            case R.id.cz:
                //充值
                Bundle bundle3 = new Bundle();
                bundle3.putInt("currIndex", 1);
                startActivity(RechargeActivity.class, bundle3);
                break;
            case R.id.tx:
                startActivity(MineRechargeActivity.class);
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
        MobclickAgent.onPageEnd("MinePlatformCurrencyActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MinePlatformCurrencyActivity");
        super.onResume();
    }
}
