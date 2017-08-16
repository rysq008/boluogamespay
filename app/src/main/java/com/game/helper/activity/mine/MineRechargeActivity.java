package com.game.helper.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.AllowTxMoneyTask;
import com.game.helper.net.task.SaveWithdrawTask;
import com.game.helper.sdk.model.returns.AllowTxMoney;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailogEd1;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 提现
 * @Path com.game.helper.activity.mine.MineRechargeActivity.java
 * @Author lbb
 * @Date 2016年8月19日 上午9:04:09
 * @Company
 */
public class MineRechargeActivity extends BaseActivity {

    @BindView(R.id.money)
    TextView tvmoney;
    @BindView(R.id.relat_weixin)
    RelativeLayout relat_weixin;
    @BindView(R.id.relat_alipay)
    RelativeLayout relat_alipay;
    @BindView(R.id.tv_Num)
    EditText tv_Num;
    @BindView(R.id.tv_Name)
    EditText tv_Name;
    @BindView(R.id.et_money)
    EditText et_money;
    @BindView(R.id.btn_toGet)
    Button btn_toGet;
    @BindView(R.id.iv_select0)
    ImageView iv_select0;
    @BindView(R.id.iv_select1)
    ImageView iv_select1;
    LoginData user;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_recharge);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("提现");
        topLeftBack.setVisibility(View.VISIBLE);
        txWay = "alipay";
        user = DBManager.getInstance(MineRechargeActivity.this).getUserMessage();
    }

    double numMoney = 0;

    @Override
    protected void initData() {
        /*if(user!=null){
			if(!TextUtils.isEmpty(user.ptb)){
				money.setText(""+user.ptb);
			}
		}*/
        new AllowTxMoneyTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof AllowTxMoney) {
                    AllowTxMoney mAllowTxMoney = (AllowTxMoney) object;
                    if (mAllowTxMoney != null) {
                        BigDecimal bd = new BigDecimal(mAllowTxMoney.data);
                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                        numMoney = Double.parseDouble(bd.toString());
                        tvmoney.setText("" + bd.toString());
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
    }

    String txWay = "alipay";//付宝：alipay，微信支付：weixin

    @Override
    @OnClick({R.id.top_left_layout, R.id.btn_toGet, R.id.relat_weixin, R.id.relat_alipay})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.relat_weixin:
                iv_select0.setImageResource(R.drawable.btn_choice_down);
                iv_select1.setImageResource(R.drawable.icon_qx_g);
                txWay = "weixin";
                break;
            case R.id.relat_alipay:
                iv_select0.setImageResource(R.drawable.icon_qx_g);
                iv_select1.setImageResource(R.drawable.btn_choice_down);
                txWay = "alipay";
                break;
            case R.id.btn_toGet:
                final MyAlertDailogEd1 dialog = new MyAlertDailogEd1(mContext);
                dialog.setTitle("验证登录密码");
                dialog.setLeftButtonText("取消");
                dialog.setHeightAndHint(60, "请输入您的登录密码");
                dialog.setRightButtonText("确定");
                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }
                dialog.setOnClickLeftListener(new MyAlertDailogEd1.onClickLeftListener() {
                    @Override
                    public void onClickLeft() {
                        dialog.dismiss();

                    }
                });
                dialog.setOnClickRightListener(new MyAlertDailogEd1.onClickRightListener() {

                    @Override
                    public void onClickRight() {
                        String txet = dialog.getEdText();
                        if (!TextUtils.isEmpty(txet) && txet.equals(user.pwd)) {
                            if (TextUtils.isEmpty(tv_Num.getText().toString())) {
                                ToastUtil.showToast(mContext, "请输入提现账户");
                            } else if (TextUtils.isEmpty(tv_Name.getText().toString())) {
                                ToastUtil.showToast(mContext, "请输入提现账户名称");
                            } else if (TextUtils.isEmpty(et_money.getText().toString()) || TextUtils.isEmpty(txet) && txet.equals(et_money.getText().toString())
                                    || et_money.getText().toString().equals(".")) {
                                ToastUtil.showToast(mContext, "请输入需提现金额");
                            } else {

                                if (Double.parseDouble(et_money.getText().toString()) <= 100) {
                                    ToastUtil.showToast(mContext, "提现金额需大于100");
                                } else if (Double.parseDouble(et_money.getText().toString()) > numMoney) {
                                    ToastUtil.showToast(mContext, "提现金额需小于可提现金额");
                                } else {
                                    dialog.dismiss();
                                    new SaveWithdrawTask(mContext, user.userId, et_money.getText().toString(), txWay,
                                            tv_Num.getText().toString(),
                                            tv_Name.getText().toString(), new Back() {

                                        @Override
                                        public void success(Object object, String msg) {
                                            // TODO Auto-generated method stub
                                            ToastUtil.showToast(mContext, "已成功发起提现申请，请注意查收");
                                            initData();
                                        }

                                        @Override
                                        public void fail(String status, String msg, Object object) {
                                            // TODO Auto-generated method stub
                                            ToastUtil.showToast(mContext, msg);
                                        }
                                    }).start();
                                }
                            }

                        } else {
                            ToastUtil.showToast(mContext, "密码错误");
                        }

                    }
                });
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
        MobclickAgent.onPageEnd("MineRechargeActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineRechargeActivity");
        super.onResume();
    }
}
