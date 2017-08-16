package com.game.helper.activity.mine;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.activity.home.InviteFriendsActivity;
import com.game.helper.adapter.mine.MineInvitedFriendsAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetShareResultTask;
import com.game.helper.sdk.model.returns.GetShareResult;
import com.game.helper.sdk.model.returns.GetShareResult.ShareResult;
import com.game.helper.sdk.model.returns.LoginData;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 邀请的好友
 * @Path com.game.helper.activity.mine.MineInvitedFriendsActivity.java
 * @Author lbb
 * @Date 2016年8月26日 下午7:14:39
 * @Company
 */
public class MineInvitedFriendsActivity extends BaseActivity {
    @BindView(R.id.listView_mineInvitedFriends)
    ListView listView_mineInvitedFriends;
    MineInvitedFriendsAdapter mMineInvitedFriendsAdapter;
    protected List<ShareResult> data = new ArrayList<ShareResult>();
    LoginData user;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_invited_friends);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("邀请的好友");
        topLeftBack.setVisibility(View.VISIBLE);
        topRight.setText("邀请好友");
        topRight.setVisibility(View.VISIBLE);
        mMineInvitedFriendsAdapter = new MineInvitedFriendsAdapter(mContext, data);
        listView_mineInvitedFriends.setAdapter(mMineInvitedFriendsAdapter);
        listView_mineInvitedFriends.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", mMineInvitedFriendsAdapter.getData().get(position).userId);
                startActivity(PersonalHomepageActivity.class, bundle);

            }
        });
    }

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();
        new GetShareResultTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetShareResult) {
                    GetShareResult mGetShareResult = (GetShareResult) object;
                    if (mGetShareResult.data != null) {
                        data.clear();
                        data.addAll(mGetShareResult.data);
                        mMineInvitedFriendsAdapter.setData(data);
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
    @OnClick({R.id.top_left_layout, R.id.top_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_right:
                startActivity(InviteFriendsActivity.class);
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
        MobclickAgent.onPageEnd("MineInvitedFriendsActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineInvitedFriendsActivity");
        super.onResume();
    }
}
