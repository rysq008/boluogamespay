package com.game.helper.activity.community;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.community.SociatySignInAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetSignListTask;
import com.game.helper.net.task.JudgeSignTask;
import com.game.helper.net.task.SaveSignTask;
import com.game.helper.sdk.model.returns.GetSignList;
import com.game.helper.sdk.model.returns.GetSignList.GetSignListData;
import com.game.helper.sdk.model.returns.JudgeSign;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.TimeUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickSingleListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 公会签到
 * @Path com.game.helper.activity.community.SociatySignInActivity.java
 * @Author lbb
 * @Date 2016年8月25日 上午10:56:20
 * @Company
 */
public class SociatySignInActivity extends BaseActivity {
    @BindView(R.id.tv_IsSignIn)
    TextView tv_IsSignIn;
    @BindView(R.id.tv_SingNum_user)
    TextView tv_SingNum_user;
    @BindView(R.id.tv_Signnum)
    TextView tv_Signnum;
    @BindView(R.id.lv_community_sign_in)
    ListView lv_community_sign_in;
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
    private SociatySignInAdapter mSociatySignInAdapter;
    private List<GetSignListData> datas = new ArrayList<GetSignListData>();
    private String guildId;
    private String userId;
    private LoginData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_sociaty_sign_in);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        user = DBManager.getInstance(mContext).getUserMessage();

        topTitle.setText("公会签到");
        topLeftBack.setVisibility(View.VISIBLE);

        //topRight.setVisibility(View.VISIBLE);

        tv_IsSignIn.setEnabled(false);
        tv_IsSignIn.setText("已签到");


        mSociatySignInAdapter = new SociatySignInAdapter(mContext, datas);
        lv_community_sign_in.setAdapter(mSociatySignInAdapter);
        lv_community_sign_in.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", mSociatySignInAdapter.getDatas().get(arg2).userId);
                startActivity(PersonalHomepageActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initData() {
        userId = getIntent().getStringExtra("userId");
        guildId = getIntent().getStringExtra("guildId");
        String json = SharedPreUtil.getStringValue(mContext, ACTION_JudgeSign + "_" + userId + "_" + guildId, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(JudgeSign.class, json);
            if (mObject != null && mObject instanceof JudgeSign) {
                JudgeSign mJudgeSign = (JudgeSign) mObject;
                if (mJudgeSign != null && mJudgeSign.data != null) {
                    if (!TextUtils.isEmpty(mJudgeSign.data.isSign)) {
                        if (mJudgeSign.data.isSign.equals("Y")) {
                            tv_IsSignIn.setEnabled(false);
                            tv_IsSignIn.setText("已签到");
                        } else {
                            tv_IsSignIn.setText("签到");
                            tv_IsSignIn.setEnabled(true);
                        }
                    }
                    if (!TextUtils.isEmpty(mJudgeSign.data.sumCount)) {
                        tv_SingNum_user.setText("连续签到" + mJudgeSign.data.sumCount + "天");
                    }


                }
            }
        }
        //查询是否已签到
        new JudgeSignTask(mContext, false, userId, guildId, TimeUtil.getCurTime(TimeUtil.TIME_FORMAT_XXXX_XX_XX), new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof JudgeSign) {
                    JudgeSign mJudgeSign = (JudgeSign) object;
                    if (mJudgeSign.data != null) {
                        if (!TextUtils.isEmpty(mJudgeSign.data.isSign)) {
                            if (mJudgeSign.data.isSign.equals("Y")) {
                                tv_IsSignIn.setEnabled(false);
                                tv_IsSignIn.setText("已签到");
                            } else {
                                tv_IsSignIn.setText("签到");
                                tv_IsSignIn.setEnabled(true);
                            }
                        }
                        if (!TextUtils.isEmpty(mJudgeSign.data.sumCount)) {
                            tv_SingNum_user.setText("连续签到" + mJudgeSign.data.sumCount + "天");
                        }
                        SharedPreUtil.putStringValue(mContext, ACTION_JudgeSign + "_" + userId + "_" + guildId, new JsonBuild().setModel(object).getJson1());
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json = SharedPreUtil.getStringValue(mContext, ACTION_JudgeSign + "_" + userId + "_" + guildId, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(JudgeSign.class, json);
                    if (mObject != null && mObject instanceof JudgeSign) {
                        JudgeSign mJudgeSign = (JudgeSign) mObject;
                        if (mJudgeSign != null && mJudgeSign.data != null) {
                            if (!TextUtils.isEmpty(mJudgeSign.data.isSign)) {
                                if (mJudgeSign.data.isSign.equals("Y")) {
                                    tv_IsSignIn.setEnabled(false);
                                    tv_IsSignIn.setText("已签到");
                                } else {
                                    tv_IsSignIn.setText("签到");
                                    tv_IsSignIn.setEnabled(true);
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(mJudgeSign.data.sumCount)) {
                            tv_SingNum_user.setText("连续签到" + mJudgeSign.data.sumCount + "天");
                        }
                    }
                }
            }
        }).start();
        json = SharedPreUtil.getStringValue(mContext, ACTION_GetSignList + guildId, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(GetSignList.class, json);
            if (mObject != null && mObject instanceof GetSignList) {
                GetSignList mGetSignList = (GetSignList) mObject;
                if (mGetSignList.data != null && mGetSignList.data.size() >= 0) {
                    datas.clear();
                    datas.addAll(mGetSignList.data);
                    /*for(GetSignListData m:mGetSignList.data){
						if(!TextUtils.isEmpty(m.userId)&&m.userId.equals(""+userId)){
							datas.remove(m);
							break;
						}
					}*/
                    mSociatySignInAdapter.setDatas(datas);
                    tv_Signnum.setText("已签到" + datas.size() + "人");
                }
            }
        }
        //获取工会签到列表（默认展示6个）
        new GetSignListTask(mContext, true, guildId, TimeUtil.getCurTime(TimeUtil.TIME_FORMAT_XXXX_XX_XX), new Back() {

            @Override
            public void success(Object object, String msg) {
                // TODO Auto-generated method stub
                if (object != null && object instanceof GetSignList) {
                    GetSignList mGetSignList = (GetSignList) object;
                    if (mGetSignList.data != null && mGetSignList.data.size() >= 0) {
                        datas.clear();
                        datas.addAll(mGetSignList.data);
						/*for(GetSignListData m:mGetSignList.data){
							if(!TextUtils.isEmpty(m.userId)&&m.userId.equals(""+userId)){
								datas.remove(m);
								break;
							}
						}*/
                        mSociatySignInAdapter.setDatas(datas);
                        tv_Signnum.setText("已签到" + datas.size() + "人");
                        SharedPreUtil.putStringValue(mContext, ACTION_GetSignList + guildId, new JsonBuild().setModel(object).getJson1());
                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub
                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetSignList + guildId, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetSignList.class, json);
                    if (mObject != null && mObject instanceof GetSignList) {
                        GetSignList mGetSignList = (GetSignList) mObject;
                        if (mGetSignList.data != null && mGetSignList.data.size() >= 0) {
                            datas.clear();
                            datas.addAll(mGetSignList.data);
							/*for(GetSignListData m:mGetSignList.data){
								if(!TextUtils.isEmpty(m.userId)&&m.userId.equals(""+userId)){
									datas.remove(m);
									break;
								}
							}*/
                            mSociatySignInAdapter.setDatas(datas);
                            tv_Signnum.setText("已签到" + datas.size() + "人");
                        }
                    }
                }
            }
        }).start();

    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.tv_IsSignIn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.tv_IsSignIn:
                //签到
                if (tv_IsSignIn.isEnabled()) {
                    tv_IsSignIn.setEnabled(false);
                    if (!TextUtils.isEmpty(guildId) && !guildId.equals("0") && user != null && !TextUtils.isEmpty(user.guildId) && !user.guildId.equals("0") && guildId.equals(user.guildId)) {//已加入公会
                        new SaveSignTask(mContext, userId, guildId, new Back() {
                            @Override
                            public void success(Object object, String msg) {
                                tv_IsSignIn.setEnabled(false);
                                tv_IsSignIn.setText("已签到");

                                new GetSignListTask(mContext, false, guildId, TimeUtil.getCurTime(TimeUtil.TIME_FORMAT_XXXX_XX_XX), new Back() {

                                    @Override
                                    public void success(Object object, String msg) {
                                        // TODO Auto-generated method stub
                                        if (object != null && object instanceof GetSignList) {
                                            GetSignList mGetSignList = (GetSignList) object;
                                            if (mGetSignList.data != null && mGetSignList.data.size() >= 0) {
                                                datas.clear();
                                                datas.addAll(mGetSignList.data);
											/*for(GetSignListData m:mGetSignList.data){
												if(!TextUtils.isEmpty(m.userId)&&m.userId.equals(""+userId)){
													datas.remove(m);
													break;
												}
											}*/
                                                mSociatySignInAdapter.setDatas(datas);
                                                tv_Signnum.setText("已签到" + datas.size() + "人");
                                                SharedPreUtil.putStringValue(mContext, ACTION_GetSignList + guildId, new JsonBuild().setModel(object).getJson1());
                                            }
                                        }
                                    }

                                    @Override
                                    public void fail(String status, String msg, Object object) {
                                        // TODO Auto-generated method stub
                                        String json = SharedPreUtil.getStringValue(mContext, ACTION_GetSignList + guildId, "");
                                        if (!TextUtils.isEmpty(json)) {
                                            Object mObject = new JsonBuild().getData(GetSignList.class, json);
                                            if (mObject != null && mObject instanceof GetSignList) {
                                                GetSignList mGetSignList = (GetSignList) mObject;
                                                if (mGetSignList.data != null && mGetSignList.data.size() >= 0) {
                                                    datas.clear();
                                                    datas.addAll(mGetSignList.data);
												/*for(GetSignListData m:mGetSignList.data){
													if(!TextUtils.isEmpty(m.userId)&&m.userId.equals(""+userId)){
														datas.remove(m);
														break;
													}
												}*/
                                                    mSociatySignInAdapter.setDatas(datas);
                                                    tv_Signnum.setText("已签到" + datas.size() + "人");
                                                }
                                            }
                                        }
                                    }
                                }).start();


                                final MyAlertDailog dialog1 = new MyAlertDailog(mContext);
                                Resources res1 = mContext.getResources();
                                dialog1.setTitle("提示", Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                dialog1.setContent1("签到成功!"
                                        , Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
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

                            @Override
                            public void fail(String status, String msg, Object object) {
                                tv_IsSignIn.setEnabled(true);
                                ToastUtil.showToast(mContext, msg);
                            }
                        }).start();
                    } else {
                        tv_IsSignIn.setEnabled(true);
                        ToastUtil.showToast(mContext, "请先加入公会后再签到");
                    }

                }


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
        MobclickAgent.onPageEnd("SociatySignInActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("SociatySignInActivity");
        super.onResume();
    }
}
