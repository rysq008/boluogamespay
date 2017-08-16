package com.game.helper.activity.community;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.community.DeleteSociatyMembersAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.BatchExitTask;
import com.game.helper.net.task.GetGuildUserTask;
import com.game.helper.sdk.model.returns.GetGuildUser;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.GetGuildUser.GetGuildUserData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.umeng.analytics.MobclickAgent;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description  删除公会成员（会长特权）
 * @Path com.game.helper.activity.community.EditSociatyMembersActivity.java
 * @Author lbb
 * @Date 2016年8月30日 下午12:21:10
 * @Company
 */
public class DeleteSociatyMembersActivity extends BaseActivity{
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
    @BindView(R.id.LinearLayout1)
    LinearLayout LinearLayout1;
    @BindView(R.id.lv_searchSociatyMembers)
    ListView lv_searchSociatyMembers;
    /*
	@BindView(R.id.LinearLayout1) LinearLayout  LinearLayout1;
	@BindView(R.id.lv_searchSociatyMembers) ListView lv_searchSociatyMembers;*/
	private DeleteSociatyMembersAdapter mDeleteSociatyMembersAdapter;
	private List<GetGuildUserData> mDatas= new ArrayList<GetGuildUserData>();
	private String guildId,guild_userId;
	private List<String> userList=new ArrayList<String>();

	LoginData user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_community_sociaty_members);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initView() {
		guild_userId=getIntent().getStringExtra("guild_userId");
		user=DBManager.getInstance(mContext).getUserMessage();
		topTitle.setText("公会成员");
		topLeftBack.setVisibility(View.VISIBLE);
		if(user!=null&&!TextUtils.isEmpty(user.userId)&&!TextUtils.isEmpty(guild_userId)&&user.userId.equals(guild_userId)){
            topRight.setVisibility(View.VISIBLE);
            topRight.setText("删除");
		}else{
            topRight.setVisibility(View.GONE);
		}


		mDeleteSociatyMembersAdapter=new DeleteSociatyMembersAdapter(mContext,mDatas);
		lv_searchSociatyMembers.setAdapter(mDeleteSociatyMembersAdapter);
		lv_searchSociatyMembers.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Map<String, Object> map=mDeleteSociatyMembersAdapter.getmDatas().get(position);
				if((Integer)map.get("sel")==1){
					map.put("sel",0);
				}else{
					map.put("sel",1);
				}
				mDeleteSociatyMembersAdapter.notifyDataSetChanged();
				userList.clear();

			}
		});
	}

	@Override
	protected void initData() {
		guildId=getIntent().getStringExtra("guildId");
		new GetGuildUserTask(mContext, guildId, "", new Back() {

			@Override
			public void success(Object object, String msg) {
				// TODO Auto-generated method stub
				if(object!=null && object instanceof GetGuildUser){
					GetGuildUser mGetGuildUser=(GetGuildUser) object;
					if(mGetGuildUser.data!=null&&mGetGuildUser.data.size()>=0){
						mDatas.clear();
						mDatas.addAll(mGetGuildUser.data);
						mDeleteSociatyMembersAdapter.setmDatas(mDatas);
						SharedPreUtil.putStringValue(mContext, ACTION_GetGuildUser+"_Delete"+guildId, new JsonBuild().setModel(object).getJson1());
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				String json=SharedPreUtil.getStringValue(mContext, ACTION_GetGuildUser+"_Delete"+guildId,"");
				if (!TextUtils.isEmpty(json)) {
					Object mObject =new JsonBuild().getData(GetGuildUser.class, json);
					if(mObject !=null&& mObject instanceof GetGuildUser){
						GetGuildUser mData=(GetGuildUser) mObject;
						if(mData!=null &&mData.data!=null&&mData.data.size()>=0){
							mDatas.clear();
							mDatas.addAll(mData.data);
							mDeleteSociatyMembersAdapter.setmDatas(mDatas);
						}
					}
				}
			}
		}).start();


	}
	@Override
	@OnClick({R.id.top_left_layout,R.id.top_right,R.id.LinearLayout1})
	public void onClick(View v) {
		switch ( v.getId()) {
		case R.id.top_left_layout:
			finish1();
			break;
		case R.id.LinearLayout1:
			startActivity(SearchSociatyMembersActivity.class);
			break;
		case R.id.top_right:
			//删除
			final MyAlertDailog dialog = new MyAlertDailog(
					mContext);
			Resources res = mContext.getResources();
			dialog.setTitle("提示",Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
			dialog.setContent1("确定要删除选中人员吗？"
					,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
			dialog.setLeftButtonText("取消");
			dialog.setRightButtonText("确定");
			if (dialog != null && !dialog.isShowing()) {
				dialog.show();
			}
			dialog.setOnClickLeftListener(new onClickLeftListener() {
				@Override
				public void onClickLeft() {
					dialog.dismiss();

				}
			});
			dialog.setOnClickRightListener(new onClickRightListener() {

				@Override
				public void onClickRight() {
					dialog.dismiss();
					//如果是会长
						GetGuildUserData line;
						for(Map<String, Object> md :mDeleteSociatyMembersAdapter.getmDatas()){
							if((Integer)md.get("sel")==1){
								line =(GetGuildUserData) md.get("GetGuildUserData");
								userList.add(line.userId);
							}
						}
						if(userList.size()>0){
							new BatchExitTask(mContext, userList, new Back() {

								@Override
								public void success(Object object, String msg) {
									// TODO Auto-generated method stub

									new GetGuildUserTask(mContext, guildId, "", new Back() {

										@Override
										public void success(Object object, String msg) {
											// TODO Auto-generated method stub
											if(object!=null && object instanceof GetGuildUser){
												GetGuildUser mGetGuildUser=(GetGuildUser) object;
												if(mGetGuildUser.data!=null&&mGetGuildUser.data.size()>=0){
													mDatas.clear();
													mDatas.addAll(mGetGuildUser.data);
													mDeleteSociatyMembersAdapter.setmDatas(mDatas);
													ToastUtil.showToast(mContext, "选中人员已推出本公会");
													SharedPreUtil.putStringValue(mContext, ACTION_GetGuildUser+"_Delete"+guildId, new JsonBuild().setModel(object).getJson1());

												}
											}
										}

										@Override
										public void fail(String status, String msg, Object object) {

										}
									}).start();
								}

								@Override
								public void fail(String status, String msg, Object object) {
									// TODO Auto-generated method stub

								}
							}).start();
						}else{
							ToastUtil.showToast(mContext, "请选择您要删除的公会成员");
						}

				}
			});



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
		MobclickAgent.onPageEnd("DeleteSociatyMembersActivity");
		super.onPause();
	}

	@Override
	protected void onResume() {
		MobclickAgent.onPageStart("DeleteSociatyMembersActivity");
		super.onResume();
	}
}
