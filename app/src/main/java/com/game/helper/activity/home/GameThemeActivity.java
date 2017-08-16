package com.game.helper.activity.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.home.GameThemeAdapter;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.QueryThemeTask;
import com.game.helper.sdk.model.returns.QueryTheme;
import com.game.helper.sdk.model.returns.QueryTheme.ThemeGame;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 主题游戏
 * @Path com.game.helper.activity.home.GameThemeActivity.java
 * @Author lbb
 * @Date 2016年8月24日 下午1:48:23
 * @Company
 */
public class GameThemeActivity extends BaseActivity {
    @BindView(R.id.themegame_listView)
    ListView themegame_listView;
    GameThemeAdapter mGameThemeAdapter;
    protected List<ThemeGame> data = new ArrayList<ThemeGame>();
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_theme_game);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("主题");
        topLeftBack.setVisibility(View.VISIBLE);

        mGameThemeAdapter = new GameThemeAdapter(mContext, data);
        themegame_listView.setAdapter(mGameThemeAdapter);

        themegame_listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                ThemeGame mThemeGame = mGameThemeAdapter.getData().get(arg2);
                Bundle bundles = new Bundle();
                if (mThemeGame != null) {
                    bundles.putString("remark", mThemeGame.remark);
                    bundles.putString("themeId", mThemeGame.themeId);
                    bundles.putString("createTimeString", mThemeGame.createTimeString);
                    bundles.putString("themeName", mThemeGame.themeName);
                    bundles.putString("pic", mThemeGame.pic);
                    bundles.putString("picThumb", mThemeGame.picThumb);
                    bundles.putString("gameNum", mThemeGame.gameNum);
                    bundles.putString("fileAskPath", mThemeGame.fileAskPath);
                }
                startActivity(GameThemeDetailsActivity.class, bundles);
            }
        });
    }

    @Override
    protected void initData() {

        String json = SharedPreUtil.getStringValue(mContext, ACTION_QueryTheme, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(QueryTheme.class, json);
            if (mObject != null && mObject instanceof QueryTheme) {
                QueryTheme mData = (QueryTheme) mObject;
                if (mData.data != null && mData.data.size() >= 0) {
                    data.clear();
                    data.addAll(mData.data);
                    mGameThemeAdapter.setData(data);
                }
            }
        }
        new QueryThemeTask(mContext, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryTheme) {
                    QueryTheme mQueryTheme = (QueryTheme) object;
                    if (mQueryTheme.data != null && mQueryTheme.data.size() >= 0) {
                        data.clear();
                        data.addAll(mQueryTheme.data);
                        mGameThemeAdapter.setData(data);

                        SharedPreUtil.putStringValue(mContext, ACTION_QueryTheme, new JsonBuild().setModel(object).getJson1());

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
        MobclickAgent.onPageEnd("GameThemeActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("GameThemeActivity");
        super.onResume();
    }
}