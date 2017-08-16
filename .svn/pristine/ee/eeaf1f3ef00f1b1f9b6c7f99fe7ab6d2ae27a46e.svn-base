package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.sdk.model.returns.QueryGameCutPl.GameCutPl;
import com.game.helper.view.widget.CircleImageView;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.home.StrategyCommentsAdapter.java
 * @Author lbb
 * @Date 2016年10月12日 下午2:53:29
 * @Company 
 */
public class StrategyCommentsAdapter extends BaseAdapter{
	private Context mContext;
	protected LayoutInflater mInflater;
	protected List<GameCutPl> commentList=new ArrayList<GameCutPl>();

	public StrategyCommentsAdapter(Context mContext, List<GameCutPl> commentList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.commentList=commentList;
	}

	public List<GameCutPl> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<GameCutPl> commentList) {
		this.commentList = commentList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return commentList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return commentList.get(position);
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
			convertView = mInflater.inflate(R.layout.item_listview_home_strategy_detail_comments, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final GameCutPl mComment=(GameCutPl) getItem(position);
		holder.setData(mComment);
		holder.imageView_pic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle bundle=new Bundle();
				bundle.putString("userId", mComment.userId);
				((BaseActivity)mContext).startActivity(PersonalHomepageActivity.class,bundle);
			}
		});
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.imageView_pic) CircleImageView imageView_pic;

		public @BindView(R.id.tv_item) TextView tv_item;
		public @BindView(R.id.tv_time) TextView tv_time;

		public @BindView(R.id.tv_msg) TextView tv_msg;


		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(GameCutPl mCommentData){
			if(mCommentData!=null){
				if(!TextUtils.isEmpty(mCommentData.userIcon)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+mCommentData.fileAskPath+mCommentData.userIcon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(imageView_pic);
				}else{
					imageView_pic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
				}
				
				tv_item.setText(mCommentData.userName);
				tv_time.setText(mCommentData.createTimeString);
				tv_msg.setText(mCommentData.content);
			}
		}
	}


}

