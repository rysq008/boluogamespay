package com.game.helper.adapter.community;

import java.util.ArrayList;
import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.download.bean.AppContent;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.community.TAPlayingLVAdapter.java
 * @Author lbb
 * @Date 2016年11月10日 下午8:40:04
 * @Company 
 */
public class TAPlayingLVAdapter extends BaseAdapter {

	private List<AppContent> mList=new ArrayList<AppContent>();
	private Context mContext;
	protected LayoutInflater mInflater;
	public TAPlayingLVAdapter(Context mContext,List<AppContent> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}

	public List<AppContent> getmList() {
		return mList;
	}

	public void setmList(List<AppContent> mList) {
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
			convertView = mInflater.inflate(R.layout.item_listview_home_special_game, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final AppContent mGuildGame=(AppContent) getItem(position);
		holder.setData(mGuildGame);
		holder.tv_download.setVisibility(View.GONE);
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.iv_item) XCRoundImageViewByXfermode iv_item;
		public @BindView(R.id.mLinear_dz) LinearLayout mLinear_dz;
		public @BindView(R.id.tv_dz) TextView tv_dz;

		public @BindView(R.id.tv_item) TextView tv_item;
		public @BindView(R.id.tv_first) TextView tv_first;
		public @BindView(R.id.tv_gift) TextView tv_gift;

		public @BindView(R.id.item_mLinearMsg) LinearLayout item_mLinearMsg;
		public @BindView(R.id.tv_type) TextView tv_type;
		public @BindView(R.id.tv_datasize) TextView tv_datasize;

		public @BindView(R.id.tv_msg) TextView tv_msg;
		public @BindView(R.id.pb_update_progress) ProgressBar pb_update_progress;
		public @BindView(R.id.tv_download) TextView tv_download;

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(AppContent mGuildGame){
			if(mGuildGame!=null){
				iv_item.setType(XCRoundImageViewByXfermode.TYPE_ROUND);
				iv_item.setRoundBorderRadius(23);
				mLinear_dz.setVisibility(View.GONE);
				if(!TextUtils.isEmpty(mGuildGame.tagName)){
					tv_gift.setText(mGuildGame.tagName);
					tv_gift.setVisibility(View.VISIBLE);
				}
				Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+mGuildGame.fileAskPath+mGuildGame.logo)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
				.error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
				//.crossFade()
				.into(iv_item);
				tv_item.setText(mGuildGame.gameName);
				
				tv_type.setText(mGuildGame.platName);
				tv_datasize.setText(mGuildGame.fileSize+"M");
				tv_msg.setVisibility(View.INVISIBLE);
				
			}
		}
		
	}

}