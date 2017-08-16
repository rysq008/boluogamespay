package com.game.helper.activity.mall;

import android.app.Activity;
import android.content.Intent;
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
import com.game.helper.adapter.mall.SeclectAddressAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetAddressListTask;
import com.game.helper.net.task.SaveOrUpdateAddressTask;
import com.game.helper.sdk.model.returns.GetAddressList;
import com.game.helper.sdk.model.returns.GetAddressList.Address;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description
 * @Path com.game.helper.activity.mall.SeclectAddressActivity.java
 * @Author lbb
 * @Date 2016年9月27日 下午1:54:00
 * @Company
 */
public class SeclectAddressActivity extends BaseActivity {
    @BindView(R.id.listView_Address)
    ListView listView_Address;
    SeclectAddressAdapter mSeclectAddressAdapter;
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

        topRight.setText("确定");
        topRight.setVisibility(View.VISIBLE);

        mSeclectAddressAdapter = new SeclectAddressAdapter(mContext, mList);
        listView_Address.setAdapter(mSeclectAddressAdapter);
    }

    @Override
    protected void initData() {
        listView_Address.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).isSelcet = 0;
                }
                mList.get(position).isSelcet = 1;
                mSeclectAddressAdapter.setmList(mList);

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
                //确定
                if (topRight.isEnabled()) {
                    topRight.setEnabled(false);
                    int num = 0;
                    String addressId = "";
                    Address mAdd = null;
                    for (Address mAddress : mSeclectAddressAdapter.getmList()) {
                        if (mAddress.isSelcet == 1) {
                            addressId = mAddress.addressId;
                            mAdd = mAddress;
                            num++;
                        }
                    }
                    if (num == 0) {
                        topRight.setEnabled(true);
                        ToastUtil.showToast(mContext, "请选择收货地址");
                    } else {
                        //将改地址设置为默认收货地址，给抽奖抽中时使用
                        if (mAdd != null) {
                            new SaveOrUpdateAddressTask(mContext, user.userId, mAdd.realName, mAdd.phone, mAdd.cityName, mAdd.areaName, mAdd.address, "Y", mAdd.addressId, new Back() {

                                @Override
                                public void success(Object object, String msg) {
                                    if (object != null && object instanceof GetAddressList) {
                                        GetAddressList mAddress = (GetAddressList) object;
                                        if (mAddress.data != null && mAddress.data.size() >= 0) {
                                            SharedPreUtil.putStringValue(mContext, ACTION_GetAddressList, new JsonBuild().setModel(object).getJson1());
                                        }
                                    }

                                }

                                @Override
                                public void fail(String status, String msg, Object object) {
                                    // TODO Auto-generated method stub
                                }
                            }).start();
                        }
                        Intent lastIntent = new Intent();
                        lastIntent.putExtra("addressId", addressId);
                        setResult(Activity.RESULT_OK, lastIntent);
                        topRight.setEnabled(true);
                        finish1();
                    }

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

    LoginData user;

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("SeclectAddressActivity");
        user = DBManager.getInstance(mContext).getUserMessage();
        String json = SharedPreUtil.getStringValue(mContext, ACTION_GetAddressList, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(GetAddressList.class, json);
            if (mObject != null && mObject instanceof GetAddressList) {
                GetAddressList mData = (GetAddressList) mObject;
                if (mData != null && mData.data != null && mData.data.size() >= 0) {
                    mList.clear();
                    mList.addAll(mData.data);
                    /*for( int i=0;i<mData.data.size();i++){
						if(!TextUtils.isEmpty(mData.data.get(i).isDefault)&&mData.data.get(i).isDefault.equals("Y")){
							mList.get(i).isSelcet=1;
							break;
						}
					}*/
                    mSeclectAddressAdapter.setmList(mList);
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
						/*for( int i=0;i<address.data.size();i++){
							if(!TextUtils.isEmpty(address.data.get(i).isDefault)&&address.data.get(i).isDefault.equals("Y")){
								mList.get(i).isSelcet=1;
								break;
							}
						}*/
                        mSeclectAddressAdapter.setmList(mList);
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
							/*for( int i=0;i<mData.data.size();i++){
								if(!TextUtils.isEmpty(mData.data.get(i).isDefault)&&mData.data.get(i).isDefault.equals("Y")){
									mList.get(i).isSelcet=1;
									break;
								}
							}*/
                            mSeclectAddressAdapter.setmList(mList);
                        }
                    }
                }

            }
        }).start();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("SeclectAddressActivity");
        super.onPause();
    }


}
