package com.game.helper.activity.mine;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylistview.swipemenu.MyListView;
import com.example.mylistview.swipemenu.MyListView.OnMenuItemClickListener;
import com.example.mylistview.swipemenu.MyListView.OnSwipeListener;
import com.example.mylistview.swipemenu.SwipeMenu;
import com.example.mylistview.swipemenu.SwipeMenuCreator;
import com.example.mylistview.swipemenu.SwipeMenuItem;
import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.activity.home.GiftsDetailActivity;
import com.game.helper.adapter.mine.MineGiftsAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.QueryMyGiftTask;
import com.game.helper.net.task.RemoveMyGiftTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryMyGift;
import com.game.helper.sdk.model.returns.QueryMyGift.MyGift;
import com.game.helper.util.DensityUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 我的礼包
 * @Path com.game.helper.activity.mine.MineGiftsActivity.java
 * @Author lbb
 * @Date 2016年8月19日 下午12:38:39
 * @Company
 */
public class MineGiftsActivity extends BaseActivity {

    @BindView(R.id.school_friend_member)
    MyListView mListView;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    private Handler handler = new Handler();
    private MineGiftsAdapter mMineGiftsAdapter;
    private List<MyGift> mList = new ArrayList<MyGift>();
    LoginData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_gifts);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("我的礼包");
        topLeftBack.setVisibility(View.VISIBLE);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerview = inflater.inflate(R.layout.headerview, null);
        //【主要，添加headerview，则在OnItenClick中的Position就会错乱】
        mListView.addHeaderView(headerview, null, false);
        mListView.setHasMoreData(false);
        mListView.setLoadmoreVisible(false);
        mListView.setOnSwipeListener(new OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                //pullToRefreshLayout.setIsProhibitPull(true);
            }

            @Override
            public void onSwipeEnd(int position) {
                //pullToRefreshLayout.setIsProhibitPull(false);
            }
        });

        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO 【修正有Header的ListView的position的BUG】
                int headerViewsCount = mListView.getHeaderViewsCount();//得到header的总数量
                //【得到新的修正后的position】
                int newPosition = position - headerViewsCount;
                Bundle bundle = new Bundle();
                bundle.putString("giftId", mMineGiftsAdapter.getmList().get(newPosition).giftId);
                startActivity(GiftsDetailActivity.class, bundle);
            }
        });
        initswipe();
        mListView.setDividerHeight(0);
        mMineGiftsAdapter = new MineGiftsAdapter(MineGiftsActivity.this, mList);
        mListView.setAdapter(mMineGiftsAdapter);
    }

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();
        new QueryMyGiftTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryMyGift) {
                    QueryMyGift mQueryMyGift = (QueryMyGift) object;
                    if (mQueryMyGift.data != null) {
                        mList.clear();
                        mList.addAll(mQueryMyGift.data);
                        mMineGiftsAdapter.setmList(mList);
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

    /**
     * 初始化ListView的Item菜单
     */
    public void initswipe() {
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackgroundColor(getResources().getColor(
                        R.color.red));
                // set item width
                openItem.setWidth(DensityUtil.dip2px(mContext, 65));
                // set item title
                //	openItem.setIcon(R.drawable.icon_delete);
                openItem.setTitle("删除");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // // create "delete" item
                // SwipeMenuItem deleteItem = new SwipeMenuItem(
                // getApplicationContext());
                // // set item background
                // deleteItem.setBackgroundDrawable(new ColorDrawable(Color.rgb(0xF9,
                // 0x3F, 0x25)));
                // // set item width
                // deleteItem.setWidth(dp2px(90));
                // // set a icon
                // deleteItem.setIcon(R.drawable.ic_delete);
                // // add to menu
                // menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);
        mListView.setOnSwipeListener(new OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                //pullToRefreshLayout.setIsProhibitPull(true);
            }

            @Override
            public void onSwipeEnd(int position) {
                //pullToRefreshLayout.setIsProhibitPull(false);
            }
        });
        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(final int position, SwipeMenu menu, int index) {
                // ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                        final MyAlertDailog dialog = new MyAlertDailog(
                                mContext);
                        Resources res = mContext.getResources();
                        dialog.setTitle(res
                                .getString(R.string.mydailog_title));
                        dialog.setContent("是否确认删除此条记录？");
                        dialog.setLeftButtonText("取消");
                        dialog.setRightButtonText("确定");
                        if (dialog != null && !dialog.isShowing()) {
                            dialog.show();
                        }
                        dialog.setOnClickLeftListener(new onClickLeftListener() {
                            @Override
                            public void onClickLeft() {
                                dialog.dismiss();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mListView.closeMenu();
                                    }
                                }, 200);
                            }
                        });
                        dialog.setOnClickRightListener(new onClickRightListener() {

                            @Override
                            public void onClickRight() {
                                dialog.dismiss();
                                new RemoveMyGiftTask(mContext, mMineGiftsAdapter.getmList().get(position).giftId, new Back() {

                                    @Override
                                    public void success(Object object, String msg) {
                                        ToastUtil.showToast(mContext, "删除成功");
                                        mMineGiftsAdapter.getmList().remove(position);
                                        mMineGiftsAdapter.notifyDataSetChanged();

                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mListView.closeMenu();
                                            }
                                        }, 200);

                                    }

                                    @Override
                                    public void fail(String status, String msg, Object object) {
                                        ToastUtil.showToast(mContext, msg);
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mListView.closeMenu();
                                            }
                                        }, 200);
                                    }
                                }).start();

                            }
                        });
                        break;

                }
            }
        });
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
        MobclickAgent.onPageEnd("MineGiftsActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineGiftsActivity");
        super.onResume();
    }
}
