package com.game.helper.activity.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.model.mine.PersonaImg;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetAddressListTask;
import com.game.helper.net.task.QueryUserIconTask;
import com.game.helper.net.task.UpdSexTask;
import com.game.helper.sdk.model.returns.GetAddressList;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryUserIcon;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.Util;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description 编辑资料
 * @Path com.game.helper.activity.mine.MineDataEditingActivity.java
 * @Author lbb
 * @Date 2016年8月18日 下午5:29:28
 * @Company
 */
public class MineDataEditingActivity extends BaseActivity {

    public static final int TO_SELECT_PHOTO = 3;
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

    @BindView(R.id.relat_name)
    RelativeLayout relatName;
    @BindView(R.id.relat_sex)
    ImageView relat_sex;
    @BindView(R.id.tv_instruction)
    TextView tv_instruction;
    @BindView(R.id.relat_instruction)
    RelativeLayout relatInstruction;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.relat_set_phone)
    RelativeLayout relatSetPhone;
    @BindView(R.id.tv_addressNum)
    TextView tv_addressNum;
    @BindView(R.id.relat_set_address)
    RelativeLayout relatSetAddress;

    @BindView(R.id.relat_set_pic)
    RelativeLayout relatSetPic;
    private ArrayList<PersonaImg> mList = new ArrayList<PersonaImg>();
    @BindView(R.id.tv_picNum)
    TextView tv_picNum;
    @BindView(R.id.tv_name)
    TextView tv_name;


    boolean sex = true;
    //List<Images> imagesList=new ArrayList<Images>() ;

    /*	private Handler handler  = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 1){

                }else if(msg.what == 2){

                    Images data=(Images) msg.obj;
                    new AddUserIconTask(mContext, user.userId, data.srcFilePath,
                            data.thumbFilePath, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            if(object!=null&& object instanceof AddUserIcon){
                                AddUserIcon mAddUserIcon=(AddUserIcon) object;
                                if(mAddUserIcon.data!=null){

                                    try {
                                    //	mList.clear();

                                        mAddUserIcon.data.smallBitmap= ImageCompression.getSmallBitmap(picPaths);
                                        //mAddUserIcon.data.index=mList.size();
                                        mList.add(mAddUserIcon.data);

                                        //mList1.addAll(mList);

                                        PersonaImg mPersonaImg=new PersonaImg();
                                        if(mList.size()>0){
                                            mPersonaImg.id=mList.get(mList.size()-1).id+1;
                                        }else{
                                            mPersonaImg.id=1;
                                        }
                                        //mPersonaImg.index=mList1.size();
                                        mList1.add(mPersonaImg);

                                        mDragAdapter.setList(mList);
                                        //Collections.sort(mList);

                                    } catch (Exception e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            ToastUtil.showToast(mContext, "图片上传失败 "+msg);

                        }
                    }).start();
                }
            }
        };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_dataediting);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("编辑资料");
        topLeftBack.setVisibility(View.VISIBLE);

        topRight.setText("保存");
        topRight.setVisibility(View.GONE);
        LoginData user = DBManager.getInstance(mContext).getUserMessage();
        if (user != null && !TextUtils.isEmpty(user.field1) && user.field1.equals("男")) {
            sex = true;
        } else {
            sex = false;
        }

        if (sex) {
            relat_sex.setImageDrawable(getResources().getDrawable(R.drawable.wode_83));
        } else {
            relat_sex.setImageDrawable(getResources().getDrawable(R.drawable.wode_81));
        }
        /*iv_icon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mList.size()<8){
					
					startActivityForResult(ChoosePicActivity.class, null,
							MineDataEditingActivity.TO_SELECT_PHOTO);
				}
			}
		});*/
        //mList.clear();
        //mList1.clear();
		/*PersonaImg mPersonaImg=new PersonaImg();
		mPersonaImg.id=1;
		//mPersonaImg.index=mList1.size();
		mList1.add(mPersonaImg);*/
		/*mDragDropGridAdapter=new DragDropGridAdapter(this,photo_gridview,mList1,3,3,8);
		mDragDropGridAdapter.setmDeleteListener(this);
		photo_gridview.setAdapter(mDragDropGridAdapter);*/
		/*photo_gridview.setNumColumns(3);
		mDragAdapter = new DragAdapter(this, mList);
		mDragAdapter.setmDeleteListener(this);
		photo_gridview.setAdapter(mDragAdapter);
		photo_gridview.setOnChangeListener(new OnChanageListener() {  

			@Override  
			public void onChange(int from, int to) {  
				PersonaImg temp = mList.get(from); 
				PersonaImg tempTo = mList.get(to);  
				new ChangeIconOrderbyTask(mContext, ""+temp.id, ""+tempTo.orderby, new Back() {
					
					@Override
					public void success(Object object, String msg) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void fail(String status, String msg, Object object) {
						// TODO Auto-generated method stub
						
					}
				}).start();
				//直接交互item  
				//              dataSourceList.set(from, dataSourceList.get(to));  
				//              dataSourceList.set(to, temp);  
				//              dataSourceList.set(to, temp);  


				//这里的处理需要注意下  
				if(from < to){  
					for(int i=from; i<to; i++){  
						Collections.swap(mList, i, i+1);  
					}  
				}else if(from > to){  
					for(int i=from; i>to; i--){  
						Collections.swap(mList, i, i-1);  
					}  
				}  

				mList.set(to, temp);  

				//mDragAdapter.setList(dataSourceList);  

                for(PersonaImg map:mList){
                	Log.e("lbb", "-----HashMap--"+map.id);
                }
			}  
		});  */
    }

    @Override
    protected void initData() {
        LoginData user = DBManager.getInstance(mContext).getUserMessage();


    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.relat_name, R.id.relat_sex, R.id.relat_instruction
            , R.id.relat_set_phone, R.id.relat_set_address, R.id.relat_set_pic})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.relat_set_pic:
                startActivity(MineDataEditingPicActivity.class);
                break;
            case R.id.relat_set_address:
                startActivity(AddressManagementActivity.class);
                break;
            case R.id.relat_name:
                startActivity(MineSetingNameActivity.class);
                break;
            case R.id.relat_sex:
                //startActivity(MineSetingSexActivity.class);
                if (relat_sex.isEnabled()) {
                    relat_sex.setEnabled(false);
                    if (sex) {
                        sex = false;
                        relat_sex.setImageDrawable(getResources().getDrawable(R.drawable.wode_81));
                    } else {
                        relat_sex.setImageDrawable(getResources().getDrawable(R.drawable.wode_83));
                        sex = true;
                    }
                    String field1 = (sex ? "男" : "女");
                    LoginData user = DBManager.getInstance(mContext).getUserMessage();
                    user.jsonData = null;
                    user.field1 = field1;
                    user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                    DBManager.getInstance(mContext).insert(user);
                    new UpdSexTask(mContext, user.userId, field1, new Back() {

                        @Override
                        public void success(Object object, String msg) {

                            relat_sex.setEnabled(true);
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {

                            relat_sex.setEnabled(true);
                        }
                    }).start();
                }

                break;
            case R.id.relat_instruction:
                startActivity(MineSetingInstructionActivity.class);
                break;
            case R.id.relat_set_phone:
                startActivity(MineSetingPhoneActivity.class);
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
        MobclickAgent.onPageStart("MineDataEditingActivity");
        super.onResume();
        user = DBManager.getInstance(mContext).getUserMessage();
        if (user != null && !TextUtils.isEmpty(user.nickName)) {
            tv_name.setText(Util.setLongStringPoint(user.nickName));
            /* if (user.nickName.length()>10){
                tv_name.setText(user.nickName+"...");
            }else{
                tv_name.setText(user.nickName);
            }*/
        }
        if (user != null && !TextUtils.isEmpty(user.field2)) {
            tv_instruction.setText(user.field2);
        }
        if (user != null && !TextUtils.isEmpty(user.phone)) {
            tv_phone.setText(user.phone);
        }
        String json = SharedPreUtil.getStringValue(mContext, ACTION_GetAddressList, "");
        if (!TextUtils.isEmpty(json)) {
            Object mObject = new JsonBuild().getData(GetAddressList.class, json);
            if (mObject != null && mObject instanceof GetAddressList) {
                GetAddressList mData = (GetAddressList) mObject;
                if (mData != null && mData.data != null && mData.data.size() >= 0) {
                    tv_addressNum.setText(mData.data.size() + "个");
                }
            }
        }
        new GetAddressListTask(mContext, true, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetAddressList) {
                    GetAddressList address = (GetAddressList) object;
                    if (address.data != null && address.data.size() >= 0) {
                        tv_addressNum.setText(address.data.size() + "个");
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
                            tv_addressNum.setText(mData.data.size() + "个");
                        }
                    }
                }

            }
        }).start();
        new QueryUserIconTask(mContext, false, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof QueryUserIcon) {
                    QueryUserIcon mQueryUserIcon = (QueryUserIcon) object;
                    if (mQueryUserIcon.data != null) {
                        try {
                            mList.clear();
                            mList.addAll(mQueryUserIcon.data);
                            tv_picNum.setText("" + mList.size() + "个");
                            Collections.sort(mList);

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
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
    protected void onPause() {
        MobclickAgent.onPageEnd("MineDataEditingActivity");
        super.onPause();
    }


}
