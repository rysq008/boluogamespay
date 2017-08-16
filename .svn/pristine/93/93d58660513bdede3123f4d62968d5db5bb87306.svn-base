package com.game.helper.adapter.mine;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetMyCollection.MyCollection;

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
 * @Path com.game.helper.adapter.mine.MineCollectAdapter.java
 * @Author lbb
 * @Date 2016年8月19日 下午3:12:36
 * @Company 
 */
public class MineCollectAdapter extends BaseAdapter{

	private List<MyCollection> mList=new ArrayList<MyCollection>();
	private Context mContext;
	protected LayoutInflater mInflater;
	public MineCollectAdapter(Context mContext,List<MyCollection> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}

	public List<MyCollection> getmList() {
		return mList;
	}

	public void setmList(List<MyCollection> mList) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_listview_mine_collect, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyCollection mMineCollect=(MyCollection) getItem(position);
		holder.setData(mMineCollect);
		return convertView;
		
	}
	class ViewHolder {
		public @BindView(R.id.title) TextView title;
		public @BindView(R.id.msg) TextView msg;
		public @BindView(R.id.icon) ImageView icon;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(MyCollection mMineCollect){
			if(mMineCollect!=null){
				Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+mMineCollect.fileAskPath+mMineCollect.img)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
				.error(R.drawable.picture_defeated)//加载失败时显示的图片
				//.crossFade()
				.into(icon);
				
				title.setText(mMineCollect.field1);
				//msg.setText(mMineCollect.content);
			}
		}
		
	}
}
