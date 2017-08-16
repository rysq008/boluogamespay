package com.game.helper.adapter.mine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.model.home.RecommendBoutique;
import com.game.helper.sdk.model.returns.GetShareResult.ShareResult;
import com.game.helper.view.widget.CircleImageView;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.mine.MineInvitedFriendsAdapter.java
 * @Author lbb
 * @Date 2016年8月26日 下午7:17:18
 * @Company 
 */
public class MineInvitedFriendsAdapter extends BaseAdapter {

	private Context mContext;
	protected LayoutInflater mInflater;
	protected List<ShareResult> data=new ArrayList<ShareResult>();
	public MineInvitedFriendsAdapter(Context mContext,List<ShareResult> data) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.data = data;
	}

	public List<ShareResult> getData() {
		return data;
	}

	public void setData(List<ShareResult> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public ShareResult getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
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
			convertView = mInflater.inflate(R.layout.item_listview_mine_invited_friends, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setData(getItem(position));
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.imageView_pic) CircleImageView imageView_pic;
		
		public @BindView(R.id.tv_userName) TextView tv_userName;
		public @BindView(R.id.tv_Num) TextView tv_Num;

		public @BindView(R.id.tv_userID) TextView tv_userID;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(ShareResult mShareResult){
			if(mShareResult!=null){
				if(!TextUtils.isEmpty(mShareResult.icon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+mShareResult.baseAcessPath+mShareResult.icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(imageView_pic);
				}else{
					imageView_pic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
				}
				
				tv_userName.setText(mShareResult.nickName);
				tv_userID.setText(mShareResult.userId);
				try {
					if(!TextUtils.isEmpty(mShareResult.totalSum)){
						BigDecimal bd = new BigDecimal(mShareResult.totalSum);
						bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
						tv_Num.setText(bd.toString());
					}else{
						tv_Num.setText("0");
					}
				} catch (Exception e) {
					e.printStackTrace();
					tv_Num.setText(mShareResult.totalSum);
				}
				
				
			}
		}
	}

}