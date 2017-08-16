package com.game.helper.activity.community;

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
import com.game.helper.activity.home.GameDetailActivity;
import com.game.helper.adapter.community.EveryonePlayingLVAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetGuildGameListTask;
import com.game.helper.sdk.model.returns.GetGuildGameList;
import com.game.helper.sdk.model.returns.GetGuildGameList.GuildGame;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EveryonePlayingActivity extends BaseActivity {
    EveryonePlayingLVAdapter mEveryonePlayingAdapter;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.mSpecialGame_listView)
    ListView mEveryonePlaying_listView;

    private List<GuildGame> gameList = new ArrayList<GuildGame>();
    String guildId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_special_game);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("大家都在玩");
        topLeftBack.setVisibility(View.VISIBLE);
        mEveryonePlayingAdapter = new EveryonePlayingLVAdapter(mContext, gameList);
        mEveryonePlaying_listView.setAdapter(mEveryonePlayingAdapter);
        mEveryonePlaying_listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("gameId", mEveryonePlayingAdapter.getmList().get(position).gameId);
                startActivity(GameDetailActivity.class, bundle);

            }
        });
    }

    @Override
    protected void initData() {
        guildId = getIntent().getStringExtra("guildId");
        //大家都在玩
        new GetGuildGameListTask(mContext, guildId, new Back() {

            @Override
            public void success(Object object, String msg) {
                // TODO Auto-generated method stub
                if (object != null && object instanceof GetGuildGameList) {

                    GetGuildGameList mGetGuildGameList = (GetGuildGameList) object;
                    if (mGetGuildGameList.data != null) {
                        if (mGetGuildGameList.data.gameList != null && mGetGuildGameList.data.gameList.size() >= 0) {
                            gameList.clear();
                            gameList.addAll(mGetGuildGameList.data.gameList);
                            mEveryonePlayingAdapter.setmList(gameList);
                        }

                    }
                }
            }

            @Override
            public void fail(String status, String msg, Object object) {


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
    protected void onPause() {
        MobclickAgent.onPageEnd("DynamicDetailsActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("DynamicDetailsActivity");
        super.onResume();
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
