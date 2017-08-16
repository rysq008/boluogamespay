package com.game.helper.fragment.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.home.PaySelectActivity;
import com.game.helper.activity.home.SelectGameActivity;
import com.game.helper.activity.home.SelectGameNoActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.interfaces.broadcast.BaseBroadcast;
import com.game.helper.model.home.RecommendBoutique;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetBasicTask;
import com.game.helper.net.task.GetRechargeWayTask;
import com.game.helper.net.task.QueryPtbTask;
import com.game.helper.sdk.model.returns.GetBasic;
import com.game.helper.sdk.model.returns.GetBasic.Datas;
import com.game.helper.sdk.model.returns.GetRechargeModel;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryMyAccount.Account;
import com.game.helper.sdk.model.returns.QueryPtb;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickSingleListener;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")

/**
 * @Description 充值-游戏
 * @Path com.game.helper.fragment.home.RechargeGameFragment.java
 * @Author lbb
 * @Date 2016年8月23日 下午7:09:18
 * @Company
 */
public class RechargeGameFragment extends BaseFragment {
    @BindView(R.id.recharge_chose_game_name_tv)
    TextView rechargeChoseGameNameTv;
    @BindView(R.id.xcsc_tv)
    TextView xcscTv;
    @BindView(R.id.call_service_ll)
    LinearLayout callServiceLl;


    //private RechargeAdapter mRechargeAdapter;
    private List<RecommendBoutique> mList = new ArrayList<RecommendBoutique>();
    //ListViewForScrollView recharge_ListView ;

    TextView tv_numbers;
    TextView et_mNumber;
    EditText et_mMoney;
    //TextView tv_recharge1;//新账号首充
    //TextView tv_recharge2;//折扣号续充
    RelativeLayout relat_select_game;//选择充值游戏
    //LinearLayout mLinear_gamePackagedetail;//选择后的充值游戏展示
    LinearLayout mLinear_moneys;//充值金额
    //LinearLayout bottom1;//新账号首充
    LinearLayout bottom2;//折扣号续充
    //TextView tv_getsrecharges;//点击立即领取
    //LinearLayout mPackageDetails_top;//新账号首充套餐(头部)

    ImageView iv_select0;
    ImageView iv_select1;
    RelativeLayout relat_weixin;
    RelativeLayout relat_alipay;
    TextView tv_Exchange;

    //LinearLayout trecharge1;//新账号说明
    //LinearLayout trecharge2;

    //XCRoundImageViewByXfermode iv_item;//选中的游戏信息
    //LinearLayout mLinear_dz;
    //TextView tv_dz;
    TextView tv_get;

    LinearLayout mLinearLayout_bottom2;//xc的支付按钮
    TextView tv_num0;//为您节省
    TextView tv_num1;//需付的钱

    LinearLayout mLinear_select1;
    private String orderType;
    private String orderType_zk;
    private String gamename;
    private String gameaccount;
    private Double xczk;

    //TextView tv_Contexts;
    public RechargeGameFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    public RechargeGameFragment(BaseApplication application, Activity activity, Context context) {
        super(application, activity, context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mApplication = (BaseApplication) getActivity().getApplication();
        mApplication.context = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home_recharge_game, container, false);
            ButterKnife.bind(this, mView);
            initViews();
            initEvents();
            init();
        }
        FragmentCache(mView);
        return mView;
    }


    @Override
    protected void initViews() {
        //tv_Contexts=(TextView) findViewById(R.id.tv_Contexts);

        mLinear_select1 = (LinearLayout) findViewById(R.id.mLinear_select1);

        tv_num0 = (TextView) findViewById(R.id.tv_num0);
        tv_num1 = (TextView) findViewById(R.id.tv_num1);

        et_mMoney = (EditText) findViewById(R.id.et_mMoney);
        mLinearLayout_bottom2 = (LinearLayout) findViewById(R.id.mLinearLayout_bottom2);
        //iv_item = (XCRoundImageViewByXfermode) findViewById(R.id.iv_item);
        //mLinear_dz = (LinearLayout) findViewById(R.id.mLinear_dz);
        //tv_dz = (TextView) findViewById(R.id.tv_dz);
        tv_get = (TextView) findViewById(R.id.tv_get);
        //tv_num = (TextView) findViewById(R.id.tv_num);
        //tv_item = (TextView) findViewById(R.id.tv_item);

        //trecharge1 = (LinearLayout) findViewById(R.id.trecharge1);
        //trecharge2 = (LinearLayout) findViewById(R.id.trecharge2);

        //recharge_ListView=(ListViewForScrollView) findViewById(R.id.recharge_ListView);
        //mRechargeAdapter=new RechargeAdapter(getActivity(), mList);
        //recharge_ListView.setAdapter(mRechargeAdapter);

        tv_numbers = (TextView) findViewById(R.id.tv_numbers);
        tv_Exchange = (TextView) findViewById(R.id.tv_Exchange);
        //tv_recharge1 = (TextView) findViewById(R.id.tv_recharge1);
        //tv_recharge2 = (TextView) findViewById(R.id.tv_recharge2);

        relat_select_game = (RelativeLayout) findViewById(R.id.relat_select_game);

        et_mNumber = (TextView) findViewById(R.id.et_mNumber);

        //mLinear_gamePackagedetail = (LinearLayout) findViewById(R.id.mLinear_gamePackagedetail);
        mLinear_moneys = (LinearLayout) findViewById(R.id.mLinear_moneys);
        //bottom1 = (LinearLayout) findViewById(R.id.bottom1);
        bottom2 = (LinearLayout) findViewById(R.id.bottom2);
        //tv_getsrecharges = (TextView) findViewById(R.id.tv_getsrecharges);
        //mPackageDetails_top = (LinearLayout) findViewById(R.id.mPackageDetails);

        et_mMoney.setSelection(et_mMoney.getText().length());
        /*tv_numbers.setEnabled(true);
        tv_numbers.setFocusable(true);
		tv_numbers.setFocusableInTouchMode(true);
		tv_numbers.requestFocus();*/

    }

    @Override
    protected void initEvents() {
        // TODO Auto-generated method stub
        mLinear_select1.setOnClickListener(this);
        //trecharge1.setOnClickListener(this);
        //trecharge2.setOnClickListener(this);

        tv_Exchange.setOnClickListener(this);
        //tv_recharge1.setOnClickListener(this);
        //tv_recharge2.setOnClickListener(this);
        relat_select_game.setOnClickListener(this);
        //tv_getsrecharges.setOnClickListener(this);
        /*recharge_ListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				((BaseActivity)getActivity()).startActivity(RechargePackageDetailsActivity.class);
			}
		});*/
        //限制2位小数点
        et_mMoney.addTextChangedListener(new TextWatcher() {

            private double sn1;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(gamename) || TextUtils.isEmpty(gameaccount)) {
                    ToastUtil.showToast(getActivity(), "请选择游戏和账号");
                    return;
                    //et_mMoney.setText("");
                }

                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        et_mMoney.setText(s);
                        et_mMoney.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    et_mMoney.setText(s);
                    et_mMoney.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        et_mMoney.setText(s.subSequence(0, 1));
                        et_mMoney.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(orderType_zk)) {
                    ToastUtil.showToast(getActivity(), "请选择账号");
                    et_mNumber.setText("");
                    return;
                }

                if (!TextUtils.isEmpty(s.toString()) &&
                        s.toString().equals(".") == false) {
                    String[] a = s.toString().split(".");
                    if (a.length != 1) {
                        double sn2 = Double.valueOf(s.toString());
                        BigDecimal bd = new BigDecimal(sn2);
                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                        sn2 = bd.doubleValue();

                        if (xczk <= 0) {
                            sn1 = sn2;
                        } else {
                            sn1 = sn2 * xczk;
                        }
                        bd = new BigDecimal(sn1);
                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                        //sn1=bd.doubleValue();
                        tv_num1.setText("" + bd.toString());

                        bd = new BigDecimal(sn2 - sn1);
                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                        sn1 = bd.doubleValue();
                        tv_num0.setText("" + sn1);

                    }
                } else {
                    double sn2 = 0;
                    double sn1 = sn2 * xczk;
                    tv_num0.setText("" + (sn2 - sn1));
                    tv_num1.setText("" + sn1);
                }

            }
        });


    }

    int key = 0;//0;表示新账号首充，
    AppContent selAppContent = null;
    boolean isSelGame = false;
    double xcZeKou = 1.0f;

    @Override
    protected void init() {
        receiver = new BaseBroadcast(getActivity()) {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                super.onReceive(context, intent);
                String action = intent.getAction();
                if (ACTION_SelectGame.equals(action)) {
                    AppContent mAppContent = (AppContent) BaseApplication.mInstance.get(KEY_SelectGame);
                    if (mAppContent != null) {

                        isSelGame = true;
                        selAppContent = mAppContent;
                        BaseApplication.mInstance.put(KEY_SelectGame, null);
                        //显示
                        setView();
                    } else {
                        isSelGame = false;
                        selAppContent = null;
                    }
                } else if (ACTION_SelectGameNNo.equals(action)) {
                    Account mAccount = (Account) BaseApplication.mInstance.get(KEY_SelectGameNNo);
                    if (mAccount != null) {
                        gameaccount = mAccount.gameAccount;

                        et_mNumber.setText(gameaccount);
                        BaseApplication.mInstance.put(KEY_SelectGameNNo, null);
                        et_mNumber.setEnabled(false);
                        if (null!=selAppContent) {
                            getRechargeWay(selAppContent.gameId, mAccount.gameAccount);
                            if (TextUtils.isEmpty(gamename) || TextUtils.isEmpty(gameaccount)) {
                                et_mMoney.setEnabled(false);
                            } else {
                                et_mMoney.setEnabled(true);
                            }

                            tv_num1.setText("0");
                            tv_num0.setText("0");
                        }else{
                            ToastUtil.showToast(getActivity(),"请选择游戏");
                            et_mMoney.setEnabled(false);
                        }
                    }
                }
            }

        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SelectGame);
        filter.addAction(ACTION_SelectGameNNo);
        getActivity().registerReceiver(receiver, filter);

        Datas mDatas = (Datas) BaseApplication.mInstance.get(KEY_MSGTEXT_GAME);

        key = getArguments().getInt("KEY", 0);
        setView();
        user = DBManager.getInstance(mContext).getUserMessage();
    }

    LoginData user;

    public void setView() {
        if (key == 0) {
            boolean isFisrt = SharedPreUtil.getBooleanValue(getActivity(), SHA_FIRST_SC1, false);
            if (isFisrt == false) {
                SharedPreUtil.putBooleanValue(getActivity(), SHA_FIRST_SC1, true);
                final Datas mDatas = (Datas) BaseApplication.mInstance.get(KEY_MSGTEXT_SC);
                if (mDatas != null && !TextUtils.isEmpty(mDatas.context)) {

                    new GetBasicTask(getActivity(), false, "txt1", new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            if (object != null && object instanceof GetBasic) {
                                GetBasic mGetBasic = (GetBasic) object;
                                if (mGetBasic.data != null) {
                                    BaseApplication.mInstance.put(KEY_MSGTEXT_SC, mGetBasic.data);

                                    final MyAlertDailog dialog = new MyAlertDailog(mContext);
                                    Resources res = mContext.getResources();
                                    dialog.setTitle("新账号首充", Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    dialog.setContent1("" + mGetBasic.data.context, Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                    dialog.setSingle("确定");
                                    if (dialog != null && !dialog.isShowing()) {
                                        dialog.show();
                                    }

                                    dialog.setOnClickSingleListener(new onClickSingleListener() {

                                        @Override
                                        public void onClickSingle() {
                                            dialog.dismiss();

                                        }
                                    });
                                }

                            }

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            final MyAlertDailog dialog = new MyAlertDailog(mContext);
                            Resources res = mContext.getResources();
                            dialog.setTitle("新账号首充", Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                            dialog.setContent1("" + mDatas.context, Gravity.CENTER_VERTICAL | Gravity.LEFT);
                            dialog.setSingle("确定");
                            if (dialog != null && !dialog.isShowing()) {
                                dialog.show();
                            }

                            dialog.setOnClickSingleListener(new onClickSingleListener() {

                                @Override
                                public void onClickSingle() {
                                    dialog.dismiss();

                                }
                            });

                        }
                    }).start();
                } else {
                    new GetBasicTask(getActivity(), false, "txt1", new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            if (object != null && object instanceof GetBasic) {
                                GetBasic mGetBasic = (GetBasic) object;
                                if (mGetBasic.data != null) {
                                    BaseApplication.mInstance.put(KEY_MSGTEXT_SC, mGetBasic.data);
                                    final MyAlertDailog dialog = new MyAlertDailog(mContext);
                                    Resources res = mContext.getResources();
                                    dialog.setTitle("新账号首充", Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    dialog.setContent1("" + mGetBasic.data.context, Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                    dialog.setSingle("确定");
                                    if (dialog != null && !dialog.isShowing()) {
                                        dialog.show();
                                    }

                                    dialog.setOnClickSingleListener(new onClickSingleListener() {

                                        @Override
                                        public void onClickSingle() {
                                            dialog.dismiss();

                                        }
                                    });
                                }

                            }

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            ToastUtil.showToast(getActivity(), msg);

                        }
                    }).start();
                }


            }

            if (isSelGame) {
                if (selAppContent != null) {
                    //iv_item.setType(XCRoundImageViewByXfermode.TYPE_ROUND);
                    //iv_item.setRoundBorderRadius(23);
                    new QueryPtbTask(getActivity(), user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            if (object != null && object instanceof QueryPtb) {
                                QueryPtb mQueryPtb = (QueryPtb) object;
                                if (mQueryPtb.data != null) {
                                    user.jsonData = null;
                                    user.ptb = mQueryPtb.data;
                                    user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                                    DBManager.getInstance(mContext).insert(user);
                                    user = DBManager.getInstance(getActivity()).getUserMessage();

                                }
                            }
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                        }
                    }).start();

                    //iv_item.setVisibility(View.VISIBLE);
                    //mLinear_dz.setVisibility(View.GONE);
                    //tv_get.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(selAppContent.sczk) && Double.parseDouble(selAppContent.sczk) > 0) {
                        //tv_get.setVisibility(View.VISIBLE);
                        //tv_get.setText("" + selAppContent.sczk + "折");
                        if (!TextUtils.isEmpty(selAppContent.sczk)) {
                            xcZeKou = Double.parseDouble(selAppContent.sczk) / 10.0f;
                        }
                    } else if (!TextUtils.isEmpty(selAppContent.xczk) && Double.parseDouble(selAppContent.xczk) > 0) {
                        //tv_get.setVisibility(View.VISIBLE);
                        //tv_get.setText("" + selAppContent.xczk + "折");
                    }

                    gamename = selAppContent.gameName;
                    rechargeChoseGameNameTv.setText("" + gamename);
                    et_mNumber.setText("");
                    xcscTv.setVisibility(View.GONE);
                    tv_get.setText("");
                    tv_num1.setText("0");
                    tv_num0.setText("0");
                    et_mMoney.clearFocus();//初始化
                    et_mMoney.setText("");
                    if (TextUtils.isEmpty(gamename) || TextUtils.isEmpty(gameaccount)) {
                        et_mMoney.setEnabled(false);
                    } else {
                        et_mMoney.setEnabled(true);
                    }


                    if (!TextUtils.isEmpty(et_mMoney.getText().toString()) &&
                            et_mMoney.getText().toString().equals(".") == false) {
                        String[] a = et_mMoney.getText().toString().split(".");
                        if (a.length != 1) {
                            double sn2 = Double.valueOf(et_mMoney.getText().toString());

                            BigDecimal bd = new BigDecimal(sn2);
                            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                            sn2 = bd.doubleValue();

                            double sn1 = sn2 * xcZeKou;
                            bd = new BigDecimal(sn1);
                            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                            //sn1=bd.doubleValue();

                            tv_num1.setText("" + bd.toString());

                            bd = new BigDecimal(sn2 - sn1);
                            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                            sn1 = bd.doubleValue();
                            tv_num0.setText("" + sn1);

                            //tv_num0.setText(""+(sn2-sn1));

                        }
                    } else {
                        double sn2 = 0;
                        double sn1 = sn2 * xcZeKou;
                        tv_num0.setText("" + (sn2 - sn1));
                        tv_num1.setText("" + sn1);
                    }
                }

                //tv_recharge1.setSelected(false);
                //tv_recharge2.setSelected(false);
                relat_select_game.setVisibility(View.VISIBLE);
                //mLinear_gamePackagedetail.setVisibility(View.VISIBLE);//选中的游戏详情
                mLinear_moneys.setVisibility(View.VISIBLE);//GONE本来要隐藏的，但套餐暂时没有就只能直接充值了
                // bottom1.setVisibility(View.VISIBLE);
                xcscTv.setText("首充");
                ///mPackageDetails_top.setVisibility(View.GONE);//VISIBLE本来要显示
                bottom2.setVisibility(View.VISIBLE);//GONE本来要隐藏的
                mLinearLayout_bottom2.setVisibility(View.VISIBLE);//GONE本来要隐藏的，但套餐暂时没有就只能直接充值了
            } else {
                //未选择游戏，还原初始化的状态
                //tv_recharge1.setSelected(false);
                //tv_recharge2.setSelected(false);
                relat_select_game.setVisibility(View.VISIBLE);
                //mLinear_gamePackagedetail.setVisibility(View.GONE);//选中的游戏详情
                mLinear_moneys.setVisibility(View.VISIBLE);//GONE本来要隐藏的，但套餐暂时没有就只能直接充值了
                //bottom1.setVisibility(View.VISIBLE);
                xcscTv.setText("续充");
                //mPackageDetails_top.setVisibility(View.GONE);
                bottom2.setVisibility(View.VISIBLE);//GONE本来要隐藏的
                mLinearLayout_bottom2.setVisibility(View.VISIBLE);//本来要隐藏的，但套餐暂时没有就只能直接充值了
            }
        } else if (key == 1) {

            boolean isFisrt = SharedPreUtil.getBooleanValue(getActivity(), SHA_FIRST_XC1, false);
            if (isFisrt == false) {
                SharedPreUtil.putBooleanValue(getActivity(), SHA_FIRST_XC1, true);


                final Datas mDatas = (Datas) BaseApplication.mInstance.get(KEY_MSGTEXT_XC);
                if (mDatas != null && !TextUtils.isEmpty(mDatas.context)) {

                    new GetBasicTask(getActivity(), false, "txt2", new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            if (object != null && object instanceof GetBasic) {
                                GetBasic mGetBasic = (GetBasic) object;
                                if (mGetBasic.data != null) {
                                    BaseApplication.mInstance.put(KEY_MSGTEXT_XC, mGetBasic.data);

                                    final MyAlertDailog dialog1 = new MyAlertDailog(mContext);
                                    Resources res1 = mContext.getResources();
                                    dialog1.setTitle("折扣号续充", Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    dialog1.setContent1("" + mGetBasic.data.context, Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                    dialog1.setSingle("确定");
                                    if (dialog1 != null && !dialog1.isShowing()) {
                                        dialog1.show();
                                    }

                                    dialog1.setOnClickSingleListener(new onClickSingleListener() {

                                        @Override
                                        public void onClickSingle() {
                                            dialog1.dismiss();

                                        }
                                    });
                                }

                            }

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            final MyAlertDailog dialog1 = new MyAlertDailog(mContext);
                            Resources res1 = mContext.getResources();
                            dialog1.setTitle("折扣号续充", Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                            dialog1.setContent1("" + mDatas.context, Gravity.CENTER_VERTICAL | Gravity.LEFT);
                            dialog1.setSingle("确定");
                            if (dialog1 != null && !dialog1.isShowing()) {
                                dialog1.show();
                            }

                            dialog1.setOnClickSingleListener(new onClickSingleListener() {

                                @Override
                                public void onClickSingle() {
                                    dialog1.dismiss();

                                }
                            });

                        }
                    }).start();
                } else {
                    new GetBasicTask(getActivity(), false, "txt2", new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            if (object != null && object instanceof GetBasic) {
                                GetBasic mGetBasic = (GetBasic) object;
                                if (mGetBasic.data != null) {
                                    BaseApplication.mInstance.put(KEY_MSGTEXT_XC, mGetBasic.data);
                                    final MyAlertDailog dialog1 = new MyAlertDailog(mContext);
                                    Resources res1 = mContext.getResources();
                                    dialog1.setTitle("折扣号续充", Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                    dialog1.setContent1("" + mGetBasic.data.context, Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                    dialog1.setSingle("确定");
                                    if (dialog1 != null && !dialog1.isShowing()) {
                                        dialog1.show();
                                    }

                                    dialog1.setOnClickSingleListener(new onClickSingleListener() {

                                        @Override
                                        public void onClickSingle() {
                                            dialog1.dismiss();

                                        }
                                    });
                                }

                            }

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            ToastUtil.showToast(getActivity(), msg);

                        }
                    }).start();
                }


            }


            if (isSelGame) {
                if (selAppContent != null) {
                    //iv_item.setType(XCRoundImageViewByXfermode.TYPE_ROUND);
                    //iv_item.setRoundBorderRadius(23);
                    //iv_item.setVisibility(View.VISIBLE);
                    //mLinear_dz.setVisibility(View.GONE);
                    //tv_get.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(selAppContent.sczk) && Double.parseDouble(selAppContent.sczk) > 0) {
                        //tv_get.setVisibility(View.VISIBLE);
                        //tv_get.setText("" + selAppContent.sczk + "折");
                    } else if (!TextUtils.isEmpty(selAppContent.xczk) && Double.parseDouble(selAppContent.xczk) > 0) {
                        //tv_get.setVisibility(View.VISIBLE);
                        //tv_get.setText("" + selAppContent.xczk + "折");
                        if (!TextUtils.isEmpty(selAppContent.xczk)) {
                            xcZeKou = Double.parseDouble(selAppContent.xczk) / 10.0f;
                        }

                    }
                    new QueryPtbTask(getActivity(), user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            if (object != null && object instanceof QueryPtb) {
                                QueryPtb mQueryPtb = (QueryPtb) object;
                                if (mQueryPtb.data != null) {
                                    user.jsonData = null;
                                    user.ptb = mQueryPtb.data;
                                    user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                                    DBManager.getInstance(mContext).insert(user);
                                    user = DBManager.getInstance(getActivity()).getUserMessage();

                                }
                            }
                          /*  Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                    .load("" + selAppContent.fileAskPath + selAppContent.logo)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    //.centerCrop()// 长的一边撑满
                                    //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                    .error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
                                    //.crossFade()
                                    .into(iv_item);*/
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                           /* Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                    .load("" + selAppContent.fileAskPath + selAppContent.logo)
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    //.centerCrop()// 长的一边撑满
                                    //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                    .error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
                                    //.crossFade()
                                    .into(iv_item);*/

                        }
                    }).start();
                    //tv_item.setText(selAppContent.gameName);
                    //tv_num.setText(selAppContent.platName);

                    if (!TextUtils.isEmpty(et_mMoney.getText().toString()) &&
                            et_mMoney.getText().toString().equals(".") == false) {
                        String[] a = et_mMoney.getText().toString().split(".");
                        if (a.length != 1) {
                            double sn2 = Double.valueOf(et_mMoney.getText().toString());

                            BigDecimal bd = new BigDecimal(sn2);
                            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                            sn2 = bd.doubleValue();

                            double sn1 = sn2 * xcZeKou;
                            bd = new BigDecimal(sn1);
                            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                            //sn1=bd.doubleValue();

                            tv_num1.setText("" + bd.toString());

                            bd = new BigDecimal(sn2 - sn1);
                            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                            sn1 = bd.doubleValue();
                            tv_num0.setText("" + sn1);
                            //tv_num0.setText(""+(sn2-sn1));

                        }
                    } else {
                        double sn2 = 0;
                        double sn1 = sn2 * xcZeKou;
                        tv_num0.setText("" + (sn2 - sn1));
                        tv_num1.setText("" + sn1);
                    }
                }
                //tv_recharge1.setSelected(true);
                //tv_recharge2.setSelected(true);
                relat_select_game.setVisibility(View.VISIBLE);
                //mLinear_gamePackagedetail.setVisibility(View.VISIBLE);
                mLinear_moneys.setVisibility(View.VISIBLE);
                //bottom1.setVisibility(View.GONE);
                //mPackageDetails_top.setVisibility(View.GONE);
                bottom2.setVisibility(View.VISIBLE);
                mLinearLayout_bottom2.setVisibility(View.VISIBLE);
            } else {
                //未选择游戏，还原初始化的状态
                //tv_recharge1.setSelected(true);
                //tv_recharge2.setSelected(true);
                relat_select_game.setVisibility(View.VISIBLE);
                //mLinear_gamePackagedetail.setVisibility(View.GONE);
                mLinear_moneys.setVisibility(View.VISIBLE);
                //bottom1.setVisibility(View.GONE);
                //mPackageDetails_top.setVisibility(View.GONE);
                bottom2.setVisibility(View.VISIBLE);
                mLinearLayout_bottom2.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_recharge1:
                //tv_recharge1.setSelected(false);
                //tv_recharge2.setSelected(false);
                key = 0;
                setView();

                break;
            case R.id.tv_recharge2:
                //tv_recharge1.setSelected(true);
                //tv_recharge2.setSelected(true);
                key = 1;
                setView();

                break;
            case R.id.relat_select_game:
                //选择游戏
                ((BaseActivity) getActivity()).startActivity(SelectGameActivity.class);
                break;
           /* case R.id.tv_getsrecharges:
                ((BaseActivity) getActivity()).startActivity(CollarDiscountNumberActivity.class);
                break;*/
            case R.id.tv_Exchange:
                //showPopupWindow();
                double count1 = 0;
                if (et_mMoney.getText().toString().equals("") == false && et_mMoney.getText().toString().equals(".") == false) {
                    count1 = Double.valueOf(et_mMoney.getText().toString());

                    BigDecimal bd = new BigDecimal(count1);
                    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                    count1 = bd.doubleValue();

                }
                if (selAppContent == null || isSelGame == false) {
                    ToastUtil.showToast(mContext, "请选择充值游戏");
                } else if (TextUtils.isEmpty(et_mNumber.getText().toString())) {
                    ToastUtil.showToast(mContext, "请输入充值游戏账号");
                } else if (Double.parseDouble((TextUtils.isEmpty(tv_num1.getText().toString()) ? "0" : tv_num1.getText().toString())) <= 0) {
                    ToastUtil.showToast(mContext, "输入的充值金额太小不符合规定");
                } else if (count1 <= 0) {
                    ToastUtil.showToast(mContext, "请选择充值金额");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("key", orderType);
                    bundle.putString("gameId", selAppContent.gameId);
                    bundle.putString("gameAccount", et_mNumber.getText().toString());
                    bundle.putString("ptb", "0");//
                    bundle.putString("money", "" + count1);

                    String saveMoney = TextUtils.isEmpty(tv_num0.getText().toString()) ? "0" : tv_num0.getText().toString();
                    String realPay = TextUtils.isEmpty(tv_num1.getText().toString()) ? "0" : tv_num1.getText().toString();
                    bundle.putString("saveMoney", saveMoney);
                    bundle.putString("realPay", realPay);
                    bundle.putParcelable("appcontent", selAppContent);
                    ((BaseActivity) getActivity()).startActivity(PaySelectActivity.class, bundle);
                    ((BaseActivity) getActivity()).finish1();
                }


                break;
            case R.id.relat_weixin:
                iv_select0.setImageResource(R.drawable.btn_choice_down);
                iv_select1.setImageResource(R.drawable.icon_qx_g);
                break;
            case R.id.relat_alipay:
                iv_select0.setImageResource(R.drawable.icon_qx_g);
                iv_select1.setImageResource(R.drawable.btn_choice_down);
                break;
            case R.id.btn_cancel:
                //popupWindow.dismiss();
                break;
            case R.id.mLinear_select1:
                Bundle bundle00 = new Bundle();
                bundle00.putInt("type", 0);
                ((BaseActivity) getActivity()).startActivity(SelectGameNoActivity.class, bundle00);
                break;
            default:
                break;
        }
        super.onClick(v);
    }

    /**
     * 根据账号id 游戏id得到首充还是续充
     *
     * @param gameId
     * @param gameAccount
     */
    private void getRechargeWay(String gameId, String gameAccount) {
        new GetRechargeWayTask(getActivity(), gameId, gameAccount, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetRechargeModel) {
                    GetRechargeModel getrechargemodel = (GetRechargeModel) object;
                    if (getrechargemodel.data != null) {
                        //s首冲还是续充
                        orderType = getrechargemodel.data.orderType;
                        //折扣
                        orderType_zk = getrechargemodel.data.zk;
                        xczk = Double.parseDouble(orderType_zk) / 10.0f;
                        tv_get.setText(orderType_zk);//折扣显示
                        if (!TextUtils.isEmpty(orderType)) {
                            xcscTv.setVisibility(View.VISIBLE);
                            if (orderType.equals("sc")) {
                                xcscTv.setText("首充");
                            } else if (orderType.equals("xc")) {
                                xcscTv.setText("续充");
                            }
                        }
                    }

                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                ToastUtil.showToast(getActivity(), msg);

            }
        }).start();
    }


    private PopupWindow popupWindow;

    private void showPopupWindow() {

        View view = (LinearLayout) LayoutInflater.from(getActivity())
                .inflate(R.layout.activity_home_select_pay_recharge, null);

        iv_select0 = (ImageView) view.findViewById(R.id.iv_select0);
        iv_select1 = (ImageView) view.findViewById(R.id.iv_select1);
        relat_weixin = (RelativeLayout) view.findViewById(R.id.relat_weixin);
        relat_alipay = (RelativeLayout) view.findViewById(R.id.relat_alipay);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        relat_weixin.setOnClickListener(this);
        relat_alipay.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        if (popupWindow == null) {

            popupWindow = new PopupWindow(getActivity());
            popupWindow.setBackgroundDrawable(new BitmapDrawable());

            //popupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
            popupWindow.setTouchable(true); // 设置PopupWindow可触摸
            popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸

            popupWindow.setContentView(view);

            popupWindow.setWidth(LayoutParams.MATCH_PARENT);
            popupWindow.setHeight(LayoutParams.MATCH_PARENT);

            popupWindow.setAnimationStyle(R.style.popuStyle);   //设置 popupWindow 动画样式
        }

        //popupWindow.showAtLocation(tv_recharge2, Gravity.BOTTOM, 0, 0);

        popupWindow.update();

    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd("RechargeGameFragment");
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onPageStart("RechargeGameFragment");
    }


    @OnClick({R.id.call_service_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.call_service_ll:
                Util.showPopupWindow(getActivity(), tv_num0);
                break;

        }
    }
}
