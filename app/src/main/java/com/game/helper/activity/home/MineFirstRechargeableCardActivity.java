package com.game.helper.activity.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.home.FirstRechargeableCardAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.QueryMyCardTask;
import com.game.helper.sdk.model.returns.GetCard.Card;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryMyCard;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFirstRechargeableCardActivity extends BaseActivity {
    @BindView(R.id.lv_searchSociatyMembers)
    ListView lv_searchSociatyMembers;
    FirstRechargeableCardAdapter mFirstRechargeableCardAdapter;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    private List<Card> mList = new ArrayList<Card>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_mine_first_rechargeable_card);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("我的首充卡");
        topLeftBack.setVisibility(View.VISIBLE);
        mFirstRechargeableCardAdapter = new FirstRechargeableCardAdapter(mContext, mList);
        lv_searchSociatyMembers.setAdapter(mFirstRechargeableCardAdapter);
    }

    @Override
    protected void initData() {
        LoginData user = DBManager.getInstance(mContext).getUserMessage();
        new QueryMyCardTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryMyCard) {
                    QueryMyCard mQueryMyCard = (QueryMyCard) object;
                    if (mQueryMyCard.data != null) {
                        mList.clear();
                        mList.addAll(mQueryMyCard.data);
                        mFirstRechargeableCardAdapter.setmList(mList);
                    }

                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

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
        MobclickAgent.onPageEnd("MineFirstRechargeableCardActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineCollarDiscountNumberActivity");
        super.onResume();
    }


}
