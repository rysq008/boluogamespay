package com.game.helper.activity.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.QueryPtbTask;
import com.game.helper.pay.PayPtb;
import com.game.helper.pay.PayPtb.setEnabledCallbackPayPtb;
import com.game.helper.pay.alipay.PayAlipay;
import com.game.helper.pay.alipay.PayAlipay.OnAliPayFinishListener;
import com.game.helper.pay.alipay.PayAlipay.setEnabledCallback;
import com.game.helper.pay.alipay.PayAlipayPtb;
import com.game.helper.pay.wechat.Constants;
import com.game.helper.pay.wechat.PayWeixin;
import com.game.helper.pay.wechat.PayWeixin.setEnabledCallbackWX;
import com.game.helper.pay.wechat.PayWeixinPtb;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryPtb;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.ToastUtil;
import com.game.helper.wxapi.WXPayEntryActivity;
import com.game.helper.wxapi.WXPayEntryActivity.OnWeiXinPayFinishListener;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaySelectActivity extends BaseActivity implements OnWeiXinPayFinishListener,
        OnAliPayFinishListener, setEnabledCallback, setEnabledCallbackWX, setEnabledCallbackPayPtb {
    /* @BindView(R.id.mLinearFirst)
     LinearLayout mLinearFirst;
     @BindView(R.id.tv_First)
     TextView tv_First;
     @BindView(R.id.iv_select10)
     ImageView iv_select10;*/
    @BindView(R.id.mLinearPtb)
    LinearLayout mLinearPtb;
    @BindView(R.id.tv_Ptb)
    TextView tv_Ptb;
    @BindView(R.id.iv_select11)
    ImageView iv_select11;

    @BindView(R.id.iv_select0)
    ImageView iv_select0;//微信支付
    @BindView(R.id.iv_select1)
    ImageView iv_select1;//支付宝支付
    @BindView(R.id.tv_Money)
    TextView tv_Money;//
    @BindView(R.id.btn_cancel)
    TextView btn_cancel;//

    String peyTay = "weixin";
    String key = "xc";
    double ptb = 0;
    LoginData user;
    double realPay = 0;
    //double card = 0;

    double iv_Money = 0;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.home_payselect_gamename)
    TextView homePayselectGamename;
    @BindView(R.id.home_payselect_gameid)
    TextView homePayselectGameid;
    @BindView(R.id.home_payselect_formermoney)
    TextView homePayselectFormermoney;
    @BindView(R.id.home_payselect_from)
    TextView homePayselectFrom;
    @BindView(R.id.pay_recharge_game_ll)
    LinearLayout payRechargeGameLl;
    @BindView(R.id.pay_recharge_account_ll)
    LinearLayout payRechargeAccountLl;
    @BindView(R.id.pay_recharge_client_ll)
    LinearLayout payRechargeClientLl;
    @BindView(R.id.btn_ensure)
    Button btnEnsure;

    private AppContent appContent;
    private double aDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_select_pay_recharge);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        key = getIntent().getStringExtra("key");
        appContent = getIntent().getParcelableExtra("appcontent");
        ptb = TextUtils.isEmpty(getIntent().getStringExtra("ptb"))
                ? 0 : Double.valueOf(getIntent().getStringExtra("ptb"));
        /*card = TextUtils.isEmpty(getIntent().getStringExtra("card"))
                ? 0 : Double.valueOf(getIntent().getStringExtra("card"));首充卡*/
        topTitle.setText("确认订单");
        topLeftBack.setVisibility(View.VISIBLE);
        peyTay = "weixin";
        //iv_select10.setSelected(false);
        iv_select11.setSelected(false);

        iv_select0.setSelected(true);
        iv_select1.setSelected(false);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        isPaySupported = api.isWXAppInstalled() && api.isWXAppSupportAPI();
    }

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();
        if (!TextUtils.isEmpty(key)) {
            if (key.equals("xc")) {
                realPay = TextUtils.isEmpty(getIntent().getStringExtra("realPay"))
                        ? 0 : Double.valueOf(getIntent().getStringExtra("realPay"));
                if ((realPay - ptb) <= 0) {
                    iv_Money = 0;
                    iv_select0.setSelected(false);
                    iv_select1.setSelected(false);
                    peyTay = "ptb";
                } else {
                    iv_Money = realPay - ptb;
                    BigDecimal bd = new BigDecimal(iv_Money);
                    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                    iv_Money = bd.doubleValue();

                }
                tv_Money.setText("￥" + iv_Money);

                //mLinearFirst.setVisibility(View.GONE);
                //mView.setVisibility(View.GONE);
                mLinearPtb.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(user.ptb)) {
                    tv_Ptb.setText("" + user.ptb);
                    ptb = Double.valueOf(user.ptb);
                }


            } else if (key.equals("sc")) {

                realPay = TextUtils.isEmpty(getIntent().getStringExtra("realPay"))
                        ? 0 : Double.valueOf(getIntent().getStringExtra("realPay"));
                if ((realPay - ptb) <= 0) {
                    iv_Money = 0;
                    peyTay = "ptb";
                    iv_select0.setSelected(false);
                    iv_select1.setSelected(false);
                } else {
                    iv_Money = realPay - ptb;
                    BigDecimal bd = new BigDecimal(iv_Money);
                    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                    iv_Money = bd.doubleValue();
                }
                tv_Money.setText("￥" + iv_Money);

                if (!TextUtils.isEmpty(user.ptb)) {
                    tv_Ptb.setText("" + user.ptb);
                    ptb = Double.valueOf(user.ptb);
                }
                //mLinearFirst.setVisibility(View.GONE);
                //mView.setVisibility(View.GONE);
                mLinearPtb.setVisibility(View.VISIBLE);

            } else if (key.equals("ptb")) {
                realPay = TextUtils.isEmpty(getIntent().getStringExtra("money"))
                        ? 0 : Double.valueOf(getIntent().getStringExtra("money"));
                ptb = 0;//不能用金币抵扣
                if ((realPay - ptb) <= 0) {
                    iv_Money = 0;
                } else {
                    iv_Money = realPay - ptb;
                    BigDecimal bd = new BigDecimal(iv_Money);
                    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                    iv_Money = bd.doubleValue();
                }
                tv_Money.setText("￥" + iv_Money);
                payRechargeAccountLl.setVisibility(View.GONE);
                payRechargeClientLl.setVisibility(View.GONE);
                payRechargeGameLl.setVisibility(View.GONE);
                mLinearPtb.setVisibility(View.GONE);
            }

            if (!key.equals("ptb")) {

                if (!appContent.gameName.isEmpty()) {
                    homePayselectGamename.setText(appContent.gameName);
                }
                if (!getIntent().getStringExtra("gameAccount").isEmpty()) {
                    homePayselectGameid.setText(getIntent().getStringExtra("gameAccount"));
                }
                if (!appContent.platName.isEmpty()) {
                    homePayselectFrom.setText(appContent.platName);
                }
            }
        }


        if (!getIntent().getStringExtra("money").isEmpty()) {
            homePayselectFormermoney.setText(getIntent().getStringExtra("money"));
        }


    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.iv_select0, R.id.iv_select1
            , R.id.btn_cancel/*, R.id.iv_select10*/, R.id.iv_select11, R.id.btn_ensure})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
            case R.id.top_left_layout:
                finish1();
                break;

            case R.id.iv_select11:
                if (iv_select11.isSelected()) {
                    iv_select11.setSelected(false);
                   /* if (iv_select10.isSelected()) {
                        if ((realPay - card) <= 0) {
                            iv_Money = 0;
                            iv_select0.setSelected(false);
                            iv_select1.setSelected(false);
                            peyTay = "ptb";
                        } else {
                            iv_Money = realPay - card;
                        }
                        BigDecimal bd = new BigDecimal(iv_Money);
                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                        iv_Money = bd.doubleValue();
                        tv_Money.setText("￥" + iv_Money);
                    } else {*/
                    if ((realPay) <= 0) {
                        iv_Money = 0;
                        iv_select0.setSelected(false);
                        iv_select1.setSelected(false);
                        peyTay = "ptb";
                    } else {
                        iv_Money = realPay;
                    }
                    BigDecimal bd = new BigDecimal(iv_Money);
                    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                    iv_Money = bd.doubleValue();
                    tv_Money.setText("￥" + iv_Money);
                    //  }

                } else {
                    iv_select11.setSelected(true);
                    /*if (iv_select10.isSelected()) {
                        if ((realPay - ptb - card) <= 0) {
                            iv_Money = 0;
                            iv_select0.setSelected(false);
                            iv_select1.setSelected(false);
                            peyTay = "ptb";
                        } else {
                            iv_Money = realPay - ptb - card;
                        }
                        BigDecimal bd = new BigDecimal(iv_Money);
                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                        iv_Money = bd.doubleValue();
                        tv_Money.setText("￥" + iv_Money);
                    } else {*/
                    if ((realPay - ptb) <= 0) {
                        iv_Money = 0;
                        iv_select0.setSelected(false);
                        iv_select1.setSelected(false);
                        peyTay = "ptb";
                    } else {
                        iv_Money = realPay - ptb;
                    }
                    BigDecimal bd = new BigDecimal(iv_Money);
                    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                    iv_Money = bd.doubleValue();
                    tv_Money.setText("￥" + iv_Money);
                    //}
                }

                break;
            case R.id.iv_select0:
                if (iv_Money <= 0) {
                    iv_select0.setSelected(false);
                    iv_select1.setSelected(false);
                    peyTay = "ptb";
                    ToastUtil.showToast(mContext, "请先取消勾选使用金币抵扣，在金币抵扣充足时只能使用一种支付方式，不足抵扣时允许同时选择其他支付");
                } else {

                    peyTay = "weixin";
                    iv_select0.setSelected(true);
                    iv_select1.setSelected(false);
                }
                break;
            case R.id.iv_select1:
                if (iv_Money <= 0) {
                    iv_select0.setSelected(false);
                    iv_select1.setSelected(false);
                    peyTay = "ptb";
                    ToastUtil.showToast(mContext, "请先取消勾选使用金币抵扣，在金币抵扣充足时只能使用一种支付方式，不足抵扣时允许同时选择其他支付");
                } else {
                    peyTay = "alipay";
                    iv_select1.setSelected(true);
                    iv_select0.setSelected(false);
                }
                break;
            case R.id.btn_ensure:
                if (key.equals("ptb")) {
                    if ((!iv_select0.isSelected()) && (!iv_select1.isSelected())) {
                        ToastUtil.showToast(mContext, "请选择支付方式");
                        return;
                    }
                } else {
                    if ((!iv_select0.isSelected()) && (!iv_select1.isSelected()) && (!iv_select11.isSelected())) {
                        ToastUtil.showToast(mContext, "请选择支付方式");
                        return;
                    }
                }
                if (peyTay.equals("weixin")) {
                    if (!isPaySupported) {
                        //未安装微信//只能选择支付宝方式
                        ToastUtil.showToast(mContext, "未安装微信,请选择其他支付方式");
                    } else {
                        if (key.equals("xc")) {
                            double ptbs = 0;
                            if (iv_select11.isSelected()) {
                                if ((realPay - ptb) <= 0) {
                                    ptbs = realPay;
                                } else {
                                    ptbs = ptb;
                                }
                                BigDecimal bd = new BigDecimal(ptbs);
                                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                ptbs = bd.doubleValue();

                            }
                            btn_cancel.setEnabled(false);
                            WXPayEntryActivity.setOnWeiXinPayFinishListener(PaySelectActivity.this);
                            PayWeixin mPayWeixin = new PayWeixin(PaySelectActivity.this,
                                    user.userId
                                    , getIntent().getStringExtra("gameId")
                                    , getIntent().getStringExtra("gameAccount")
                                    , key
                                    , "" + ptbs
                                    , getIntent().getStringExtra("money")
                                    , "" + (iv_Money)
                                    , getIntent().getStringExtra("saveMoney"));
                            mPayWeixin.setMsetEnabledCallbackWX(PaySelectActivity.this);
                            mPayWeixin.pay();
                        } else if (key.equals("ptb")) {

                            btn_cancel.setEnabled(false);
                            WXPayEntryActivity.setOnWeiXinPayFinishListener(PaySelectActivity.this);
                            PayWeixinPtb mPayWeixin = new PayWeixinPtb(PaySelectActivity.this,
                                    user.userId
                                    , getIntent().getStringExtra("ptb")
                                    , "" + (iv_Money)
                            );
                            mPayWeixin.setMsetEnabledCallbackWX(PaySelectActivity.this);
                            mPayWeixin.pay();
                        } else if (key.equals("sc")) {
                            double cards = 0;
                           /* if (iv_select10.isSelected()) {
                                cards = card;
                            } else {
                                cards = 0;
                            }*/
                            cards = 0;
                            double ptbs = 0;
                            if (iv_select11.isSelected()) {
                                if ((realPay - cards - ptb) <= 0) {
                                    ptbs = realPay - cards;
                                } else {
                                    ptbs = ptb;
                                }
                                BigDecimal bd = new BigDecimal(ptbs);
                                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                ptbs = bd.doubleValue();
                            }
                            btn_cancel.setEnabled(false);
                            WXPayEntryActivity.setOnWeiXinPayFinishListener(PaySelectActivity.this);
                            PayWeixin mPayWeixin = new PayWeixin(PaySelectActivity.this,
                                    user.userId
                                    , getIntent().getStringExtra("gameId")
                                    , getIntent().getStringExtra("gameAccount")
                                    , key
                                    , "" + ptbs
                                    , getIntent().getStringExtra("money")
                                    //, "" + cards
                                    , "" + (iv_Money)
                                    , getIntent().getStringExtra("saveMoney"));
                            mPayWeixin.setMsetEnabledCallbackWX(PaySelectActivity.this);
                            mPayWeixin.pay();
                        }
                    }


                } else if (peyTay.equals("alipay")) {
                    if (key.equals("xc")) {
                        double ptbs = 0;
                        if (iv_select11.isSelected()) {
                            if ((realPay - ptb) <= 0) {
                                ptbs = realPay;
                            } else {
                                ptbs = ptb;
                            }

                        }
                        btn_cancel.setEnabled(false);
                        PayAlipay mPayAlipay = new PayAlipay(PaySelectActivity.this,
                                user.userId
                                , getIntent().getStringExtra("gameId")
                                , getIntent().getStringExtra("gameAccount")
                                , key
                                , "" + ptbs
                                , getIntent().getStringExtra("money")
                                //, "" + card
                                , "" + iv_Money
                                , getIntent().getStringExtra("saveMoney")
                        );
                        mPayAlipay.setOnAliPayFinishListener(PaySelectActivity.this);
                        mPayAlipay.setMsetEnabledCallback(PaySelectActivity.this);
                        mPayAlipay.pay();
                    } else if (key.equals("ptb")) {
                        /* double cards = 0;
                       if (iv_select10.isSelected()) {
                            cards = card;
                        } else {
                            cards = 0;
                        }*/
                        btn_cancel.setEnabled(false);
                        PayAlipayPtb mPayAlipay = new PayAlipayPtb(PaySelectActivity.this,
                                user.userId
                                , getIntent().getStringExtra("ptb")
                                , "" + iv_Money
                                /*, "" + cards*/);
                        mPayAlipay.setOnAliPayFinishListener(PaySelectActivity.this);
                        mPayAlipay.setMsetEnabledCallback(PaySelectActivity.this);
                        mPayAlipay.pay();
                    } else if (key.equals("sc")) {
                        double cards = 0;
                       /* if (iv_select10.isSelected()) {
                            cards = card;
                        } else {
                            cards = 0;
                        }*/
                        cards = 0;
                        double ptbs = 0;
                        if (iv_select11.isSelected()) {
                            if ((realPay - cards - ptb) <= 0) {
                                ptbs = realPay - cards;
                            } else {
                                ptbs = ptb;
                            }
                            BigDecimal bd = new BigDecimal(ptbs);
                            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                            ptbs = bd.doubleValue();
                        }
                        btn_cancel.setEnabled(false);
                        PayAlipay mPayAlipay = new PayAlipay(PaySelectActivity.this,
                                user.userId
                                , getIntent().getStringExtra("gameId")
                                , getIntent().getStringExtra("gameAccount")
                                , key
                                , "" + ptbs
                                , getIntent().getStringExtra("money")
                                //, "" + cards
                                , "" + iv_Money
                                , getIntent().getStringExtra("saveMoney")
                        );
                        mPayAlipay.setOnAliPayFinishListener(PaySelectActivity.this);
                        mPayAlipay.setMsetEnabledCallback(PaySelectActivity.this);
                        mPayAlipay.pay();

                    }
                } else if (peyTay.equals("ptb")) {//金币支付方式
                    if (key.equals("xc")) {
                        double ptbs = 0;
                        if (iv_select11.isSelected()) {
                            if ((realPay - ptb) <= 0) {
                                ptbs = realPay;
                            } else {
                                ptbs = ptb;
                            }

                        }
                        btn_cancel.setEnabled(false);
                        PayPtb mPayPtb = new PayPtb(PaySelectActivity.this,
                                user.userId,
                                getIntent().getStringExtra("gameId"),
                                getIntent().getStringExtra("gameAccount"),
                                key,
                                "" + ptbs,
                                getIntent().getStringExtra("money"),
                                //"" + card,
                                "" + iv_Money,
                                getIntent().getStringExtra("saveMoney"));
                        mPayPtb.setMsetEnabledCallbackPayPtb(PaySelectActivity.this);
                        mPayPtb.pay();
                    } else if (key.equals("sc")) {

                        double cards = 0;
                        /*if (iv_select10.isSelected()) {
                            cards = card;
                        } else {
                            cards = 0;
                        }*/
                        cards = 0;
                        double ptbs = 0;
                        if (iv_select11.isSelected()) {
                            if ((realPay - cards - ptb) <= 0) {
                                ptbs = realPay - cards;
                            } else {
                                ptbs = ptb;
                            }
                            BigDecimal bd = new BigDecimal(ptbs);
                            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                            ptbs = bd.doubleValue();
                        }
                        btn_cancel.setEnabled(false);

                        PayPtb mPayPtb = new PayPtb(PaySelectActivity.this,
                                user.userId,
                                getIntent().getStringExtra("gameId"),
                                getIntent().getStringExtra("gameAccount"),
                                key,
                                "" + ptbs,
                                getIntent().getStringExtra("money"),
                                //"" + cards,
                                "" + iv_Money,
                                getIntent().getStringExtra("saveMoney"));
                        mPayPtb.setMsetEnabledCallbackPayPtb(PaySelectActivity.this);
                        mPayPtb.pay();

                    } else if (key.equals("ptb")) {
                        ToastUtil.showToast(mContext, "金币充值请选择微信支付或者支付宝支付");
                    }

                }


                break;
            default:
                super.onClick(v);
                break;
        }
    }

    private IWXAPI api;
    private boolean isPaySupported = false;

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
    public void callbackWX(boolean is) {
        // TODO Auto-generated method stub
        btn_cancel.setEnabled(is);
    }

    @Override
    public void callback(boolean is) {
        // TODO Auto-generated method stub
        btn_cancel.setEnabled(is);
    }


    @Override
    public void callbackWeiXin(boolean is) {
        // TODO Auto-generated method stub
        btn_cancel.setEnabled(is);
    }

    @Override
    public void onAliPayFinish(boolean isSuccess, String orderID, String price) {
        if (isSuccess) {
            new QueryPtbTask(mContext, user.userId, new Back() {

                @Override
                public void success(Object object, String msg) {
                    if (object != null && object instanceof QueryPtb) {
                        QueryPtb mQueryPtb = (QueryPtb) object;
                        if (mQueryPtb.data != null) {
                            user.jsonData = null;
                            user.ptb = mQueryPtb.data;
                            user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                            DBManager.getInstance(mContext).insert(user);
                            //user=DBManager.getInstance(getActivity()).getUserMessage();

                        }
                    }

                }

                @Override
                public void fail(String status, String msg, Object object) {
                    // TODO Auto-generated method stub

                }
            }).start();
            btn_cancel.setEnabled(true);
            finish1();
            ToastUtil.showToast(mContext, "支付成功");
        } else {
            btn_cancel.setEnabled(true);
            ToastUtil.showToast(mContext, "支付失败");
        }

    }

    @Override
    public void onWeiXinPayFinish(boolean isSuccess) {
        if (isSuccess) {
            new QueryPtbTask(mContext, user.userId, new Back() {

                @Override
                public void success(Object object, String msg) {
                    if (object != null && object instanceof QueryPtb) {
                        QueryPtb mQueryPtb = (QueryPtb) object;
                        if (mQueryPtb.data != null) {
                            user.jsonData = null;
                            user.ptb = mQueryPtb.data;
                            user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                            DBManager.getInstance(mContext).insert(user);
                            //user=DBManager.getInstance(getActivity()).getUserMessage();

                        }
                    }

                }

                @Override
                public void fail(String status, String msg, Object object) {
                    // TODO Auto-generated method stub

                }
            }).start();
            btn_cancel.setEnabled(true);
            finish1();
            ToastUtil.showToast(mContext, "支付成功");

        } else {
            btn_cancel.setEnabled(true);
            ToastUtil.showToast(mContext, "支付失败");
        }
    }

    @Override
    public void onWeiXinPaying(String orderID, String price) {
        // TODO Auto-generated method stub

    }

    @Override
    public void callbackPayPtb(boolean is) {
        btn_cancel.setEnabled(true);
        if (is) {
            new QueryPtbTask(mContext, user.userId, new Back() {

                @Override
                public void success(Object object, String msg) {
                    if (object != null && object instanceof QueryPtb) {
                        QueryPtb mQueryPtb = (QueryPtb) object;
                        if (mQueryPtb.data != null) {
                            user.jsonData = null;
                            user.ptb = mQueryPtb.data;
                            user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                            DBManager.getInstance(mContext).insert(user);
                            //user=DBManager.getInstance(getActivity()).getUserMessage();

                        }
                    }

                }

                @Override
                public void fail(String status, String msg, Object object) {
                    // TODO Auto-generated method stub

                }
            }).start();

            finish1();
            ToastUtil.showToast(mContext, "支付成功");
        }

    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("PaySelectActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("PaySelectActivity");
        super.onResume();
    }

}
