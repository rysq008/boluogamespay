package com.game.helper.activity.community;


import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.home.RechargeActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.interfaces.listener.ShakeListener;
import com.game.helper.interfaces.listener.ShakeListener.OnShakeListener;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetAwardTask;
import com.game.helper.net.task.GetRestTimeTask;
import com.game.helper.sdk.model.returns.GetAward;
import com.game.helper.sdk.model.returns.GetRestTime;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickSingleListener;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 摇一摇
 * @Path com.game.helper.activity.community.ShakingActivity.java
 * @Author lbb
 * @Date 2016年8月26日 下午5:36:40
 * @Company
 */
public class ShakingActivity extends BaseActivity {
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.tv_Num)
    TextView tv_Num;
    @BindView(R.id.tv_goto_sociaty)
    TextView tv_goto_sociaty;
    private SensorManager sensorManager = null;
    ShakeListener mShakeListener = null;
    Vibrator mVibrator;
    private ImageView mImgUp;
    private ImageView mImgDn;
    private LoginData user;
    private int type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_community_shaking);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        MobclickAgent.onPageEnd("ShakingActivity");
        super.onPause();
        if (sensorManager != null && mShakeListener != null) {
            sensorManager.unregisterListener(mShakeListener);
        }
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("ShakingActivity");
        super.onResume();
        if (sensorManager != null && mShakeListener != null) {
            sensorManager.registerListener(mShakeListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void initView() {
        topTitle.setText("摇一摇");
        topLeftBack.setVisibility(View.VISIBLE);
        topRight.setText("奖品内容");
        topRight.setVisibility(View.VISIBLE);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);

        mImgUp = (ImageView) findViewById(R.id.shakeImgUp);
        mImgDn = (ImageView) findViewById(R.id.shakeImgDown);

        user = DBManager.getInstance(mContext).getUserMessage();
        mShakeListener = new ShakeListener(ShakingActivity.this);
        setL();
    }

    @Override
    protected void initData() {

        new GetRestTimeTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {

                if (object != null && object instanceof GetRestTime) {
                    GetRestTime mGetRestTime = (GetRestTime) object;
                    if (mGetRestTime != null) {
                        if (mGetRestTime.data > 0) {
                            tv_Num.setText("您还有" + mGetRestTime.data + "次机会");
                            ToastUtil.showToast(mContext, "今日您还有" + mGetRestTime.data + "次免费机会");
                            type = 0;
                        } else {
                            tv_Num.setText(BaseApplication.mInstance.awardNum + "金币继续摇一摇");
                            ToastUtil.showToast(mContext, "今日免费次数已用完，继续摇一摇每次将使用" + BaseApplication.mInstance.awardNum + "金币");
                            type = 1;
                        }
                    }


                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                ToastUtil.showToast(mContext, msg);

            }
        }).start();
    }

    public void setL() {

        mShakeListener.setOnShakeListener(new OnShakeListener() {

            public void onShake() {

                startAnim();  //开始 摇一摇手掌动画
                mShakeListener.stop();

                startVibrato(); //开始 震动
                new GetAwardTask(mContext, user.userId, "" + type, new Back() {

                    @Override
                    public void success(Object object, String msg) {
                        //刷新个人金币信息
                        initData();
                        if (object != null && object instanceof GetAward) {
                            GetAward mGetAward = (GetAward) object;
                            if (mGetAward.data != null) {
                                final MyAlertDailog dialog = new MyAlertDailog(mContext);
                                dialog.setContent("摇中奖品：" + mGetAward.data.awardName + "！");
                                dialog.setSingle("继续摇一摇");
                                if (dialog != null && !dialog.isShowing()) {
                                    dialog.show();
                                }
                                dialog.setOnClickSingleListener(new onClickSingleListener() {

                                    @Override
                                    public void onClickSingle() {

                                        dialog.dismiss();
                                        mVibrator.cancel();
                                        mShakeListener.start();
                                    }
                                });
                            } else {
                                final MyAlertDailog dialog = new MyAlertDailog(mContext);
                                dialog.setContent(msg + "！");
                                dialog.setSingle("继续摇一摇");
                                if (dialog != null && !dialog.isShowing()) {
                                    dialog.show();
                                }
                                dialog.setOnClickSingleListener(new onClickSingleListener() {

                                    @Override
                                    public void onClickSingle() {

                                        dialog.dismiss();
                                        mVibrator.cancel();
                                        mShakeListener.start();
                                    }
                                });
                            }
                        } else {

                            mVibrator.cancel();
                            mShakeListener.start();
                        }

                    }

                    @Override
                    public void fail(String status, String msg, Object object) {
                        //刷新个人金币信息
                        //mVibrator.cancel();
                        //mShakeListener.start();
                        initData();
                        if (TextUtils.isEmpty(status)) {
                            final MyAlertDailog dialog = new MyAlertDailog(mContext);
                            if (!TextUtils.isEmpty(msg)) {
                                dialog.setContent(msg);
                            } else {
                                dialog.setContent("暂时不能摇一摇");
                            }
                            dialog.setSingle("知道了");
                            if (dialog != null && !dialog.isShowing()) {
                                dialog.show();
                            }
                            dialog.setOnClickSingleListener(new onClickSingleListener() {

                                @Override
                                public void onClickSingle() {

                                    dialog.dismiss();
                                    mVibrator.cancel();
                                    mShakeListener.start();
                                }
                            });

                            //ToastUtil.showToast(mContext, msg);

                        } else {
                            if (status.equals("110")) {//未中奖
                                final MyAlertDailog dialog = new MyAlertDailog(mContext);
                                dialog.setContent(msg + "！");
                                dialog.setSingle("继续摇一摇");
                                if (dialog != null && !dialog.isShowing()) {
                                    dialog.show();
                                }
                                dialog.setOnClickSingleListener(new onClickSingleListener() {

                                    @Override
                                    public void onClickSingle() {

                                        dialog.dismiss();
                                        mVibrator.cancel();
                                        mShakeListener.start();
                                    }
                                });

                            } else if (status.equals("confirm")) {
                                //免费次数用完
                                final MyAlertDailog dialog1 = new MyAlertDailog(mContext);
                                dialog1.setTitle("温馨提示", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                dialog1.setContent1(msg + ""
                                        , Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                dialog1.setLeftButtonText("取消");
                                dialog1.setRightButtonText("继续");
                                if (dialog1 != null && !dialog1.isShowing()) {
                                    dialog1.show();
                                }
                                dialog1.setOnClickLeftListener(new onClickLeftListener() {
                                    @Override
                                    public void onClickLeft() {
                                        dialog1.dismiss();
                                        mVibrator.cancel();
                                        mShakeListener.start();
                                    }
                                });
                                dialog1.setOnClickRightListener(new onClickRightListener() {

                                    @Override
                                    public void onClickRight() {
                                        dialog1.dismiss();
                                        mVibrator.cancel();
                                        mShakeListener.start();
                                    }
                                });

                            } else if (status.equals("yebz")) {
                                //金币不足
                                final MyAlertDailog dialog1 = new MyAlertDailog(mContext);
                                dialog1.setTitle("余额不足", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                dialog1.setContent1(msg + ""
                                        , Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                dialog1.setLeftButtonText("取消");
                                dialog1.setRightButtonText("充值");
                                if (dialog1 != null && !dialog1.isShowing()) {
                                    dialog1.show();
                                }
                                dialog1.setOnClickLeftListener(new onClickLeftListener() {
                                    @Override
                                    public void onClickLeft() {
                                        dialog1.dismiss();
                                        mVibrator.cancel();
                                        mShakeListener.start();
                                    }
                                });
                                dialog1.setOnClickRightListener(new onClickRightListener() {

                                    @Override
                                    public void onClickRight() {
                                        dialog1.dismiss();
                                        mVibrator.cancel();
                                        mShakeListener.start();
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("currIndex", 1);
                                        startActivity(RechargeActivity.class, bundle);
                                    }
                                });

                            }

                        }


                    }
                }).start();

            }
        });
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.tv_goto_sociaty, R.id.top_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_right:
                startActivity(ShakeJPSMActivity.class);
                break;
            case R.id.tv_goto_sociaty:
                GetLottery();
                break;
            default:
                super.onClick(v);
                break;
        }
    }

    public void GetLottery() {

        if (tv_goto_sociaty.isEnabled()) {
            tv_goto_sociaty.setEnabled(false);
            new GetAwardTask(mContext, user.userId, "" + type, new Back() {

                @Override
                public void success(Object object, String msg) {
                    //code是110，则说明未中奖；code是confirm，则说明免费次数用完或者金币不足； code是100，则说明中奖了
                    initData();
                    tv_goto_sociaty.setEnabled(true);
                    if (object != null && object instanceof GetAward) {
                        GetAward mGetAward = (GetAward) object;
                        if (mGetAward.data != null) {
                            final MyAlertDailog dialog = new MyAlertDailog(mContext);
                            dialog.setContent("摇中奖品：" + mGetAward.data.awardName + "！");
                            dialog.setLeftButtonText("取消");
                            dialog.setRightButtonText("继续摇一摇");
                            dialog.setOnClickLeftListener(new onClickLeftListener() {
                                @Override
                                public void onClickLeft() {
                                    dialog.dismiss();

                                }
                            });
                            dialog.setOnClickRightListener(new onClickRightListener() {

                                @Override
                                public void onClickRight() {
                                    dialog.dismiss();
                                    tv_goto_sociaty.setEnabled(true);
                                    GetLottery();
                                }
                            });
                            if (dialog != null && !dialog.isShowing()) {
                                dialog.show();
                            }

                        } else {
                            final MyAlertDailog dialog = new MyAlertDailog(mContext);
                            dialog.setContent(msg + "！");
                            dialog.setLeftButtonText("取消");
                            dialog.setRightButtonText("继续摇一摇");
                            dialog.setOnClickLeftListener(new onClickLeftListener() {
                                @Override
                                public void onClickLeft() {
                                    dialog.dismiss();

                                }
                            });
                            dialog.setOnClickRightListener(new onClickRightListener() {

                                @Override
                                public void onClickRight() {
                                    dialog.dismiss();
                                    tv_goto_sociaty.setEnabled(true);
                                    GetLottery();
                                }
                            });
                            if (dialog != null && !dialog.isShowing()) {
                                dialog.show();
                            }

                        }
                    }

                }

                @Override
                public void fail(String status, String msg, Object object) {

                    initData();
                    if (TextUtils.isEmpty(status)) {
                        final MyAlertDailog dialog = new MyAlertDailog(mContext);
                        if (!TextUtils.isEmpty(msg)) {
                            dialog.setContent(msg);
                        } else {
                            dialog.setContent("暂时不能摇一摇");
                        }
                        dialog.setSingle("知道了");

                        dialog.setOnClickSingleListener(new onClickSingleListener() {

                            @Override
                            public void onClickSingle() {

                                dialog.dismiss();
                                tv_goto_sociaty.setEnabled(true);

                            }
                        });
                        if (dialog != null && !dialog.isShowing()) {
                            dialog.show();
                        }
                        //tv_goto_sociaty.setEnabled(true);
                        //ToastUtil.showToast(mContext, msg);
                    } else {
                        if (status.equals("110")) {//未中奖
                            final MyAlertDailog dialog = new MyAlertDailog(mContext);
                            dialog.setContent(msg + "！");
                            dialog.setLeftButtonText("取消");
                            dialog.setRightButtonText("继续摇一摇");
                            dialog.setOnClickLeftListener(new onClickLeftListener() {
                                @Override
                                public void onClickLeft() {
                                    dialog.dismiss();

                                }
                            });
                            dialog.setOnClickRightListener(new onClickRightListener() {

                                @Override
                                public void onClickRight() {
                                    dialog.dismiss();
                                    tv_goto_sociaty.setEnabled(true);
                                    GetLottery();
                                }
                            });
                            if (dialog != null && !dialog.isShowing()) {
                                dialog.show();
                            }


                        } else if (status.equals("confirm")) {
                            //免费次数用完
                            final MyAlertDailog dialog1 = new MyAlertDailog(mContext);
                            dialog1.setTitle("温馨提示", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                            dialog1.setContent1(msg + ""
                                    , Gravity.CENTER_VERTICAL | Gravity.LEFT);
                            dialog1.setLeftButtonText("取消");
                            dialog1.setRightButtonText("继续");
                            if (dialog1 != null && !dialog1.isShowing()) {
                                dialog1.show();
                            }
                            dialog1.setOnClickLeftListener(new onClickLeftListener() {
                                @Override
                                public void onClickLeft() {
                                    dialog1.dismiss();
                                    tv_goto_sociaty.setEnabled(true);

                                }
                            });
                            dialog1.setOnClickRightListener(new onClickRightListener() {

                                @Override
                                public void onClickRight() {
                                    dialog1.dismiss();
                                    tv_goto_sociaty.setEnabled(true);
                                    GetLottery();
                                }
                            });

                        } else if (status.equals("yebz")) {
                            //金币不足
                            final MyAlertDailog dialog1 = new MyAlertDailog(mContext);
                            dialog1.setTitle("余额不足", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                            dialog1.setContent1(msg + ""
                                    , Gravity.CENTER_VERTICAL | Gravity.LEFT);
                            dialog1.setLeftButtonText("取消");
                            dialog1.setRightButtonText("充值");
                            if (dialog1 != null && !dialog1.isShowing()) {
                                dialog1.show();
                            }
                            dialog1.setOnClickLeftListener(new onClickLeftListener() {
                                @Override
                                public void onClickLeft() {
                                    dialog1.dismiss();
                                    tv_goto_sociaty.setEnabled(true);
                                }
                            });
                            dialog1.setOnClickRightListener(new onClickRightListener() {

                                @Override
                                public void onClickRight() {
                                    dialog1.dismiss();
                                    tv_goto_sociaty.setEnabled(true);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("currIndex", 1);
                                    startActivity(RechargeActivity.class, bundle);
                                }
                            });

                        }

                    }


                }
            }).start();
        }
    }

    public void startAnim() {   //定义摇一摇动画动画
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f);
        mytranslateanimup0.setDuration(1000);
        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.5f);
        mytranslateanimup1.setDuration(1000);
        mytranslateanimup1.setStartOffset(1000);
        animup.addAnimation(mytranslateanimup0);
        animup.addAnimation(mytranslateanimup1);
        mImgUp.startAnimation(animup);

        AnimationSet animdn = new AnimationSet(true);
        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.5f);
        mytranslateanimdn0.setDuration(1000);
        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f);
        mytranslateanimdn1.setDuration(1000);
        mytranslateanimdn1.setStartOffset(1000);
        animdn.addAnimation(mytranslateanimdn0);
        animdn.addAnimation(mytranslateanimdn1);
        mImgDn.startAnimation(animdn);
    }

    public void startVibrato() {
        /*MediaPlayer player;
		player = MediaPlayer.create(this, R.raw.awe);
		player.setLooping(false);
        player.start();*/

        //定义震动
        mVibrator.vibrate(new long[]{200, 200, 300, 300}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
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

}
