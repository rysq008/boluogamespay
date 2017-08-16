package com.game.helper.activity.home;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.mine.MineProblemsActivity;
import com.game.helper.adapter.home.SpinerAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.download.bean.AppContent;
import com.game.helper.interfaces.broadcast.BaseBroadcast;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.CgameplatlistTask;
import com.game.helper.net.task.GetAccountTask;
import com.game.helper.net.task.GetBasicTask;
import com.game.helper.net.task.IsGetMoreThanThreeTask;
import com.game.helper.sdk.model.returns.Cgameplat;
import com.game.helper.sdk.model.returns.Cgameplatlist;
import com.game.helper.sdk.model.returns.GetAccount;
import com.game.helper.sdk.model.returns.GetBasic;
import com.game.helper.sdk.model.returns.GetBasic.Datas;
import com.game.helper.sdk.model.returns.IsGetMoreThanThree;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.game.helper.view.pop.SpinerPopWindow;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 领折扣号
 * @Path com.game.helper.activity.home.CollarDiscountNumberActivity.java
 * @Author lbb
 * @Date 2016年8月24日 下午8:27:15
 * @Company
 */
public class CollarDiscountNumberActivity extends BaseActivity implements SpinerAdapter.IOnItemSelectListener {
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_left_layout)
    LinearLayout topLeftLayout;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_iv_right)
    ImageView topIvRight;
    @BindView(R.id.tv_set)
    TextView tvSet;
    @BindView(R.id.isDownload1)
    View isDownload1;
    @BindView(R.id.top_liner_right)
    RelativeLayout topLinerRight;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.mRelativeLayout)
    RelativeLayout mRelativeLayout;
    private List<Cgameplat> mListType = new ArrayList<Cgameplat>();  //类型列表

    @BindView(R.id.tv_value)
    TextView mTView;

    @BindView(R.id.tv_helper)
    LinearLayout tv_helper;
    @BindView(R.id.bt_dropdown)
    ImageView bt_dropdown;
    private SpinerAdapter mAdapter;

    //设置PopWindow
    private SpinerPopWindow mSpinerPopWindow;
    @BindView(R.id.tv_gets)
    TextView tv_gets;//领号

    @BindView(R.id.tv_Contexts)
    TextView tv_Contexts;


    @BindView(R.id.tv_value1)
    TextView mTView1;
    @BindView(R.id.bt_dropdown1)
    ImageView bt_dropdown1;
    private SpinerAdapter mAdapter1;
    //设置PopWindow
    private SpinerPopWindow mSpinerPopWindow1;
    private List<Cgameplat> mListType1 = new ArrayList<Cgameplat>();  //选择游戏，（其实只有“通用”一个供选择）

    LoginData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_collar_discount_number);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        receiver = new BaseBroadcast(CollarDiscountNumberActivity.this) {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                super.onReceive(context, intent);
                String action = intent.getAction();
                if (ACTION_SelectGame.equals(action)) {
                    AppContent mAppContent = (AppContent) BaseApplication.mInstance.get(KEY_SelectGame);
                    if (mAppContent != null) {
                        //显示
                        gameId = mAppContent.gameId;
                        mTView1.setText(mAppContent.gameName);
                        mTView1.setTag(mAppContent.gameId);
                        BaseApplication.mInstance.put(KEY_SelectGame, null);

                    } else {

                    }
                }
            }

        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SelectGame);
        registerReceiver(receiver, filter);


        Datas mDatas = (Datas) BaseApplication.mInstance.get(KEY_MSGTEXT_LZKH);
        if (mDatas != null && !TextUtils.isEmpty(mDatas.context)) {
            tv_Contexts.setText("" + mDatas.context);
            new GetBasicTask(mContext, false, "txt5", new Back() {

                @Override
                public void success(Object object, String msg) {
                    if (object != null && object instanceof GetBasic) {
                        GetBasic mGetBasic = (GetBasic) object;
                        if (mGetBasic.data != null) {
                            BaseApplication.mInstance.put(KEY_MSGTEXT_LZKH, mGetBasic.data);
                            tv_Contexts.setText(mGetBasic.data.context);
                        }

                    }

                }

                @Override
                public void fail(String status, String msg, Object object) {
                    // TODO Auto-generated method stub

                }
            }).start();
        } else {
            new GetBasicTask(mContext, false, "txt5", new Back() {

                @Override
                public void success(Object object, String msg) {
                    if (object != null && object instanceof GetBasic) {
                        GetBasic mGetBasic = (GetBasic) object;
                        if (mGetBasic.data != null) {
                            BaseApplication.mInstance.put(KEY_MSGTEXT_LZKH, mGetBasic.data);
                            tv_Contexts.setText(mGetBasic.data.context);
                        }

                    }

                }

                @Override
                public void fail(String status, String msg, Object object) {
                    // TODO Auto-generated method stub

                }
            }).start();
        }


        user = DBManager.getInstance(mContext).getUserMessage();
        topTitle.setText("领折扣号");
        topLeftBack.setVisibility(View.VISIBLE);
        topRight.setText("我的账号");
        topRight.setVisibility(View.VISIBLE);
        mTView.setHint("选择客户端");
        mTView.setTag("");

        mAdapter = new SpinerAdapter(CollarDiscountNumberActivity.this, mListType);
        //初始化PopWindow
        mSpinerPopWindow = new SpinerPopWindow(this);
        mSpinerPopWindow.setAdatper(mAdapter);
        mSpinerPopWindow.setItemListener(this);

        Cgameplat mCgameplat = new Cgameplat();
        mCgameplat.field2 = null;
        mCgameplat.platId = "all";//其实代表是gameId为“all”
        mCgameplat.platName = "通用";
        mListType1.add(mCgameplat);
        mAdapter1 = new SpinerAdapter(CollarDiscountNumberActivity.this, mListType1);
        //初始化PopWindow
        mSpinerPopWindow1 = new SpinerPopWindow(this);
        mSpinerPopWindow1.setAdatper(mAdapter1);
        mSpinerPopWindow1.setItemListener(new SpinerAdapter.IOnItemSelectListener() {

            @Override
            public void onItemClick(int pos) {
                if (pos >= 0 && pos <= mListType1.size()) {
                    String value = mListType1.get(pos).platName;
                    gameId = mListType1.get(pos).platId;
                    mTView1.setText(value.toString());
                    mTView1.setTag(mListType1.get(pos).platId);
                }
            }
        });

        new IsGetMoreThanThreeTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {

                if (object != null && object instanceof IsGetMoreThanThree) {
                    IsGetMoreThanThree mdata = (IsGetMoreThanThree) object;
                    if (!TextUtils.isEmpty(mdata.data)) {
                        if (mdata.data.equals("N")) {
                            tv_gets.setEnabled(true);
                            //tv_gets.setSelected(false);
                            tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_btn_shape__selector));
                            tv_gets.setTextColor(getResources().getColor(R.color.gh_white_to_maincolor_selector));

                        } else {
                            tv_gets.setEnabled(false);
                            tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
                            tv_gets.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                            //tv_gets.setSelected(true);
                        }
                    } else {
                        tv_gets.setEnabled(false);
                        tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
                        tv_gets.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                        //tv_gets.setSelected(true);
                    }
                } else {
                    tv_gets.setEnabled(false);
                    tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
                    tv_gets.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                    //tv_gets.setSelected(true);
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                tv_gets.setEnabled(false);
                tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
                tv_gets.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                //tv_gets.setSelected(true);

            }
        }).start();
    }

    @Override
    protected void initData() {
        new CgameplatlistTask(mContext, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof Cgameplatlist) {
                    Cgameplatlist mCgameplatlist = (Cgameplatlist) object;
                    if (mCgameplatlist.data != null) {
                        mListType.clear();
                        mListType.addAll(mCgameplatlist.data);
                        mSpinerPopWindow.refreshData(mListType, 0);
                        mTView.setHint("选择客户端");
                        mTView.setTag("");
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {


            }
        }).start();


    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.tv_gets, R.id.top_right
            , R.id.tv_value, R.id.bt_dropdown, R.id.tv_helper, R.id.tv_value1, R.id.bt_dropdown1})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.tv_value:
            case R.id.bt_dropdown:
                showSpinWindow();
                break;
            case R.id.tv_value1:
            case R.id.bt_dropdown1:
                if (!TextUtils.isEmpty(platId)) {
                    if (!TextUtils.isEmpty(field2) && field2.equals("Y")) {//需要选择游戏
                        Bundle bundle = new Bundle();
                        bundle.putString("platId", platId);
                        startActivity(SelectGameActivity.class, bundle);
                    } else {//通用
                        showSpinWindow1();
                    }
                } else {
                    ToastUtil.showToast(mContext, "请选择客户端");
                }

                break;
            case R.id.tv_gets:
                if (tv_gets.isEnabled()) {
                    tv_gets.setEnabled(false);
                    if (!TextUtils.isEmpty(platId)) {
                        if (!TextUtils.isEmpty(gameId)) {

                            new GetAccountTask(mContext, user.userId, platId, gameId, new Back() {

                                @Override
                                public void success(Object object, String msg) {
                                    if (object != null && object instanceof GetAccount) {
                                        final GetAccount mGetAccount = (GetAccount) object;
                                        if (mGetAccount.data != null) {


                                            final MyAlertDailog dialog = new MyAlertDailog(
                                                    mContext);
                                            Resources res = mContext.getResources();
                                            dialog.setTitle("恭喜，成功领取折扣号", Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                            dialog.setContent1("恭喜您成功领取折扣号，请尽快登录使用并修改密码吧~\n\n账号："
                                                            + mGetAccount.data.gameAccount
                                                            + "\n密码："
                                                            + mGetAccount.data.gamePwd
                                                            + "\n"
                                                    , Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                            dialog.setLeftButtonText("关闭");
                                            dialog.setRightButtonText("复制账号");
                                            if (dialog != null && !dialog.isShowing()) {
                                                dialog.show();
                                            }
                                            dialog.setOnClickLeftListener(new onClickLeftListener() {
                                                @Override
                                                public void onClickLeft() {
                                                    dialog.dismiss();
                                                    tv_gets.setEnabled(true);
                                                    new IsGetMoreThanThreeTask(mContext, user.userId, new Back() {

                                                        @Override
                                                        public void success(Object object, String msg) {

                                                            if (object != null && object instanceof IsGetMoreThanThree) {
                                                                IsGetMoreThanThree mdata = (IsGetMoreThanThree) object;
                                                                if (!TextUtils.isEmpty(mdata.data)) {
                                                                    if (mdata.data.equals("N")) {
                                                                        tv_gets.setEnabled(true);
                                                                        //tv_gets.setSelected(false);
                                                                        tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_btn_shape__selector));
                                                                        tv_gets.setTextColor(getResources().getColor(R.color.gh_white_to_maincolor_selector));
                                                                    } else {
                                                                        tv_gets.setEnabled(false);
                                                                        tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
                                                                        tv_gets.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                                                                        //tv_gets.setSelected(true);
                                                                    }
                                                                } else {
                                                                    tv_gets.setEnabled(false);
                                                                    tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
                                                                    tv_gets.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                                                                    //tv_gets.setSelected(true);
                                                                }
                                                            } else {
                                                                tv_gets.setEnabled(false);
                                                                tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
                                                                tv_gets.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                                                                //tv_gets.setSelected(true);
                                                            }

                                                        }

                                                        @Override
                                                        public void fail(String status, String msg, Object object) {
                                                            tv_gets.setEnabled(false);
                                                            tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
                                                            tv_gets.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                                                            //tv_gets.setSelected(true);

                                                        }
                                                    }).start();
                                                }
                                            });
                                            dialog.setOnClickRightListener(new onClickRightListener() {

                                                @Override
                                                public void onClickRight() {
                                                    dialog.dismiss();
                                                    tv_gets.setEnabled(true);
                                                    if (SystemUtil.getSDKVersionNumber() >= 11) {
                                                        ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                                        clipboardManager.setText(mGetAccount.data.gameAccount);
                                                    } else {
                                                        // 得到剪贴板管理器
                                                        android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                                        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, mGetAccount.data.gameAccount));
                                                    }
                                                    new IsGetMoreThanThreeTask(mContext, user.userId, new Back() {

                                                        @Override
                                                        public void success(Object object, String msg) {

                                                            if (object != null && object instanceof IsGetMoreThanThree) {
                                                                IsGetMoreThanThree mdata = (IsGetMoreThanThree) object;
                                                                if (!TextUtils.isEmpty(mdata.data)) {
                                                                    if (mdata.data.equals("N")) {
                                                                        tv_gets.setEnabled(true);
                                                                        tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_btn_shape__selector));
                                                                        tv_gets.setTextColor(getResources().getColor(R.color.gh_white_to_maincolor_selector));
                                                                        //tv_gets.setSelected(false);

                                                                    } else {
                                                                        tv_gets.setEnabled(false);
                                                                        tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
                                                                        tv_gets.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                                                                        //tv_gets.setSelected(true);
                                                                    }
                                                                } else {
                                                                    tv_gets.setEnabled(false);
                                                                    tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
                                                                    tv_gets.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                                                                    //tv_gets.setSelected(true);
                                                                }
                                                            } else {
                                                                tv_gets.setEnabled(false);
                                                                tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
                                                                tv_gets.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                                                                //tv_gets.setSelected(true);
                                                            }

                                                        }

                                                        @Override
                                                        public void fail(String status, String msg, Object object) {
                                                            tv_gets.setEnabled(false);
                                                            tv_gets.setBackgroundDrawable(getResources().getDrawable(R.drawable.login_getcode_shape));
                                                            tv_gets.setTextColor(getResources().getColor(R.color.gh_qianhui_color));
                                                            //tv_gets.setSelected(true);

                                                        }
                                                    }).start();
                                                    ToastUtil.showToast(mContext, "成功复制到剪切板");

                                                }
                                            });
                                        }
                                    }

                                }

                                @Override
                                public void fail(String status, String msg, Object object) {
                                    tv_gets.setEnabled(true);
                                    ToastUtil.showToast(mContext, msg);
                                }
                            }).start();
                        } else {
                            tv_gets.setEnabled(true);
                            ToastUtil.showToast(mContext, "请选择游戏");
                        }

                    } else {
                        tv_gets.setEnabled(true);
                        ToastUtil.showToast(mContext, "请选择客户端");
                    }

                }

                break;
            case R.id.top_right:
                startActivity(MineCollarDiscountNumberActivity.class);
                break;
            case R.id.tv_helper:
                startActivity(MineProblemsActivity.class);
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    private void showSpinWindow() {
        mSpinerPopWindow.setWidth(mTView.getWidth());
        mSpinerPopWindow.showAsDropDown(mTView);
    }

    private void showSpinWindow1() {
        mSpinerPopWindow1.setWidth(mTView1.getWidth());
        mSpinerPopWindow1.showAsDropDown(mTView1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mSpinerPopWindow != null && mSpinerPopWindow.isShowing()) {
                mSpinerPopWindow.dismiss();
            }
            if (mSpinerPopWindow1 != null && mSpinerPopWindow1.isShowing()) {
                mSpinerPopWindow1.dismiss();
            }
            finish1();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    String platId;
    String field2;
    String gameId;

    @Override
    public void onItemClick(int pos) {

        if (pos >= 0 && pos <= mListType.size()) {
            String value = mListType.get(pos).platName;
            platId = mListType.get(pos).platId;
            field2 = mListType.get(pos).field2;
            if (!TextUtils.isEmpty(field2) && field2.equals("Y")) {//需要选择游戏

            }
            mTView1.setHint("选择游戏");
            mTView1.setTag("");
            mTView1.setText("");
            gameId = null;

            mTView.setText(value.toString());
            mTView.setTag(mListType.get(pos).platId);
        }
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("CollarDiscountNumberActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("CollarDiscountNumberActivity");
        super.onResume();
    }


}