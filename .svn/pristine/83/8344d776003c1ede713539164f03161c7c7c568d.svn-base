package com.game.helper.adapter.community;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetGuildGameList.GuildGame;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description 大家都在玩的游戏
 * @Path com.game.helper.adapter.community.EveryonePlayingAdapter.java
 * @Author lbb
 * @Date 2016年8月30日 上午10:41:27
 * @Company 
 */
public class EveryonePlayingAdapter extends BaseAdapter {

	private Context mContext;
	protected LayoutInflater mInflater;
	public List<GuildGame> gameList=new ArrayList<GuildGame>();
	public EveryonePlayingAdapter(Context mContext,List<GuildGame> gameList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.gameList=gameList;
	}

	public List<GuildGame> getGameList() {
		return gameList;
	}


	public void setGameList(List<GuildGame> gameList) {
		this.gameList = gameList;
		notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		return gameList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return gameList.get(position);
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
			convertView = mInflater.inflate(R.layout.item_listview_community_everyoneplaying, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setData(gameList.get(position));
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.iv_icon) ImageView iv_icon;
		public @BindView(R.id.tv_title) TextView tv_title;
		
		
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(GuildGame mGuildGame){
			if(mGuildGame!=null){
				Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+mGuildGame.fileAskPath+mGuildGame.logo)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
				.error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
				//.crossFade()
				.into(iv_icon);
				tv_title.setText(mGuildGame.gameName);
			}
		}
	}
	

}
