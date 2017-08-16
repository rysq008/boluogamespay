package com.game.helper.adapter.home;


import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.QueryTheme.ThemeGame;
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
 * @Description
 * @Path .GameThemeAdapter.java
 * @Author lbb
 * @Date 2016年8月24日 下午1:59:33
 * @Company 
 */
public class GameThemeAdapter extends BaseAdapter{
	private Context mContext;
	protected LayoutInflater mInflater;
	protected List<ThemeGame> data=new ArrayList<ThemeGame>();
	
	public GameThemeAdapter(Context mContext,List<ThemeGame> data) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.data=data;
	}

	public List<ThemeGame> getData() {
		return data;
	}

	public void setData(List<ThemeGame> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.item_listview_home_theme_game, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ThemeGame mThemeGame=(ThemeGame) getItem(position);
		holder.setData(mThemeGame);
		
		return convertView;
	}

	class ViewHolder {
		
		public @BindView(R.id.iv_imge) ImageView iv_imge;
		public @BindView(R.id.tv_title) TextView tv_title;
		public @BindView(R.id.tv_msg) TextView tv_msg;
		
		
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(ThemeGame mThemeGame){
			if(mThemeGame!=null){
				Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+mThemeGame.fileAskPath+mThemeGame.pic)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
				.error(R.drawable.picture_defeated)//加载失败时显示的图片
				//.crossFade()
				.into(iv_imge);
				tv_msg.setText(mThemeGame.remark);
				tv_title.setText(mThemeGame.themeName);
			}
		}
	}
	

}
