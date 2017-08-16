package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.model.home.Gift;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetGiftTask;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.sdk.model.returns.GetGift;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickSingleListener;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;

import android.content.ClipData;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.home.GameGiftsAdapter.java
 * @Author lbb
 * @Date 2016年8月23日 下午2:09:37
 * @Company 
 */
public class GameGiftsAdapter extends BaseAdapter {

	private List<Gift> mList=new ArrayList<Gift>();
	private Context mContext;
	protected LayoutInflater mInflater;
	public GameGiftsAdapter(Context mContext,List<Gift> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
		
	}

	public List<Gift> getmList() {
		return mList;
	}

	public void setmList(List<Gift> mList) {
		this.mList = mList;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_listview_home_game_gift, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Gift mGift=(Gift) getItem(position);
		holder.setData(mGift);
		holder.tv_get.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				final MyAlertDailog dialog = new MyAlertDailog(
						mContext);
				Resources res = mContext.getResources();
				dialog.setTitle("确认领取",Gravity.CENTER_VERTICAL|Gravity.LEFT);
				dialog.setContent1("您将免费《"+mGift.giftName+"》新手礼包，确定要领取吗？"
						,Gravity.CENTER_VERTICAL|Gravity.LEFT);
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
						final LoginData user=DBManager.getInstance(mContext).getUserMessage();
						new GetGiftTask(mContext, user.userId, mGift.gameId, mGift.giftId, new Back() {
							
							@Override
							public void success(Object object, String msg) {
								
								if(object!=null && object instanceof GetGift){
									final GetGift mGetGift=(GetGift) object;
									if(mGetGift.data!=null){
										mList.get(position).amount=mGetGift.data.amount;
										mList.get(position).field1=user.userId;
										notifyDataSetChanged();
										
										final MyAlertDailog dialog = new MyAlertDailog(mContext);
										dialog.setTitle(""+mGetGift.data.title);
										dialog.setContent1(""+mGetGift.data.content+"\n\n",Gravity.CENTER_VERTICAL|Gravity.LEFT);
										dialog.setContentT(""+mGetGift.data.getCode+"\n",R.color.gh_shenhui_color);
										dialog.setSingle("复制卡号");
										if (dialog != null && !dialog.isShowing()) {
											dialog.show();
										}
										dialog.setOnClickSingleListener(new onClickSingleListener() {
											
											@Override
											public void onClickSingle() {
												dialog.dismiss();
												if(SystemUtil.getSDKVersionNumber() >= 11){
													android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);  
													clipboardManager.setText(mGetGift.data.getCode);  
												}else{
													// 得到剪贴板管理器
													android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);  
													clipboardManager.setPrimaryClip(ClipData.newPlainText(null, mGetGift.data.getCode));
												}
												ToastUtil.showToast(mContext, "成功复制到剪切板");
											}
										});
									}else{
										ToastUtil.showToast(mContext, msg);
									}
								}else{
									ToastUtil.showToast(mContext, msg);
								}
								
								
							}
							
							@Override
							public void fail(String status, String msg, Object object) {
								ToastUtil.showToast(mContext, msg);
								
							}
						}).start();
					}
				});
				/*((BaseActivity)mContext).startActivity(GiftsDetailActivity.class);*/
			}
		});
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.iv_item) XCRoundImageViewByXfermode iv_item;
		public @BindView(R.id.tv_item) TextView tv_item;
		
		public @BindView(R.id.item_mLinearMsg) LinearLayout item_mLinearMsg;
		public @BindView(R.id.tv_type) TextView tv_type;
		public @BindView(R.id.tv_num) TextView tv_num;
		
		public @BindView(R.id.tv_msg) TextView tv_msg;
		public @BindView(R.id.tv_get) TextView tv_get;

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(Gift mGift){
			if(mGift!=null){
				iv_item.setType(XCRoundImageViewByXfermode.TYPE_ROUND);
				iv_item.setRoundBorderRadius(23);
				Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+mGift.fileAskPath+mGift.logo)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
				.error(R.drawable.picture_defeated)//加载失败时显示的图片
				//.crossFade()
				.into(iv_item);
				tv_item.setText(mGift.giftName);
				if(!TextUtils.isEmpty(mGift.amount)){
					tv_num.setText(""+mGift.amount);
				}else{
					tv_num.setText("0");
				}
				
				tv_msg.setText(mGift.content);
				if(TextUtils.isEmpty(mGift.field1)){
					tv_get.setEnabled(true);
					tv_get.setText("领取");
				}else{
					tv_get.setEnabled(false);
					tv_get.setText("已领取");
				}
			}
		}
	}
	

}
