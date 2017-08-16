package com.game.helper.activity.community;

import android.os.Bundle;
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
import com.game.helper.activity.home.GameDetailActivity;
import com.game.helper.adapter.community.TAPlayingLVAdapter;
import com.game.helper.download.bean.AppContent;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.MygameTask;
import com.game.helper.sdk.model.returns.Mygame;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description
 * @Path com.game.helper.activity.community.TAPlayingActivity.java
 * @Author lbb
 * @Date 2016年11月10日 下午8:34:44
 * @Company
 */
public class TAPlayingActivity extends BaseActivity {
    @BindView(R.id.mSpecialGame_listView)
    ListView mlistView;
    TAPlayingLVAdapter mTAPlayingLVAdapter;
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

    private List<AppContent> gameList = new ArrayList<AppContent>();
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_special_game);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("TA的游戏");
        topLeftBack.setVisibility(View.VISIBLE);
        mTAPlayingLVAdapter = new TAPlayingLVAdapter(mContext, gameList);
        mlistView.setAdapter(mTAPlayingLVAdapter);
        mlistView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("gameId", mTAPlayingLVAdapter.getmList().get(position).gameId);
                bundle.putParcelable("appcontent", mTAPlayingLVAdapter.getmList().get(position));
                startActivity(GameDetailActivity.class, bundle);

            }
        });
    }

    @Override
    protected void initData() {
        userId = getIntent().getStringExtra("userId");
        //TA的游戏
        new MygameTask(mContext, false, userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof Mygame) {
                    Mygame mMygame = (Mygame) object;
                    if (mMygame.data != null && mMygame.data.size() >= 0) {
                        gameList.clear();
                        gameList.addAll(mMygame.data);
                        mTAPlayingLVAdapter.setmList(gameList);
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
        MobclickAgent.onPageEnd("TAPlayingActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("TAPlayingActivity");
        super.onResume();
    }


}
