package com.game.helper.fragment.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.QueryPtbTask;
import com.game.helper.sdk.model.returns.GetBasic.Datas;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryPtb;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")

/**
 * @Description 充值-金币
 * @Path com.game.helper.fragment.home.PlatformCurrencyFragment.java
 * @Author lbb
 * @Date 2016年8月23日 下午7:07:56
 * @Company
 */
public class RechargePlatformCurrencyFragment extends BaseFragment {
    TextView tv_recharge1;
    TextView tv_recharge2;
    TextView tv_recharge3;
    TextView tv_recharge4;
    CheckBox cb_clause1;
    CheckBox cb_clause2;
    CheckBox cb_clause3;
    CheckBox cb_clause4;
    CheckBox cb_clause5;
    EditText et_mMoney;
    //TextView tv_num;//金币
    TextView tv_num1;//支付金额
    TextView tv_Exchange;

    TextView tv_cur;
    //LinearLayout tv_recharge6Linear;
    ImageView iv_select0;
    ImageView iv_select1;
    RelativeLayout relat_weixin;
    RelativeLayout relat_alipay;
    @BindView(R.id.call_service_ll)
    LinearLayout callServiceLl;
    //TextView tv_Contexts;
    public RechargePlatformCurrencyFragment() {
        super();
        // TODO Auto-generated constructor stub
    }

    public RechargePlatformCurrencyFragment(BaseApplication application, Activity activity, Context context) {
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
            mView = inflater.inflate(R.layout.fragment_home_recharge_platform_currency, container, false);
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
        tv_recharge1 = (TextView) findViewById(R.id.tv_recharge1);
        tv_recharge2 = (TextView) findViewById(R.id.tv_recharge2);
        tv_recharge3 = (TextView) findViewById(R.id.tv_recharge3);
        tv_recharge4 = (TextView) findViewById(R.id.tv_recharge4);

        cb_clause5 = (CheckBox) findViewById(R.id.cb_clause5);
        et_mMoney = (EditText) findViewById(R.id.et_mMoney);
        tv_Exchange = (TextView) findViewById(R.id.tv_Exchange);
        //tv_recharge6Linear=(LinearLayout) findViewById(R.id.tv_recharge6Linear);
        //tv_num=(TextView) findViewById(R.id.tv_num);
        tv_cur = (TextView) findViewById(R.id.tv_cur);
        tv_num1 = (TextView) findViewById(R.id.tv_num1);

        cb_clause1 = (CheckBox) findViewById(R.id.cb_clause1);
        cb_clause2 = (CheckBox) findViewById(R.id.cb_clause2);
        cb_clause3 = (CheckBox) findViewById(R.id.cb_clause3);
        cb_clause4 = (CheckBox) findViewById(R.id.cb_clause4);
        /*tv_recharge6.setEnabled(false);
		tv_recharge6.setFocusable(false);
		tv_recharge6.setFocusableInTouchMode(false);*/

        cb_clause1.setChecked(true);
        cb_clause2.setChecked(false);
        cb_clause3.setChecked(false);
        cb_clause4.setChecked(false);
        cb_clause5.setChecked(false);
        ptb = 10;

        tv_num1.setText("" + ptb);

        user = DBManager.getInstance(mContext).getUserMessage();
        if (!TextUtils.isEmpty(user.ptb)) {
            tv_cur.setText("" + user.ptb);

        }
    }

    LoginData user;
    double ptb = 10;

    @Override
    protected void initEvents() {
        tv_Exchange.setOnClickListener(this);
        tv_recharge1.setOnClickListener(this);
        tv_recharge2.setOnClickListener(this);
        tv_recharge3.setOnClickListener(this);
        tv_recharge4.setOnClickListener(this);
        cb_clause5.setOnClickListener(this);
        cb_clause1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                if (isChecked) {

                    cb_clause2.setChecked(false);
                    cb_clause3.setChecked(false);
                    cb_clause4.setChecked(false);
                    cb_clause5.setChecked(false);
                    ptb = 10;
                    //tv_num.setText(""+ptb);
                    tv_num1.setText("" + ptb);
                } else {
                    if (cb_clause2.isChecked() == false
                            && cb_clause3.isChecked() == false
                            && cb_clause4.isChecked() == false
                            && cb_clause5.isChecked() == false) {
                        cb_clause1.setChecked(true);
                    }
                }

            }
        });
        cb_clause2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                if (isChecked) {
                    cb_clause1.setChecked(false);
                    cb_clause3.setChecked(false);
                    cb_clause4.setChecked(false);
                    cb_clause5.setChecked(false);
                    ptb = 50;
                    //tv_num.setText(""+ptb);
                    tv_num1.setText("" + ptb);
                } else {
                    if (cb_clause1.isChecked() == false
                            && cb_clause3.isChecked() == false
                            && cb_clause4.isChecked() == false
                            && cb_clause5.isChecked() == false) {
                        cb_clause2.setChecked(true);
                    }

                }

            }
        });
        cb_clause3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                if (isChecked) {
                    cb_clause1.setChecked(false);
                    cb_clause2.setChecked(false);
                    cb_clause4.setChecked(false);
                    cb_clause5.setChecked(false);
                    ptb = 100;
                    //tv_num.setText(""+ptb);
                    tv_num1.setText("" + ptb);
                } else {
                    if (cb_clause1.isChecked() == false
                            && cb_clause2.isChecked() == false
                            && cb_clause4.isChecked() == false
                            && cb_clause5.isChecked() == false) {
                        cb_clause3.setChecked(true);
                    }

                }

            }
        });
        cb_clause4.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                if (isChecked) {
                    cb_clause1.setChecked(false);
                    cb_clause2.setChecked(false);
                    cb_clause3.setChecked(false);
                    cb_clause5.setChecked(false);
                    ptb = 200;
                    //tv_num.setText(""+ptb);
                    tv_num1.setText("" + ptb);
                } else {
                    if (cb_clause1.isChecked() == false
                            && cb_clause2.isChecked() == false
                            && cb_clause3.isChecked() == false
                            && cb_clause5.isChecked() == false) {
                        cb_clause4.setChecked(true);
                    }
                }

            }
        });
        cb_clause5.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                if (isChecked) {
                    cb_clause1.setChecked(false);
                    cb_clause2.setChecked(false);
                    cb_clause3.setChecked(false);
                    cb_clause4.setChecked(false);
                    if (!TextUtils.isEmpty(et_mMoney.getText().toString())) {
                        ptb = Double.valueOf(et_mMoney.getText().toString());
                    } else {
                        ptb = 0;
                    }
                    //tv_num.setText(""+ptb);
                    tv_num1.setText("" + ptb);
                } else {
                    if (cb_clause1.isChecked() == false
                            && cb_clause2.isChecked() == false
                            && cb_clause3.isChecked() == false
                            && cb_clause4.isChecked() == false) {
                        cb_clause5.setChecked(true);
                    }
                }

            }
        });
        //	tv_recharge5.setOnClickListener(this);
        //	tv_recharge6Linear.setOnClickListener(this);

        et_mMoney.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (cb_clause5.isChecked()) {
                    if (!TextUtils.isEmpty(s.toString()) &&
                            s.toString().equals(".") == false) {
                        String[] a = s.toString().split(".");
                        if (a.length != 1) {
                            ptb = Double.valueOf(s.toString());
                        }
                    } else {
                        ptb = 0;
                    }
                    //tv_num.setText(""+ptb);
                    tv_num1.setText("" + ptb);
                }


            }
        });
    }

    @Override
    protected void init() {
        // TODO Auto-generated method stub
        tv_recharge1.setSelected(true);
        tv_recharge2.setSelected(false);
        tv_recharge3.setSelected(false);
        tv_recharge4.setSelected(false);
        //	tv_recharge5.setSelected(false);
        //	tv_recharge6Linear.setSelected(false);

        Datas mDatas = (Datas) BaseApplication.mInstance.get(KEY_MSGTEXT_PTB);
        /*if(mDatas!=null&&!TextUtils.isEmpty(mDatas.context)){
        	tv_Contexts.setText(""+mDatas.context);
            new GetBasicTask(getActivity(),false, "txt4", new Back() {
    			
    			@Override
    			public void success(Object object, String msg) {
    				if(object!=null && object instanceof GetBasic){
    					GetBasic mGetBasic=(GetBasic) object;
    					if(mGetBasic.data!=null){
    						BaseApplication.mInstance.put(KEY_MSGTEXT_PTB, mGetBasic.data);
    						tv_Contexts.setText(mGetBasic.data.context);
    					}
    					
    				}
    				
    			}
    			
    			@Override
    			public void fail(String status, String msg, Object object) {
    				// TODO Auto-generated method stub
    				
    			}
    		}).start();
        }else{
        	new GetBasicTask(getActivity(),false, "txt4", new Back() {
    			
    			@Override
    			public void success(Object object, String msg) {
    				if(object!=null && object instanceof GetBasic){
    					GetBasic mGetBasic=(GetBasic) object;
    					if(mGetBasic.data!=null){
    						BaseApplication.mInstance.put(KEY_MSGTEXT_PTB, mGetBasic.data);
    						tv_Contexts.setText(mGetBasic.data.context);
    					}
    					
    				}
    				
    			}
    			
    			@Override
    			public void fail(String status, String msg, Object object) {
    				// TODO Auto-generated method stub
    				
    			}
    		}).start();
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_recharge1:
                ptb = 10;
                tv_num1.setText("" + ptb);
                cb_clause1.setChecked(true);
                cb_clause2.setChecked(false);
                cb_clause3.setChecked(false);
                cb_clause4.setChecked(false);
                cb_clause5.setSelected(false);

                break;
            case R.id.tv_recharge2:
                ptb = 50;
                tv_num1.setText("" + ptb);
                cb_clause1.setChecked(false);
                cb_clause2.setChecked(true);
                cb_clause3.setChecked(false);
                cb_clause4.setChecked(false);
                cb_clause5.setSelected(false);
                break;
            case R.id.tv_recharge3:
                ptb = 100;
                tv_num1.setText("" + ptb);
                cb_clause1.setChecked(false);
                cb_clause2.setChecked(false);
                cb_clause3.setChecked(true);
                cb_clause4.setChecked(false);
                cb_clause5.setSelected(false);
                break;
            case R.id.tv_recharge4:
                ptb = 200;
                tv_num1.setText("" + ptb);
                cb_clause1.setChecked(false);
                cb_clause2.setChecked(false);
                cb_clause3.setChecked(false);
                cb_clause4.setChecked(true);
                cb_clause5.setSelected(false);
                break;
		/*case R.id.tv_recharge5:
			tv_num.setEnabled(true);
			tv_num.setFocusable(true);
			tv_num.setFocusableInTouchMode(true);
			tv_num.requestFocus();
			tv_num.setText("500");
			tv_recharge1.setSelected(false);
			tv_recharge2.setSelected(false);
			tv_recharge3.setSelected(false);
			tv_recharge4.setSelected(false);
			tv_recharge5.setSelected(true);
			tv_recharge6.setSelected(false);
			break;
		case R.id.tv_recharge6Linear:
			
			
			tv_recharge1.setSelected(false);
			tv_recharge2.setSelected(false);
			tv_recharge3.setSelected(false);
			tv_recharge4.setSelected(false);
			tv_recharge5.setSelected(false);
			tv_recharge6.setSelected(true);
			
			tv_recharge6.setEnabled(true);
			tv_recharge6.setFocusable(true);
			tv_recharge6.setFocusableInTouchMode(true);
			tv_recharge6.requestFocus();
			break;*/
            case R.id.tv_Exchange:
                //showPopupWindow();
                if (ptb <= 0) {
                    ToastUtil.showToast(mContext, "请输入充值金币数额");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "ptb");
                    bundle.putString("ptb", "" + ptb);//充值金币数额
                    bundle.putString("money", "" + ptb);//最后需支付的金额
                    //bundle.putString("card", "0");//首充卡金额
                    isRe = 1;
                    ((BaseActivity) getActivity()).startActivity(PaySelectActivity.class, bundle);
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
                popupWindow.dismiss();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    int isRe = 0;
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

        popupWindow.showAtLocation(tv_cur, Gravity.BOTTOM, 0, 0);

        popupWindow.update();

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("RechargePlatformCurrencyFragment");
        if (isRe == 1) {
            isRe = 0;
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
                            if (!TextUtils.isEmpty(user.ptb)) {
                                tv_cur.setText("" + user.ptb);

                            }
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

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPageEnd("RechargePlatformCurrencyFragment");
    }



    @OnClick({R.id.call_service_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.call_service_ll:
                Util.showPopupWindow(getActivity(),tv_cur);
                break;

        }
    }
}
