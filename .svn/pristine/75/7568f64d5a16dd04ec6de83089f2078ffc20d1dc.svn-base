package com.game.helper.adapter.community;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetInfoList.GetInfoListData;
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
 * @Path com.game.helper.adapter.community.ConsultingAdapter.java
 * @Author lbb
 * @Date 2016年8月25日 下午4:59:37
 * @Company 
 */
public class ConsultingAdapter extends BaseAdapter {

	private Context mContext;
	protected LayoutInflater mInflater;
	private List<GetInfoListData> data=new ArrayList<GetInfoListData>();
	public ConsultingAdapter(Context mContext,List<GetInfoListData> data) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.data=data;
	}

	public List<GetInfoListData> getData() {
		return data;
	}

	public void setData(List<GetInfoListData> data) {
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
			convertView = mInflater.inflate(R.layout.item_listview_community_consulting_activity, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setData(data.get(position));
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.iv_item) ImageView iv_item;
		public @BindView(R.id.tv_title) TextView tv_title;
		
		public @BindView(R.id.tv_msg) TextView tv_msg;


		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(GetInfoListData mGetInfoListData){
			if(mGetInfoListData!=null){
				Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+mGetInfoListData.fileAskPath+mGetInfoListData.contentImage)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
				.error(R.drawable.picture_defeated)//加载失败时显示的图片
				//.crossFade()
				.into(iv_item);
				tv_title.setText(mGetInfoListData.contentTitle);
				tv_msg.setText(mGetInfoListData.contentAbstract);
			}
		}
	}
	

}