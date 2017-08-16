package com.game.helper.activity.mall;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.mall.MallExchangeRecordAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetExchangelistTask;
import com.game.helper.sdk.model.returns.GetExchangelist;
import com.game.helper.sdk.model.returns.GetExchangelist.Exchange;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 兑换记录
 * @Path com.game.helper.activity.mall.MallExchangeRecordActivity.java
 * @Author lbb
 * @Date 2016年8月20日 下午12:40:09
 * @Company
 */
public class MallExchangeRecordActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    private List<Exchange> mList = new ArrayList<Exchange>();
    MallExchangeRecordAdapter mMallExchangeRecordAdapter;

    private LoginData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mall_exchangerecord);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        user = DBManager.getInstance(mContext).getUserMessage();

        topTitle.setText("兑换记录");
        topLeftBack.setVisibility(View.VISIBLE);

        mMallExchangeRecordAdapter = new MallExchangeRecordAdapter(mContext, mList);
        listView.setAdapter(mMallExchangeRecordAdapter);
    }

    @Override
    protected void initData() {

        new GetExchangelistTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetExchangelist) {
                    GetExchangelist mGetExchangelist = (GetExchangelist) object;
                    if (mGetExchangelist.data != null) {
                        if (mGetExchangelist.data.exchangList != null && mGetExchangelist.data.exchangList.size() >= 0) {
                            mList.clear();
                            mList.addAll(mGetExchangelist.data.exchangList);
                            mMallExchangeRecordAdapter.setmList(mList);
                        }
                        if (mGetExchangelist.data.sumPtb != null) {
                            money.setText(mGetExchangelist.data.sumPtb);
                        }
                        SharedPreUtil.putStringValue(mContext, ACTION_GetExchangelist + user.userId, new JsonBuild().setModel(object).getJson1());
                    }

                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetExchangelist + user.userId, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetExchangelist.class, json);
                    if (mObject != null && mObject instanceof GetExchangelist) {
                        GetExchangelist mData = (GetExchangelist) mObject;
                        if (mData.data != null) {
                            if (mData.data.exchangList != null && mData.data.exchangList.size() >= 0) {
                                mList.clear();
                                mList.addAll(mData.data.exchangList);
                                mMallExchangeRecordAdapter.setmList(mList);
                            }
                            if (mData.data.sumPtb != null) {
                                money.setText(mData.data.sumPtb);
                            }

                        }
                    }

                }

            }
        }).start();

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
        MobclickAgent.onPageEnd("MallExchangeRecordActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MallExchangeRecordActivity");
        super.onResume();
    }

}
