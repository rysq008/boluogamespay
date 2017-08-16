package com.game.helper.activity.mine;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.mine.MineGameOrdersAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.DelGameOrderByNoTask;
import com.game.helper.net.task.DeleteDynamicTask;
import com.game.helper.net.task.GetMyGameOrderTask;
import com.game.helper.sdk.model.returns.DelGameOrderModel;
import com.game.helper.sdk.model.returns.GetMyGameOrder;
import com.game.helper.sdk.model.returns.GetMyGameOrder.GameOrder;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.DensityUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.util.Util;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.OrderDetialDailog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.trinea.android.common.util.ViewUtils;

/**
 * @Description 游戏订单
 * @Path com.game.helper.activity.mine.MineGameOrdersActivity.java
 * @Author lbb
 * @Date 2016年8月26日 下午5:58:57
 * @Company
 */
public class MineGameOrdersActivity extends BaseActivity {
    /*@BindView(R.id.listView_gameOrders)
    ListView listView_gameOrders;*/
    MineGameOrdersAdapter mMineGameOrdersAdapter;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.listView)
    SwipeMenuListView listView;
    private List<GameOrder> data = new ArrayList<GameOrder>();
    LoginData user;
    private int del_line_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_game_orders);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("游戏订单");
        topLeftBack.setVisibility(View.VISIBLE);
        mMineGameOrdersAdapter = new MineGameOrdersAdapter(mContext, data);
    /*    listView_gameOrders.setAdapter(mMineGameOrdersAdapter);
        listView_gameOrders.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putString("orderId", mMineGameOrdersAdapter.getData().get(position).orderNo);
                bundle.putString("tradedate", mMineGameOrdersAdapter.getData().get(position).createTimeString);
                bundle.putString("tradetype", "7");
                bundle.putString("title", "订单详情");
                ((BaseActivity) mContext).startActivity(MineRechargeDetailActivity.class, bundle);
            }
        });*/

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openitem=new SwipeMenuItem(getApplication());
                openitem.setWidth(DensityUtil.dp2px(MineGameOrdersActivity.this,90));
                openitem.setBackground(R.color.red);
                openitem.setTitle("删除");
                openitem.setTitleSize(18);
                openitem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openitem);
            }
        };

        listView.setMenuCreator(creator);
        listView.setAdapter(mMineGameOrdersAdapter);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                delGameOrderByNo(data.get(position).orderNo);
                del_line_position = position;
                //data.remove(position);
                //mMineGameOrdersAdapter.notifyDataSetChanged();
                return false;
            }
        });
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*final OrderDetialDailog dialog = new OrderDetialDailog(mContext,data.get(position));
                dialog.setCancelable(true);
                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }

                dialog.setOnClickSingleListener(new OrderDetialDailog.onClickSingleListener() {
                    @Override
                    public void onClickSingle() {
                        dialog.dismiss();
                    }
                });*/
                Bundle bundle = new Bundle();
                bundle.putString("orderId", mMineGameOrdersAdapter.getData().get(position).orderNo);
                bundle.putString("tradedate", mMineGameOrdersAdapter.getData().get(position).createTimeString);
                bundle.putString("tradetype", "7");
                bundle.putString("title", "订单详情");
                ((BaseActivity) mContext).startActivity(MineRechargeDetailActivity.class, bundle);
            }
        });
    }

    private void delGameOrderByNo(String orderNo) {
        new DelGameOrderByNoTask(mContext, orderNo, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof DelGameOrderModel) {
                    DelGameOrderModel delGameOrderModel = (DelGameOrderModel) object;
                    ToastUtil.showToast(MineGameOrdersActivity.this,delGameOrderModel.data);
                    data.remove(del_line_position);
                    mMineGameOrdersAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                ToastUtil.showToast(MineGameOrdersActivity.this,msg);
            }
        }).start();
    }

    @Override
    protected void initData() {
        user = DBManager.getInstance(mContext).getUserMessage();
        new GetMyGameOrderTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetMyGameOrder) {
                    GetMyGameOrder mGetMyGameOrder = (GetMyGameOrder) object;
                    if (mGetMyGameOrder.data != null) {
                        data.clear();
                        data.addAll(mGetMyGameOrder.data);
                        mMineGameOrdersAdapter.setData(data);
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
        MobclickAgent.onPageEnd("MineGameOrdersActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MineGameOrdersActivity");
        super.onResume();
    }
}