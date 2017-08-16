package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.DzGamePlTask;
import com.game.helper.sdk.model.returns.DzGamePl;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.QueryGamePl.GamePl;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.widget.CircleImageView;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.home.CommentAdapter.java
 * @Author lbb
 * @Date 2016年8月23日 下午2:50:14
 * @Company 
 */
public class CommentAdapter extends BaseAdapter {

	private List<GamePl> mList=new ArrayList<GamePl>();
	private Context mContext;
	protected LayoutInflater mInflater;
	public CommentAdapter(Context mContext,List<GamePl> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
		
	}

	public List<GamePl> getmList() {
		return mList;
	}

	public void setmList(List<GamePl> mList) {
		
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
			convertView = mInflater.inflate(R.layout.item_listview_home_comment, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final GamePl mGamePl=(GamePl) getItem(position);
		holder.setData(mGamePl,position);
		holder.iv_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("userId", mGamePl.userId);
				((BaseActivity)mContext).startActivity(PersonalHomepageActivity.class,bundle);
				
			}
		});
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.iv_item) CircleImageView iv_item;
		public @BindView(R.id.tv_item) TextView tv_item;
		
		public @BindView(R.id.tv_msg) TextView tv_msg;
		public @BindView(R.id.tv_time) TextView tv_time;
		public @BindView(R.id.tv_num) TextView tv_num;
		
		public @BindView(R.id.iv_zan) ImageView iv_zan;
		
		public @BindView(R.id.mLinear_zan) LinearLayout mLinear_zan;
		
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(final GamePl mGamePl,final int position){
			if(mGamePl!=null){
				
				if(!TextUtils.isEmpty(mGamePl.userIcon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+mGamePl.fileAskPath+mGamePl.userIcon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(iv_item);
				}else{
					iv_item.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
				}
				
                
				tv_item.setText(mGamePl.userName);
				tv_msg.setText(mGamePl.content);
				tv_time.setText(mGamePl.createTimeString);
				tv_num.setText("("+mGamePl.dzNum+")");
				mLinear_zan.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						LoginData user=DBManager.getInstance(mContext).getUserMessage();
						new DzGamePlTask(mContext, user.userId, mGamePl.id, new Back() {
							
							@Override
							public void success(Object object, String msg) {
								ToastUtil.showToast(mContext, "点赞成功");
								if(object!=null &&object instanceof  DzGamePl ){
									DzGamePl mDzGamePl=(DzGamePl) object;
									if(!TextUtils.isEmpty(mDzGamePl.data)){
										mList.get(position).dzNum=mDzGamePl.data;
										notifyDataSetChanged();
									}
								}
								
							}
							
							@Override
							public void fail(String status, String msg, Object object) {
								ToastUtil.showToast(mContext, msg);
								
							}
						}).start();
						
					}
				});
				/*tv_num.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						LoginData user=DBManager.getInstance(mContext).getUserMessage();
						new DzGamePlTask(mContext, user.userId, mGamePl.id, new Back() {
							
							@Override
							public void success(Object object, String msg) {
								if(object!=null &&object instanceof  DzGamePl ){
									DzGamePl mDzGamePl=(DzGamePl) object;
									if(!TextUtils.isEmpty(mDzGamePl.data)){
										mList.get(position).dzNum=mDzGamePl.data;
										notifyDataSetChanged();
									}
								}
								
							}
							
							@Override
							public void fail(String status, String msg, Object object) {
								ToastUtil.showToast(mContext, msg);
								
							}
						}).start();
						
					}
				});*/
			}
		}
	}
	

}
