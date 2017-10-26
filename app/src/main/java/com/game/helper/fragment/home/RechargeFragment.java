package com.game.helper.fragment.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.BaseFragment;
import com.game.helper.R;
import com.game.helper.activity.home.GameDetailActivity;
import com.game.helper.activity.home.PaySelectActivity;
import com.game.helper.activity.home.SelectGameNoActivity;
import com.game.helper.adapter.home.RechargeAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.interfaces.broadcast.BaseBroadcast;
import com.game.helper.model.home.RecommendBoutique;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetRechargeWayTask;
import com.game.helper.sdk.model.returns.GetRechargeModel;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryMyAccount.Account;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
/**
 * @Description 游戏详情-充值
 * @Path com.game.helper.fragment.home.RechargeFragment.java
 * @Author lbb
 * @Date 2016年8月23日 上午10:52:40
 * @Company
 */
public class RechargeFragment extends BaseFragment {

    @BindView(R.id.detial_server_ll)
    LinearLayout detialServerLl;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.rehcarge_fragment_xcsc_tv)
    TextView rehcargeFragmentXcscTv;
    @BindView(R.id.game_datale_recharge_paymoney_tv)
    TextView gameDataleRechargePaymoneyTv;//z需要支付
    @BindView(R.id.game_datale_recharge_savemoney_tv)
    TextView gameDataleRechargeSavemoneyTv;//节省
    //Unbinder unbinder;
    private RechargeAdapter mRechargeAdapter;
    private List<RecommendBoutique> mList = new ArrayList<RecommendBoutique>();
    //ListViewForScrollView recharge_ListView;
    //Button btn_login2;
    Button btn_login;
    //TextView getFirst;
    //ImageView dialog1;
    //ImageView dialog2;
    TextView recharge_fragment_zk_tv;
    //TextView sczk;
    TextView ed_Num1;
    EditText et_mMoney;

    LinearLayout mLinear_select1;
    //LinearLayout mLinear_select2;
    private String orderType;
    private String orderType_zk;
    private Account mAccount;

    AppContent selAppContent;
    LoginData user;
    private Double zknumber;
    //String gameId;
    //double scZeKou = 1;
    //double xcZeKou = 1;


    public RechargeFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    public RechargeFragment(BaseApplication application, Activity activity, Context context) {
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
            mView = inflater.inflate(R.layout.fragment_home_recharge, container, false);
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
        mLinear_select1 = (LinearLayout) findViewById(R.id.mLinear_select1);
        //mLinear_select2 = (LinearLayout) findViewById(R.id.mLinear_select2);

        //recharge_ListView = (ListViewForScrollView) findViewById(R.id.recharge_ListView);
        mRechargeAdapter = new RechargeAdapter(getActivity(), mList);
        //recharge_ListView.setAdapter(mRechargeAdapter);
       /* recharge_ListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                ((BaseActivity) getActivity()).startActivity(RechargePackageDetailsActivity.class);
            }
        });*/
        //btn_login2 = (Button) findViewById(R.id.btn_login2);
        btn_login = (Button) findViewById(R.id.btn_login);
        //getFirst = (TextView) findViewById(R.id.getFirst);
        //dialog1=(ImageView) findViewById(R.id.dialog1);
        //dialog2 = (ImageView) findViewById(R.id.dialog2);
        recharge_fragment_zk_tv = (TextView) findViewById(R.id.recharge_fragment_zk_tv);
        //sczk = (TextView) findViewById(R.id.sczk);
        ed_Num1 = (TextView) findViewById(R.id.ed_Num1);
        //ed_Num2 = (EditText) findViewById(R.id.ed_Num2);
        et_mMoney = (EditText) findViewById(R.id.et_mMoney);
        //recharge_ListView.setVisibility(View.GONE);
    }

    @Override
    protected void initEvents() {
        // TODO Auto-generated method stub
        mLinear_select1.setOnClickListener(this);
        //mLinear_select2.setOnClickListener(this);

        btn_login.setOnClickListener(this);
        //btn_login2.setOnClickListener(this);
        //getFirst.setOnClickListener(this);
        //dialog1.setOnClickListener(this);
        //dialog2.setOnClickListener(this);
        et_mMoney.addTextChangedListener(new TextWatcher() {

            private double sn1;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(orderType_zk)) {
                    ToastUtil.showToast(getActivity(), "请选择账号");
                    ed_Num1.setText("");
                    return;
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
                    ed_Num1.setText("");
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

                        if (zknumber <= 0) {

                            sn1 = sn2;
                        } else {
                            sn1 = sn2 * zknumber;

                        }
                        bd = new BigDecimal(sn1);
                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                        //sn1=bd.doubleValue();
                        gameDataleRechargePaymoneyTv.setText(bd.toString() + "");

                        bd = new BigDecimal(sn2 - sn1);
                        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                        sn1 = bd.doubleValue();
                        gameDataleRechargeSavemoneyTv.setText(sn1 + "");

                    }
                } else {
                    double sn2 = 0;
                    if (zknumber <= 0) {
                        sn1 = sn2;
                    } else {
                        sn1 = sn2 * zknumber;
                    }
                    gameDataleRechargeSavemoneyTv.setText((sn2 - sn1) + "");
                    gameDataleRechargePaymoneyTv.setText("" + sn1);
                }
            }
        });
    }

    @Override
    protected void init() {
        //gameId = getArguments().getString("gameId", "");
        selAppContent = getArguments().getParcelable("appContent");

        receiver = new BaseBroadcast(getActivity()) {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                super.onReceive(context, intent);
                String action = intent.getAction();
                if (ACTION_SelectGameNNo.equals(action)) {
                    mAccount = (Account) BaseApplication.mInstance.get(KEY_SelectGameNNo);
                    if (mAccount != null) {
                        if (!TextUtils.isEmpty(mAccount.gameAccount)) {
                            ed_Num1.setText(mAccount.gameAccount);
                        }
                        //ed_Num1.setSelection(ed_Num1.getText().length());//移动光标
                        gameDataleRechargePaymoneyTv.setText("0.00");
                        gameDataleRechargeSavemoneyTv.setText("0.00");
                        BaseApplication.mInstance.put(KEY_SelectGameNNo, null);
                    }
                } else if (ACTION_SelectGameNNo1.equals(action)) {
                    mAccount = (Account) BaseApplication.mInstance.get(KEY_SelectGameNNo1);
                    if (mAccount != null) {
                        //ed_Num2.setText(mAccount.gameAccount);
                        //ed_Num2.setSelection(ed_Num2.getText().length());
                        BaseApplication.mInstance.put(KEY_SelectGameNNo1, null);
                    }
                }

                getRechargeWay(null == selAppContent.gameId ? "" : selAppContent.gameId, TextUtils.isEmpty(mAccount.gameAccount) ? "" : mAccount.gameAccount);
                //ed_Num1.setFocusable(false);
            }

        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SelectGameNNo);
        filter.addAction(ACTION_SelectGameNNo1);
        getActivity().registerReceiver(receiver, filter);
        user = DBManager.getInstance(mContext).getUserMessage();

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

                        recharge_fragment_zk_tv.setText(orderType_zk + "折");//折扣显示

                        zknumber = Double.parseDouble(orderType_zk) / 10.0f;

                        if (!TextUtils.isEmpty(orderType)) {
                            rehcargeFragmentXcscTv.setVisibility(View.VISIBLE);
                            if (orderType.equals("sc")) {
                                rehcargeFragmentXcscTv.setText("首充");
                            } else if (orderType.equals("xc")) {
                                rehcargeFragmentXcscTv.setText("续充");
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                double count1 = 0;
                if (et_mMoney.getText().toString().equals("") == false && et_mMoney.getText().toString().equals(".") == false) {
                    count1 = Double.valueOf(et_mMoney.getText().toString());//输入的金额
                }
                if (count1 <= 0) {
                    ToastUtil.showToast(mContext, "请选择充值金额");
                } else if (Double.parseDouble((TextUtils.isEmpty(gameDataleRechargePaymoneyTv.getText().toString()) ? "0" : gameDataleRechargePaymoneyTv.getText().toString())) <= 0) {
                    ToastUtil.showToast(mContext, "输入的充值金额太小不符合规定");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("key", orderType);
                    bundle.putString("gameId", selAppContent.gameId);
                    bundle.putString("gameAccount", ed_Num1.getText().toString());
                    //bundle.putString("orderType", "xc");
                    bundle.putString("ptb", "0");//
                    bundle.putString("money", "" + count1);
                    //bundle.putString("card", "0");//

                    String savemoney = "" + Double.parseDouble((TextUtils.isEmpty(gameDataleRechargeSavemoneyTv.getText().toString()) ? "0" : gameDataleRechargeSavemoneyTv.getText().toString()));
                    bundle.putString("saveMoney", savemoney);

                    String realPay = "" + Double.parseDouble((TextUtils.isEmpty(gameDataleRechargePaymoneyTv.getText().toString()) ? "0" : gameDataleRechargePaymoneyTv.getText().toString()));
                    bundle.putString("realPay", realPay);

                    GameDetailActivity gameDetailActivity = (GameDetailActivity) getActivity();
                    bundle.putParcelable("appcontent", gameDetailActivity.appContent);
                    ((BaseActivity) getActivity()).startActivity(PaySelectActivity.class, bundle);
                    ((GameDetailActivity) getActivity()).finish1();
                }
                break;

            case R.id.mLinear_select1:
                Bundle bundle00 = new Bundle();
                bundle00.putInt("type", 0);
                ((BaseActivity) getActivity()).startActivity(SelectGameNoActivity.class, bundle00);
                break;
           /* case R.id.mLinear_select2:
                Bundle bundle001 = new Bundle();
                bundle001.putInt("type", 1);
                ((BaseActivity) getActivity()).startActivity(SelectGameNoActivity.class, bundle001);
                break;*/
            default:
                super.onClick(v);
                break;
        }
    }


    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd("RechargeFragment");
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


        MobclickAgent.onPageStart("RechargeFragment");
    }

    @OnClick({R.id.detial_server_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.detial_server_ll:
                Util.showPopupWindow(getActivity(), detialServerLl);
                break;

        }
    }
}
