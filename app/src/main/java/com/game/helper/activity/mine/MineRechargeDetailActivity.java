package com.game.helper.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.CptbordergetOrderByNoTask;
import com.game.helper.net.task.GetOrderByNoTask;
import com.game.helper.sdk.model.returns.CptbordergetOrderByNo;
import com.game.helper.sdk.model.returns.GetOrderByNo;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 充值信息
 * @Path com.game.helper.activity.mine.MineRechargeDetailActivity.java
 * @Author lbb
 * @Date 2016年8月19日 下午2:46:04
 * @Company
 */
public class MineRechargeDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    //@BindView(R.id.time) 	TextView time;
    @BindView(R.id.orderNo)
    TextView orderNo;
    @BindView(R.id.orderTime)
    TextView orderTime;
    @BindView(R.id.orderGame)
    TextView orderGame;
    @BindView(R.id.orderType)
    TextView orderType;
    @BindView(R.id.payNo)
    TextView payNo;
    @BindView(R.id.orderMoney)
    TextView orderMoney;
    @BindView(R.id.payMoney)
    TextView payMoney;
    @BindView(R.id.msg)
    TextView tvmsg;
    @BindView(R.id.orderGame1)
    TextView orderGame1;
    @BindView(R.id.payNo1)
    TextView payNo1;

    @BindView(R.id.mLReason)
    LinearLayout mLReason;
    @BindView(R.id.backReason)
    TextView backReason;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_recharge_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        String title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            topTitle.setText(title);
        } else {
            topTitle.setText("充值信息");
        }
        topLeftBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        String orderId = getIntent().getStringExtra("orderId");
        final String tradedate = getIntent().getStringExtra("tradedate");
        final String tradetype = getIntent().getStringExtra("tradetype");
        if (!TextUtils.isEmpty(tradetype)) {
            if (tradetype.equals("7") || tradetype.equals("8")) {
                orderGame1.setText("游戏充值：");
                payNo1.setText("充值账号：");
                new GetOrderByNoTask(mContext, orderId, new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        if (object != null && object instanceof GetOrderByNo) {
                            GetOrderByNo mGetOrderByNo = (GetOrderByNo) object;
                            if (mGetOrderByNo.data != null) {
                                /*if(!TextUtils.isEmpty(mGetOrderByNo.data.status)
										&&mGetOrderByNo.data.status.equals("Y")){
									title.setText("");
									
								}else{
									title.setText("恭喜您，已成功充值！");
									
								}*/
                                if (!TextUtils.isEmpty(mGetOrderByNo.data.dealStatus) && mGetOrderByNo.data.dealStatus.equals("Y")) {
                                    //退款
                                    title.setText("订单状态：已退款");
                                    orderTime.setText(mGetOrderByNo.data.field7);
                                    mLReason.setVisibility(View.VISIBLE);
                                    backReason.setText(mGetOrderByNo.data.field8);
                                } else {
                                    if (!TextUtils.isEmpty(mGetOrderByNo.data.status)
                                            && mGetOrderByNo.data.status.equals("2")) {
                                        title.setText("恭喜您，已成功充值！");
                                        orderTime.setText(mGetOrderByNo.data.payTimeString);
                                    } else if (!TextUtils.isEmpty(mGetOrderByNo.data.status)
                                            && mGetOrderByNo.data.status.equals("1")) {
                                        title.setText("订单状态：已支付 ");
                                        orderTime.setText(mGetOrderByNo.data.payTimeString);

                                    } else {
                                        title.setText("订单状态：待支付 ");
                                        orderTime.setText(mGetOrderByNo.data.createTimeString);
                                    }
                                }


                                orderNo.setText(mGetOrderByNo.data.orderNo);

                                orderGame.setText(mGetOrderByNo.data.gameName);

                                payNo.setText(mGetOrderByNo.data.gameAccount);
                                orderMoney.setText("" + mGetOrderByNo.data.money + "元");
                                payMoney.setText("" + mGetOrderByNo.data.realPay + "元");

                                if (!TextUtils.isEmpty(mGetOrderByNo.data.orderType)
                                        && mGetOrderByNo.data.orderType.equals("sc")) {
                                    orderType.setText("游戏首充" + "  " + tradedate);
                                } else if (!TextUtils.isEmpty(mGetOrderByNo.data.orderType)
                                        && mGetOrderByNo.data.orderType.equals("xc")) {
                                    orderType.setText("游戏续充" + "  " + tradedate);
                                } else if (!TextUtils.isEmpty(mGetOrderByNo.data.orderType)
                                        && mGetOrderByNo.data.orderType.equals("ptb")) {
                                    orderType.setText("金币充值" + "  " + tradedate);
                                }

                                //tvmsg.setText(""+mGetOrderByNo.data.orderNo);

                            }
                        }

                    }

                    @Override
                    public void fail(String status, String msg, Object object) {
                        // TODO Auto-generated method stub

                    }
                }).start();
            } else if (tradetype.equals("6")) {
                orderGame1.setText("充值类型：");
                payNo1.setText("支付方式：");
                new CptbordergetOrderByNoTask(mContext, orderId, new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        if (object != null && object instanceof CptbordergetOrderByNo) {
                            CptbordergetOrderByNo mGetOrderByNo = (CptbordergetOrderByNo) object;
                            if (mGetOrderByNo.data != null) {
                                if (!TextUtils.isEmpty(mGetOrderByNo.data.status)
                                        && mGetOrderByNo.data.status.equals("Y")) {
                                    title.setText(" 恭喜您，已成功充值！");
                                    orderTime.setText(mGetOrderByNo.data.payTimeString);
                                } else {
                                    title.setText("订单状态：待支付");
                                    orderTime.setText(mGetOrderByNo.data.createTimeString);
                                }

                                orderNo.setText(mGetOrderByNo.data.orderNo);

                                if (!TextUtils.isEmpty(mGetOrderByNo.data.field2) && mGetOrderByNo.data.field2.equals("Y")) {
                                    if (!TextUtils.isEmpty(mGetOrderByNo.data.field1)) {
                                        BigDecimal bd = new BigDecimal(mGetOrderByNo.data.field1);
                                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                        orderGame.setText("金币首充（使用卡首充金额" + bd.toString() + "）");
                                    } else {
                                        orderGame.setText("金币首充");
                                    }

                                } else {
                                    orderGame.setText("金币充值");
                                }

                                payNo.setText(mGetOrderByNo.data.payWay);
                                orderMoney.setText("" + mGetOrderByNo.data.ptb + "元");
                                payMoney.setText("" + mGetOrderByNo.data.money + "元");
                                orderType.setText("  " + tradedate);
                            }
                        }
                    }

                    @Override
                    public void fail(String status, String msg, Object object) {
                        // TODO Auto-generated method stub

                    }
                }).start();
            }
        }

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
        MobclickAgent.onPageEnd("MineRechargeDetailActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineRechargeDetailActivity");
        super.onResume();
    }
}
