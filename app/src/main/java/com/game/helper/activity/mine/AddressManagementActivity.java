package com.game.helper.activity.mine;

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
import com.game.helper.activity.mall.MallAddressManagementActivity;
import com.game.helper.adapter.mine.AddressManagementAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetAddressListTask;
import com.game.helper.sdk.model.returns.GetAddressList;
import com.game.helper.sdk.model.returns.GetAddressList.Address;
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
 * @Description
 * @Path com.game.helper.activity.mine.AddressManagementActivity.java
 * @Author lbb
 * @Date 2016年9月27日 上午11:29:45
 * @Company
 */
public class AddressManagementActivity extends BaseActivity {

    @BindView(R.id.listView_Address)
    ListView listView_Address;
    AddressManagementAdapter mAddressManagementAdapter;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_right)
    TextView topRight;
    @BindView(R.id.top_title)
    TextView topTitle;
    private List<Address> mList = new ArrayList<Address>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_address_management);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("收货地址管理");
        topLeftBack.setVisibility(View.VISIBLE);

        topRight.setText("新增");
        topRight.setVisibility(View.VISIBLE);

        mAddressManagementAdapter = new AddressManagementAdapter(mContext, mList);
        listView_Address.setAdapter(mAddressManagementAdapter);
    }

    @Override
    protected void initData() {
        listView_Address.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Address mAddress = mAddressManagementAdapter.getmList().get(position);
                Bundle bundle = new Bundle();
                bundle.putString("address", mAddress.address);
                bundle.putString("userId", mAddress.userId);
                bundle.putString("realName", mAddress.realName);
                bundle.putString("addressId", mAddress.addressId);
                bundle.putString("phone", mAddress.phone);
                bundle.putString("cityName", mAddress.cityName);
                bundle.putString("areaName", mAddress.areaName);
                bundle.putString("isDefault", mAddress.isDefault);
                bundle.putInt("key", 1);
                startActivity(MallAddressManagementActivity.class, bundle);
            }
        });
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.top_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_right:
                Bundle bundle = new Bundle();
                bundle.putInt("key", 0);
                startActivity(MallAddressManagementActivity.class, bundle);
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

    LoginData user;

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("AddressManagementActivity");
        super.onResume();
        user = DBManager.getInstance(mContext).getUserMessage();
        String json = SharedPreUtil.getStringValue(mContext, ACTION_GetAddressList, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(GetAddressList.class, json);
            if (mObject != null && mObject instanceof GetAddressList) {
                GetAddressList mData = (GetAddressList) mObject;
                if (mData != null && mData.data != null && mData.data.size() >= 0) {
                    mList.clear();
                    mList.addAll(mData.data);
                    mAddressManagementAdapter.setmList(mList);
                }
            }
        }
        new GetAddressListTask(mContext, true, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetAddressList) {
                    GetAddressList address = (GetAddressList) object;
                    if (address.data != null && address.data.size() >= 0) {
                        mList.clear();
                        mList.addAll(address.data);
                        mAddressManagementAdapter.setmList(mList);
                        SharedPreUtil.putStringValue(mContext, ACTION_GetAddressList, new JsonBuild().setModel(object).getJson1());
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                String json = SharedPreUtil.getStringValue(mContext, ACTION_GetAddressList, "");
                if (!TextUtils.isEmpty(json)) {
                    Object mObject = new JsonBuild().getData(GetAddressList.class, json);
                    if (mObject != null && mObject instanceof GetAddressList) {
                        GetAddressList mData = (GetAddressList) mObject;
                        if (mData != null && mData.data != null && mData.data.size() >= 0) {
                            mList.clear();
                            mList.addAll(mData.data);
                            mAddressManagementAdapter.setmList(mList);
                        }
                    }
                }

            }
        }).start();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("AddressManagementActivity");
        super.onPause();
    }


}
