package com.game.helper.activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.DeleteAddressTask;
import com.game.helper.net.task.SaveOrUpdateAddressTask;
import com.game.helper.sdk.model.returns.GetAddressList;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.CitiesActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 地址管理
 * @Path com.game.helper.activity.mall.MallAddressManagementActivity.java
 * @Author lbb
 * @Date 2016年8月20日 下午12:40:09
 * @Company
 */
public class MallAddressManagementActivity extends BaseActivity {
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.relat_city)
    LinearLayout relat_city;
    @BindView(R.id.relat_region)
    LinearLayout relat_region;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_region)
    TextView tv_region;

    @BindView(R.id.et_address)
    EditText et_address;

    @BindView(R.id.cb_clause)
    CheckBox cb_clause;

    @BindView(R.id.btn_DELETE)
    LinearLayout btn_DELETE;
    @BindView(R.id.btn_OK)
    LinearLayout btn_OK;
    @BindView(R.id.mView1)
    View mView1;
    LoginData user;
    int key = 0;
    protected String address;
    protected String userId;
    protected String realName;
    protected String addressId;
    protected String phone;
    protected String cityName;
    protected String areaName;
    protected String isDefault;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mall_addressmanagement);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

        topLeftBack.setVisibility(View.VISIBLE);
        key = getIntent().getIntExtra("key", 0);
        user = DBManager.getInstance(MallAddressManagementActivity.this).getUserMessage();

        if (key == 0) {
            topTitle.setText("新增收货地址");
            btn_DELETE.setVisibility(View.GONE);
            mView1.setVisibility(View.GONE);
            if (user != null) {
                if (!TextUtils.isEmpty(user.realName)) {
                    et_username.setText(user.realName);
                    et_username.setSelection(et_username.getText().length());
                }
                if (!TextUtils.isEmpty(user.phone)) {
                    et_phone.setText(user.phone);
                    et_phone.setSelection(et_phone.getText().length());
                }
            }
        } else {
            topTitle.setText("修改收货地址");
            btn_DELETE.setVisibility(View.VISIBLE);
            mView1.setVisibility(View.VISIBLE);

            address = getIntent().getStringExtra("address");
            userId = getIntent().getStringExtra("userId");
            addressId = getIntent().getStringExtra("addressId");
            realName = getIntent().getStringExtra("realName");
            phone = getIntent().getStringExtra("phone");
            cityName = getIntent().getStringExtra("cityName");
            areaName = getIntent().getStringExtra("areaName");
            isDefault = getIntent().getStringExtra("isDefault");

            if (!TextUtils.isEmpty(realName)) {
                et_username.setText(realName);
                et_username.setSelection(et_username.getText().length());
            }
            if (!TextUtils.isEmpty(phone)) {
                et_phone.setText(phone);
                et_phone.setSelection(et_phone.getText().length());
            }
            if (!TextUtils.isEmpty(cityName)) {
                tv_city.setText(cityName);
            }
            if (!TextUtils.isEmpty(areaName)) {
                tv_region.setText(areaName);
            }
            if (!TextUtils.isEmpty(address)) {
                et_address.setText(address);
                et_address.setSelection(et_address.getText().length());
            }
            if (!TextUtils.isEmpty(isDefault) && isDefault.equals("Y")) {
                cb_clause.setChecked(true);
            } else {
                cb_clause.setChecked(false);
            }

        }
    }


    @Override
    protected void initData() {


    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.relat_city, R.id.relat_region, R.id.btn_DELETE, R.id.btn_OK})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.relat_region:
            case R.id.relat_city:
                startActivityForResult(CitiesActivity.class, null, 1);
                break;
            case R.id.btn_DELETE:
                if (btn_DELETE.isEnabled()) {
                    btn_DELETE.setEnabled(false);
                    new DeleteAddressTask(mContext, addressId, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            btn_DELETE.setEnabled(true);
                            ToastUtil.showToast(mContext, "删除成功");
                            finish1();
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            btn_DELETE.setEnabled(true);
                            ToastUtil.showToast(mContext, msg);
                        }
                    }).start();
                }

                break;
            case R.id.btn_OK:
                //新增或者修改
                if (btn_OK.isEnabled()) {
                    btn_OK.setEnabled(false);
                    if (user != null) {
                        String realName = et_username.getText().toString();
                        String phone = et_phone.getText().toString();
                        String cityName = tv_city.getText().toString();
                        String areaName = tv_region.getText().toString();
                        String address = et_address.getText().toString();
                        String isDefault = cb_clause.isChecked() ? "Y" : "N";
                        if (TextUtils.isEmpty(realName)) {
                            btn_OK.setEnabled(true);
                            ToastUtil.showToast(mContext, "请输入姓名");
                        } else if (TextUtils.isEmpty(phone)) {
                            btn_OK.setEnabled(true);
                            ToastUtil.showToast(mContext, "请输入联系电话");
                        } else if (TextUtils.isEmpty(cityName) || cityName.equals("请选择")) {
                            btn_OK.setEnabled(true);
                            ToastUtil.showToast(mContext, "请选择您所在省市");
                        } else if (TextUtils.isEmpty(areaName) || areaName.equals("请选择")) {
                            btn_OK.setEnabled(true);
                            ToastUtil.showToast(mContext, "请选择您所在区域");
                        } else if (TextUtils.isEmpty(address)) {
                            btn_OK.setEnabled(true);
                            ToastUtil.showToast(mContext, "请填写您的详细地址");
                        } else {
                            new SaveOrUpdateAddressTask(mContext, user.userId, realName, phone, cityName, areaName, address, isDefault, addressId, new Back() {

                                @Override
                                public void success(Object object, String msg) {
                                    if (object != null && object instanceof GetAddressList) {
                                        GetAddressList mAddress = (GetAddressList) object;
                                        if (mAddress.data != null && mAddress.data.size() >= 0) {
                                            SharedPreUtil.putStringValue(mContext, ACTION_GetAddressList, new JsonBuild().setModel(object).getJson1());

                                        }
                                    }
                                    btn_OK.setEnabled(true);
                                    ToastUtil.showToast(mContext, "保存成功");
                                    finish1();
                                }

                                @Override
                                public void fail(String status, String msg, Object object) {
                                    // TODO Auto-generated method stub
                                    btn_OK.setEnabled(true);
                                    ToastUtil.showToast(mContext, msg);
                                }
                            }).start();
                        }
                    } else {
                        btn_OK.setEnabled(true);
                        ToastUtil.showToast(mContext, "用户不存在");
                    }

                }
                break;

            default:
                super.onClick(v);
                break;
        }

    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 1 && arg1 == CitiesActivity.resultCode) {
            String mCurrentProviceName = arg2.getStringExtra("ProviceName");
            String mCurrentCityName = arg2.getStringExtra("CityName");
            String mCurrentAreaName = arg2.getStringExtra("AreaName");

            String str = "省 ";
            if (!TextUtils.isEmpty(mCurrentProviceName)) {
                if (mCurrentProviceName.equals("北京") || mCurrentProviceName.equals("天津")
                        || mCurrentProviceName.equals("上海") || mCurrentProviceName.equals("重庆")) {
                    str = "市 ";
                    tv_city.setText(mCurrentProviceName + str);
                    tv_region.setText(mCurrentCityName + mCurrentAreaName);
                    tv_city.setTextColor(getResources().getColor(R.color.gh_shenhui_color));
                    tv_region.setTextColor(getResources().getColor(R.color.gh_shenhui_color));
                } else if (mCurrentProviceName.equals("香港") || mCurrentProviceName.equals("澳门")) {
                    str = "特别行政区 ";
                    tv_city.setText(mCurrentProviceName + str);
                    tv_region.setText(mCurrentCityName + mCurrentAreaName);
                    tv_city.setTextColor(getResources().getColor(R.color.gh_shenhui_color));
                    tv_region.setTextColor(getResources().getColor(R.color.gh_shenhui_color));
                } else if (mCurrentProviceName.equals("台湾")) {
                    tv_city.setText(mCurrentProviceName + str);
                    tv_region.setText(mCurrentCityName + mCurrentAreaName);
                    tv_city.setTextColor(getResources().getColor(R.color.gh_shenhui_color));
                    tv_region.setTextColor(getResources().getColor(R.color.gh_shenhui_color));
                } else {
                    tv_city.setText(mCurrentProviceName + str);
                    String cy = mCurrentCityName;
                    if (!TextUtils.isEmpty(mCurrentCityName)) {
                        String b = mCurrentCityName.substring(mCurrentCityName.length() - 1, mCurrentCityName.length());
                        if (!TextUtils.isEmpty(b) && b.equals("市")) {
                            cy = mCurrentCityName.substring(0, mCurrentCityName.length() - 1);
                            tv_region.setText(cy + "市" + mCurrentAreaName);
                        } else {
                            tv_region.setText(mCurrentCityName + mCurrentAreaName);
                        }
                    }
                    tv_city.setTextColor(getResources().getColor(R.color.gh_shenhui_color));
                    tv_region.setTextColor(getResources().getColor(R.color.gh_shenhui_color));
                }
            }


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
        MobclickAgent.onPageEnd("MallAddressManagementActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("MallAddressManagementActivity");
        super.onResume();
    }

}
