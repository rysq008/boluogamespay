package com.game.helper.activity.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetBasicTask;
import com.game.helper.net.task.GetCardTask;
import com.game.helper.sdk.model.returns.GetBasic;
import com.game.helper.sdk.model.returns.GetCard;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.URLImageParser;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickSingleListener;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstChargeFreeActivity extends BaseActivity {
    @BindView(R.id.tv_gets)
    TextView tv_gets;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.iv_Img)
    ImageView iv_Img;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_first_chargefree);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("免费首充");
        topLeftBack.setVisibility(View.VISIBLE);

    }

    @Override
    protected void initData() {
        new GetBasicTask(mContext, true, "free", new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetBasic) {
                    GetBasic mGetBasic = (GetBasic) object;
                    if (mGetBasic.data != null) {
                        //默认图片，无图片或没加载完显示此图片
                        Drawable defaultDrawable = getResources().getDrawable(R.drawable.preview_card_pic_loading);
                        //调用
                        Spanned sp = Html.fromHtml(mGetBasic.data.context, new URLImageParser(tv_content), null);

                        tv_content.setText(sp);

                        //tv_content.setText(Html.fromHtml(mGetBasic.data.context,imgGetter,  null));
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
    }

    int i = 0;

    @Override
    @OnClick({R.id.top_left_layout, R.id.tv_gets})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.tv_gets:
                if (i == 0) {
                    LoginData user = DBManager.getInstance(mContext).getUserMessage();
                    new GetCardTask(mContext, user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            if (object != null && object instanceof GetCard) {
                                GetCard mGetCard = (GetCard) object;
                                if (mGetCard.data != null) {

                                    final MyAlertDailog dialog = new MyAlertDailog(mContext);
                                    dialog.setContent("领取首充卡成功！");
                                    dialog.setSingle("确定");
                                    if (dialog != null && !dialog.isShowing()) {
                                        dialog.show();
                                    }
                                    dialog.setOnClickSingleListener(new onClickSingleListener() {

                                        @Override
                                        public void onClickSingle() {
                                            // TODO Auto-generated method stub
                                            dialog.dismiss();

                                        }
                                    });

                                    i = 1;
                                    tv_gets.setText("我的首充卡");

                                }
                            }

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            i = 1;
                            tv_gets.setText("我的首充卡");
                            ToastUtil.showToast(mContext, msg);

                        }
                    }).start();

                } else {
                    startActivity(MineFirstRechargeableCardActivity.class);
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
        MobclickAgent.onPageEnd("FirstChargeFreeActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("FirstChargeFreeActivity");
        super.onResume();
    }


}
